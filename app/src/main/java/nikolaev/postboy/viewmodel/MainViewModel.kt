package nikolaev.postboy.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import nikolaev.postboy.R
import nikolaev.postboy.view.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {

    val fragmentRouteToMove = MutableLiveData<Int>()

    fun oncli(v: View) {
        fragmentRouteToMove.postValue(R.id.responseFragment)
    }

}