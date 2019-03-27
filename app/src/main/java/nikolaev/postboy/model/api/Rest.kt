package nikolaev.postboy.model.api

import android.content.Context
import nikolaev.postboy.R
import nikolaev.postboy.model.getTypeBody
import nikolaev.postboy.model.utils.phaseHeaders
import nikolaev.postboy.util.NetworkManager
import nikolaev.postboy.view.models.Pairs
import okhttp3.*
import java.io.IOException

class Rest(context: Context) : IRest {

    override val NO_NETWORK_ERROR = context.getString(R.string.no_network_error_text)
    private val client = OkHttpClient()
    private val networkManager = NetworkManager(context)

    override fun getRequest(
        url: String,
        headers: List<Pairs>,
        callback: (response: Response?, error: String) -> Unit
    ) {
        if (networkManager.isNetworkAvailable()) {
            val request =
                Request.Builder()
                    .url(url)
                    .headers(phaseHeaders(headers))
                    .get()
                    .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback(null, e.message!!)
                }

                override fun onResponse(call: Call, response: Response) {
                    callback(response, "")
                }

            })
        } else {
            callback(null, NO_NETWORK_ERROR)
        }
    }

    override fun postRequest(
        url: String,
        headers: List<Pairs>,
        body: String?,
        bodyType: String,
        callback: (response: Response?, error: String) -> Unit
    ) {
        if (networkManager.isNetworkAvailable()) {
            val request =
                Request.Builder()
                    .url(url)
                    .headers(phaseHeaders(headers))
                    .post(getTypeBody(body, bodyType))
                    .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback(null, e.message!!)
                }

                override fun onResponse(call: Call, response: Response) {
                    callback(response, "")
                }

            })
        } else {
            callback(null, NO_NETWORK_ERROR)
        }
    }

    override fun putRequest(
        url: String,
        headers: List<Pairs>,
        body: String?,
        bodyType: String,
        callback: (response: Response?, error: String) -> Unit
    ) {

        if (networkManager.isNetworkAvailable()) {
            val request =
                Request.Builder()
                    .url(url)
                    .headers(phaseHeaders(headers))
                    .put(getTypeBody(body, bodyType))
                    .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback(null, e.message!!)
                }

                override fun onResponse(call: Call, response: Response) {
                    callback(response, "")
                }

            })
        } else {
            callback(null, NO_NETWORK_ERROR)
        }
    }

    override fun deleteRequest(
        url: String,
        headers: List<Pairs>,
        body: String?,
        bodyType: String,
        callback: (response: Response?, error: String) -> Unit
    ) {

        if (networkManager.isNetworkAvailable()) {
            val request =
                Request.Builder()
                    .url(url)
                    .headers(phaseHeaders(headers))
                    .delete(getTypeBody(body, bodyType))
                    .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback(null, e.message!!)
                }

                override fun onResponse(call: Call, response: Response) {
                    callback(response, "")
                }

            })
        } else {
            callback(null, NO_NETWORK_ERROR)
        }
    }

//    fun headRequest(url: String, headers: List<Pair<String, String>>, callback: Callback): Call {
//        val request = Request.Builder().url(url).headers(phaseHeaders(headers)).head()
//            .build()
//        val call = client.newCall(request)
//        call.enqueue(callback)
//        return call
//    }
//    fun patchRequest(url: String, headers: List<Pair<String, String>>, body: String?, callback: Callback): Call {
//        val request = Request.Builder().url(url).headers(phaseHeaders(headers))
//            .patch(if (body == null) Util.EMPTY_REQUEST else RequestBody.create(null, body)).build()
//        val call = client.newCall(request)
//        call.enqueue(callback)
//        return call
//    }
}