package nikolaev.postboy.model.api

interface IRest {

    val NO_NETWORK_ERROR: String

    fun getRequest(
        url: String, headers: List<Pair<String, String>>,
        callback: (response: String, error: String) -> Unit
    )

}