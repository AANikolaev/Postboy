package anikolaev.postboy.model

import anikolaev.postboy.util.BODY_TYPE_JSON
import anikolaev.postboy.util.BODY_TYPE_TEXT
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.internal.Util


fun getTypeBody(body: String?, bodyType: String): RequestBody {
    if (body == null) {
        return Util.EMPTY_REQUEST
    } else {
        if (bodyType == BODY_TYPE_JSON) {
            return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body)
        } else if (bodyType == BODY_TYPE_TEXT) {
            return RequestBody.create(null, body)
        }
    }
    return Util.EMPTY_REQUEST
}