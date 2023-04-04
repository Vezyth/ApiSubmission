package com.example.myapisubmission

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapisubmission.databinding.FragmentFollowBinding



class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = ""
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        var position = 0
        var username = arguments?.getString(ARG_USERNAME)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1){
            showLoading(true)
            username?.let {detailViewModel.getFollower(it)}
            detailViewModel.followers.observe(viewLifecycleOwner) {
                setFollowData(it)
                showLoading(false)
            }
        } else {
            showLoading(true)
            username?.let { detailViewModel.getFollowing(it) }
            detailViewModel.following.observe(viewLifecycleOwner) {
                setFollowData(it)
                showLoading(false)
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun setFollowData(usersData : List<ItemsItem>) {
        binding.apply {
            binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
            val dataName = usersData.map{ it.login }
            val dataPhoto = usersData.map { it.avatarUrl }
            val listUsers = ArrayList<Users>()
            for (i in dataName.indices) {
                val user = Users(dataName[i],  dataPhoto[i])
                listUsers.add(user)
            }
            val adapter = UserAdapter(listUsers)
            binding.rvFollow.adapter = adapter
        }

    }
}