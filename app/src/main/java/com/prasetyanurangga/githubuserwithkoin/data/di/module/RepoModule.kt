package com.prasetyanurangga.githubuserwithkoin.data.di.module

import com.prasetyanurangga.githubuserwithkoin.data.repository.UserRepository
import com.prasetyanurangga.githubuserwithkoin.data.repository.UserRepositoryImpl
import com.prasetyanurangga.githubuserwithkoin.data.service.ApiService
import org.koin.dsl.module

val repoModule = module {
    single<UserRepository> {
        return@single UserRepositoryImpl(get())
    }
}