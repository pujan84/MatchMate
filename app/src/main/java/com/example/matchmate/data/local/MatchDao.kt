package com.example.matchmate.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MatchDao {

    @Query("SELECT * FROM matches")
    fun getAllMatches(): LiveData<List<MatchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matches: List<MatchEntity>)

    @Query("UPDATE matches SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: String, status: String)

    @Query("SELECT COUNT(*) FROM matches")
    suspend fun getCount(): Int

    @Query("DELETE FROM matches")
    suspend fun clear()
}