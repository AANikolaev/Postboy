package nikolaev.postboy.model.api


import nikolaev.postboy.view.models.Pairs

interface IRest {

    val NO_NETWORK_ERROR: String

    fun getRequest(
            url: String, headers: List<Pairs>,
            callback: (response: String, error: String) -> Unit
    )

    fun putRequest(
            url: String, headers: List<Pairs>, body: String?, bodyType: String,
            callback: (response: String, error: String) -> Unit
    )

}