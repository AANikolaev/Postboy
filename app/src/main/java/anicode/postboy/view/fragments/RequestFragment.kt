package anicode.postboy.view.fragments

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
import anicode.postboy.R
import anicode.postboy.databinding.FragmentRequestBinding
import anicode.postboy.model.db.entities.RequestEntity
import anicode.postboy.util.REST_MENU_ITEM
import anicode.postboy.view.activities.MainActivity
import anicode.postboy.view.adapter.HeaderRecyclerViewAdapter
import anicode.postboy.view.adapter.ParameterRecyclerViewAdapter
import anicode.postboy.view.base.BaseFragment
import anicode.postboy.view.interfaces.IClickHeadersPairModel
import anicode.postboy.view.interfaces.IClickParametersPairModel
import anicode.postboy.view.models.Pairs
import anicode.postboy.viewmodel.MainViewModel
import okhttp3.HttpUrl


class RequestFragment : BaseFragment<MainViewModel, FragmentRequestBinding>(),
    IClickHeadersPairModel,
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

        binding.imageButtonAddHeader.setOnClickListener {
            viewModel.addHeaderItem(Pairs("", ""))
        }

        binding.imageButtonAddParameters.setOnClickListener {
            viewModel.addParameterItem(Pairs("", ""))
        }

        binding.editTextUrl.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.toString().startsWith("http://")) {
                    binding.spinnerHttp.setSelection(1)
                    s.delete(0, 7)
                } else if (s.toString().startsWith("https://")) {
                    binding.spinnerHttp.setSelection(0)
                    s.delete(0, 8)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.buttonSend.isEnabled = s.isNotEmpty() && HttpUrl.parse("http://$s") != null
            }
        })

        binding.spinnerMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.spinnerMethod.set(binding.spinnerMethod.selectedItem.toString())
            }
        }

        binding.spinnerHttp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "onNothingSelected")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.spinnerHttp.set(binding.spinnerHttp.selectedItem.toString())
            }
        }

        binding.spinnerBodyType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d(TAG, "onNothingSelected")
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.spinnerBodyType.set(binding.spinnerBodyType.selectedItem.toString())
                }
            }

        binding.buttonSend.setOnClickListener {
            viewModel.onClickSendRequest()
        }
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
        binding.includeFieldHeaders.layoutManager = LinearLayoutManager(activity)
        binding.includeFieldHeaders.adapter = adapterHeader
        viewModel.headersListAdapter.observe(this, Observer {
            adapterHeader.update(it)
        })

        val adapterParameter = ParameterRecyclerViewAdapter(this)
        binding.includeFieldParameters.layoutManager = LinearLayoutManager(activity)
        binding.includeFieldParameters.adapter = adapterParameter
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

        binding.spinnerMethod.setSelection(
            resources.getStringArray(R.array.methods_array).indexOf(entity.method)
        )
        binding.spinnerHttp.setSelection(
            resources.getStringArray(R.array.http_array).indexOf(entity.http)
        )
        binding.spinnerBodyType.setSelection(
            resources.getStringArray(R.array.body_type_array).indexOf(entity.bodyType)
        )

    }
}
