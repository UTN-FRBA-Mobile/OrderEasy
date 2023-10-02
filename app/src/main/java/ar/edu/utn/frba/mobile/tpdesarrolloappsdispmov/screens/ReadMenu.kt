package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.sampledata.SampleDataMenu

@Composable
fun ReadMenu(navCont: NavController) {
    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "BARRA SUPERIOR DE LA APP") })
        },
        content = { innerPadding ->
            Column (modifier = Modifier.fillMaxSize()){
                Surface (modifier = Modifier.fillMaxSize()){
                    Menu(foods = SampleDataMenu.foodListSample)
                }
                Button(
                    onClick = {navCont.navigate(route="mainmenu")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .wrapContentSize()
                ) {
                    Text(text = "volver al menu")
                }
            }
        }
    )
}
@Preview
@Composable
fun foodsPreview(){
    Surface (modifier = Modifier.fillMaxSize()){
        Menu(foods = SampleDataMenu.foodListSample)
    }
}

data class Food(
    @DrawableRes val image: Int,
    val name: String,
    val description: String
)

@Composable
fun Menu(foods: List<Food>){
    LazyColumn {
        items(foods){
                food -> FoodCard(food = food, modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun FoodCard(food: Food, modifier: Modifier){
    Surface(shape= RoundedCornerShape(8.dp), tonalElevation = 8.dp, shadowElevation = 8.dp, modifier=modifier) {
        Column (modifier=Modifier.fillMaxWidth()){
            Image(painter = painterResource(id = food.image),
                contentDescription = food.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(144.dp)
            )

            Column(modifier=Modifier.padding(16.dp)) {
                Text(text = food.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier=Modifier.padding(bottom = 4.dp)
                )
                Text(text = food.description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier=Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}