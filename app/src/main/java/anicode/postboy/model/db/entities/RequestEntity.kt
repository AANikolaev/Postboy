package anicode.postboy.model.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import anicode.postboy.view.models.Pairs

/**
 *  Created by Alexander Nikolaev on 3/28/19.
 *  alexandr.nikolaev.dev@gmail.com
 */

@Entity(tableName = "request_entity")
class RequestEntity(
    @ColumnInfo(name = "method") var method: String,
    @ColumnInfo(name = "http") var http: String,
    @ColumnInfo(name = "url") var url: String,
    @ColumnInfo(name = "headers") var headers: List<Pairs>?,
    @ColumnInfo(name = "parameters") var parameters: List<Pairs>?,
    @ColumnInfo(name = "bodyType") var bodyType: String?,
    @ColumnInfo(name = "body") var body: String?,
    @ColumnInfo(name = "time") var time: String
) {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = null

}