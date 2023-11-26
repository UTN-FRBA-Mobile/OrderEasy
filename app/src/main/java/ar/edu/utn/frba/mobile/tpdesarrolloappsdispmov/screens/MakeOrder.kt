package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.components.TopBar
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.MenuViewModel
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.Plato
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.UserViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

@Composable
fun MakeOrder (navCont: NavController, menu: MenuViewModel,userViewModel: UserViewModel) {
    var total:Float = remember { 0.0f }
    total = menu.getTotalCartPrice()

    Scaffold (
        topBar = { TopBar(userViewModel)},
        bottomBar = { BottomAppBarMakeOrder(navCont, menu)},
        content = { innerPadding ->
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){
                LazyColumn(modifier = Modifier.fillMaxWidth()){
                    items(menu.estadoMenu.pedidos){p ->
                        if(p.estado == "selected"){
                            var e = menu.estadoMenu.platos.find { m -> m.idPlato == p.idPlato }
                            if (e != null) {
                                var quantity = menu.estadoMenu.pedidos.find{p -> (p.idPlato == e.idPlato && p.estado == "selected")}?.cantidad?:0
                                FoodCardMakeOrder(food = e, quantity = quantity, modifier = Modifier.padding(16.dp), onAddToCart =  {
                                    menu.addItem(e.idPlato)
                                },
                                onRemoveToCart =  {
                                    menu.delItem(e.idPlato)
                                })
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
fun BottomAppBarMakeOrder(navCont: NavController, menu: MenuViewModel) {

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
                onClick = { /* Acción DE CONFIRMAR PEDIDO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(top = 5.dp),
                shape = RoundedCornerShape(3.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                )
            ) {
                Text(text = "Confirmar pedido", color= Color.Black)
            }
        }
    }
}
