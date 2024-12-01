package com.truongdc.movie_tmdb.core.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.truongdc.movie_tmdb.core.designsystem.dimens.rememberWindowSizeClass
import com.truongdc.movie_tmdb.core.designsystem.theme.Black
import com.truongdc.movie_tmdb.core.designsystem.theme.BlackCard
import com.truongdc.movie_tmdb.core.designsystem.theme.Gray
import com.truongdc.movie_tmdb.core.designsystem.theme.MovieTMDBTheme
import com.truongdc.movie_tmdb.core.designsystem.theme.White

@Composable
fun PrimaryButton(
    label: String,
    isEnable: Boolean = true,
    onClick: () -> Unit = {},
    roundedCornerShape: Dp = 24.dp,
    paddingValues: PaddingValues = PaddingValues(top = 8.dp, bottom = 8.dp),
) {
    Button(
        enabled = isEnable,
        onClick = { onClick() },
        shape = RoundedCornerShape(roundedCornerShape),
        colors = ButtonDefaults.buttonColors(
            containerColor = Black,
            disabledContainerColor = BlackCard,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            color = if (isEnable) White else Gray,
            modifier = Modifier.padding(paddingValues),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MovieTMDBTheme(windowSizeClass = rememberWindowSizeClass()) {
        PrimaryButton(
            label = "Login",
        )
    }
}