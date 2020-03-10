package anikolaev.postboy.view.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_request.*
import anikolaev.postboy.R
import anikolaev.postboy.databinding.FragmentRequestBinding
import anikolaev.postboy.model.db.entities.RequestEntity
import anikolaev.postboy.util.REST_MENU_ITEM
import anikolaev.postboy.view.activities.MainActivity
import anikolaev.postboy.view.adapter.HeaderRecyclerViewAdapter
import anikolaev.postboy.view.adapter.ParameterRecyclerViewAdapter
import anikolaev.postboy.view.base.BaseFragment
import anikolaev.postboy.view.interfaces.IClickHeadersPairModel
import anikolaev.postboy.view.interfaces.IClickParametersPairModel
import anikolaev.postboy.view.models.Pairs
import anikolaev.postboy.viewmodel.MainViewModel
import okhttp3.HttpUrl


class RequestFragment : BaseFragment<MainViewModel, FragmentRequestBinding>(), IClickHeadersPairModel,
    IClickParametersPairModel {

    val TAG = this::class.java.simpleName

    override fun getMenuId(): Int = REST_MENU_ITEM

    override fun isVisibleToolbar(): Boolean = true

    override fun getToolbarTitle(): String = getString(R.string.app_name)

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_request

    override fun onViewModelReady() {
        initialRecyclerViewAdapters()

        viewModel.changeHistoryModel.observe(this, Observer {
            setModelRequest(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageButtonAddHeader.setOnClickListener {
            viewModel.addHeaderItem(Pairs("", ""))
        }

        imageButtonAddParameters.setOnClickListener {
            viewModel.addParameterItem(Pairs("", ""))
        }

        editTextUrl.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.toString().startsWith("http://")) {
                    spinnerHttp.setSelection(1)
                    s.delete(0, 7)
                } else if (s.toString().startsWith("https://")) {
                    spinnerHttp.setSelection(0)
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

        spinnerBodyType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.spinnerBodyType.set(spinnerBodyType.selectedItem.toString())
            }
        }

        buttonSend.setOnClickListener {
            viewModel.onClickSendRequest()
        }

        (activity as MainActivity).nav_view.menu.getItem(0).isChecked = true
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    override fun onItemHeadersClick(model: Pairs) {
        viewModel.deleteHeaderItem(model)
    }

    override fun onItemParametersClick(model: Pairs) {
        viewModel.deleteParameterItem(model)
    }

    private fun initialRecyclerViewAdapters() {
        val adapterHeader = HeaderRecyclerViewAdapter(this)
        includeFieldHeaders.layoutManager = LinearLayoutManager(activity)
        includeFieldHeaders.adapter = adapterHeader
        viewModel.headersListAdapter.observe(this, Observer {
            adapterHeader.update(it)
        })

        val adapterParameter = ParameterRecyclerViewAdapter(this)
        includeFieldParameters.layoutManager = LinearLayoutManager(activity)
        includeFieldParameters.adapter = adapterParameter
        viewModel.parametersListAdapter.observe(this, Observer {
            adapterParameter.update(it)
        })
    }

    private fun hideKeyboard() {
        val view = activity!!.currentFocus
        if (view != null) {
            val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun setModelRequest(entity: RequestEntity) {

        spinnerMethod.setSelection(resources.getStringArray(R.array.methods_array).indexOf(entity.method))
        spinnerHttp.setSelection(resources.getStringArray(R.array.http_array).indexOf(entity.http))
        spinnerBodyType.setSelection(resources.getStringArray(R.array.body_type_array).indexOf(entity.bodyType))

    }
}
