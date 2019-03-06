package nikolaev.postboy.view.fragments

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_response_preview.*
import nikolaev.postboy.R
import nikolaev.postboy.databinding.FragmentResponsePreviewBinding
import nikolaev.postboy.view.activities.MainActivity
import nikolaev.postboy.view.base.BaseFragment
import nikolaev.postboy.viewmodel.MainViewModel

class ResponsePreviewFragment : BaseFragment<MainViewModel, FragmentResponsePreviewBinding>() {

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_response_preview

    override fun onViewModelReady() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webSettings = mWebView.settings
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        mWebView.loadUrl("https://www.google.ru/?hl=uk")
    }
}
