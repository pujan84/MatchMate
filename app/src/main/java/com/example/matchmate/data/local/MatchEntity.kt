package com.example.matchmate.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class MatchEntity(

    @PrimaryKey
    val id: String,

    val name: String,
    val age: Int,
    val city: String,
    val state: String,
    val country: String,

    val email: String,
    val phone: String,

    val image: String,

    // PENDING, ACCEPTED, DECLINED
    val status: String = "PENDING"
)