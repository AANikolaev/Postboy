package anicode.postboy.view.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import anicode.postboy.model.Repository
import anicode.postboy.model.RepositoryImpl

open class BaseViewModel(application: Application) : AndroidViewModel(application), LifecycleOwner {

    // todo: check warning
    private var mLifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    protected val resources = application.resources

    init {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    protected val repository: Repository =
        RepositoryImpl.getInstance(application.baseContext)

    protected fun getString(@StringRes id: Int): String = resources.getString(id)

    val isLoading = MutableLiveData<Boolean>().apply {
        postValue(false)
    }

    fun clearRequestQueue() {
        isLoading.postValue(false)
    }

    fun stopListening() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    fun startListening() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    override val lifecycle: Lifecycle
        get() = mLifecycleRegistry

}