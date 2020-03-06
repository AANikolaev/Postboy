package anikolaev.postboy.model.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import anikolaev.postboy.view.models.Pairs

/**
 *  Created by Alexander Nikolaev on 4/1/19.
 *  alexandr.nikolaev.dev@gmail.com
 */


class PairConverter {

    @TypeConverter
    fun listToJson(value: List<Pairs>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Pairs>? {
        val objects = Gson().fromJson(value, Array<Pairs>::class.java) as Array<Pairs>
        return objects.toList()
    }
}