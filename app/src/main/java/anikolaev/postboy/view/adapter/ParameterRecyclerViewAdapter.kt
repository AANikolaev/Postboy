package anikolaev.postboy.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import anikolaev.postboy.R
import anikolaev.postboy.databinding.FieldParametersBinding
import anikolaev.postboy.view.interfaces.IClickParametersPairModel
import anikolaev.postboy.view.models.Pairs

class ParameterRecyclerViewAdapter(listener: IClickParametersPairModel) :
    RecyclerView.Adapter<ParameterRecyclerViewAdapter.ItemRowHolder>() {

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
                R.layout.field_parameters, parent, false
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

    inner class ItemRowHolder(private val binding: FieldParametersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Pairs, listener: IClickParametersPairModel) {
            binding.model = model
            binding.listener = listener
        }
    }
}