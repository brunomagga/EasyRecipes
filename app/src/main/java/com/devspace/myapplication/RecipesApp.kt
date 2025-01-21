package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun RecipesApp () {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "recipesList" ){
        composable(route = "recipesList"){
            RecipesListScreen(navController)
        }
        composable(
            route = "recipesDetail" + "/{itemId}",
            arguments = listOf(navArgument("itemId"){
                type = NavType.StringType
            })

        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getString("itemId"))
            RecipesDetailScreen(movieId, navController)
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