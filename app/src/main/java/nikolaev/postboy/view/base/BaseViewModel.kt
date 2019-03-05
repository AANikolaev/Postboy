package nikolaev.postboy.view.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import nikolaev.postboy.model.Repository
import nikolaev.postboy.model.RepositoryImpl

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val resources = application.resources

    protected val repository: Repository =
        RepositoryImpl.getInstance(application.baseContext)

    protected fun getString(@StringRes id: Int): String = resources.getString(id)

    val isLoading = MutableLiveData<Boolean>().apply {
        postValue(false)
    }

    fun clearRequestQueue() {
        isLoading.postValue(false)
    }


}