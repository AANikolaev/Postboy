package nikolaev.postboy.viewmodel

import android.app.Application
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import nikolaev.postboy.R
import nikolaev.postboy.util.*
import nikolaev.postboy.view.base.BaseViewModel
import nikolaev.postboy.view.models.Pairs
import org.json.JSONException
import java.util.*

class MainViewModel(application: Application) : BaseViewModel(application) {

    val TAG = this::class.java.simpleName

    val onDeleteHeadersView = MutableLiveData<View>()
    val onDeleteParameterView = MutableLiveData<View>()

    val spinnerMethod = ObservableField<String>()
    val spinnerHttp = ObservableField<String>()
    val textUrl = ObservableField<String>()

    var headersList = ArrayList<Pairs>()
    var parametersList = ArrayList<Pairs>()

    var headersListAdapter = MutableLiveData<ArrayList<Pairs>>()
    private var headersArrayList = ArrayList<Pairs>()

    var parametersListAdapter = MutableLiveData<ArrayList<Pairs>>()
    private var parametersArrayList = ArrayList<Pairs>()

    val progressDialogEvent = MutableLiveData<ProgressDialogModel>()
    val errorDialogEvent = MutableLiveData<Event<ErrorDialogModel>>()

    private lateinit var texts: List<CharSequence>
    val nextFragment = MutableLiveData<Int>()
    val respon = MutableLiveData<List<CharSequence>>()

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
        Log.d("+", spinnerMethod.get())
        Log.d("+", spinnerHttp.get())
        Log.d("+", textUrl.get())
        headersList.addAll(headersListAdapter.value!!)

        for (i in parametersList) {
            Log.d("+", "parameter Pair(${i.first}, ${i.second})")
        }

        progressDialogEvent.postValue(
            ProgressDialogModel(
                true,
                getString(R.string.pre_loader_description_text_default)
            )
        )
        repository.getApi(spinnerHttp.get() + textUrl.get()!!, headersList) { response, error ->
            progressDialogEvent.postValue(ProgressDialogModel(isProgressDialogNeeded = false))
            Log.d("+", "re($response, $error)")
            headersList.clear()
            if (response != "") {
                val a = LinkedList<String>()
                a.add(response)
                val text = TextUtils.join("\n", a)
//                val jsonObject = MyJSONObject(text)
//                texts = jsonObject.getCharSequences(2)

                texts = try {
                    val jsonObject = MyJSONObject(text)
                    jsonObject.getCharSequences(2)
                } catch (e: JSONException) {
                    try {
                        val jsonArray = MyJSONArray(text)
                        jsonArray.getCharSequences(2)
                    } catch (e1: JSONException) {
                        a
                    }
                }

                nextFragment.postValue(R.id.responseFragment)
                respon.postValue(texts)

                Log.d("+", texts.toString())

            } else {
                errorDialogEvent.postValue(Event(ErrorDialogModel(errorMessage = error)))
            }

        }

    }
}