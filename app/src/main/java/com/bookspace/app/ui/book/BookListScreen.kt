package com.bookspace.app.ui.book

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bookspace.app.data.api.model.book.Book
import com.bookspace.app.ui.Screen

@Composable
fun BookListScreen(navController: NavHostController) {

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Book Space") })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(Screen.AddBook.route)
        }) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) { BookList(navController) }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookList(navController: NavHostController) {
    val bookListViewModel = hiltViewModel<BookListViewModel>()

    val bookList = bookListViewModel.bookList.collectAsLazyPagingItems()
    var refreshing by remember {
        mutableStateOf(false)
    }
    val pullRefreshState = rememberPullRefreshState(refreshing, {
        bookList.refresh()

    })

    LaunchedEffect(Unit) {
        refreshing = true
        bookList.refresh()
        refreshing = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(modifier = Modifier.padding(8.dp), content = {
            items(bookList.itemCount) {
                val data = bookList[it] ?: return@items
                BookItem(data = data) { book ->
                    navController.navigate(Screen.AddBook.route + "/${book.id}")
                }
            }

            bookList.apply {
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                val loading = when {
                    loadState.prepend is LoadState.Loading -> loadState.prepend as LoadState.Loading
                    loadState.append is LoadState.Loading -> loadState.append as LoadState.Loading
                    loadState.refresh is LoadState.Loading -> loadState.refresh as LoadState.Loading
                    else -> null
                }

                if (loading != null) {
                    item { LoadingItem() }
                }

                if (error != null) {
                    //TODO: add error handler
                }
            }
        })
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun BookItem(data: Book, onClick: (Book) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 0.5.dp, color = Color.Gray, shape = RoundedCornerShape(
                            topStart = 8.dp, bottomEnd = 8.dp
                        )
                    )
                    .padding(horizontal = 16.dp)
                    .clickable { onClick(data) }
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = data.name)
                Spacer(modifier = Modifier.height(16.dp))
                Column {
                    BookItemTv(text = "Author：${data.author}")
                    Spacer(modifier = Modifier.width(8.dp))
                    BookItemTv(text = "publication：${data.publication}")
                    Spacer(modifier = Modifier.width(8.dp))
                    BookItemTv(text = "ISBN：${data.isbn}")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun BookItemTv(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}