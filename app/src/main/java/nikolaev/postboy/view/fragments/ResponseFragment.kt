package nikolaev.postboy.view.fragments


import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_response.*
import nikolaev.postboy.R
import nikolaev.postboy.databinding.FragmentResponseBinding
import nikolaev.postboy.util.REST_MENU_ITEM
import nikolaev.postboy.view.activities.MainActivity
import nikolaev.postboy.view.base.BaseFragment
import nikolaev.postboy.viewmodel.MainViewModel

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
