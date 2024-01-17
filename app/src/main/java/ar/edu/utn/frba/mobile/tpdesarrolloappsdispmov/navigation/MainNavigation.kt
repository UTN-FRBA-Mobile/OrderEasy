package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.navigation

//import android.util.Log
import androidx.compose.runtime.Composable
//import androidx.navigation.NavDeepLink
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
//import androidx.navigation.navArgument
//import androidx.navigation.navDeepLink
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.CallMozo
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.ChallengeTicket
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.CloseTable
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.Desafio
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.DivideTicket
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.Game
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.IndividualTicket
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.InviteTicket
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.MainMenu
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.MakeOrder
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.Notificacion
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.OrdersState
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.ReadMenu
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.RequestTicket
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.MenuViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

@Composable
fun MainNavigation (tabStateViewModel: TableViewModel,menuStateViewModel: MenuViewModel,usuarioViewModel:UserViewModel,navController:NavHostController) {
    val uri ="order://easy"
    NavHost(
        navController = navController,
        startDestination = "mainmenu"
    ){
        composable(route="mainmenu"){ MainMenu(navController,usuarioViewModel,tabStateViewModel) }
        composable(route="readmenu"){ ReadMenu(navController,menuStateViewModel,usuarioViewModel) }
        composable(route="makeorder"){ MakeOrder(navController,menuStateViewModel,usuarioViewModel) }
        composable(route="callmozo"){ CallMozo(navController,usuarioViewModel) }
        composable(route="closetable"){ CloseTable(navController,usuarioViewModel) }
        composable(route="ordersstate"){OrdersState(navController,tabStateViewModel,usuarioViewModel) }
        composable(route="requestticket"){ RequestTicket(navController,usuarioViewModel,tabStateViewModel) }
        composable(route="individualTicket"){ IndividualTicket(navController,usuarioViewModel) }
        composable(route="divideTicket"){ DivideTicket(navController,usuarioViewModel, tabStateViewModel) }
        composable(route="inviteTicket"){ InviteTicket(navController,usuarioViewModel,tabStateViewModel)}
        composable(route="challengeTicket"){ ChallengeTicket(navController,usuarioViewModel,tabStateViewModel) }
        composable(route="desafio"){ Desafio(navController,usuarioViewModel) }
        composable(route="game"){ Game(navController,usuarioViewModel) }
        composable(route="notificacion",
            /*deepLinks= listOf(NavDeepLink("$uri/notif/{total}/{cantidad}/{pago}")),
            arguments = listOf(
                navArgument("total"){type= NavType.StringType
                    defaultValue=""},
                navArgument("pago"){type= NavType.StringType
                    defaultValue=""},
                navArgument("cantidad"){type = NavType.StringType
                    defaultValue=""})*/
        ){
            //entry ->
            Notificacion(navController,usuarioViewModel)
                /*entry.arguments?.getString("total")?:"",
                entry.arguments?.getString("pago")?:"",
                entry.arguments?.getString("cantidad")?:""*/

        }
    }
}