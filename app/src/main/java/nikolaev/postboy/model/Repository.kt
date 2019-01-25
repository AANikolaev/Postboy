package nikolaev.postboy.model

interface Repository {

    fun getApi(
        url: String, headers: List<Pair<String, String>>,
        callback: (response: String, error: String) -> Unit
    )
}