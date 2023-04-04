package com.example.myapisubmission.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.myapisubmission.retrofit.api.ApiConfig
import com.example.myapisubmission.retrofit.response.GitResponse
import com.example.myapisubmission.retrofit.response.ItemsItem
import com.example.myapisubmission.ui.settings.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel(private val preferences: SettingPreferences) : ViewModel() {
    private val _listUsers = MutableLiveData<List<ItemsItem>>()
    val listUsers: LiveData<List<ItemsItem>> = _listUsers





    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getTheme()=preferences.getThemeSetting().asLiveData()

    companion object{
        const val TAG = "MainViewModel"
        const val username = "arif"

    }

    init{
        searchUser(username)
    }





    fun searchUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(query)
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
    class Factory(private val preferences: SettingPreferences) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
    }

}