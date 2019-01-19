package nikolaev.postboy.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_request.*
import nikolaev.postboy.R
import nikolaev.postboy.databinding.FragmentRequestBinding
import nikolaev.postboy.view.activities.MainActivity
import nikolaev.postboy.view.base.BaseFragment
import nikolaev.postboy.viewmodel.MainViewModel
import okhttp3.HttpUrl


class RequestFragment : BaseFragment<MainViewModel, FragmentRequestBinding>() {

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_request

    override fun onViewModelReady() {
        viewModel.oNDeleteHeadersView.observe(this, Observer {
            includeFieldHeaders.removeView(it.parent as View)
        })

        viewModel.oNDeleteParameterView.observe(this, Observer {
            includeFieldParameters.removeView(it.parent as View)
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageButtonAddHeader.setOnClickListener {
            val inflater = LayoutInflater.from(context)
            val rowView = inflater.inflate(R.layout.field_header, null)
            includeFieldHeaders.addView(rowView, includeFieldHeaders.childCount - 1)
        }

        imageButtonAddParameters.setOnClickListener {
            val inflater = LayoutInflater.from(context)
            val rowView = inflater.inflate(R.layout.field_parameters, null)
            includeFieldParameters.addView(rowView, includeFieldParameters.childCount - 1)
        }

        editTextUrl.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.toString().startsWith("http://")) {
                    spinnerHttp.setSelection(0)
                    s.delete(0, 7)
                } else if (s.toString().startsWith("https://")) {
                    spinnerHttp.setSelection(1)
                    s.delete(0, 8)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                buttonSend.isEnabled = s.isNotEmpty() && HttpUrl.parse("http://$s") != null
            }

        })

    }


}
