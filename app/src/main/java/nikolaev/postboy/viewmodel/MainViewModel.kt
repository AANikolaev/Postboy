package nikolaev.postboy.viewmodel

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import nikolaev.postboy.R
import nikolaev.postboy.model.db.entities.RequestEntity
import nikolaev.postboy.model.utils.combineUrl
import nikolaev.postboy.util.*
import nikolaev.postboy.view.base.BaseViewModel
import nikolaev.postboy.view.models.Pairs
import okhttp3.Response
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*


class MainViewModel(application: Application) : BaseViewModel(application) {

    val TAG = this::class.java.simpleName

    /* main fragment */
    val spinnerMethod = ObservableField<String>()
    val spinnerHttp = ObservableField<String>()
    val spinnerBodyType = ObservableField<String>()
    val textUrl = ObservableField<String>()
    val textBody = ObservableField<String>()

    private var headersList = ArrayList<Pairs>()
    private var parametersList = ArrayList<Pairs>()

    /* response */
    var bodyPreview = String()
    val responseCharSequenceRequest = ArrayList<CharSequence>()

    /* info fragment */
    var codeInfoFragment: String = String()
    var codeInfoTime = 0L
    var codeColorText = resources.getColor(R.color.colorBackgroundTitle)
    var headersInfoFragment: CharSequence = String()

    val progressDialogEvent = MutableLiveData<ProgressDialogModel>()
    val errorDialogEvent = MutableLiveData<Event<ErrorDialogModel>>()

    val nextFragment = MutableLiveData<Int>()

    private var headersArrayList = ArrayList<Pairs>()
    var headersListAdapter = MutableLiveData<ArrayList<Pairs>>().apply {
        postValue(headersArrayList)
    }

    private var parametersArrayList = ArrayList<Pairs>()
    var parametersListAdapter = MutableLiveData<ArrayList<Pairs>>().apply {
        postValue(parametersArrayList)
    }

    val historyRequest: MutableLiveData<ArrayList<RequestEntity>> = MutableLiveData()

    init {
        repository.getAllRequests().observe(this, androidx.lifecycle.Observer {
            historyRequest.value = it as ArrayList<RequestEntity>
        })
    }

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

        insertDataToBD()
        headersList.addAll(headersListAdapter.value!!)
        parametersList.addAll(parametersListAdapter.value!!)

        when (spinnerMethod.get()) {
            GET_METHOD -> {
                getMethodRequest(combineUrl(spinnerHttp.get() + textUrl.get(), parametersList), headersList)
            }
            POST_METHOD -> {
                postMethodRequest(
                        combineUrl(spinnerHttp.get() + textUrl.get(), parametersList),
                        headersList, textBody.get().orEmpty(), spinnerBodyType.get()!!
                )
            }
            PUT_METHOD -> {
                putMethodRequest(
                        combineUrl(spinnerHttp.get() + textUrl.get(), parametersList),
                        headersList, textBody.get().orEmpty(), spinnerBodyType.get()!!
                )
            }
            DELETE_METHOD -> {
                deleteMethodRequest(
                        combineUrl(spinnerHttp.get() + textUrl.get(), parametersList),
                        headersList, textBody.get().orEmpty(), spinnerBodyType.get()!!
                )
            }
        }
    }

    private fun getMethodRequest(url: String, headers: ArrayList<Pairs>) {
        val startTime = System.currentTimeMillis()
        repository.getApi(url, headers) { response, error ->
            codeInfoTime = System.currentTimeMillis() - startTime
            progressDialogEvent.postValue(ProgressDialogModel(isProgressDialogNeeded = false))
            clearLists()

            if (error == "") {
                responseToJsonObject(response?.body()!!.string())
                setInfoText(response)
                nextFragment.postValue(R.id.tabRootFragment)
            } else {
                errorDialogEvent.postValue(Event(ErrorDialogModel(errorMessage = error)))
            }
        }
    }

    private fun postMethodRequest(url: String, headers: ArrayList<Pairs>, body: String, bodyType: String) {
        val startTime = System.currentTimeMillis()
        repository.postApi(url, headers, body, bodyType) { response, error ->
            codeInfoTime = System.currentTimeMillis() - startTime
            progressDialogEvent.postValue(ProgressDialogModel(isProgressDialogNeeded = false))
            clearLists()

            if (error == "") {
                responseToJsonObject(response?.body()!!.string())
                setInfoText(response)
                nextFragment.postValue(R.id.tabRootFragment)
            } else {
                errorDialogEvent.postValue(Event(ErrorDialogModel(errorMessage = error)))
            }
        }
    }

    private fun putMethodRequest(url: String, headers: ArrayList<Pairs>, body: String, bodyType: String) {
        val startTime = System.currentTimeMillis()
        repository.putApi(url, headers, body, bodyType) { response, error ->
            progressDialogEvent.postValue(ProgressDialogModel(isProgressDialogNeeded = false))
            codeInfoTime = System.currentTimeMillis() - startTime
            clearLists()

            if (error == "") {
                responseToJsonObject(response?.body()!!.string())
                setInfoText(response)
                nextFragment.postValue(R.id.tabRootFragment)
            } else {
                errorDialogEvent.postValue(Event(ErrorDialogModel(errorMessage = error)))
            }
        }
    }

    private fun deleteMethodRequest(url: String, headers: ArrayList<Pairs>, body: String, bodyType: String) {
        val startTime = System.currentTimeMillis()
        repository.deleteApi(url, headers, body, bodyType) { response, error ->
            codeInfoTime = System.currentTimeMillis() - startTime
            progressDialogEvent.postValue(ProgressDialogModel(isProgressDialogNeeded = false))
            clearLists()

            if (error == "") {
                responseToJsonObject(response?.body()!!.string())
                setInfoText(response)
                nextFragment.postValue(R.id.tabRootFragment)
            } else {
                errorDialogEvent.postValue(Event(ErrorDialogModel(errorMessage = error)))
            }
        }
    }

    private fun responseToJsonObject(response: String) {
        val responseLinkedList = LinkedList<String>()
        val texts: List<CharSequence>
        responseLinkedList.add(response)
        val text = TextUtils.join("\n", responseLinkedList)
        bodyPreview = text
        texts = try {
            val jsonObject = ResponseJSONObject(text)
            jsonObject.getCharSequences(2)
        } catch (e: JSONException) {
            try {
                val jsonArray = ResponseJSONArray(text)
                jsonArray.getCharSequences(2)
            } catch (e1: JSONException) {
                responseLinkedList
            }
        }
        responseCharSequenceRequest.clear()
        responseCharSequenceRequest.addAll(texts)
    }

    private fun clearLists() {
        headersList.clear()
        parametersList.clear()
    }

    private fun setInfoText(response: Response) {
        codeInfoFragment = response.code().toString()
        codeColorText = setColorCodeInfo(response.code(), resources)
        headersInfoFragment = response.headers().toString()
        headersInfoFragment = headersToCharSequence(response.headers(), resources)
    }

    private fun insertDataToBD() {
        repository.insertRequest(
                RequestEntity(
                        spinnerMethod.get().toString(),
                        spinnerHttp.get() + textUrl.get(),
                        headersList,
                        parametersList,
                        textBody.get().orEmpty(),
                        SimpleDateFormat("MMM d, yyyy h:mm:ss a", Locale.getDefault()).format(Calendar.getInstance().time)
                )
        )
    }

    fun  onClickItemHistory(requestEntity: RequestEntity){
        repository.deleteRequest(requestEntity)
    }

}