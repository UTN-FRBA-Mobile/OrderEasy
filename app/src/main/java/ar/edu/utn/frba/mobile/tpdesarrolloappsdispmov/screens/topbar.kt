package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R

@Composable
fun OderEasyTopAppBar(navCont: NavController) {
    val translucentBlueColor = Color.Blue.copy(alpha = 0.8f)
    val navBackStackEntry = navCont.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = translucentBlueColor),
    ) {
        TopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "OrderEasy",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    )
                }
            },
            navigationIcon = {
                if (currentRoute != "mainmenu") {
                    IconButton(
                        onClick = { navCont.navigate("mainmenu") }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.flechaizquierda),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            }
        )
    }
}

