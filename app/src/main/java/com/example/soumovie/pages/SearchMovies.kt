package com.example.soumovie.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.soumovie.components.NavBar
import com.example.soumovie.repository.SavedMovieRepository
import com.example.soumovie.viewmodel.MovieViewModel
import com.example.soumovie.viewmodel.SavedMoviesViewModel

@Composable
fun SearchMovies(navController: NavController, repository: SavedMovieRepository) {

    val viewModel = remember {
        SavedMoviesViewModel(repository)
    }

    val watchlist by viewModel.getWatchlist().observeAsState(emptyList())

    val movieViewModel: MovieViewModel = viewModel()
    val searchResults by movieViewModel.searchResults.observeAsState(emptyList())
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // 每次输入更新时触发搜索
    LaunchedEffect(searchQuery.text) {
        if (searchQuery.text.isNotBlank()) {
            movieViewModel.searchMovies(searchQuery.text)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp) // 给底部 NavBar 留空间
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // 搜索输入框
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Movies", color = Color.White) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 搜索结果展示
            if (searchResults.isEmpty() && searchQuery.text.isNotBlank()) {
                Text(
                    text = "No movies found.",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn {
                    items(searchResults) { movie ->
                        MovieItem(movie = movie, navController = navController)
                    }
                }
            }
        }

        // 底部导航栏
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomCenter)
        ) {
            NavBar(navController, "SearchMovies")
        }
    }
}
