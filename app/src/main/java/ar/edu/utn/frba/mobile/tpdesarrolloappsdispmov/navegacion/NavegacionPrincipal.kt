package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.navegacion

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
        startDestination ="menuprincipal"
    ){
        composable(route="menuprincipal"){ MenuPrincipal(controladorNav,vistaModeloUsuario,vistaModeloEstadoMesa) }
        composable(route="leercartaplatos"){ LeerMenuPlatos(controladorNav,vistaModeloMenu,vistaModeloUsuario) }
        composable(route="ordenarpedido"){ OrdenarPedido(controladorNav,vistaModeloMenu,vistaModeloUsuario) }
        composable(route="llamarmozo"){ LlamarMozo(controladorNav,vistaModeloUsuario) }
        composable(route="cerrarmesa"){ CerrarMesa(controladorNav,vistaModeloUsuario) }
        composable(route="estadoordenes"){EstadoDeOrdenes(controladorNav,vistaModeloEstadoMesa,vistaModeloUsuario) }
        composable(route="pedircuenta"){ PedirCuenta(controladorNav,vistaModeloUsuario,vistaModeloEstadoMesa) }
        composable(route="cuentaindividual"){ CuentaIndividual(controladorNav,vistaModeloUsuario) }
        composable(route="cuentadividida"){ CuentaDividida(controladorNav,vistaModeloUsuario, vistaModeloEstadoMesa) }
        composable(route="cuentainvitados"){ CuentaInvitados(controladorNav,vistaModeloUsuario,vistaModeloEstadoMesa)}
        composable(route="notificacion"){Notificacion(controladorNav,vistaModeloUsuario)}
    }
}