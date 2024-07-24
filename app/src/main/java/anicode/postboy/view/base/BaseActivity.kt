package anicode.postboy.view.base

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import anicode.postboy.BR
import anicode.postboy.util.showPreLoader

abstract class BaseActivity<V : BaseViewModel, B : ViewDataBinding> : AppCompatActivity() {

    lateinit var viewModel: V
    protected lateinit var binding: B

    private var preLoader: AlertDialog? = null

    abstract fun obtainViewModel(): V

    abstract fun getContentViewLayoutId(): Int

    protected abstract fun onViewModelReady()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initBinding()
        setContentView(binding.root)
    }

    private fun initViewModel() {
        viewModel = obtainViewModel()
        onViewModelReady()
    }

    private fun initBinding() {
        binding =
            DataBindingUtil.inflate(layoutInflater, getContentViewLayoutId(), null, false) as B
        binding.setVariable(BR.viewModel, this.viewModel)
    }

    override fun onResume() {
        super.onResume()
        (viewModel as BaseViewModel).isLoading.observe(this, loadingObserver)
    }


    override fun onPause() {
        super.onPause()
        preLoader?.dismiss()
        (viewModel as BaseViewModel).isLoading.removeObserver(loadingObserver)
    }

    private var loadingObserver = Observer<Boolean> {
        preLoader = if (it!!) {
            showPreLoader(this, null, true)
        } else {
            preLoader?.dismiss()
            null
        }
    }
}