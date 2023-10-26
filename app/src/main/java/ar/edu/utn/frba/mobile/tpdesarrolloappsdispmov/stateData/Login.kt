package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navCont: NavHostController) {
    var username by remember { mutableStateOf("") }
    var showWelcomeDialog by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val coroutineScope = rememberCoroutineScope()


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            // TODO: Tomar la imagen del fondo de pantalla
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido a OrderEasy",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            BasicTextField(
                value = username,
                onValueChange = { username = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Podria ir una logica para chequear este campo?
                        keyboardController?.hide()
                    }
                ),
                textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.7f))
                    .padding(8.dp)
                    .height(56.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    keyboardController?.hide()
                    showWelcomeDialog = true
                    coroutineScope.launch {
                        delay(3000)
                        showWelcomeDialog = false
                        navCont.navigate("greeting") // TODO: Modificar la ruta al meun
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Ingresar", fontSize = 18.sp)
            }
        }
    }

    if (showWelcomeDialog) {
        AlertDialog(
            onDismissRequest = {  },
            title = { Text("Bienvenido $username") },
            text = { Text("¡Esperamos que comas rico!") },
            confirmButton = {
                // Sin boton
            }
        )
    }
}
