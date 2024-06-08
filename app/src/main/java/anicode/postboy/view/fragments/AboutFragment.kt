package anicode.postboy.view.fragments

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import kotlinx.android.synthetic.main.fragment_about.*
import anicode.postboy.BuildConfig
import anicode.postboy.R
import anicode.postboy.databinding.FragmentAboutBinding
import anicode.postboy.util.ABOUT_ITEM
import anicode.postboy.view.activities.MainActivity
import anicode.postboy.view.base.BaseFragment
import anicode.postboy.viewmodel.MainViewModel


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

        tvCompanyName.apply {
            text = Html.fromHtml("<a href=\"https://kinect.pro/\">KinectPro</a>")
            movementMethod = LinkMovementMethod.getInstance()
        }
        tvAuthor.apply {
            text = Html.fromHtml("<a href=\"https://www.linkedin.com/in/alexander-nikolaev-12504a144/\">Alexander Nikolaev</a>")
            movementMethod = LinkMovementMethod.getInstance()
        }
    }
}
