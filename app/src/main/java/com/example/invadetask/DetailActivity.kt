package com.example.invadetask

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.invadetask.models.University
import com.google.gson.Gson

class DetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)
        val composeView = findViewById<ComposeView>(R.id.details_compose_view)
        val json = intent.getStringExtra("selectedUniversity")
        val selectedUniversity = Gson().fromJson(json, University::class.java)

        composeView.setContent {

            UniversityDetails(selectedUniversity,LocalContext.current)

        }
    }
}

@Composable
fun UniversityDetails(university: University,context: Context) {

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            val resultIntent = Intent().apply {
                putExtra("result_key", "refresh")
            }
            (context as? Activity)?.setResult(Activity.RESULT_OK, resultIntent)
            (context as? Activity)?.finish()
                         },modifier = Modifier.align(Alignment.End).padding(4.dp) ){
            Text("Refresh")
        }
        university.name?.let { Text(text = it,textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)) }
        university.stateProvince?.let { Text(text = it,textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)) }
        university.country?.let { Text(text = it,textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)) }
        university.alphaTwoCode?.let { Text(text = it,textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)) }
        university.webPages?.get(0)?.let { Text(text = it,textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)) }
    }
}
