package com.truongdc.android.base.ui.screens.login

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.truongdc.android.base.base.compose.UiStateContent
import com.truongdc.android.base.common.extensions.showToast
import com.truongdc.android.base.resource.dimens.isLandscape
import com.truongdc.android.base.resource.dimens.isPortrait
import com.truongdc.android.base.resource.theme.AppTheme
import com.truongdc.android.base.resource.theme.MovieDbTheme
import com.truongdc.android.base.ui.components.ObserverKeyBoard
import com.truongdc.android.base.ui.components.PrimaryButton
import com.truongdc.android.base.ui.components.PrimaryTextField

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val view = LocalView.current
    view.ObserverKeyBoard { viewModel.onUpdateTextFiledFocus(it) }
    UiStateContent(viewModel = viewModel, modifier = Modifier, onEventEffect = { event ->
        when (event) {
            LoginViewModel.Event.LoginSuccess -> {
                context.showToast("Login Success!")
                viewModel.navigateMovies()
            }

            LoginViewModel.Event.LoginFailed -> {
                context.showToast("Login Failed, Please try again!")
            }
        }
    }, content = { uiState ->
        LoginContent(
            email = uiState.email,
            pass = uiState.pass,
            inValid = uiState.isInValid,
            isTextFieldFocused = uiState.isTextFieldFocused,
            onEmailChange = viewModel::onEmailChange,
            onPassChange = viewModel::onPassChange,
            onSubmitLogin = viewModel::onSubmitLogin,
            onNavigateRegister = viewModel::navigateRegister
        )
    })
}

@Composable
private fun LoginContent(
    email: String,
    pass: String,
    inValid: Boolean,
    isTextFieldFocused: Boolean,
    onEmailChange: (String) -> Unit,
    onPassChange: (String) -> Unit,
    onSubmitLogin: (String, String) -> Unit,
    onNavigateRegister: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val paddingTopContent = if (AppTheme.orientation.isLandscape()) 10.dp else
        if (!isTextFieldFocused) 150.dp else 80.dp
    val paddingHorizontalContent = if (AppTheme.orientation.isLandscape()) 230.dp else 24.dp
    Scaffold(
        floatingActionButton = {
            if (AppTheme.orientation.isLandscape())
                FloatingActionButton(
                    onClick = { onNavigateRegister() },
                    content = { Icon(Icons.Filled.Add, contentDescription = null) }
                )
        }
    ) {
        Column(
            modifier = Modifier
                .background(AppTheme.colors.primary)
                .fillMaxSize()
                .padding(it)
                .padding(
                    top = paddingTopContent,
                    start = paddingHorizontalContent,
                    end = paddingHorizontalContent
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome Back!",
                style = AppTheme.styles.displaySmall,
                color = AppTheme.colors.onPrimary
            )
            Text(
                text = "Login to continue",
                style = AppTheme.styles.bodyLarge,
                color = AppTheme.colors.onPrimary
            )
            PrimaryTextField(
                value = email,
                onValueChange = { email -> onEmailChange((email)) },
                textPlaceholder = "Mail ID",
                paddingValues = PaddingValues(
                    top = if (AppTheme.orientation.isLandscape()) 10.dp else 50.dp
                )
            )
            PrimaryTextField(
                value = pass,
                onValueChange = { pass -> onPassChange(pass) },
                textPlaceholder = "Password",
                isPassWord = true,
                paddingValues = PaddingValues(
                    top = if (AppTheme.orientation.isLandscape()) 10.dp else 20.dp
                )
            )
            Spacer(
                modifier = Modifier.size(
                    if (AppTheme.orientation.isLandscape()) 10.dp else 30.dp
                )
            )
            Text(
                text = "Forget Password?",
                style = AppTheme.styles.bodyLarge.copy(
                    textDecoration = TextDecoration.Underline
                ),
                color = AppTheme.colors.onPrimary,
            )
            Spacer(
                modifier = Modifier.size(
                    if (AppTheme.orientation.isLandscape()) 10.dp else 30.dp
                )
            )
            PrimaryButton(
                label = "Login",
                isEnable = !inValid,
                onClick = {
                    keyboardController?.hide()
                    onSubmitLogin(email, pass)
                })
            Spacer(modifier = Modifier.weight(1f))
            if (AppTheme.orientation.isPortrait())
                Text(
                    text = "CREATE ACCOUNT",
                    style = AppTheme.styles.bodyLarge.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    fontWeight = FontWeight.W500,
                    color = AppTheme.colors.onPrimary,
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                onNavigateRegister()
                            })
                        },
                )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    device = "spec:orientation=landscape,width=411dp,height=891dp"
)
@Composable
private fun Preview() {
    MovieDbTheme {
        LoginContent(
            email = "",
            pass = "",
            inValid = false,
            isTextFieldFocused = false,
            onEmailChange = {},
            onPassChange = {},
            onSubmitLogin = { _, _ -> },
            onNavigateRegister = {}
        )
    }
}

