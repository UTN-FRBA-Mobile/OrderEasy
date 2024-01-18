package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.navegacion

//import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas.LlamarMozo
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas.CerrarMesa
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas.CuentaDividida
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas.CuentaIndividual
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas.CuentaInvitados
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas.MenuPrincipal
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas.OrdenarPedido
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas.Notificacion
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas.EstadoDeOrdenes
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas.LeerMenuPlatos
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas.PedirCuenta
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloMenu
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloMesa
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario

@Composable
fun NavegacionPrincipal (vistaModeloEstadoMesa: VistaModeloMesa, vistaModeloMenu: VistaModeloMenu, vistaModeloUsuario:VistaModeloUsuario, controladorNav:NavHostController) {
    NavHost(
        navController = controladorNav,
        startDestination = "mainmenu"
    ){
        composable(route="mainmenu"){ MenuPrincipal(controladorNav,vistaModeloUsuario,vistaModeloEstadoMesa) }
        composable(route="readmenu"){ LeerMenuPlatos(controladorNav,vistaModeloMenu,vistaModeloUsuario) }
        composable(route="makeorder"){ OrdenarPedido(controladorNav,vistaModeloMenu,vistaModeloUsuario) }
        composable(route="callmozo"){ LlamarMozo(controladorNav,vistaModeloUsuario) }
        composable(route="closetable"){ CerrarMesa(controladorNav,vistaModeloUsuario) }
        composable(route="ordersstate"){EstadoDeOrdenes(controladorNav,vistaModeloEstadoMesa,vistaModeloUsuario) }
        composable(route="requestticket"){ PedirCuenta(controladorNav,vistaModeloUsuario,vistaModeloEstadoMesa) }
        composable(route="individualTicket"){ CuentaIndividual(controladorNav,vistaModeloUsuario) }
        composable(route="divideTicket"){ CuentaDividida(controladorNav,vistaModeloUsuario, vistaModeloEstadoMesa) }
        composable(route="inviteTicket"){ CuentaInvitados(controladorNav,vistaModeloUsuario,vistaModeloEstadoMesa)}
        composable(route="notificacion",
        ){
            Notificacion(controladorNav,vistaModeloUsuario)
        }
    }
}