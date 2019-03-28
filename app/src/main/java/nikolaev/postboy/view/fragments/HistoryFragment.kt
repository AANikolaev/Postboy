package nikolaev.postboy.view.fragments


import nikolaev.postboy.R
import nikolaev.postboy.databinding.FragmentHistoryBinding
import nikolaev.postboy.view.activities.MainActivity
import nikolaev.postboy.view.base.BaseFragment
import nikolaev.postboy.viewmodel.MainViewModel

class HistoryFragment : BaseFragment<MainViewModel, FragmentHistoryBinding>() {

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_history

    override fun onViewModelReady() {

    }


}
