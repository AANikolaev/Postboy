package nikolaev.postboy.view.interfaces

import nikolaev.postboy.model.db.entities.RequestEntity

/**
 *  Created by Alexander Nikolaev on 2019-05-17.
 *  alexandr.nikolaev.dev@gmail.com
 */


interface OnClickHistoryItem {

    fun onClickItemHistory(model: RequestEntity)
}