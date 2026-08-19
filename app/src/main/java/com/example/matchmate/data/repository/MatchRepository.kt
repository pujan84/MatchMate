package com.example.matchmate.data.repository

import androidx.lifecycle.LiveData
import com.example.matchmate.data.local.MatchDao
import com.example.matchmate.data.local.MatchEntity
import com.example.matchmate.data.remote.RetrofitClient

class MatchRepository(
    private val dao: MatchDao
) {

    val allMatches: LiveData<List<MatchEntity>> = dao.getAllMatches()

    suspend fun loadUsers(page: Int): Result<Unit> {

        return try {

            val response = RetrofitClient.api.getUsers(page = page)

            val users = response.results.map {

                MatchEntity(
                    id = it.login.uuid,
                    name = "${it.name.first} ${it.name.last}",
                    age = it.dob.age,
                    city = it.location.city,
                    state = it.location.state,
                    country = it.location.country,
                    email = it.email,
                    phone = it.phone,
                    image = it.picture.large,
                    status = "PENDING"
                )
            }

            dao.insertMatches(users)

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }

    suspend fun accept(id: String) {
        dao.updateStatus(id, "ACCEPTED")
    }

    suspend fun decline(id: String) {
        dao.updateStatus(id, "DECLINED")
    }

    suspend fun isDatabaseEmpty() = dao.getCount() == 0
}