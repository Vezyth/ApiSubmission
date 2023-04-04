package com.example.myapisubmission.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapisubmission.retrofit.response.ItemsItem
import com.example.myapisubmission.R
import com.example.myapisubmission.UserAdapter
import com.example.myapisubmission.Users
import com.example.myapisubmission.databinding.ActivityMainBinding
import com.example.myapisubmission.ui.favs.FavsActivity
import com.example.myapisubmission.ui.settings.SettingActivity
import com.example.myapisubmission.ui.settings.SettingPreferences


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainViewModel.listUsers.observe(this) { dataUsers ->
            setUsersData(dataUsers)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }



    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_view, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView




        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {

                binding.rvUsers.visibility = View.GONE
                Toast.makeText(this@MainActivity,query, Toast.LENGTH_SHORT).show()

                mainViewModel.searchUser(query)





                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {


                return false
            }
        })
        return true


    }




    private fun setUsersData(usersData: List<ItemsItem>) {
        val dataName = usersData.map{ it.login }
        val dataPhoto = usersData.map { it.avatarUrl }
        val listUsers = ArrayList<Users>()
        for (i in dataName.indices) {
            val user = Users(dataName[i],  dataPhoto[i])
            listUsers.add(user)
        }

        val adapter = UserAdapter(listUsers)
        binding.rvUsers.adapter = adapter
        binding.rvUsers.visibility = View.VISIBLE

    }







    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.favs -> {
                val i = Intent(this, FavsActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }



}
