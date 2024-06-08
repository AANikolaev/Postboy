package anicode.postboy.view.interfaces

import android.view.View
import anicode.postboy.model.db.entities.RequestEntity

/**
 *  Created by Alexander Nikolaev on 3/29/19.
 *  alexandr.nikolaev.dev@gmail.com
 */


interface OnDeleteClickHistoryItem {
    fun onDeleteItemHistoryClick(view: View, position: Int, model: RequestEntity)
}