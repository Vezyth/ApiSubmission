package com.example.myapisubmission.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favs: Favs)
    @Delete
    fun delete(favs: Favs)
    @Query("SELECT * from favs ORDER BY username ASC")
    fun getAllFavs(): LiveData<List<Favs>>

    @Query("SELECT * FROM favs WHERE username = :username")
    fun getFavsByUser(username: String): List<Favs>
}