package com.example.myapisubmission.database


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Favs(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String = "",
)