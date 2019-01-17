package nikolaev.postboy.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import nikolaev.postboy.BR

abstract class BaseActivity<V : ViewModel, B : ViewDataBinding> : AppCompatActivity() {

    lateinit var viewModel: V
    private lateinit var binding: B

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
        binding = DataBindingUtil.inflate(layoutInflater, getContentViewLayoutId(), null, false) as B
        binding.setVariable(BR.viewModel, this.viewModel)
    }
}