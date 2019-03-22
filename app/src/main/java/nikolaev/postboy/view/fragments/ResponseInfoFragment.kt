package nikolaev.postboy.view.fragments


import nikolaev.postboy.R
import nikolaev.postboy.databinding.FragmentResponseInfoBinding
import nikolaev.postboy.view.activities.MainActivity
import nikolaev.postboy.view.base.BaseFragment
import nikolaev.postboy.viewmodel.MainViewModel

class ResponseInfoFragment : BaseFragment<MainViewModel, FragmentResponseInfoBinding>() {

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_response_info

    override fun onViewModelReady() {

    }

    companion object {
        fun newInstance() = ResponseInfoFragment()
    }

}
