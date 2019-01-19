package nikolaev.postboy.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import nikolaev.postboy.view.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {

    val oNDeleteHeadersView = MutableLiveData<View>()

    val oNDeleteParameterView = MutableLiveData<View>()

}