package nikolaev.postboy.view.fragments

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_about.*
import nikolaev.postboy.BuildConfig
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

    override fun isVisibleToolbar(): Boolean = true

    override fun getToolbarTitle(): String = getString(R.string.nav_about)

    override fun onViewModelReady() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvVersion.text = String.format("Version - %s", BuildConfig.VERSION_NAME)
    }
}
