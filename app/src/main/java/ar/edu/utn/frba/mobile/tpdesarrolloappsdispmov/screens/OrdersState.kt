package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.apiReqs.ReqsService
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.VolverBtn
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.TableViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel
import com.google.android.gms.common.util.Hex
import com.google.android.material.color.MaterialColors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrdersState(navCont: NavController, viewmodelo: TableViewModel,userViewModel: UserViewModel) {
    //val viewmodelo =EstadoPedidos(RetrofitHelper.getInstance())
    //val viewmodelo = remember {TableViewModel(ReqsService.instance)}
    Scaffold (
        topBar = { TopBar(userViewModel)},
        content = { innerPadding ->
            Column (modifier = Modifier.fillMaxSize().padding(innerPadding)){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Lista de estados de los pedidos de la mesa",
                    textAlign = TextAlign.Center
                )
                /*if(viewmodelo.state.requestingData){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
                        CircularProgressIndicator()
                    }
                }else{*/
                    LazyColumn(modifier = Modifier.fillMaxWidth()){
                        viewmodelo.estadoMesa.pedidosMesa.forEach { cli->
                            stickyHeader {
                                Row (
                                    modifier = Modifier.padding(vertical = 10.dp).height(38.dp).fillMaxWidth().background(Color(0xABD1E8FF)),
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Icon(imageVector = Icons.Filled.AccountCircle, contentDescription ="user" )
                                    Text(text = cli.nombre)
                                }
                            }
                            item {
                                cli.Pedidos.forEach {  ped ->
                                    ExtendedFloatingActionButton(
                                        modifier = Modifier.fillMaxWidth(),//.padding(14.dp),
                                        onClick = {},
                                        //Icon(painter = painterResource(id = icono)
                                        //icon = { Icon(painter = painterResource(id = R.drawable.baseline_flatware_24 ), contentDescription ="carta",modifier=Modifier.size(18.dp) ) },
                                        content = {
                                                Row ( verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(painter = painterResource(id = R.drawable.baseline_flatware_24 ), contentDescription ="carta",modifier=Modifier.size(20.dp).weight(1f) )
                                                    Text(text=ped.Plato.nombre+" (x"+ped.cantidad.toString()+")", modifier = Modifier.weight(8f))
                                                    Text(text = ped.estado, modifier = Modifier.weight(4f))
                                                }
                                                /*Text(
                                                    text = ped.Plato.nombre,
                                                    //textAlign = TextAlign.Justify,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(vertical = 6.dp),
                                                    fontFamily = FontFamily.SansSerif,
                                                    fontWeight = FontWeight.Normal,
                                                    style = TextStyle(
                                                        fontSize = 16.sp,
                                                        fontStyle = FontStyle.Normal
                                                    )
                                                )*/
                                        },
                                    )
                                }
                            }
                        }
                        item {
                            VolverBtn(navCont)
                        }
                    }
            }
        }
    )
}