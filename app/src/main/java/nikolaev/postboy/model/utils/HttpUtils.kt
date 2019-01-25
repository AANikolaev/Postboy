package nikolaev.postboy.model.utils

import okhttp3.Headers

fun phaseHeaders(headers: List<Pair<String, String>>): Headers {
    val headerBuilder = Headers.Builder()
    for (keyAndValue in headers) {
        headerBuilder.add(keyAndValue.first, keyAndValue.second)
    }
    return headerBuilder.build()
}