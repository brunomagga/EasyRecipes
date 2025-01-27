package com.devspace.myapplication.List.presentation.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.List.presentation.RecipesListViewModel
import com.devspace.myapplication.common.model.RecipesDto
import com.devspace.myapplication.Search.presentation.SearchScreen


@Composable
fun RecipesListScreen(
    navController: NavHostController,
    viewModel: RecipesListViewModel
) {
    val randomRecipes by viewModel.uiGetRecipes.collectAsState()

    MainScreenContent(
        getRandom = randomRecipes,
        onSearchClicked = { query ->
            val tempCleanQuery = query.trim()
            if (tempCleanQuery.isNotEmpty()) {
                navController.navigate(route = "search_recipes/$tempCleanQuery")
            }
        },
        onClick =
        { itemClicked ->
            navController.navigate(route = "recipesDetail/${itemClicked.id}")
        }
    )
}


@Composable
fun MainScreenContent(
    getRandom: List<RecipesDto>,
    onSearchClicked: (String) -> Unit,
    onClick: (RecipesDto) -> Unit

) {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {


        Text(
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp,
            text = "EasyRecipes"
        )

        SearchScreen()


        Spacer(modifier = Modifier.padding(10.dp))

        RecipeSession(
            label = "Random Recipes",
            recipes = getRandom,
            onClick = onClick
        )
    }

}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String = "Search..."
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        keyboardActions = KeyboardActions(),

        singleLine = true
    )

}

@Composable
private fun RecipeSession(
    label: String,
    recipes: List<RecipesDto>,
    onClick: (RecipesDto) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        text = label
    )
    RecipeList(
        recipes = recipes,
        onClick = onClick
    )
}

@Composable
private fun RecipeList(
    modifier: Modifier = Modifier,
    recipes: List<RecipesDto>,
    onClick: (RecipesDto) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        items(recipes) {
            RecipeItem(
                recipe = it,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun RecipeItem(
    recipe: RecipesDto,
    onClick: (RecipesDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .width(IntrinsicSize.Min)
            .padding(8.dp)
            .clickable {
                onClick.invoke(recipe)
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = recipe.image, contentDescription = "${recipe.title} Image"
        )

        Spacer(modifier = Modifier.size(6.dp))
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            text = recipe.title
        )
        Text(
            fontSize = 14.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            text = recipe.summary
        )


    }
}
