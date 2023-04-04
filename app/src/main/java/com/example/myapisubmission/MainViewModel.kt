package com.example.myapisubmission

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel() {
    private val _listUsers = MutableLiveData<List<ItemsItem>>()
    val listUsers: LiveData<List<ItemsItem>> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading




    companion object{
        const val TAG = "MainViewModel"

    }

    init{
        setUsers()
    }



    private fun setUsers() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUsers("arif")
        client.enqueue(object : Callback<GitResponse> {
            override fun onResponse(

                call: Call<GitResponse>,
                response: Response<GitResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {

                    _listUsers.value = response.body()?.items

                } else {

                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GitResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}