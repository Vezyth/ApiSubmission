package com.example.myapisubmission.ui.favs

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapisubmission.database.Favs
import com.example.myapisubmission.repository.FavsRepository

class FavsViewModel(application: Application) : ViewModel() {
    private val mFavsRepository: FavsRepository = FavsRepository(application)
    fun getAllFavs(): LiveData<List<Favs>> = mFavsRepository.getAllFavs()
}