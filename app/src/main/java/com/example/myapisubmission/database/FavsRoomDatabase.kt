package com.example.myapisubmission.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favs::class], version = 1)
abstract class FavsRoomDatabase : RoomDatabase() {
    abstract fun favsDao(): FavsDao
    companion object {
        @Volatile
        private var INSTANCE: FavsRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FavsRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavsRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavsRoomDatabase::class.java, "favs_database")
                        .build()
                }
            }
            return INSTANCE as FavsRoomDatabase
        }
    }
}