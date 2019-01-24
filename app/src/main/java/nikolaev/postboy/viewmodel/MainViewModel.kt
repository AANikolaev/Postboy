package nikolaev.postboy.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import nikolaev.postboy.view.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {

    val TAG = "MainViewModel"

    val oNDeleteHeadersView = MutableLiveData<View>()
    val oNDeleteParameterView = MutableLiveData<View>()

    val spinnerMethod = ObservableField<String>()
    val spinnerHttp = ObservableField<String>()
    val textUrl = ObservableField<String>()


    fun onClickSendRequest() {
        Log.d("+", "onClickSendRequest")
        Log.d("+", spinnerMethod.get())
        Log.d("+", spinnerHttp.get())
        Log.d("+", textUrl.get())

    }


}