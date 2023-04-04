package com.example.myapisubmission.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myapisubmission.database.Favs
import com.example.myapisubmission.database.FavsDao
import com.example.myapisubmission.database.FavsRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavsRepository(application: Application) {
    private val mFavsDao: FavsDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavsRoomDatabase.getDatabase(application)
        mFavsDao = db.favsDao()
    }
    fun getAllFavs(): LiveData<List<Favs>> = mFavsDao.getAllFavs()
    fun insert(favs: Favs) {
        executorService.execute { mFavsDao.insert(favs) }
    }
    fun delete(favs: Favs) {
        executorService.execute { mFavsDao.delete(favs) }
    }
    fun update(favs: Favs) {
        executorService.execute { mFavsDao.update(favs) }
    }
}