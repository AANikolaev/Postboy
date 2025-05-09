package anicode.postboy.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import anicode.postboy.R
import anicode.postboy.databinding.FieldHeaderBinding
import anicode.postboy.view.interfaces.IClickHeadersPairModel
import anicode.postboy.view.models.Pairs

class HeaderRecyclerViewAdapter(listener: IClickHeadersPairModel) :
    RecyclerView.Adapter<HeaderRecyclerViewAdapter.ItemRowHolder>() {

    private var pairList = ArrayList<Pairs>()
    private var listener = listener

    fun update(list: ArrayList<Pairs>) {
        pairList.clear()
        pairList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowHolder {
        return ItemRowHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.field_header, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return pairList.size
    }

    override fun onBindViewHolder(holder: ItemRowHolder, position: Int) {
        if (pairList.isNotEmpty())
            holder.bind(pairList[position], listener)
    }

    inner class ItemRowHolder(private val binding: FieldHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Pairs, listener: IClickHeadersPairModel) {
            binding.model = model
            binding.listener = listener
        }
    }
}