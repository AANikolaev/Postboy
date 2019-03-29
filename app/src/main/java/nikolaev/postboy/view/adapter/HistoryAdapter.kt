package nikolaev.postboy.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import nikolaev.postboy.BR
import nikolaev.postboy.R
import nikolaev.postboy.model.db.entities.RequestEntity
import nikolaev.postboy.view.interfaces.OnClickHistoryItem

/**
 *  Created by Alexander Nikolaev on 3/29/19.
 *  alexandr.nikolaev.dev@gmail.com
 */


class HistoryAdapter(
    private var listRequest: List<RequestEntity>,
    private var onItemClick: OnClickHistoryItem
) :
    RecyclerView.Adapter<HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_history, parent, false
        )
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = listRequest.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.binding.setVariable(BR.model, listRequest[position])
        holder.bind(listRequest[position], onItemClick)
    }
}

class HistoryViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(requestEntity: RequestEntity, clickHistoryItem: OnClickHistoryItem) {
        binding.root.setOnClickListener {
            clickHistoryItem.onItemHistoryClick(itemView, layoutPosition, requestEntity)
        }
    }
}