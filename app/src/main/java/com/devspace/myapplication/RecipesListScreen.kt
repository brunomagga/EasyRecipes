package com.devspace.myapplication

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RecipesListScreen(navController: NavHostController) {
    var randomRecipes by remember { mutableStateOf<List<RecipesDto>>(emptyList()) }



    LaunchedEffect(Unit) {
        val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)
        val callRandom = apiService.getRandom()

        callRandom.enqueue(object : Callback<RecipesResponse> {
            override fun onResponse(
                call: Call<RecipesResponse>,
                response: Response<RecipesResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("Response", "error.message ${response}")
                    randomRecipes = response.body()?.recipes ?: emptyList()
                    //onResult(recipes)

                } else {
                    Log.d("MainActivity", "Request Error ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<RecipesResponse>, t: Throwable) {
                Log.d("MainActivity", "Network Error ${t.message}")
            }


        })


    }


    MainScreenContent(
        getRandom = randomRecipes,
        onSearchClicked = { query ->
            val tempCleanQuery = query.trim()
            if(tempCleanQuery.isNotEmpty()){
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
    onClick: (RecipesDto) ->Unit

) {
    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {

        var query by remember { mutableStateOf("") }
        SearchSession(
            label = "Find best recipes for cooking",
            query = query,
            onValueChanged = { newValue ->
                query = newValue
            },
            onSearchClicked = onSearchClicked
        )

        Text(
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp,
            text = "EasyRecipes"
        )



        RecipeSession(
            label = "Random Recipes",
            recipes = getRandom,
            onClick = onClick
        )
    }

}

@Composable
fun SearchSession(
    label: String,
    query: String,
    onValueChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 16.dp , end = 16.dp, top = 16.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        text = label
    )
    ERSearchBar(
        query = query,
        placeHolder = "Search recipes",
        onValueChange = onValueChanged,
        onSearchClicked = {
            onSearchClicked.invoke(query)
        }
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
