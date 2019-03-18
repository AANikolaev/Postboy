package nikolaev.postboy.viewmodel

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import nikolaev.postboy.R
import nikolaev.postboy.model.utils.combineUrl
import nikolaev.postboy.util.*
import nikolaev.postboy.view.base.BaseViewModel
import nikolaev.postboy.view.models.Pairs
import org.json.JSONException
import java.util.*

class MainViewModel(application: Application) : BaseViewModel(application) {

    val TAG = this::class.java.simpleName

    val spinnerMethod = ObservableField<String>()
    val spinnerHttp = ObservableField<String>()
    val textUrl = ObservableField<String>()
    val textBody = ObservableField<String>()

    private var headersList = ArrayList<Pairs>()
    private var parametersList = ArrayList<Pairs>()

    var linkPreview = String()

    private var headersArrayList = ArrayList<Pairs>()
    var headersListAdapter = MutableLiveData<ArrayList<Pairs>>().apply {
        postValue(headersArrayList)
    }

    private var parametersArrayList = ArrayList<Pairs>()
    var parametersListAdapter = MutableLiveData<ArrayList<Pairs>>().apply {
        postValue(parametersArrayList)
    }

    val responseCharSequence = MutableLiveData<List<CharSequence>>()

    val responseCharSequence1 = ArrayList<CharSequence>()

    val progressDialogEvent = MutableLiveData<ProgressDialogModel>()
    val errorDialogEvent = MutableLiveData<Event<ErrorDialogModel>>()

    val nextFragment = MutableLiveData<Int>()


    fun addHeaderItem(item: Pairs) {
        headersArrayList.add(item)
        headersListAdapter.postValue(headersArrayList)
    }

    fun deleteHeaderItem(item: Pairs) {
        headersArrayList.remove(item)
        headersListAdapter.postValue(headersArrayList)
    }

    fun addParameterItem(item: Pairs) {
        parametersArrayList.add(item)
        parametersListAdapter.postValue(parametersArrayList)
    }

    fun deleteParameterItem(item: Pairs) {
        parametersArrayList.remove(item)
        parametersListAdapter.postValue(parametersArrayList)
    }

    fun onClickSendRequest() {
        progressDialogEvent.postValue(
            ProgressDialogModel(
                true,
                getString(R.string.pre_loader_description_text_default)
            )
        )

        Log.d("+", spinnerMethod.get())
        Log.d("+", spinnerHttp.get())
        Log.d("+", textUrl.get())
        Log.d("+", " ${textBody.get()}")

        headersList.addAll(headersListAdapter.value!!)
        parametersList.addAll(parametersListAdapter.value!!)

        when (spinnerMethod.get()) {
            GET_METHOD -> {
                getMethodRequest(combineUrl(spinnerHttp.get() + textUrl.get(), parametersList), headersList)
                linkPreview = combineUrl(spinnerHttp.get() + textUrl.get(), parametersList)
            }
        }


    }

    private fun getMethodRequest(url: String, headers: ArrayList<Pairs>) {
        repository.getApi(url, headers) { response, error ->
            progressDialogEvent.postValue(ProgressDialogModel(isProgressDialogNeeded = false))
            headersList.clear()
            parametersList.clear()

            if (response != "") {
                val responseLinkedList = LinkedList<String>()
                val texts: List<CharSequence>
                responseLinkedList.add(response)
                val text = TextUtils.join("\n", responseLinkedList)
                texts = try {
                    val jsonObject = MyJSONObject(text)
                    jsonObject.getCharSequences(2)
                } catch (e: JSONException) {
                    try {
                        val jsonArray = MyJSONArray(text)
                        jsonArray.getCharSequences(2)
                    } catch (e1: JSONException) {
                        responseLinkedList
                    }
                }
                responseCharSequence1.clear()
                responseCharSequence1.addAll(texts)

                nextFragment.postValue(R.id.tabRootFragment)
            } else {
                errorDialogEvent.postValue(Event(ErrorDialogModel(errorMessage = error)))
            }
        }
    }
}