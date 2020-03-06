package anikolaev.postboy.view.fragments


import android.view.View
import android.view.animation.AlphaAnimation
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_history.*
import anikolaev.postboy.R
import anikolaev.postboy.databinding.FragmentHistoryBinding
import anikolaev.postboy.model.db.entities.RequestEntity
import anikolaev.postboy.util.HISTORY_MENU_ITEM
import anikolaev.postboy.view.activities.MainActivity
import anikolaev.postboy.view.adapter.HistoryAdapter
import anikolaev.postboy.view.base.BaseFragment
import anikolaev.postboy.view.interfaces.OnClickHistoryItem
import anikolaev.postboy.view.interfaces.OnDeleteClickHistoryItem
import anikolaev.postboy.viewmodel.MainViewModel

class HistoryFragment : BaseFragment<MainViewModel, FragmentHistoryBinding>(),
    OnDeleteClickHistoryItem,
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

            if (listHistory.isNotEmpty()) {
                recyclerViewHistory.visibility = View.VISIBLE
                noHistory.visibility = View.GONE
            } else {
                recyclerViewHistory.visibility = View.GONE
                noHistory.run {
                    visibility = View.VISIBLE
                    AlphaAnimation(
                        ANIMATION_ALPHA_START,
                        ANIMATION_ALPHA_END
                    ).run {
                        duration =
                            ANIMATION_ALPHA_DURATION
                        fillAfter = true
                        startAnimation(this)
                    }
                }
            }
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


const val ANIMATION_ALPHA_START = 0.0f
const val ANIMATION_ALPHA_END = 1.0f
const val ANIMATION_ALPHA_DURATION = 800L
