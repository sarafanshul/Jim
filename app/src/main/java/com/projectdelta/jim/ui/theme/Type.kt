package com.projectdelta.jim.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.projectdelta.jim.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val InterFont = FontFamily(
    Font(R.font.inter_regular),
    Font(R.font.inter_thin, FontWeight.Thin),
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_extra_light, FontWeight.ExtraLight),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_semi_bold, FontWeight.SemiBold),
    Font(R.font.inter_extra_bold, FontWeight.ExtraBold),
)

val InterTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.Normal,
        fontSize   = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.15).sp
    ),
    displayMedium = TextStyle(
        fontFamily = InterFont,
        lineHeight = 52.sp,
        fontSize   = 45.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (0).sp
    ),
    displaySmall = TextStyle(
        fontFamily = InterFont,
        lineHeight = 44.sp,
        fontSize   = 36.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (0).sp
    ),
    headlineLarge = TextStyle(
        fontFamily = InterFont,
        lineHeight = 40.sp,
        fontSize   = 32.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (0).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = InterFont,
        lineHeight = 36.sp,
        fontSize   = 28.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (0).sp
    ),
    headlineSmall = TextStyle(
        fontFamily = InterFont,
        lineHeight = 32.sp,
        fontSize   = 24.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (0).sp
    ),
    titleLarge = TextStyle(
        fontFamily = InterFont,
        lineHeight = 28.sp,
        fontSize   = 22.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (0).sp
    ),
    titleMedium = TextStyle(
        fontFamily = InterFont,
        lineHeight = 24.sp,
        fontSize   = 16.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = (0.15).sp
    ),
    titleSmall = TextStyle(
        fontFamily = InterFont,
        lineHeight = 20.sp,
        fontSize   = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = (0.1).sp
    ),
    labelLarge = TextStyle(
        fontFamily = InterFont,
        lineHeight = 24.sp,
        fontSize   = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = (0.15).sp
    ),
    labelMedium = TextStyle(
        fontFamily = InterFont,
        lineHeight = 16.sp,
        fontSize   = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = (0.5).sp
    ),
    labelSmall = TextStyle(
        fontFamily = InterFont,
        lineHeight = 16.sp,
        fontSize   = 11.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = (0.5).sp
    ),
    bodyLarge = TextStyle(
        fontFamily = InterFont,
        lineHeight = 24.sp,
        fontSize   = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (0.5).sp
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFont,
        lineHeight = 20.sp,
        fontSize   = 14.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (0.25).sp
    ),
    bodySmall = TextStyle(
        fontFamily = InterFont,
        lineHeight = 16.sp,
        fontSize   = 12.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (0.4).sp
    ),
)
