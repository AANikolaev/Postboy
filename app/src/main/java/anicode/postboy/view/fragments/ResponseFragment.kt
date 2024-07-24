package anicode.postboy.view.fragments


import android.widget.ArrayAdapter
import anicode.postboy.R
import anicode.postboy.databinding.FragmentResponseBinding
import anicode.postboy.util.REST_MENU_ITEM
import anicode.postboy.view.activities.MainActivity
import anicode.postboy.view.base.BaseFragment
import anicode.postboy.viewmodel.MainViewModel

class ResponseFragment : BaseFragment<MainViewModel, FragmentResponseBinding>() {

    override fun getMenuId(): Int = REST_MENU_ITEM

    override fun isVisibleToolbar(): Boolean = false

    override fun getToolbarTitle(): String = getString(R.string.app_name)

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_response

    override fun onViewModelReady() {
        binding.listView.adapter = ArrayAdapter(
            requireContext(), R.layout.list_response_textview,
            viewModel.responseCharSequenceRequest
        )
    }

    companion object {
        fun newInstance() = ResponseFragment()
    }
}
