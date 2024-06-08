package anicode.postboy.view.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.*
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

    override fun getLifecycle(): Lifecycle {
        return mLifecycleRegistry
    }

    fun stopListening() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    fun startListening() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

}