package com.example.matchmate

import android.app.Application
import com.example.matchmate.data.local.MatchDatabase

class MatchMateApplication : Application() {

    val database by lazy {
        MatchDatabase.getDatabase(this)
    }
}