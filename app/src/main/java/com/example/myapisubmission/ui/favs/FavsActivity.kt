package com.example.myapisubmission.ui.favs


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapisubmission.*
import com.example.myapisubmission.database.Favs
import com.example.myapisubmission.databinding.ActivityFavsBinding
import com.example.myapisubmission.ui.ViewModelFactory


class FavsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavsBinding
    private var list = ArrayList<Users>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(true)
        getFavorite()

    }


    private fun getFavorite() {
        val mFavorite = obtainViewModel(this@FavsActivity)
        mFavorite.getAllFavs().observe(this) { userFav ->
            if (userFav != null) {
                binding.rvUsers.visibility = View.VISIBLE
                setDataFavorite(userFav)
            }
        }
    }

    private fun setDataFavorite(userFav: List<Favs>) {
        list.clear()
        for (data in userFav) {
            val mFollow = Users(
                data.username ?: "",
                data.avatarUrl ?: ""
            )
            list.add(mFollow)
        }
        showLoading(false)
        showRecyleList()
    }

    private fun showRecyleList() {
        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@FavsActivity)
            val listFavAdapter = UserAdapter (list)
            rvUsers.adapter = listFavAdapter
            binding.progressBar.visibility = View.GONE
        }
    }








    private fun obtainViewModel(activity: AppCompatActivity): FavsViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavsViewModel::class.java]
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }



}