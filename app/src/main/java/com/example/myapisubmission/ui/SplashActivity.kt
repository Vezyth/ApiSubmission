package com.example.myapisubmission.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import com.example.myapisubmission.databinding.ActivitySplashBinding


class SplashActivity() : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding



    private val splash_time:Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()



        Glide.with(this@SplashActivity)
            .load("https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png")
            .into(binding.splashImg)


        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, splash_time)
    }


}