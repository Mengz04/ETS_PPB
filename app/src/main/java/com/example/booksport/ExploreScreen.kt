package com.example.booksport

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(navController: NavController) {
    val courts = CourtRepository.getAllCourts()

    Column(modifier = Modifier.fillMaxSize()) {
        var query by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(Color(0xFF1A237E))
        ) {
            Text("Muhammad Rafi Insan Fillah (5025211169)", fontSize = 12.sp, color = Color.White)
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { expanded = false },
                active = false,
                onActiveChange = { expanded = it },
                placeholder = { Text("Search for sport facilities...") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ){

            }
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(courts) { court ->
                if(court.name.contains(query, ignoreCase = true) || court.jenisOlahraga.contains(query, ignoreCase = true)){
                    CourtItem(court, navController)
                }
            }
        }
        BottomNavigationBar(navController)
    }
}
