package com.devspace.myapplication

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.ui.theme.EasyRecipesTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun RecipesDetailScreen(
    recipeId: String,
     navHostController: NavHostController) {
    var recipeDto by remember { mutableStateOf<RecipesDto?>(null) }

    val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)
    apiService.getRecipesInformation(recipeId).enqueue(
        object : Callback<RecipesDto> {
            override fun onResponse(call: Call<RecipesDto>, response: Response<RecipesDto>) {
                if (response.isSuccessful) {
                    recipeDto = response.body()
                } else {
                    Log.d("MainActivity", "Request Error ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<RecipesDto>, t: Throwable) {
                Log.d("MainActivity", "Network Error ${t.message}")
            }

        }
    )

    recipeDto?.let {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
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