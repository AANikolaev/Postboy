package nikolaev.postboy.model.utils

import nikolaev.postboy.view.models.Pairs
import okhttp3.Headers
import android.text.TextUtils



fun phaseHeaders(headers: List<Pairs>): Headers {
    val headerBuilder = Headers.Builder()
    for (keyAndValue in headers) {
        if (keyAndValue.first != "" && keyAndValue.second != "")
            headerBuilder.add(keyAndValue.first, keyAndValue.second)
    }
    return headerBuilder.build()
}

fun combineUrl(url: String, parameters: List<Pairs>?): String {
    if (parameters == null || parameters.isEmpty()) return url
    val list = ArrayList<String>()
    for (keyAndValue in parameters) {
        list.add(keyAndValue.first + "=" + keyAndValue.second)
    }
    return if (url.indexOf('?') > -1)
        url + '&'.toString() + TextUtils.join("&", list)
    else
        url + '?'.toString() + TextUtils.join("&", list)
}