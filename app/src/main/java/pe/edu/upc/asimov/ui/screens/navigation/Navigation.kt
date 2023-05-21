package pe.edu.upc.asimov.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.asimov.ui.screens.login.Login
import pe.edu.upc.asimov.ui.screens.test.Test

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login"){
        composable("login"){
            Login(goTest = {
                navController.navigate("test")
            })
        }
        composable("test"){
            Test(goBack = {
                navController.popBackStack()
            })
        }
    }
}