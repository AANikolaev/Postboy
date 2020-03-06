package anikolaev.postboy.view.fragments


import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_response.*
import anikolaev.postboy.R
import anikolaev.postboy.databinding.FragmentResponseBinding
import anikolaev.postboy.util.REST_MENU_ITEM
import anikolaev.postboy.view.activities.MainActivity
import anikolaev.postboy.view.base.BaseFragment
import anikolaev.postboy.viewmodel.MainViewModel

class ResponseFragment : BaseFragment<MainViewModel, FragmentResponseBinding>() {

    override fun getMenuId(): Int = REST_MENU_ITEM

    override fun isVisibleToolbar(): Boolean = false

    override fun getToolbarTitle(): String = getString(R.string.app_name)

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_response

    override fun onViewModelReady() {
        listView.adapter = ArrayAdapter(
                context!!, R.layout.list_response_textview,
                viewModel.responseCharSequenceRequest
        )
    }

    companion object {
        fun newInstance() = ResponseFragment()
    }
}
