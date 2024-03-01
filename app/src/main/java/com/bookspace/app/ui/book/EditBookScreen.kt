package com.bookspace.app.ui.book

import android.app.AlertDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import java.time.LocalDate

@Composable
fun EditBookScreen(navController: NavHostController, id: String?) {

    val viewModel = hiltViewModel<EditBookViewModel>()
    val bookInput = viewModel.bookInput.value
    val context = LocalContext.current

    val errorMessage by viewModel.errorMessage.observeAsState()
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val dialog = remember { mutableStateOf<AlertDialog?>(null) }
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(id) {
        if (id != null) {
            viewModel.loadBook(id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Book") },
                actions = {
                    IconButton(onClick = { viewModel.saveBook(navController) }) {
                        Icon(Icons.Filled.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(
                bottom = innerPadding.calculateBottomPadding(),
                top = innerPadding.calculateTopPadding()
            )
        ) {
            OutlinedTextField(
                value = bookInput.name,
                onValueChange = viewModel::onNameChanged,
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = bookInput.author!!,
                onValueChange = viewModel::onAuthorChanged,
                label = { Text("Author") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = bookInput.isbn!!,
                onValueChange = viewModel::onIsbnChanged,
                label = { Text("ISBN") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
//
            OutlinedTextField(
                value = selectedDate.toString(),
                onValueChange = viewModel::onPublicationDateChanged,
                label = { Text("Publication Date") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        dialog.value?.dismiss()
                        dialog.value = AlertDialog.Builder(context).apply {
                            val datePicker = DatePicker(context).apply {
                                init(
                                    selectedDate.year,
                                    selectedDate.monthValue - 1,
                                    selectedDate.dayOfMonth
                                ) { _, year, month, dayOfMonth ->
                                    selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                                    bookInput.publication = selectedDate.toString()
                                }
                            }
                            setView(datePicker)
                            setPositiveButton("OK") { _, _ -> }
                        }.show()
                    }) {
                        Icon(
                            Icons.Filled.DateRange,
                            contentDescription = null,
                            Modifier.size(32.dp)
                        )
                    }
                },
                readOnly = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (id != null) {
                Button(
                    onClick = { viewModel.deleteBook(id, navController) },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                ) {
                    Text("Delete Book")
                }
            }

            if (viewModel.isLoading.value) {
                LoadingItem()
            }
        }
    }
}
