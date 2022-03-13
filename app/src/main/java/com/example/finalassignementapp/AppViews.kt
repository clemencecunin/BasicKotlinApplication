package com.example.finalassignementapp

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalassignementapp.ui.theme.LightYellow
import com.example.finalassignementapp.ui.theme.MintGreen
import com.example.finalassignementapp.ui.theme.PurpleLila
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

@Composable
fun MainView(){

    val userVM = viewModel<UserViewModel>()

    if(userVM.username.value.isEmpty()){
        LoginView(userVM)
    }else {
        ScaffoldView(userVM)
    }
}

@Composable
fun ScaffoldView(userVM: UserViewModel){

    val navController = rememberNavController()

    Scaffold(
        topBar = {TopBarView(userVM)},
        bottomBar = {BottomBarView(navController)},
        content = {MainContentView(navController,userVM)}
    )
}

@Composable
fun MainContentView(navController: NavHostController,userVM: UserViewModel) {

    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            HomeView(userVM)
        }
        composable(route = "food") {
            DataView()
        }
        composable(route = "news") {
            NewsView(userVM = userVM)
        }
    }
}

@Composable
fun HomeView(userVM: UserViewModel){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PurpleLila, Color.Transparent, Color.Transparent, PurpleLila)
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        ){
        Card(
            modifier = Modifier.fillMaxWidth()
            .padding(15.dp)
            .clickable { },
            elevation = 10.dp,
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = "You are logged in with username :",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(text = userVM.username.value,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(bottom = 5.dp))
                Button(
                    onClick = { userVM.logout() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Text(text = "Log out", fontWeight = FontWeight.Bold)
                }
            }

        }

    }
}

@Composable
fun TopBarView(userVM: UserViewModel){

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Black)
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = userVM.useremail.value, color = Color.White)
        OutlinedButton(onClick = { userVM.logout() }) {
            Text(text = "Log out")
        }
    }
}

@Composable
fun BottomBarView(navController: NavHostController){
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Black),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.ic_home),
            tint = Color.White,
            contentDescription = "home",
            modifier = Modifier.clickable {  navController.navigate("home")  })
        Icon(
            painter = painterResource(id = R.drawable.ic_food),
            tint = Color.White,
            contentDescription = "food",
            modifier = Modifier.clickable {  navController.navigate("food")  })
        Icon(
            painter = painterResource(id = R.drawable.ic_news),
            tint = Color.White,
            contentDescription = "news",
            modifier = Modifier.clickable {  navController.navigate("news")  })
    }
}

@Composable
fun LoginView(userVM: UserViewModel) {

    var email by remember { mutableStateOf("") }
    var passwd by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PurpleLila, Color.Transparent, Color.Transparent, PurpleLila)
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(text = "Welcome !", fontWeight = FontWeight.Bold, fontSize = 30.sp, modifier = Modifier.padding(bottom = 5.dp))
        Text(text = "Log in", fontWeight = FontWeight.Bold, fontSize = 30.sp, modifier = Modifier.padding(bottom = 5.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {Text(text = "Email")})
        OutlinedTextField(
            value = passwd,
            onValueChange = {passwd = it},
            label = {Text(text = "Password")},
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(bottom = 5.dp)
        )
        OutlinedButton(onClick = { userVM.login(email,passwd) }, modifier = Modifier.padding(top = 5.dp)) {
            Text(text = "Login")
        }
    }
}

@Composable
fun NewsView(userVM: UserViewModel) {

    var newsText by remember { mutableStateOf("") }
    val newslist = remember { mutableStateOf(listOf<News>())}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(MintGreen, Color.Transparent, Color.Transparent, MintGreen)
                    )
                )
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Share a news with the community", fontSize = 30.sp)

            OutlinedTextField(
                value = newsText,
                onValueChange = { newsText = it },
                label = { Text(text = "News text") })

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = {
                    sendNews(
                        News(
                            newsText,
                            userVM.username.value,
                            Calendar.getInstance().time.toString()
                        )
                    )
                }
            ) {
                Text(text = "Add a news")
            }

            Divider(thickness = 2.dp)
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(onClick = { getAllNews(newslist) }) {
                Text(text = "Get news")
            }
            newslist.value.forEach {
                Column() {
                    Text(text = it.message)
                    Text(text = "By : ${it.author}")
                    Text(text = "Date : ${it.date}")
                }
                Divider(thickness = 2.dp)
            }
        }
    }



@Composable
fun DataView(){

    val foodVM = viewModel<FoodViewModel>()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                Brush.verticalGradient(
                    colors = listOf(LightYellow, Color.Transparent, Color.Transparent, LightYellow)
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "Don't know what to cook ?",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 5.dp, start = 10.dp)
        )
        Text(
            "Get a food inspiration",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 5.dp, start = 10.dp)
        )
        Button(
            onClick = { foodVM.getRandomFood() },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Text(text = "Click here", fontWeight = FontWeight.Bold)
        }
        if (foodVM.FoodInfoDish.value.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .clickable { },
                elevation = 10.dp,
            ) {
                Column(
                    modifier = Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.padding(bottom = 15.dp)
                    ) {
                        Text(text = "Name : ")
                        Text(
                            text = foodVM.FoodInfoDish.value.subSequence(
                                1,
                                foodVM.FoodInfoDish.value.lastIndex
                            ).toString(),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.padding(bottom = 15.dp)
                    ) {

                        Text(text = "Description : ")

                        Text(
                            text = foodVM.FoodInfoDescription.value.subSequence(
                                1,
                                foodVM.FoodInfoDescription.value.lastIndex
                            ).toString(),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.padding(bottom = 15.dp)
                    ) {
                        Text(text = "Ingredient(s) : ")
                        Text(
                            text = foodVM.FoodInfoIngredient.value.subSequence(
                                1,
                                foodVM.FoodInfoIngredient.value.lastIndex
                            ).toString(),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.padding(bottom = 15.dp)
                    ) {

                        Text(text = "Measurements : ")

                        Text(
                            text = foodVM.FoodInfoMeasurement.value.subSequence(
                                1,
                                foodVM.FoodInfoMeasurement.value.lastIndex
                            ).toString(),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }
        }
    }

    }
