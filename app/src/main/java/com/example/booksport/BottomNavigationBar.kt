package com.example.booksport

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController


@Composable
fun BottomNavigationBar(
    navController: NavController,
    selectedIndex: Int = 0,
    onItemSelected: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    BottomNavigation(backgroundColor = Color(0xFF1A237E), modifier = modifier
        .height(64.dp)
    ) {
        BottomNavigationItem(
            selected = selectedIndex == 0,
            onClick = { onItemSelected(0); navController.navigate("ExploreScreen") },
            icon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White,
                modifier = Modifier.scale(1.4f))
                   },
            label = { Text("explore", color = Color.White) }
        )
        BottomNavigationItem(
            selected = selectedIndex == 1,
            onClick = { onItemSelected(1) },
            icon = { Icon(Icons.Default.Home, contentDescription = null, tint = Color.White,
                modifier = Modifier.scale(1.4f)) },
            label = { Text("dashboard", color = Color.White) }
        )
        BottomNavigationItem(
            selected = selectedIndex == 2,
            onClick = { onItemSelected(2) },
            icon = { Icon(Icons.Default.AccountBox, contentDescription = null, tint = Color.White,
                modifier = Modifier.scale(1.4f)) },
            label = { Text("booking", color = Color.White) }
        )
    }
}
