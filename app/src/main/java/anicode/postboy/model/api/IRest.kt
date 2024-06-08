package anicode.postboy.model.api


import anicode.postboy.view.models.Pairs
import okhttp3.Response

interface IRest {

    val NO_NETWORK_ERROR: String

    fun getRequest(
        url: String, headers: List<Pairs>,
        callback: (response: Response?, error: String) -> Unit
    )

    fun postRequest(
        url: String, headers: List<Pairs>, body: String?, bodyType: String,
        callback: (response: Response?, error: String) -> Unit
    )

    fun putRequest(
        url: String, headers: List<Pairs>, body: String?, bodyType: String,
        callback: (response: Response?, error: String) -> Unit
    )

    fun deleteRequest(
        url: String, headers: List<Pairs>, body: String?, bodyType: String,
        callback: (response: Response?, error: String) -> Unit
    )
}