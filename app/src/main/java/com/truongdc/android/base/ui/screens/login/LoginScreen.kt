package com.truongdc.android.base.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.truongdc.android.base.base.compose.UiStateContent
import com.truongdc.android.base.ui.components.BaseButton
import com.truongdc.android.base.ui.components.BaseTextField
import com.truongdc.android.base.ui.components.ObserverKeyBoard
import com.truongdc.android.base.navigation.AppDestination
import com.truongdc.android.base.navigation.navigate
import com.truongdc.android.base.resource.theme.AppColors
import com.truongdc.android.base.resource.dimens.DpSize
import com.truongdc.android.base.resource.dimens.SpSize
import com.truongdc.android.base.common.extensions.showToast

@Composable
fun LoginScreen(
    navHostController: NavHostController = rememberNavController(),
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val view = LocalView.current
    val keyboardController = LocalSoftwareKeyboardController.current
    view.ObserverKeyBoard { viewModel.onUpdateTextFiledFocus(it) }
    UiStateContent(viewModel = viewModel, modifier = Modifier, onEventEffect = { event ->
        when (event) {
            LoginViewModel.Event.LoginSuccess -> {
                context.showToast("Login Success!")
                navHostController.navigate(AppDestination.MovieList) {
                    popUpTo(AppDestination.Login.route) { inclusive = true }
                }
            }

            LoginViewModel.Event.LoginFailed -> {
                context.showToast("Login Failed, Please try again!")
            }
        }
    }, content = { uiState ->
        Column(
            modifier = Modifier
                .background(AppColors.Yellow)
                .fillMaxSize()
                .padding(
                    top = if (!uiState.isTextFieldFocused) DpSize.dp150 else DpSize.dp80,
                    start = DpSize.dp24,
                    end = DpSize.dp24
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome Back!",
                fontWeight = FontWeight.Bold,
                fontSize = SpSize.sp32,
                color = AppColors.White
            )
            Text(
                text = "Login to continue",
                fontSize = SpSize.sp16,
                color = AppColors.White
            )
            BaseTextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                textPlaceholder = "Mail ID",
                paddingValues = PaddingValues(top = DpSize.dp50)
            )
            BaseTextField(
                value = uiState.pass,
                onValueChange = viewModel::onPassChange,
                textPlaceholder = "Password",
                isPassWord = true,
                paddingValues = PaddingValues(top = DpSize.dp20)
            )
            Spacer(modifier = Modifier.size(DpSize.dp30))
            Text(
                text = "Forget Password?",
                fontSize = SpSize.sp14,
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
            Spacer(modifier = Modifier.size(DpSize.dp30))
            BaseButton(
                label = "Login",
                isEnable = !uiState.isInValid,
                onClick = {
                    keyboardController?.hide()
                    viewModel.onSubmitLogin(uiState.email, uiState.pass)
                })
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "CREATE ACCOUNT",
                fontSize = SpSize.sp18,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(bottom = DpSize.dp30)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            navHostController.navigate(AppDestination.Register) {
                                popUpTo(AppDestination.Splash.route) { inclusive = true }
                            }
                        })
                    },
            )
        }
    })

}