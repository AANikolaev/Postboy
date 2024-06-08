package anicode.postboy.view.fragments


import anicode.postboy.R
import anicode.postboy.databinding.FragmentResponseInfoBinding
import anicode.postboy.util.REST_MENU_ITEM
import anicode.postboy.view.activities.MainActivity
import anicode.postboy.view.base.BaseFragment
import anicode.postboy.viewmodel.MainViewModel

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
