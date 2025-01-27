package com.devspace.myapplication.detail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.common.model.RecipesDto
import com.devspace.myapplication.ui.theme.EasyRecipesTheme


@Composable
fun RecipesDetailScreen(
    recipeId: String,
    navHostController: NavHostController,
    detailViewModel: RecipesDetailViewModel
) {
    val recipeDto by detailViewModel.uiDetail.collectAsState()
    detailViewModel.fetchRecipeDetail(recipeId)

    recipeDto?.let {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    detailViewModel.cleanRecipesId()
                    navHostController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = it.title
                )
            }
            RecipesDetailContent(it)
        }
    }
}

@Composable
private fun RecipesDetailContent(recipe: RecipesDto) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = recipe.image,
            contentDescription = "${recipe.title} image"
        )
        Text(
            modifier = Modifier.padding(16.dp),
            fontSize = 16.sp,
            text = recipe.summary
        )
    }

}


@Preview(showBackground = true)
@Composable
private fun MovieDetailPreview() {
    EasyRecipesTheme {

    }

}