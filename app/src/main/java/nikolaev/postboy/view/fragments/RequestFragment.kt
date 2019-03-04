package nikolaev.postboy.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_request.*
import nikolaev.postboy.R
import nikolaev.postboy.databinding.FragmentRequestBinding
import nikolaev.postboy.util.inflateOptionView
import nikolaev.postboy.view.activities.MainActivity
import nikolaev.postboy.view.adapter.HeaderRecyclerViewAdapter
import nikolaev.postboy.view.base.BaseFragment
import nikolaev.postboy.view.interfaces.IClickHeaderModel
import nikolaev.postboy.view.models.Pairs
import nikolaev.postboy.viewmodel.MainViewModel
import okhttp3.HttpUrl


class RequestFragment : BaseFragment<MainViewModel, FragmentRequestBinding>(), IClickHeaderModel {

    val TAG = this::class.java.simpleName

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_request

    override fun onViewModelReady() {
        viewModel.oNDeleteHeadersView.observe(this, Observer {
            includeFieldHeaders.removeView(it.parent as View)
        })

        viewModel.oNDeleteParameterView.observe(this, Observer {
            includeFieldParameters.removeView(it.parent as View)
        })

        val adapter = HeaderRecyclerViewAdapter(this)
        includeFieldHeaders.layoutManager = LinearLayoutManager(activity)
        includeFieldHeaders.adapter = adapter

        viewModel.headersListAdapter.observe(this, Observer {
            adapter.update(it)
        })

//        if (viewModel.headersList.isNotEmpty()){
//            for (i in viewModel.headersList){
//                inflateOptionViewWithData(context!!, R.layout.field_header, includeFieldHeaders, i.first, i.second)
//            }
//        }
//        inflateOptionViewWithData(context!!, R.layout.field_header, includeFieldHeaders, viewModel.headersList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageButtonAddHeader.setOnClickListener {
            //            inflateOptionView(context!!, R.layout.field_header, includeFieldHeaders)
            viewModel.addItem(Pairs("", ""))
        }

        imageButtonAddParameters.setOnClickListener {
            inflateOptionView(context!!, R.layout.field_parameters, includeFieldParameters)
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

        spinnerMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.spinnerMethod.set(spinnerMethod.selectedItem.toString())
            }
        }

        spinnerHttp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.spinnerHttp.set(spinnerHttp.selectedItem.toString())
            }
        }

        buttonSend.setOnClickListener {
            //            getHeaders()
//            getParameters()
            viewModel.onClickSendRequest()
        }
    }

    override fun onItemClick(model: Pairs) {
        viewModel.deleteHeaderItem(model)
    }

//    private fun getHeaders() {
//        val header = ArrayList<Pair<String, String>>()
//        val countHeaders = includeFieldHeaders.childCount
//        for (i in 0 until countHeaders) {
//            val tmp = includeFieldHeaders.getChildAt(i)
//            if (tmp.textViewKey.text!!.isNotEmpty() && tmp.textViewValue.text!!.isNotEmpty()) {
//                header.add(Pair(tmp.textViewKey.text.toString(), tmp.textViewValue.text.toString()))
//            }
//        }
//        viewModel.headersList = header
//    }
//
//    private fun getParameters() {
//        val parameters = ArrayList<Pair<String, String>>()
//        val countParameters = includeFieldParameters.childCount
//        for (i in 0 until countParameters) {
//            val tmp = includeFieldParameters.getChildAt(i)
//            if (tmp.textViewKey.text!!.isNotEmpty() && tmp.textViewValue.text!!.isNotEmpty()) {
//                parameters.add(Pair(tmp.textViewKey.text.toString(), tmp.textViewValue.text.toString()))
//            }
//        }
//        viewModel.parametersList = parameters
//    }


}
