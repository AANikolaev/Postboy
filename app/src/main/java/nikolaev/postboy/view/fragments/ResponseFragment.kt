package nikolaev.postboy.view.fragments


import nikolaev.postboy.R
import nikolaev.postboy.databinding.FragmentResponseBinding
import nikolaev.postboy.view.activities.MainActivity
import nikolaev.postboy.view.base.BaseFragment
import nikolaev.postboy.viewmodel.MainViewModel

class ResponseFragment : BaseFragment<MainViewModel, FragmentResponseBinding>() {

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_response

    override fun onViewModelReady() {}

}
