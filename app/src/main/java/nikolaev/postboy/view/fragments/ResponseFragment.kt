package nikolaev.postboy.view.fragments


import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_response.*
import nikolaev.postboy.R
import nikolaev.postboy.databinding.FragmentResponseBinding
import nikolaev.postboy.view.activities.MainActivity
import nikolaev.postboy.view.base.BaseFragment
import nikolaev.postboy.viewmodel.MainViewModel

class ResponseFragment : BaseFragment<MainViewModel, FragmentResponseBinding>() {

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_response

    override fun onViewModelReady() {
        viewModel.responseCharSequence.observe(this, Observer {
            listView.adapter = ArrayAdapter(
                context!!, R.layout.list_response_textview,
                it
            )
        })
    }

}
