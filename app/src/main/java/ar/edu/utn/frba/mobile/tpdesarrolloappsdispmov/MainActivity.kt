package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                    Starting()
                }
            }
        }
    }
}

@Composable
fun Starting() {
    Scaffold (
        content = {innerPadding ->
            Text(
                modifier = Modifier.fillMaxWidth().padding(innerPadding).wrapContentSize(),
                text = "Prueba"
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TpDesarrolloAppsDispMovTheme {
        Starting()
    }
}