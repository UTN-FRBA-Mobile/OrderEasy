package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.Login
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.MainMenu

@Composable
fun InitialNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login"
        ){
        composable(route="login"){ Login(navController)}
        composable(route="mainmenu"){ MainMenu(navController)}
    }
}