package nikolaev.postboy.model

import android.content.Context
import nikolaev.postboy.model.api.Rest
import nikolaev.postboy.view.models.Pairs
import okhttp3.Response

class RepositoryImpl private constructor(
        private val rest: Rest
) : Repository {

    companion object {

        @Volatile
        private var INSTANCE: RepositoryImpl? = null

        fun getInstance(context: Context): RepositoryImpl {
            if (INSTANCE == null) {
                synchronized(RepositoryImpl::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = RepositoryImpl(
                                Rest(context.applicationContext)
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
            callback: (response: Response, error: String) -> Unit
    ) {
        rest.getRequest(url, headers) { response, error ->
            callback(response!!, error)
        }
    }

    override fun postApi(
            url: String,
            headers: List<Pairs>,
            body: String?,
            bodyType: String,
            callback: (response: String, error: String) -> Unit
    ) {
        rest.putRequest(url, headers, body, bodyType) { response, error ->
            callback(response, error)
        }
    }

    override fun putApi(
            url: String,
            headers: List<Pairs>,
            body: String?,
            bodyType: String,
            callback: (response: String, error: String) -> Unit
    ) {
        rest.putRequest(url, headers, body, bodyType) { response, error ->
            callback(response, error)
        }
    }

    override fun deleteApi(
            url: String,
            headers: List<Pairs>,
            body: String?,
            bodyType: String,
            callback: (response: String, error: String) -> Unit
    ) {
        rest.deleteRequest(url, headers, body, bodyType) { response, error ->
            callback(response, error)
        }
    }
}