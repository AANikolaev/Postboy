package anicode.postboy.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import anicode.postboy.model.db.entities.RequestEntity

/**
 *  Created by Alexander Nikolaev on 3/28/19.
 *  alexandr.nikolaev.dev@gmail.com
 */

@Dao
interface RequestDao {

    @get:Query("SELECT * FROM request_entity")
    val allRequest: LiveData<List<RequestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRequestEntity(requestEntity: RequestEntity)

    @Delete
    fun delete(requestEntity: RequestEntity)
}