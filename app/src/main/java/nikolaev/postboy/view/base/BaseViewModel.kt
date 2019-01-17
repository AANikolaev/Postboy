package nikolaev.postboy.view.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(application: Application): AndroidViewModel(application) {

    protected val resources = application.resources

    protected fun getString(@StringRes id: Int) = resources.getString(id)
}