package com.truongdc.android.base.ui.screens.register

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.truongdc.android.base.base.ScreenContent
import com.truongdc.android.base.ui.components.BaseButton
import com.truongdc.android.base.ui.components.BaseTextField
import com.truongdc.android.base.ui.components.LoadingContent
import com.truongdc.android.base.ui.components.ObserverKeyBoard
import com.truongdc.android.base.navigation.AppDestination
import com.truongdc.android.base.navigation.navigate
import com.truongdc.android.base.resource.theme.AppColors
import com.truongdc.android.base.resource.dimens.DpSize
import com.truongdc.android.base.resource.dimens.SpSize
import com.truongdc.android.base.common.extensions.showToast
import com.truongdc.android.base.base.uistate.collectEvent
import com.truongdc.android.base.base.uistate.collectLoadingWithLifecycle
import com.truongdc.android.base.base.uistate.collectWithLifecycle
import com.truongdc.android.base.data.model.User

@Composable
fun RegisterScreen(
    navHostController: NavHostController = rememberNavController(),
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    ScreenContent(
        viewModel = viewModel,
        modifier = Modifier,
        onEventEffect = { event ->
            when (event) {
                RegisterViewModel.Event.RegisterSuccess -> {
                    context.showToast("Register Success!")
                    navHostController.navigate(AppDestination.Up) {
                        popUpTo(AppDestination.Login.route) { inclusive = true }
                    }
                }

                RegisterViewModel.Event.RegisterFailed -> {
                    context.showToast("Register Failed, Please try again!")
                }
            }
        }) { uiState ->
        LocalView.current.ObserverKeyBoard { viewModel.onUpdateTextFiledFocus(it) }
        Column(
            modifier = Modifier
                .background(AppColors.Yellow)
                .fillMaxSize()
                .padding(
                    top = if (!uiState.isTextFieldFocused) DpSize.dp150 else DpSize.dp50,
                    start = DpSize.dp24,
                    end = DpSize.dp24
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create Account",
                fontWeight = FontWeight.Bold,
                fontSize = SpSize.sp32,
                color = AppColors.White
            )
            BaseTextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                textPlaceholder = "Mail ID",
                paddingValues = PaddingValues(top = DpSize.dp50)
            )
            BaseTextField(
                value = uiState.name,
                onValueChange = viewModel::onNameChange,
                textPlaceholder = "Full Name",
                paddingValues = PaddingValues(top = DpSize.dp20)
            )
            BaseTextField(
                value = uiState.pass,
                onValueChange = viewModel::onPassChange,
                textPlaceholder = "Password",
                isPassWord = true,
                paddingValues = PaddingValues(top = DpSize.dp20)
            )
            Spacer(modifier = Modifier.size(DpSize.dp40))
            BaseButton(
                onClick = {
                    viewModel.onSubmitRegister(User(uiState.name, uiState.email, uiState.pass))
                },
                label = "Register", isEnable = !uiState.isInValid,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "LOGIN",
                fontSize = SpSize.sp18,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(bottom = DpSize.dp30)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            navHostController.navigate(AppDestination.Up) {
                                popUpTo(AppDestination.Login.route) { inclusive = true }
                            }
                        })
                    },
            )
        }
    }
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true
)
private fun Preview() {
    RegisterScreen()
}

