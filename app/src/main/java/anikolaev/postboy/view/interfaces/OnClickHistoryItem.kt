package anikolaev.postboy.view.interfaces

import anikolaev.postboy.model.db.entities.RequestEntity

/**
 *  Created by Alexander Nikolaev on 2019-05-17.
 *  alexandr.nikolaev.dev@gmail.com
 */


interface OnClickHistoryItem {

    fun onClickItemHistory(model: RequestEntity)
}