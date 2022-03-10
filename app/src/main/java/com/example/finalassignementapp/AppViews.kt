package com.example.finalassignementapp

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.finalassignementapp.ui.theme.MintGreen
import com.example.finalassignementapp.ui.theme.PurpleLila
import java.util.*

@Composable
fun MainView(){
    //Showing the login view if no user already connected, menu else

    val userVM = viewModel<UserViewModel>()

    if(userVM.username.value.isEmpty()){
        LoginView(userVM)
    }else {
        ScaffoldView()
    }
}

@Composable
fun ScaffoldView(){

    val newsVM = viewModel<NewsViewModel>()
    val navController = rememberNavController()

    Scaffold(
        topBar = {TopBarView()},
        bottomBar = {BottomBarView(navController)},
        content = {MainContentView(navController)}
    )
}

@Composable
fun MainContentView(navController: NavHostController) {
    val newsVM = viewModel<NewsViewModel>()

    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            HomeView()
        }
        composable(route = "food") {
            DataView()
        }
        composable(route = "news") {
            NewsView(newVM = newsVM)
        }
    }
}

@Composable
fun HomeView(){
    val userVM = viewModel<UserViewModel>()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                Brush.verticalGradient(
                    colors = listOf(PurpleLila, Color.Transparent, Color.Transparent, PurpleLila)
                )
            )
        ){
        Text(text = "You are logged in "+userVM.username.value)
    }
}

@Composable
fun TopBarView(){

    val userVM = viewModel<UserViewModel>()

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Black)
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = userVM.username.value, color = Color.White)
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
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedButton(onClick = { userVM.login(email,passwd) }, modifier = Modifier.padding(top = 5.dp)) {
            Text(text = "Login")
        }
    }
}

@Composable
fun NewsView(newVM: NewsViewModel) {

    val userVM = viewModel<UserViewModel>()
    var newsText by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                colors = listOf(MintGreen, Color.Transparent, Color.Transparent, MintGreen)
            )
        )
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = newsText ,
            onValueChange = { newsText = it },
            label = { Text(text = "News text") })

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = { newVM.addNews( News(newsText,userVM.username.value, Calendar.getInstance().time)) }
        ) {
            Text(text = "Add a news")
        }

        Spacer(modifier = Modifier.height(10.dp))

        newVM.news.value.forEach {
            Divider(thickness = 2.dp)
            Text(text = it.message)
        }
        Divider(thickness = 2.dp)
    }
}

@Composable
fun DataView(){

}