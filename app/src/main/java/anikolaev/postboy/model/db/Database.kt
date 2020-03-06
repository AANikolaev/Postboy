package anikolaev.postboy.model.db

import android.content.Context
import androidx.lifecycle.LiveData
import anikolaev.postboy.R
import anikolaev.postboy.model.db.entities.RequestEntity
import org.jetbrains.anko.doAsync

/**
 *  Created by Alexander Nikolaev on 3/28/19.
 *  alexandr.nikolaev.dev@gmail.com
 */


class Database(context: Context) : IDatabase {

    private val database = DatabaseProvider.getInstance(context)

    override val BASIC_ERROR: String = context.getString(R.string.basic_error_text)

    override fun getAllRequests(): LiveData<List<RequestEntity>> {
        return database.requestDao().allRequest
    }

    override fun insertRequest(requestEntity: RequestEntity) {
        doAsync {
            database.requestDao().insertRequestEntity(requestEntity)
        }
    }

    override fun deleteRequest(requestEntity: RequestEntity) {
        doAsync {
            database.requestDao().delete(requestEntity)
        }
    }
}