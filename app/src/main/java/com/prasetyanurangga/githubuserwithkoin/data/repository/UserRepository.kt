package com.prasetyanurangga.githubuserwithkoin.data.repository

import com.prasetyanurangga.githubuserwithkoin.data.model.UserModel
import com.prasetyanurangga.githubuserwithkoin.data.service.ApiService

interface UserRepository {
    suspend fun getUsers(q: String, page: Int, perPage: Int): List<UserModel>
}

class UserRepositoryImpl(private val apiService: ApiService): UserRepository {

    override suspend fun getUsers(q: String, page: Int, perPage: Int): List<UserModel> {
        return apiService.getUsers(q, page, perPage).items
    }

}