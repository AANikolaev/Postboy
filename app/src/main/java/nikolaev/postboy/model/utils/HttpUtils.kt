package nikolaev.postboy.model.utils

import nikolaev.postboy.view.models.Pairs
import okhttp3.Headers

fun phaseHeaders(headers: List<Pairs>): Headers {
    val headerBuilder = Headers.Builder()
    for (keyAndValue in headers) {
        headerBuilder.add(keyAndValue.first, keyAndValue.second)
    }
    return headerBuilder.build()
}