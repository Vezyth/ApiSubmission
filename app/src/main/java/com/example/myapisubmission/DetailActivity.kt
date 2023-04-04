package com.example.myapisubmission

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myapisubmission.databinding.ActivityDetailBinding
import com.example.myapisubmission.ui.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator



class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel


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

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

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
        binding.tvName.text = detail?.name
        binding.tvUser.text = detail?.login
        binding.tvFollower.text = "Followers ${detail?.followers.toString()}"
        binding.tvFollowing.text = "Followings ${detail?.following.toString()}"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }




}