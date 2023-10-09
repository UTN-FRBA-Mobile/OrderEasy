package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.LoginScreen
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.stateData.LoginScreenPreview
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.ui.theme.TpDesarrolloAppsDispMovTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TpDesarrolloAppsDispMovTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Login()
                }
            }
        }
    }
}
@Composable
fun Login(){
    LoginScreenPreview()
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TpDesarrolloAppsDispMovTheme {
        LoginScreenPreview()
    }
}