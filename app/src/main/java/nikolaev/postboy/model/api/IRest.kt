package nikolaev.postboy.model.api

interface IRest {

    fun getRequest(
        url: String, headers: List<Pair<String, String>>, callback: (
            response: String,
            error: String
        ) -> Unit
    )

}