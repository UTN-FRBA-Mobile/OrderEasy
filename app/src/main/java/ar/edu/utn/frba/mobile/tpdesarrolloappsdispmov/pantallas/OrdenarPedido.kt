package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.BarraSuperior
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloMenu
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.Plato
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario
import coil.compose.rememberAsyncImagePainter
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R

@Composable
fun OrdenarPedido (navCont: NavController, menu: VistaModeloMenu, userViewModel: VistaModeloUsuario) {
    var total:Float = remember { 0.0f }
    total = menu.getTotalCartPrice()

    Scaffold (
        topBar = { BarraSuperior(userViewModel)},
        bottomBar = { BottomAppBarMakeOrder(navCont, menu, userViewModel)},
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                if (menu.estadoMenu.pedidos.isEmpty()) {
                    EmptyCartMessage(navCont)
                } else {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(menu.estadoMenu.pedidos) { p ->
                            if (p.estado == stringResource(id = R.string.plato_selected)) {
                                var e = menu.estadoMenu.platos.find { m -> m.idPlato == p.idPlato }
                                if (e != null) {
                                    var quantity = menu.estadoMenu.pedidos.find { p -> (p.idPlato == e.idPlato && p.estado == stringResource(id = R.string.plato_selected)) }?.cantidad ?: 0
                                    FoodCardMakeOrder(food = e,
                                        quantity = quantity,
                                        modifier = Modifier.padding(16.dp),
                                        onAddToCart = {
                                            menu.addItem(e.idPlato)
                                        },
                                        onRemoveToCart = {
                                            menu.delItem(e.idPlato)
                                        })
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun FoodCardMakeOrder(food: Plato, quantity: Int, modifier: Modifier, onAddToCart: () -> Unit, onRemoveToCart: () -> Unit){
    Surface (shape = RoundedCornerShape (8.dp), shadowElevation = 8.dp, modifier = modifier) {
        Column (modifier = Modifier.fillMaxWidth()) {
            val painter = rememberAsyncImagePainter(model = food.imagen)
            Box(
                modifier = Modifier
                    .height(144.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .wrapContentSize()
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)) {
                Text(text = food.nombre, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding (bottom = 4.dp))
            }
            Column (modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp)) {
                QuantitySelector(quantity, food.precio, onAddToCart, onRemoveToCart)
            }
        }
    }
}

@Composable
fun BottomAppBarMakeOrder(navCont: NavController, menu: VistaModeloMenu, userViewModel: VistaModeloUsuario) {
    var showDialog by remember { mutableStateOf(false) }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color.DarkGray, Color.White),
        startY = 1.0f,
        endY = 10.0f
    )
    Row(modifier = Modifier
        .fillMaxWidth()
        .border(width = 2.dp, brush = gradientBrush, shape = RectangleShape)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp, top = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Total: ",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
                Text(
                    text = "$${menu.getTotalCartPrice()}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
            }

            Button(
                onClick = {
                    if(menu.estadoMenu.pedidos.size > 0){
                        menu.orderItem(userViewModel.estadoUser.idMesa,userViewModel.estadoUser.idCliente)
                        navCont.navigate(route="mainmenu")
                    } else {
                        showDialog = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(top = 5.dp),
                shape = RoundedCornerShape(3.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                )
            ) {
                Text(text = stringResource(id = R.string.order_confirm), color= Color.Black)
            }
        }

        if (showDialog) {
            AlertDialog(
                containerColor = Color(251, 201, 143, 255),
                icon = { Icon(Icons.Default.Info, "make-order") },
                title = { Text(text = stringResource(id = R.string.order_dialog_title)) },
                text = { Text(text = stringResource(id = R.string.order_dialog_txt)) },
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(
                        colors = ButtonDefaults.buttonColors (
                            containerColor = MaterialTheme.colorScheme.inverseSurface,
                        ),
                        onClick = { showDialog = false }
                    ) {
                        Text(text =stringResource(id = R.string.btn_ok))
                    }
                }
            )
        }
    }
}

@Composable
fun EmptyCartMessage(navCont: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.order_dialog_vacio),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(14.dp),
            onClick = { navCont.navigate(route="readmenu")},
            icon = { Icon(imageVector = ImageVector.vectorResource(id = R.drawable.baseline_menu_book_24),
                    contentDescription ="carta",
                    modifier=Modifier.size(30.dp)
                    )
                   },
            text = { Text(text = stringResource(id = R.string.btn_ok)) },
        )
    }
}
