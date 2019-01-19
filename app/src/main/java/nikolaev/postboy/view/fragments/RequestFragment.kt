package nikolaev.postboy.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_request.*
import nikolaev.postboy.R
import nikolaev.postboy.databinding.FragmentRequestBinding
import nikolaev.postboy.view.activities.MainActivity
import nikolaev.postboy.view.base.BaseFragment
import nikolaev.postboy.viewmodel.MainViewModel


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
            // Add the new row before the add field_header button.
            includeFieldHeaders.addView(rowView, includeFieldHeaders.childCount - 1)
        }

        imageButtonAddParameters.setOnClickListener {
            val inflater = LayoutInflater.from(context)
            val rowView = inflater.inflate(R.layout.field_parameters, null)
            // Add the new row before the add field_header button.
            includeFieldParameters.addView(rowView, includeFieldParameters.childCount - 1)
        }

    }


}
