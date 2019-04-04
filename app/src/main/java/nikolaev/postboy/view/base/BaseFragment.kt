package nikolaev.postboy.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import nikolaev.postboy.BR
import nikolaev.postboy.view.activities.MainActivity

abstract class BaseFragment<V : ViewModel, B : ViewDataBinding> : Fragment() {

    lateinit var viewModel: V
    private lateinit var binding: B

    abstract fun obtainViewModel(): V

    abstract fun getContentViewLayoutId(): Int

    abstract fun getMenuId(): Int

    protected abstract fun onViewModelReady()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, getContentViewLayoutId(), container, false) as B
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()
        binding.setVariable(BR.viewModel, this.viewModel)
        (activity as MainActivity).nav_view.menu.getItem(getMenuId()).isChecked = true
    }

    private fun initViewModel() {
        viewModel = obtainViewModel()
        onViewModelReady()
    }

}