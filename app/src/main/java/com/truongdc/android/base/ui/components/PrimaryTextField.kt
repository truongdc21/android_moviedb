package com.truongdc.android.base.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.truongdc.android.base.resource.dimens.rememberWindowSizeClass
import com.truongdc.android.base.resource.theme.AppTheme
import com.truongdc.android.base.resource.theme.MovieDbTheme

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
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = AppTheme.colors.primary,
            focusedIndicatorColor = AppTheme.colors.onPrimary,
            unfocusedIndicatorColor = AppTheme.colors.onPrimary,
            disabledIndicatorColor =AppTheme.colors.onPrimary,
            textColor = AppTheme.colors.onPrimary
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
    MovieDbTheme(windowSizeClass = rememberWindowSizeClass()) {
        PrimaryTextField(
            value = "Input message",
            onValueChange = {},
            textPlaceholder = ""
        )
    }
}
