package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import androidx.navigation.navDeepLink
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.CallMozo
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.ChallengeTicket
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.CloseTable
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.DivideTicket
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.IndividualTicket
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.InviteTicket
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.MainMenu
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.MakeOrder
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.OrdersState
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.ReadMenu
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.RequestTicket
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.ScanQr
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.MenuViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel

@Composable
fun MainNavigation (tabStateViewModel: TableViewModel,menuStateViewModel: MenuViewModel,usuarioViewModel:UserViewModel) {
    val navCont= rememberNavController()
    //val uri ="order://easy.com"
    NavHost(
        navController = navCont,
        startDestination = "mainmenu"
    ){
        composable(route="mainmenu"){ MainMenu(navCont,usuarioViewModel,tabStateViewModel) }
        composable(route="readmenu"){ ReadMenu(navCont,menuStateViewModel,usuarioViewModel) }
        composable(route="makeorder"){ MakeOrder(navCont,menuStateViewModel,usuarioViewModel) }
        composable(route="callmozo"){ CallMozo(navCont,usuarioViewModel) }
        composable(route="scanqr"){ ScanQr(navCont,usuarioViewModel) }
        composable(route="closetable"){ CloseTable(navCont,usuarioViewModel) }
        composable(route="ordersstate"){OrdersState(navCont,tabStateViewModel,usuarioViewModel) }
        //composable(route="requestTicket",deepLinks= listOf(navDeepLink{uriPattern= "$uri/reqTicket"}) ){ RequestTicket(navCont,usuarioViewModel,tabStateViewModel) }
        composable(route="requestTicket" ){ RequestTicket(navCont,usuarioViewModel,tabStateViewModel) }
        composable(route="individualTicket"){ IndividualTicket(navCont,usuarioViewModel) }
        composable(route="divideTicket"){ DivideTicket(navCont,usuarioViewModel, tabStateViewModel) }
        composable(route="inviteTicket"){ InviteTicket(navCont,usuarioViewModel,tabStateViewModel)}
        composable(route="challengeTicket"){ ChallengeTicket(navCont,usuarioViewModel,tabStateViewModel) }
    }
}