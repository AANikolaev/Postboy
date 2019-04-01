package nikolaev.postboy.view.fragments


import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_history.*
import nikolaev.postboy.R
import nikolaev.postboy.databinding.FragmentHistoryBinding
import nikolaev.postboy.model.db.entities.RequestEntity
import nikolaev.postboy.view.activities.MainActivity
import nikolaev.postboy.view.adapter.HistoryAdapter
import nikolaev.postboy.view.base.BaseFragment
import nikolaev.postboy.view.interfaces.OnClickHistoryItem
import nikolaev.postboy.viewmodel.MainViewModel

class HistoryFragment : BaseFragment<MainViewModel, FragmentHistoryBinding>(), OnClickHistoryItem {

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_history

    override fun onViewModelReady() {
        viewModel.historyRequest.observe(this, Observer {
            recyclerViewHistory.layoutManager = LinearLayoutManager(activity)
            val adapter = HistoryAdapter(it, this)
            recyclerViewHistory.adapter = adapter
        })
    }

    override fun onItemHistoryClick(view: View, position: Int, model: RequestEntity) {
        Log.i("+", model.url)

        Toast.makeText(context, model.url, Toast.LENGTH_LONG).show()
    }


}
