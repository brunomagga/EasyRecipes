package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devspace.myapplication.List.presentation.RecipesListViewModel
import com.devspace.myapplication.List.presentation.ui.RecipesListScreen
import com.devspace.myapplication.Search.presentation.SearchRecipesScreen
import com.devspace.myapplication.detail.presentation.RecipesDetailScreen
import com.devspace.myapplication.detail.presentation.RecipesDetailViewModel

@Composable
fun RecipesApp (
    listViewModel: RecipesListViewModel,
    detailViewModel: RecipesDetailViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "recipesList" ){
        composable(route = "recipesList"){
            RecipesListScreen(navController, listViewModel)
        }
        composable(
            route = "recipesDetail" + "/{itemId}",
            arguments = listOf(navArgument("itemId"){
                type = NavType.StringType
            })

        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getString("itemId"))
            RecipesDetailScreen(movieId, navController, detailViewModel)
        }
        composable(
            route = "search_recipes" + "/{query}",
            arguments = listOf(navArgument("query"){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("query"))
            SearchRecipesScreen(id, navController)
        }
    }
}