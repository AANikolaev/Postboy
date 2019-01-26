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
import org.json.JSONException
import java.util.*

class MainViewModel(application: Application) : BaseViewModel(application) {

    val TAG = this::class.java.simpleName

    val oNDeleteHeadersView = MutableLiveData<View>()
    val oNDeleteParameterView = MutableLiveData<View>()

    val spinnerMethod = ObservableField<String>()
    val spinnerHttp = ObservableField<String>()
    val textUrl = ObservableField<String>()

    var headersList = ArrayList<Pair<String, String>>()
    var parametersList = ArrayList<Pair<String, String>>()

    val progressDialogEvent = MutableLiveData<ProgressDialogModel>()
    val errorDialogEvent = MutableLiveData<Event<ErrorDialogModel>>()

    private lateinit var texts: List<CharSequence>
    val textResponse = ObservableField<String>()
    val nextFragment = MutableLiveData<Int>()

    fun onClickSendRequest() {
        Log.d("+", spinnerMethod.get())
        Log.d("+", spinnerHttp.get())
        Log.d("+", textUrl.get())

        for (i in headersList) {
            Log.d("+", "header Pair(${i.first}, ${i.second})")
        }

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

                for (i in texts) {
                    textResponse.set(i.toString())
                }

                Log.d("+", texts.toString())

            } else {
                errorDialogEvent.postValue(Event(ErrorDialogModel(errorMessage = error)))
            }

        }

    }
}