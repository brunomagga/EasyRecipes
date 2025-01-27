package com.devspace.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.devspace.myapplication.List.presentation.RecipesListViewModel
import com.devspace.myapplication.detail.presentation.RecipesDetailViewModel
import com.devspace.myapplication.ui.theme.EasyRecipesTheme


class MainActivity : ComponentActivity() {

    private val listViewModel by viewModels<RecipesListViewModel> { RecipesListViewModel.Factory }
    private val detailViewModel by viewModels<RecipesDetailViewModel> { RecipesDetailViewModel.Factory  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyRecipesTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipesApp(
                        listViewModel = listViewModel,
                        detailViewModel = detailViewModel
                    )
                }
            }
        }
    }
}



