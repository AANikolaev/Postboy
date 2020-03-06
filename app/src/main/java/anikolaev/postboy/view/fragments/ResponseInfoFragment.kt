package anikolaev.postboy.view.fragments


import anikolaev.postboy.R
import anikolaev.postboy.databinding.FragmentResponseInfoBinding
import anikolaev.postboy.util.REST_MENU_ITEM
import anikolaev.postboy.view.activities.MainActivity
import anikolaev.postboy.view.base.BaseFragment
import anikolaev.postboy.viewmodel.MainViewModel

class ResponseInfoFragment : BaseFragment<MainViewModel, FragmentResponseInfoBinding>() {

    override fun getMenuId(): Int = REST_MENU_ITEM

    override fun isVisibleToolbar(): Boolean = false

    override fun getToolbarTitle(): String = getString(R.string.app_name)

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_response_info

    override fun onViewModelReady() {

    }

    companion object {
        fun newInstance() = ResponseInfoFragment()
    }

}
