package nikolaev.postboy.view.fragments

import nikolaev.postboy.R
import nikolaev.postboy.databinding.FragmentRequestBinding
import nikolaev.postboy.view.MainActivity
import nikolaev.postboy.view.base.BaseFragment
import nikolaev.postboy.viewmodel.MainViewModel


class RequestFragment : BaseFragment<MainViewModel, FragmentRequestBinding>() {

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_request

    override fun onViewModelReady() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
