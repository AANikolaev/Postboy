package nikolaev.postboy.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nikolaev.postboy.model.db.converter.PairConverter
import nikolaev.postboy.model.db.dao.RequestDao
import nikolaev.postboy.model.db.entities.RequestEntity

/**
 *  Created by Alexander Nikolaev on 3/28/19.
 *  alexandr.nikolaev.dev@gmail.com
 */

@Database(entities = [(RequestEntity::class)], version = 2, exportSchema = false)
@TypeConverters(PairConverter::class)
abstract class DatabaseProvider : RoomDatabase() {

    abstract fun requestDao(): RequestDao

    companion object {

        private const val DATABASE_NAME = "PostboyDatabase"

        /**
         * The only instance
         */
        private var instance: DatabaseProvider? = null

        /**
         * Gets the singleton instance of SampleDatabase.
         *
         * @param context The context.
         * @return The singleton instance of SampleDatabase.
         */
        @Synchronized
        fun getInstance(context: Context): DatabaseProvider {
            if (instance == null) {
                instance = Room
                    .databaseBuilder(context.applicationContext, DatabaseProvider::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}