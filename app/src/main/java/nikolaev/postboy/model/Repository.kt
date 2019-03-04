package nikolaev.postboy.model

import nikolaev.postboy.view.models.Pairs

interface Repository {

    fun getApi(
        url: String, headers: List<Pairs>,
        callback: (response: String, error: String) -> Unit
    )
}