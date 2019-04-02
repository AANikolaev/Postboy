package nikolaev.postboy.model

import android.content.Context
import androidx.lifecycle.LiveData
import nikolaev.postboy.model.api.Rest
import nikolaev.postboy.model.db.Database
import nikolaev.postboy.model.db.entities.RequestEntity
import nikolaev.postboy.view.models.Pairs
import okhttp3.Response

class RepositoryImpl private constructor(
    private val rest: Rest,
    private val database: Database
) : Repository {

    companion object {

        @Volatile
        private var INSTANCE: RepositoryImpl? = null

        fun getInstance(context: Context): RepositoryImpl {
            if (INSTANCE == null) {
                synchronized(RepositoryImpl::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = RepositoryImpl(
                            Rest(context.applicationContext),
                            Database(context.applicationContext)
                        )
                    }
                }
            }
            return INSTANCE!!
        }
    }

    override fun getApi(
        url: String,
        headers: List<Pairs>,
        callback: (response: Response?, error: String) -> Unit
    ) {
        rest.getRequest(url, headers) { response, error ->
            if (response != null)
                callback(response, error)
            else
                callback(null, error)
        }
    }

    override fun postApi(
        url: String,
        headers: List<Pairs>,
        body: String?,
        bodyType: String,
        callback: (response: Response?, error: String) -> Unit
    ) {
        rest.postRequest(url, headers, body, bodyType) { response, error ->
            if (response != null)
                callback(response, error)
            else
                callback(null, error)
        }
    }

    override fun putApi(
        url: String,
        headers: List<Pairs>,
        body: String?,
        bodyType: String,
        callback: (response: Response?, error: String) -> Unit
    ) {
        rest.putRequest(url, headers, body, bodyType) { response, error ->
            if (response != null)
                callback(response, error)
            else
                callback(null, error)
        }
    }

    override fun deleteApi(
        url: String,
        headers: List<Pairs>,
        body: String?,
        bodyType: String,
        callback: (response: Response?, error: String) -> Unit
    ) {
        rest.deleteRequest(url, headers, body, bodyType) { response, error ->
            if (response != null)
                callback(response, error)
            else
                callback(null, error)
        }
    }

    override fun getAllRequests(): LiveData<List<RequestEntity>> {
        return database.getAllRequests()
    }

    override fun insertRequest(requestEntity: RequestEntity) {
        database.insertRequest(requestEntity)
    }

    override fun deleteRequest(requestEntity: RequestEntity) {
        database.deleteRequest(requestEntity)
    }
}