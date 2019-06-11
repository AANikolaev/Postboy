package nikolaev.postboy.view.fragments


import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_history.*
import nikolaev.postboy.R
import nikolaev.postboy.databinding.FragmentHistoryBinding
import nikolaev.postboy.model.db.entities.RequestEntity
import nikolaev.postboy.util.HISTORY_MENU_ITEM
import nikolaev.postboy.view.activities.MainActivity
import nikolaev.postboy.view.adapter.HistoryAdapter
import nikolaev.postboy.view.base.BaseFragment
import nikolaev.postboy.view.interfaces.OnClickHistoryItem
import nikolaev.postboy.view.interfaces.OnDeleteClickHistoryItem
import nikolaev.postboy.viewmodel.MainViewModel

class HistoryFragment : BaseFragment<MainViewModel, FragmentHistoryBinding>(), OnDeleteClickHistoryItem,
    OnClickHistoryItem {

    override fun getMenuId(): Int = HISTORY_MENU_ITEM

    override fun obtainViewModel(): MainViewModel = (activity as MainActivity).viewModel

    override fun getContentViewLayoutId(): Int = R.layout.fragment_history

    override fun isVisibleToolbar(): Boolean = true

    override fun getToolbarTitle(): String = getString(R.string.nav_history)

    override fun onViewModelReady() {
        val adapter = HistoryAdapter(this, this)
        recyclerViewHistory.layoutManager = LinearLayoutManager(activity)
        recyclerViewHistory.adapter = adapter

        viewModel.historyRequest.observe(this, Observer { listHistory ->
            val sortedList = listHistory.sortedWith(compareByDescending { it.id })
            adapter.update(ArrayList(sortedList))
        })
    }

    override fun onDeleteItemHistoryClick(view: View, position: Int, model: RequestEntity) {
        viewModel.onDeleteItemHistory(model)
    }

    override fun onClickItemHistory(model: RequestEntity) {
        viewModel.onClickHistoryItem(model)
    }

}
