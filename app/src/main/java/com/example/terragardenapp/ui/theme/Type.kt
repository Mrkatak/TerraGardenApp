package com.example.terragardenapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.terragardenapp.R


val LatoFont = FontFamily(
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_light, FontWeight.Light),
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_black, FontWeight.Black),
)

// Set of Material typography styles to start with
val MyTypography = Typography (
    bodyLarge = TextStyle (
        fontFamily = LatoFont,
        fontWeight = FontWeight.Black,
        fontSize = 30.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = LatoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),

    bodySmall = TextStyle(
        fontFamily = LatoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),


)