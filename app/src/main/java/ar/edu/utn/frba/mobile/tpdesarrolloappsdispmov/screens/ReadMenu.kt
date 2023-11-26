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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.MenuViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.Plato
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel
import coil.compose.rememberImagePainter

@Composable
fun ReadMenu(navCont: NavController, menu: MenuViewModel, userViewModel: UserViewModel) {

    Scaffold(
        topBar = { TopBar(userViewModel) },
        bottomBar = {
            BottomAppBarExample(menu)
        },
        content = { innerPadding ->
            Column (modifier = Modifier.fillMaxSize().padding(innerPadding)){
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
            FoodCard(food = food, modifier = Modifier.padding(16.dp), onAddToCart =  {
                menu.addItem(food.idPlato)
            })
        }
    }
}

@Composable
fun FoodCard(food: Plato, modifier: Modifier, onAddToCart: () -> Unit) {
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
                    val painter = rememberImagePainter(data = food.imagen)
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
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .height(40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$" + food.precio,
                    style = MaterialTheme.typography.labelMedium
                )
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
fun BottomAppBarExample(menu: MenuViewModel) {
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
                onClick = { /* do something */ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.elevation()
            ) {
                Icon(Icons.Default.Add, "Localized description")
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

@Composable
@Preview
fun PreviewFoodCard() {
    FoodCard(
        food = Plato(
            idPlato = 1,
            nombre = "Sample Food",
            descripcion = "This is a sample food description. It is a long description to test how it looks.",
            imagen = "",
            precio = 20.0f,
            puntaje = 4,
            categoria = "Sample Category",
            ingredientes = "Sample Ingredients",
            infoNutri = "Sample Nutritional Info",
            disponible = true
        ),
        modifier = Modifier.padding(16.dp)
    ) {}
}