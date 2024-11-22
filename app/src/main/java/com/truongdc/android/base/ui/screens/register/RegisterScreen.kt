package com.truongdc.android.base.ui.screens.register

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.truongdc.android.base.base.compose.UiStateContent
import com.truongdc.android.base.common.extensions.showToast
import com.truongdc.android.base.data.model.User
import com.truongdc.android.base.resource.dimens.isLandscape
import com.truongdc.android.base.resource.dimens.isPortrait
import com.truongdc.android.base.resource.theme.AppTheme
import com.truongdc.android.base.resource.theme.MovieDbTheme
import com.truongdc.android.base.ui.components.ObserverKeyBoard
import com.truongdc.android.base.ui.components.PrimaryButton
import com.truongdc.android.base.ui.components.PrimaryTextField

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    UiStateContent(
        viewModel = viewModel,
        modifier = Modifier,
        onEventEffect = { event ->
            when (event) {
                RegisterViewModel.Event.RegisterSuccess -> {
                    context.showToast("Register Success!")
                    viewModel.navigateBack()
                }

                RegisterViewModel.Event.RegisterFailed -> {
                    context.showToast("Register Failed, Please try again!")
                }
            }
        }, content = { uiState ->
            RegisterContent(
                email = uiState.email,
                name = uiState.name,
                pass = uiState.pass,
                isInValid = uiState.isInValid,
                isTextFieldFocused = uiState.isTextFieldFocused,
                onUpdateTextFiledFocus = viewModel::onUpdateTextFiledFocus,
                onEmailChange = viewModel::onEmailChange,
                onNameChange = viewModel::onNameChange,
                onPassChange = viewModel::onPassChange,
                onSubmitRegister = viewModel::onSubmitRegister,
                onNavigateBack = viewModel::navigateBack
            )
        })
}

@Composable
private fun RegisterContent(
    email: String,
    name: String,
    pass: String,
    isInValid: Boolean,
    isTextFieldFocused: Boolean,
    onUpdateTextFiledFocus: (Boolean) -> Unit,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onPassChange: (String) -> Unit,
    onSubmitRegister: (User) -> Unit,
    onNavigateBack: () -> Unit,
) {
    Scaffold {
        val paddingTopContent = if (AppTheme.orientation.isLandscape()) 10.dp else
            if (!isTextFieldFocused) 150.dp else 0.dp
        val paddingHorizontalContent = if (AppTheme.orientation.isLandscape()) 230.dp else 24.dp
        LocalView.current.ObserverKeyBoard { isShown ->
            onUpdateTextFiledFocus(isShown)
        }
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
                text = "Create Account",
                style = AppTheme.styles.displaySmall,
                color = AppTheme.colors.onPrimary
            )
            Text(
                text = "Create account to login",
                style = AppTheme.styles.bodyLarge,
                color = AppTheme.colors.onPrimary
            )
            if (AppTheme.orientation.isPortrait()) {
                PrimaryTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    textPlaceholder = "Mail ID",
                    paddingValues = PaddingValues(
                        top = if (AppTheme.orientation.isLandscape()) 10.dp else 50.dp
                    )
                )
                PrimaryTextField(
                    value = name,
                    onValueChange = onNameChange,
                    textPlaceholder = "Full Name",
                    paddingValues = PaddingValues(
                        top = if (AppTheme.orientation.isLandscape()) 10.dp else 20.dp
                    )
                )
            } else {
                Row {
                    PrimaryTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        textPlaceholder = "Mail ID",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    PrimaryTextField(
                        value = name,
                        onValueChange = onNameChange,
                        textPlaceholder = "Full Name",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            PrimaryTextField(
                value = pass,
                onValueChange = onPassChange,
                textPlaceholder = "Password",
                isPassWord = true,
                paddingValues = PaddingValues(
                    top = if (AppTheme.orientation.isLandscape()) 10.dp else 20.dp
                ),
                modifier = null
            )
            Spacer(
                modifier = Modifier.size(
                    if (AppTheme.orientation.isLandscape()) 20.dp else 40.dp
                )
            )
            PrimaryButton(
                onClick = {
                    onSubmitRegister(User(name, email, pass))
                },
                label = "Register", isEnable = !isInValid,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (AppTheme.orientation.isPortrait())
                Text(
                    text = "LOGIN",
                    style = AppTheme.styles.bodyLarge.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    fontWeight = FontWeight.W500,
                    color = AppTheme.colors.onPrimary,
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                onNavigateBack()
                            })
                        },
                )
        }
    }
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    device = "spec:orientation=landscape,width=411dp,height=891dp"
)
private fun Preview() {
    MovieDbTheme {
        RegisterContent(
            email = "",
            name = "",
            pass = "",
            isInValid = false,
            isTextFieldFocused = false,
            onUpdateTextFiledFocus = {},
            onEmailChange = {},
            onNameChange = {},
            onPassChange = {},
            onSubmitRegister = {},
            onNavigateBack = {}
        )
    }
}
