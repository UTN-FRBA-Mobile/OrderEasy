package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

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
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.MenuViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.Plato
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun ReadMenu(navCont: NavController, menu: MenuViewModel, userViewModel: UserViewModel) {
    Scaffold(
        topBar = { TopBar(userViewModel) },
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
                    }else{
                        Menu(menu)
                    }
                }
            }
        }
    )
}

@Composable
fun Menu(menu: MenuViewModel) {
    LazyColumn {
        items(menu.estadoMenu.platos) { food ->
            FoodCard(
                menu = menu,
                food = food,
                modifier = Modifier.padding(16.dp),
                onAddToCart =  {
                    menu.addItem(food.idPlato)
                },
                onRemoveToCart =  {
                    menu.delItem(food.idPlato)
                }
            )
        }
    }
}

@Composable
fun FoodCard(menu: MenuViewModel, food: Plato, modifier: Modifier, onAddToCart: () -> Unit, onRemoveToCart: () -> Unit) {
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
                            text = if (isExpanded) "Leer menos" else "Leer más",
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
            QuantitySelector(quantity, food.precio, onAddToCart, onRemoveToCart)
        }
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    price: Float,
    onAddToCart: () -> Unit,
    onRemoveToCart: () -> Unit
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
                            if (quantity > 0) {
                                onRemoveToCart()
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
                            onAddToCart()
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
                        onAddToCart()
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
fun BottomAppBarExample(navCont: NavController, menu: MenuViewModel) {
    BottomAppBar(
        actions = {
            Text(
                text = "Total: $${menu.getTotalCartPrice()}",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(8.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navCont.navigate(route="makeorder")},
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.elevation()
            ) {
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.baseline_navigate_next_24), contentDescription ="Confirmar",modifier=Modifier.size(40.dp) )
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