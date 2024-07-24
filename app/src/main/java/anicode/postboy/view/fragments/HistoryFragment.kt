package anicode.postboy.view.fragments


import android.view.View
import android.view.animation.AlphaAnimation
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import anicode.postboy.R
import anicode.postboy.databinding.FragmentHistoryBinding
import anicode.postboy.model.db.entities.RequestEntity
import anicode.postboy.util.HISTORY_MENU_ITEM
import anicode.postboy.view.activities.MainActivity
import anicode.postboy.view.adapter.HistoryAdapter
import anicode.postboy.view.base.BaseFragment
import anicode.postboy.view.interfaces.OnClickHistoryItem
import anicode.postboy.view.interfaces.OnDeleteClickHistoryItem
import anicode.postboy.viewmodel.MainViewModel

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
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewHistory.adapter = adapter

        viewModel.historyRequest.observe(this, Observer { listHistory ->

            if (listHistory.isNotEmpty()) {
                binding.recyclerViewHistory.visibility = View.VISIBLE
                binding.noHistory.visibility = View.GONE
            } else {
                binding.recyclerViewHistory.visibility = View.GONE
                binding.noHistory.run {
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
