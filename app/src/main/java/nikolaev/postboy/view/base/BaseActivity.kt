package nikolaev.postboy.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import nikolaev.postboy.BR

abstract class BaseActivity<V : ViewModel, B : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var viewModel: V
    protected lateinit var binding: B

    abstract fun initViewModel(): V

    abstract fun initBinding(): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = initBinding()
        setContentView(binding.root)

        viewModel = initViewModel()

        binding.setVariable(BR.viewModel, this.viewModel)

        onViewModelReady()
    }

    open fun onViewModelReady() {}
}