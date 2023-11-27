package ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ar.edu.utn.frba.mobile.tpdesarrolloappsdispmov.R

val fonts = FontFamily(
    Font(R.font.raleway_bold, weight = FontWeight.Bold),
    //Font(R.font.raleway_light, weight = FontWeight.Light),
    Font(R.font.raleway_medium, weight = FontWeight.Medium),
    Font(R.font.raleway_regular, weight = FontWeight.Normal),
    Font(R.font.raleway_semibold, weight = FontWeight.SemiBold),
    //Font(R.font.raleway_thin, weight = FontWeight.Thin),
    //Font(R.font.montserrat_alternates_bold, weight = FontWeight.W600),
    //Font(R.font.montserrat_alternates_regular, weight = FontWeight.W400),
    //Font(R.font.montserrat_alternates_medium, weight = FontWeight.Thin),
    //Font(R.font.montserrat_alternates_light, weight = FontWeight.Light)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge =TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 19.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.1.sp,
    ) ,
    titleMedium =TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 17.sp,
        letterSpacing = 0.4.sp
    ),
    titleSmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.4.sp),

    displayLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Medium,
        fontSize = 19.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.4.sp
    ),
    displayMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.4.sp
    ),
    displaySmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Thin,
        fontSize = 15.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.4.sp
    ),

    // NUMEROS CHICOS
    labelSmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp
    ),
    // LETRAS MINI
    bodySmall= TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.1.sp
    )
)