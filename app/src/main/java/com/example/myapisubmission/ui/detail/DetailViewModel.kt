package com.example.myapisubmission.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapisubmission.retrofit.api.ApiConfig
import com.example.myapisubmission.retrofit.response.DetailResponse
import com.example.myapisubmission.retrofit.response.ItemsItem
import com.example.myapisubmission.database.Favs
import com.example.myapisubmission.repository.FavsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application :Application): ViewModel() {

    private val _detail = MutableLiveData<DetailResponse>()
    val detail: LiveData<DetailResponse> = _detail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following


    companion object{
        const val TAG = "DetailViewModel"
    }

    init {
        getUserDetail()
    }

    fun getUserDetail(query: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(query)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _detail.value = response.body()
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(
                call: Call<DetailResponse>, t: Throwable
            ) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollower(query: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(query)
        client.enqueue(object: Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _followers.value = response.body()
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}" )
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(query: String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(query)
        client.enqueue(object: Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _following.value = response.body()
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}" )
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private val mFavsRepository: FavsRepository = FavsRepository(application)
    fun insert(favs: Favs) {
        mFavsRepository.insert(favs)
    }
    fun delete(favs: Favs) {
        mFavsRepository.delete(favs)
    }

    fun getFavsByUser(username: String): List<Favs> = mFavsRepository.getFavsByUser(username)








}