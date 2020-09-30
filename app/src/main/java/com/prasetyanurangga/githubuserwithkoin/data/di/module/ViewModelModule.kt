package com.prasetyanurangga.githubuserwithkoin.data.di.module

import com.prasetyanurangga.githubuserwithkoin.ui.viewmodel.UserViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel {
        UserViewModel(get())
    }
}