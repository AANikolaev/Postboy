package nikolaev.postboy.view.fragments

import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.fragment_response_preview.*
import nikolaev.postboy.R
import nikolaev.postboy.databinding.FragmentResponsePreviewBinding
import nikolaev.postboy.util.REST_MENU_ITEM
import nikolaev.postboy.view.activities.MainActivity
import nikolaev.postboy.view.base.BaseFragment
import nikolaev.postboy.viewmodel.MainViewModel


class ResponsePreviewFragment : BaseFragment<MainViewModel, FragmentResponsePreviewBinding>() {

    override fun getMenuId(): Int = REST_MENU_ITEM

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_response_preview

    override fun onViewModelReady() {
        val webSettings = mWebView.settings
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        mWebView.webViewClient = WebViewClient()

        mWebView.loadDataWithBaseURL(null, viewModel.bodyPreview,
                "text/html", "UTF-8", null)
    }

    override fun onResume() {
        mWebView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mWebView.onPause()
    }

    override fun onDestroy() {
        if (mWebView != null) {
            mWebView.destroy()
        }
        viewModel.bodyPreview = ""
        super.onDestroy()
    }

    companion object {
        fun newInstance() = ResponsePreviewFragment()
    }
}
