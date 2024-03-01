package com.bookspace.app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bookspace.app.ui.book.BookListScreen
import com.bookspace.app.ui.book.EditBookScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.BookList.route,
        ) {
            composable(
                route = Screen.BookList.route,
            ) {
                BookListScreen(
                    navController = navController,
                )
            }
            composable(
                route = "${Screen.AddBook.route}/{id}", arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType  //类型
                        nullable = true
                    }
                )
            ) {
                val id = it.arguments?.getString("id")
                EditBookScreen(navController, id)
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object BookList : Screen("bookList")
    data object AddBook : Screen("addBook")
}
