package com.example.invadetask

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.invadetask.models.University
import com.example.invadetask.viewModels.UniversityViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val composeView = findViewById<ComposeView>(R.id.main_compose_view)
        composeView.setContent {
                    UniversityScreen()

            }
        }
    }



@Composable
fun UniversityListItem(university: University,context: Context,launcher: ActivityResultLauncher<Intent>) {
    Column(
        modifier = Modifier.fillMaxSize() .clickable {
            // Navigate to another activity
            val intent = Intent(context, DetailActivity::class.java)
            val json = Gson().toJson(university)
            intent.putExtra("selectedUniversity", json)
            launcher.launch(intent)

        }, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        university.name?.let { Text(text = it,textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(4.dp)) }
        university.country?.let { Text(text = it,textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(4.dp)) }
    }
}
@Composable
fun UniversityList(universities: List<University>,launcher: ActivityResultLauncher<Intent>) {

    LazyColumn {
        items(universities) { university ->
            UniversityListItem(university,LocalContext.current,launcher)
        }
    }
}

@Composable
fun UniversityScreen(universityViewModel: UniversityViewModel = viewModel()) {
    val universities = universityViewModel.universities.value
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data

            val resultValue = data?.getStringExtra("result_key")
            if(resultValue == "refresh"){
                universityViewModel.fetchDataFromApiAndInsertToDb()
            }
        }
    }
    UniversityList(universities,launcher)
}