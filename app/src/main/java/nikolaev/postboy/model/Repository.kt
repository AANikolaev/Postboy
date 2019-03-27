package nikolaev.postboy.model

import nikolaev.postboy.view.models.Pairs
import okhttp3.Response

interface Repository {

    fun getApi(
        url: String, headers: List<Pairs>,
        callback: (response: Response?, error: String) -> Unit
    )

    fun postApi(
        url: String, headers: List<Pairs>, body: String?, bodyType: String,
        callback: (response: Response?, error: String) -> Unit
    )

    fun putApi(
        url: String, headers: List<Pairs>, body: String?, bodyType: String,
        callback: (response: Response?, error: String) -> Unit
    )

    fun deleteApi(
        url: String, headers: List<Pairs>, body: String?, bodyType: String,
        callback: (response: Response?, error: String) -> Unit
    )

    //todo post etc
}