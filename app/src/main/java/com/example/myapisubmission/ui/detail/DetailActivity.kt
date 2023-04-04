package com.example.myapisubmission.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myapisubmission.retrofit.response.DetailResponse
import com.example.myapisubmission.R
import com.example.myapisubmission.database.Favs
import com.example.myapisubmission.databinding.ActivityDetailBinding
import com.example.myapisubmission.ui.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private lateinit var detailViewModel: DetailViewModel

    private var favs: Favs? = null


    companion object {
        const val stringExtra = "login"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this, ViewModelFactory(application))[DetailViewModel::class.java]

        val intent = Intent(intent)
        val user = intent.getStringExtra(stringExtra)

        if (user != null){
            showLoading(true)
            detailViewModel.getUserDetail(user)

            showLoading(false)
        }

        detailViewModel.detail.observe(this) { detailUsers ->
            setDetail(detailUsers)
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }


        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = user.toString()
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()
        supportActionBar?.elevation = 0f
    }



    private fun setDetail(detail: DetailResponse?) {
        Glide.with(this@DetailActivity)
            .load(detail?.avatarUrl)
            .into(binding.profileImage)
        binding.tvUrl.text = detail?.avatarUrl
        binding.tvName.text = detail?.name
        binding.tvUser.text = detail?.login
        binding.tvFollower.text = "Followers ${detail?.followers.toString()}"
        binding.tvFollowing.text = "Followings ${detail?.following.toString()}"



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favs_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        favs = Favs(username ="", avatarUrl = "")
        when (item.itemId) {
            R.id.favsMenu -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val username = intent.getStringExtra(stringExtra).toString()
                    val checkFav = detailViewModel.getFavsByUser(username)
                    withContext(Dispatchers.Main) {
                        if (checkFav.isNotEmpty()) {

                            val user = binding?.tvUser?.text.toString().trim()
                            val profile = binding?.tvUrl?.text.toString().trim()
                            if(user!=""){
                                favs.let { favs ->
                                    favs?.username = user
                                    favs?.avatarUrl = profile
                                }
                                detailViewModel.delete(favs as Favs)
                                showToast("status :${user} berhasil di delete")
                            }else{
                                showToast("Belum Load")
                            }
                        }
                        else {

                            val user = binding?.tvUser?.text.toString().trim()
                            val profile = binding?.tvUrl?.text.toString().trim()
                            if(user!=""){
                                favs.let { favs ->
                                    favs?.username = user
                                    favs?.avatarUrl = profile

                                }
                                detailViewModel.insert(favs as Favs)
                                showToast("status :${user} berhasil di add")
                            }else{
                                showToast("Belum Load")
                            }
                        }
                    }
                }
                return true
            }




            else -> return true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE  else View.GONE

    }





}