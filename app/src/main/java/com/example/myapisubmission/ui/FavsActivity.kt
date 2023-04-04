package com.example.myapisubmission.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide.init
import com.example.myapisubmission.R
import com.example.myapisubmission.SettingActivity
import com.example.myapisubmission.database.Favs
import com.example.myapisubmission.database.FavsDao
import com.example.myapisubmission.database.FavsRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favs)
    }


}