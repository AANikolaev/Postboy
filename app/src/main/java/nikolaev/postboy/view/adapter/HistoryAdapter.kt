package nikolaev.postboy.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history.view.*
import nikolaev.postboy.BR
import nikolaev.postboy.R
import nikolaev.postboy.model.db.entities.RequestEntity
import nikolaev.postboy.util.diffUtil.DiffUtilRequestEntity
import nikolaev.postboy.util.setMethodColor
import nikolaev.postboy.view.interfaces.OnClickHistoryItem

/**
 *  Created by Alexander Nikolaev on 3/29/19.
 *  alexandr.nikolaev.dev@gmail.com
 */


class HistoryAdapter(private var onItemClick: OnClickHistoryItem) : RecyclerView.Adapter<HistoryViewHolder>() {

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
        holder.bind(innerListRequestEntity[position], onItemClick)
    }

    fun update(updateList: ArrayList<RequestEntity>) {
        val diffResult = DiffUtil.calculateDiff(
                DiffUtilRequestEntity(innerListRequestEntity, updateList))
        innerListRequestEntity.clear()
        innerListRequestEntity.addAll(updateList)
        diffResult.dispatchUpdatesTo(this)
    }

}

class HistoryViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(requestEntity: RequestEntity, clickHistoryItem: OnClickHistoryItem) {
        binding.root.tv_method.setTextColor(setMethodColor(requestEntity.method))
        binding.root.btn_history_delete.setOnClickListener {
            clickHistoryItem.onItemHistoryClick(itemView, layoutPosition, requestEntity)
        }
        binding.root.setOnClickListener {
            Log.i("+", "click ${requestEntity.url}")
        }
    }
}