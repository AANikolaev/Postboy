package nikolaev.postboy.model.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  Created by Alexander Nikolaev on 3/28/19.
 *  alexandr.nikolaev.dev@gmail.com
 */

@Entity(tableName = "request_entity")
class RequestEntity {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = null

    @ColumnInfo(name = "url")
    var url: String? = null

}