package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.pantallas

import android.content.Context
import android.util.TypedValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloMenu
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.componentes.BarraSuperior
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.Plato
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.datosDeEstado.VistaModeloUsuario
import coil.compose.rememberAsyncImagePainter

@Composable
fun LeerMenuPlatos(navCont: NavController, menu: VistaModeloMenu, userViewModel: VistaModeloUsuario) {
    if (!menu.estadoMenu.menucargado){
        menu.obtenerMenu()
    }
    Scaffold(
        topBar = { BarraSuperior(userViewModel) },
        bottomBar = {
            BottomAppBarExample(navCont, menu)
        },
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                Surface(modifier = Modifier.fillMaxSize()) {
                    if(menu.estadoMenu.loadingMenu){
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ){
                            CircularProgressIndicator()
                        }
                        if (menu.estadoMenu.errorPedidoApi){
                            AlertDialog(
                                containerColor = Color(251, 201, 143, 255),
                                icon = { Icon(Icons.Default.Info, "call-mozo") },
                                title = { Text(text = stringResource(id = R.string.topbar_app)) },
                                text = { Text(text = stringResource(id = R.string.error_api)) },
                                onDismissRequest = {
                                    navCont.navigate(route = "menuprincipal")
                                    menu.cancelErrorReqApi()
                                },
                                confirmButton = {
                                    TextButton(
                                        colors = ButtonDefaults.buttonColors (
                                            containerColor = MaterialTheme.colorScheme.inverseSurface,
                                        ),
                                        onClick = {
                                            navCont.navigate(route = "menuprincipal")
                                            menu.cancelErrorReqApi()
                                        }
                                    ) {
                                        Text(text = stringResource(id = R.string.btn_ok))
                                    }
                                })
                        }
                    }else{
                        Menu(menu, userViewModel.estadoUsuario.idMesa !=0)
                    }
                }
            }
        }
    )
}

@Composable
fun Menu(menu: VistaModeloMenu, usuarioSentado:Boolean) {
    LazyColumn {
        items(menu.estadoMenu.platos) { food ->
            FoodCard(
                menu = menu,
                food = food,
                modifier = Modifier.padding(16.dp),
                onAddToCart =  {
                    menu.sumarItem(food.idPlato)
                },
                onRemoveToCart =  {
                    menu.restarItem(food.idPlato)
                },
                usuarioSentado
            )
        }
    }
}

@Composable
fun FoodCard(menu: VistaModeloMenu, food: Plato, modifier: Modifier, onAddToCart: () -> Unit, onRemoveToCart: () -> Unit, usuarioSentado: Boolean) {
    var quantity = menu.estadoMenu.pedidos.find{p -> (p.idPlato == food.idPlato && p.estado == "selected")}?.cantidad?:0

    Surface(
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 8.dp,
        modifier = modifier
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = food.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    val layout = LocalDensity.current.density
                    var context: Context = LocalContext.current
                    val intValue = spToPx(context, 15.0f)
                    val maxLines = 4
                    val descriptionLines = (food.descripcion.length * layout / intValue).toInt()

                    if (descriptionLines > 5) {
                        var isExpanded by remember { mutableStateOf(false) }
                        Text(
                            text = food.descripcion,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 4.dp),
                            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = if (isExpanded) stringResource(id = R.string.carta_less) else stringResource(
                                id = R.string.carta_more
                            ),
                            textDecoration = TextDecoration.Underline,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.clickable { isExpanded = !isExpanded }
                        )
                    } else {
                        Text(
                            text = food.descripcion,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    val painter = rememberAsyncImagePainter(model = food.imagen)
                    Box(
                        modifier = Modifier
                            .size(150.dp)
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

                    Spacer(modifier = Modifier.height(10.dp))

                }
            }
            QuantitySelector(quantity, food.precio, onAddToCart, onRemoveToCart,usuarioSentado)
        }
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    price: Float,
    onAddToCart: () -> Unit,
    onRemoveToCart: () -> Unit,
    usuarioSentado: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .padding(2.dp)
    ) {
        Column {
            Text(
                text = "$$price",
                style = MaterialTheme.typography.labelMedium
            )
        }
        if (quantity > 0) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(60.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.secondary,
                            RoundedCornerShape(4.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            if (usuarioSentado) {
                                if (quantity > 0) {
                                    onRemoveToCart()
                                }
                            }
                        },
                        modifier = Modifier
                            .size(25.dp)
                            .alpha(if (quantity > 0) 1f else 0f)
                    ) {
                        Text(
                            text = if (quantity > 0) "-" else "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Text(
                        text = if (quantity > 0) "$quantity" else "",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    IconButton(
                        onClick = {
                            if (usuarioSentado){
                                onAddToCart()
                            }
                        },
                        modifier = Modifier
                            .size(25.dp)
                    ) {
                        Text(
                            text = "+",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }
            }
        } else {
            Column {
                IconButton(
                    onClick = {
                        if (usuarioSentado) {
                            onAddToCart()
                        }
                    },
                    modifier = Modifier
                        .border(
                            1.dp,
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(all = 0.dp)
                        .size(25.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar producto",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomAppBarExample(navCont: NavController, menu: VistaModeloMenu) {
    BottomAppBar(
        actions = {
            FilledTonalButton(onClick = { navCont.navigate(route="menuprincipal") }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "")
                Text(
                    text = stringResource(id = R.string.btn_back),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            FilledTonalButton(onClick = { navCont.navigate(route = "ordenarpedido")},
                modifier = Modifier.padding(horizontal = 3.dp)) {
                Text(
                    text = stringResource(id = R.string.order_ordenar),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
                Icon(Icons.Filled.Send, contentDescription = "ok")
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(vertical = 8.dp),
                onClick = { }
            ){
                Text(
                    text = stringResource(id = R.string.ticket_total) +": $${menu.obtenerPrecioCarroTotal()}",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    )
}

fun spToPx(context: Context, spValue: Float): Int {
    val pxValue = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        spValue,
        context.resources.displayMetrics
    )
    return pxValue.toInt()
}