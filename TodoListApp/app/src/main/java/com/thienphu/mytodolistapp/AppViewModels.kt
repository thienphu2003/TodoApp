package com.thienphu.mytodolistapp

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.thienphu.mytodolistapp.ui.viewmodels.SharedViewModel

object AppViewModels {
    val Factory = viewModelFactory {
        initializer {
            val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ToDoApplication
            SharedViewModel(app.repository)
        }

    }
}