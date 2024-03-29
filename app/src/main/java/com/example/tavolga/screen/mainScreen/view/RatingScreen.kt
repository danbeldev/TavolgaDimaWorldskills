package com.example.tavolga.screen.mainScreen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tavolga.model.Nomination
import com.example.tavolga.navigation.Screen
import com.example.tavolga.ui.theme.background
import com.example.tavolga.ui.theme.white
import com.example.tavolga.utils.Converters

@Composable
fun RatingScreen(
    navController: NavController,
    nominationString: String,
    eventName:String,
) {
    val nomination = Converters().decodeFromString<Nomination>(nominationString)
    val idBar = remember { mutableStateOf("Home") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                backgroundColor = background,
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.Main.route){
                        popUpTo(Screen.Main.route){
                            inclusive = true
                        }
                    } }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            )
        }, bottomBar = {
            BottomNavigation(backgroundColor = background) {
                ButtonBarData.values().forEach { data ->
                    BottomNavigationItem(
                        selected = idBar.value == data.name,
                        onClick = { idBar.value = data.name },
                        label = {
                            Text(text = data.title)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = data.image),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        }, selectedContentColor = Color.Black,
                        unselectedContentColor = Color.White
                    )
                }
            }
        }, content = {
            when(idBar.value){
                "Profile"->{}
                "Home"-> {
                    Column {
                        Text(
                            text = "$eventName - ${nomination.name}",
                            modifier = Modifier.padding(10.dp),
                            fontWeight = FontWeight.Bold
                        )

                        LazyColumn(content = {
                            item{
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(10.dp)
                                    .background(background)
                                ){
                                    Column(Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = "Рейтинг пользователей")
                                    }
                                }
                            }
                            items(10){
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(10.dp)
                                    .clickable {
                                        navController.navigate(Screen.Criterie.base(
                                            username = "Иванов Иван Иванович $it",
                                            criterie = Converters().encodeToString(nomination.criteries)
                                        ))
                                    }
                                    .background(white)
                                ){
                                    Column(Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = "Иванов Иван Иванович $it")
                                    }
                                }
                            }
                        })
                    }
                }
                "Event"->{}
            }
        }
    )
}