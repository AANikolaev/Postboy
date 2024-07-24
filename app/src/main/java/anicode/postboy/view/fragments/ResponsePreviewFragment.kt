package anicode.postboy.view.fragments

import android.webkit.WebViewClient
import anicode.postboy.R
import anicode.postboy.databinding.FragmentResponsePreviewBinding
import anicode.postboy.util.REST_MENU_ITEM
import anicode.postboy.view.activities.MainActivity
import anicode.postboy.view.base.BaseFragment
import anicode.postboy.viewmodel.MainViewModel


class ResponsePreviewFragment : BaseFragment<MainViewModel, FragmentResponsePreviewBinding>() {

    override fun getMenuId(): Int = REST_MENU_ITEM

    override fun isVisibleToolbar(): Boolean = false

    override fun getToolbarTitle(): String = getString(R.string.app_name)

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_response_preview

    override fun onViewModelReady() {
        val webSettings = binding.mWebView.settings
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        binding.mWebView.webViewClient = WebViewClient()

        binding.mWebView.loadDataWithBaseURL(
            null, viewModel.bodyPreview,
            "text/html", "UTF-8", null
        )
    }

    override fun onResume() {
        binding.mWebView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mWebView.onPause()
    }

    override fun onDestroy() {
        if (binding.mWebView != null) {
            binding.mWebView.destroy()
        }
        viewModel.bodyPreview = ""
        super.onDestroy()
    }

    companion object {
        fun newInstance() = ResponsePreviewFragment()
    }
}
