package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.CallMozo
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.CloseTable
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.MainMenu
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.MakeOrder
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.OrdersState
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.ReadMenu
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.RequestTicket
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens.ScanQr
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.MenuViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel

@Composable
fun MainNavigation (tabStateViewModel: TableViewModel,menuStateViewModel: MenuViewModel) {
    val navCont= rememberNavController()
    NavHost(
        navController = navCont,
        startDestination = "mainmenu"
    ){
        composable(route="mainmenu"){ MainMenu(navCont) }
        composable(route="readmenu"){ ReadMenu(navCont,menuStateViewModel) }
        composable(route="makeorder"){ MakeOrder(navCont = navCont,menuStateViewModel) }
        composable(route="callmozo"){ CallMozo(navCont) }
        composable(route="scanqr"){ ScanQr(navCont) }
        composable(route="closetable"){ CloseTable(navCont) }
        composable(route="requestticket"){ RequestTicket(navCont) }
        composable(route="ordersstate"){ OrdersState(navCont,tabStateViewModel) }
    }
}