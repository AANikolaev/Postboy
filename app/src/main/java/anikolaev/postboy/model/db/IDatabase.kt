package nikolaev.postboy.model.db

import androidx.lifecycle.LiveData
import nikolaev.postboy.model.db.entities.RequestEntity

/**
 *  Created by Alexander Nikolaev on 3/28/19.
 *  alexandr.nikolaev.dev@gmail.com
 */


interface IDatabase {

    val BASIC_ERROR: String

    fun getAllRequests(): LiveData<List<RequestEntity>>

    fun insertRequest(requestEntity: RequestEntity)

    fun deleteRequest(requestEntity: RequestEntity)
}