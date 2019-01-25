package nikolaev.postboy.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import nikolaev.postboy.R
import nikolaev.postboy.util.ProgressDialogModel
import nikolaev.postboy.view.base.BaseViewModel

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
        repository.getApi(spinnerHttp.get() + textUrl.get()!!, headersList) { q, w ->
            Log.d("+", "re($q, $w)")
            progressDialogEvent.postValue(ProgressDialogModel(isProgressDialogNeeded = false))
        }

    }
}