package com.prasetyanurangga.githubuserwithkoin

import android.app.Application
import com.prasetyanurangga.githubuserwithkoin.data.di.module.appModule
import com.prasetyanurangga.githubuserwithkoin.data.di.module.repoModule
import com.prasetyanurangga.githubuserwithkoin.data.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GithubUserApp: Application() {

    override fun onCreate(){
        super.onCreate()
        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@GithubUserApp)
            // declare modules
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}