package com.truongdc.movie_tmdb.core.designsystem.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.truongdc.movie_tmdb.core.designsystem.dimens.rememberWindowSizeClass
import com.truongdc.movie_tmdb.core.designsystem.theme.AppTheme
import com.truongdc.movie_tmdb.core.designsystem.theme.MovieTMDBTheme

@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    textPlaceholder: String?,
    isPassWord: Boolean = false,
    maxLines: Int = 1,
    paddingValues: PaddingValues = PaddingValues(),
    @SuppressLint("ModifierParameter") modifier: Modifier? = null,
) {
    TextField(
        modifier = modifier ?: Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        value = value, onValueChange = onValueChange,
        textStyle = AppTheme.styles.bodyLarge,
        placeholder = {
            textPlaceholder?.let {
                Text(
                    text = it,
                    color = AppTheme.colors.onPrimary,
                    style = AppTheme.styles.bodyLarge
                )
            }
        },
        maxLines = maxLines,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = AppTheme.colors.primary,
            focusedContainerColor = AppTheme.colors.primary,
            unfocusedIndicatorColor = AppTheme.colors.onPrimary,
            focusedIndicatorColor = AppTheme.colors.onPrimary,
            disabledIndicatorColor = AppTheme.colors.onPrimary,
            focusedTextColor = AppTheme.colors.onPrimary,
            unfocusedTextColor = AppTheme.colors.onPrimary,
            cursorColor = AppTheme.colors.onPrimary,
        ),
        visualTransformation = if (isPassWord) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
    )
}

@Preview
@Composable
private fun Preview() {
    MovieTMDBTheme(windowSizeClass = rememberWindowSizeClass()) {
        PrimaryTextField(
            value = "Input message",
            onValueChange = {},
            textPlaceholder = ""
        )
    }
}

