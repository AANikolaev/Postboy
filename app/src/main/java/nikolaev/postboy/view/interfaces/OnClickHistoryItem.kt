package nikolaev.postboy.view.interfaces

import android.view.View
import nikolaev.postboy.model.db.entities.RequestEntity

/**
 *  Created by Alexander Nikolaev on 3/29/19.
 *  alexandr.nikolaev.dev@gmail.com
 */


interface OnClickHistoryItem {
    fun onItemHistoryClick(view: View, position: Int, model: RequestEntity)
}