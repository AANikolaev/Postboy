package nikolaev.postboy.view.fragments

import nikolaev.postboy.R
import nikolaev.postboy.databinding.FragmentAboutBinding
import nikolaev.postboy.util.ABOUT_ITEM
import nikolaev.postboy.view.activities.MainActivity
import nikolaev.postboy.view.base.BaseFragment
import nikolaev.postboy.viewmodel.MainViewModel


class AboutFragment : BaseFragment<MainViewModel, FragmentAboutBinding>() {

    override fun getMenuId(): Int = ABOUT_ITEM

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_about

    override fun onViewModelReady() {

    }

}
