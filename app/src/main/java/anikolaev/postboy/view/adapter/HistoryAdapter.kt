package anikolaev.postboy.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history.view.*
import anikolaev.postboy.BR
import anikolaev.postboy.R
import anikolaev.postboy.model.db.entities.RequestEntity
import anikolaev.postboy.util.diffUtil.DiffUtilRequestEntity
import anikolaev.postboy.util.setMethodColor
import anikolaev.postboy.view.interfaces.OnClickHistoryItem
import anikolaev.postboy.view.interfaces.OnDeleteClickHistoryItem

/**
 *  Created by Alexander Nikolaev on 3/29/19.
 *  alexandr.nikolaev.dev@gmail.com
 */


class HistoryAdapter(
    private var onDeleteItemClick: OnDeleteClickHistoryItem,
    private var onClickHistoryItem: OnClickHistoryItem
) : RecyclerView.Adapter<HistoryViewHolder>() {

    var innerListRequestEntity = ArrayList<RequestEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_history, parent, false
        )
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = innerListRequestEntity.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.setVariable(BR.model, innerListRequestEntity[position])
        holder.bind(innerListRequestEntity[position], onDeleteItemClick, onClickHistoryItem)
    }

    fun update(updateList: ArrayList<RequestEntity>) {
        val diffResult = DiffUtil.calculateDiff(
            DiffUtilRequestEntity(innerListRequestEntity, updateList)
        )
        innerListRequestEntity.clear()
        innerListRequestEntity.addAll(updateList)
        diffResult.dispatchUpdatesTo(this)
    }

}

class HistoryViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        requestEntity: RequestEntity,
        deleteHistoryItemClick: OnDeleteClickHistoryItem,
        onClickHistoryItem: OnClickHistoryItem
    ) {
        binding.root.tv_method.setTextColor(setMethodColor(requestEntity.method))
        binding.root.btn_history_delete.setOnClickListener {
            deleteHistoryItemClick.onDeleteItemHistoryClick(itemView, layoutPosition, requestEntity)
        }
        binding.root.setOnClickListener {
            onClickHistoryItem.onClickItemHistory(requestEntity)
        }
    }
}