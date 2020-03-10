package anikolaev.postboy.util.diffUtil

import androidx.recyclerview.widget.DiffUtil
import anikolaev.postboy.model.db.entities.RequestEntity

/**
 *  Created by Alexander Nikolaev on 4/2/19.
 *  alexandr.nikolaev.dev@gmail.com
 */


class DiffUtilRequestEntity(var oldList: List<RequestEntity>, var newList: List<RequestEntity>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].time == newList[newItemPosition].time
    }
}