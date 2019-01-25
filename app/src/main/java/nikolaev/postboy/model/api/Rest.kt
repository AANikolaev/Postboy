package nikolaev.postboy.model.api

import android.content.Context
import nikolaev.postboy.R
import nikolaev.postboy.model.utils.phaseHeaders
import nikolaev.postboy.util.NetworkManager
import okhttp3.*
import okhttp3.internal.Util
import java.io.IOException

class Rest(context: Context) : IRest {

    override val NO_NETWORK_ERROR = context.getString(R.string.no_network_error_text)
    private val client = OkHttpClient()
    private val networkManager = NetworkManager(context)

    override fun getRequest(
        url: String, headers: List<Pair<String, String>>, callback: (
            response: String,
            error: String
        ) -> Unit
    ) {
        if (networkManager.isNetworkAvailable()) {
            val request = Request.Builder().url(url).headers(phaseHeaders(headers)).get()
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback("", e.message!!)
                }

                override fun onResponse(call: Call, response: Response) {
                    callback(response.body()!!.string(), "")
                }

            })
        } else {
            callback("", NO_NETWORK_ERROR)
        }
    }


    fun postRequest(url: String, headers: List<Pair<String, String>>, body: String?, callback: Callback): Call {
        val request = Request.Builder().url(url).headers(phaseHeaders(headers))
            .post(if (body == null) Util.EMPTY_REQUEST else RequestBody.create(null, body)).build()
        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }

    fun headRequest(url: String, headers: List<Pair<String, String>>, callback: Callback): Call {
        val request = Request.Builder().url(url).headers(phaseHeaders(headers)).head()
            .build()
        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }

    fun putRequest(url: String, headers: List<Pair<String, String>>, body: String?, callback: Callback): Call {
        val request = Request.Builder().url(url).headers(phaseHeaders(headers))
            .put(if (body == null) Util.EMPTY_REQUEST else RequestBody.create(null, body)).build()
        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }

    fun deleteRequest(url: String, headers: List<Pair<String, String>>, body: String?, callback: Callback): Call {
        val request = Request.Builder().url(url).headers(phaseHeaders(headers))
            .delete(if (body == null) null else RequestBody.create(null, body)).build()
        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }

    fun patchRequest(url: String, headers: List<Pair<String, String>>, body: String?, callback: Callback): Call {
        val request = Request.Builder().url(url).headers(phaseHeaders(headers))
            .patch(if (body == null) Util.EMPTY_REQUEST else RequestBody.create(null, body)).build()
        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }
}