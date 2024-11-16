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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.truongdc.android.base.base.compose.UiStateContent
import com.truongdc.android.base.common.extensions.showToast
import com.truongdc.android.base.data.model.User
import com.truongdc.android.base.resource.dimens.DpSize
import com.truongdc.android.base.resource.dimens.SpSize
import com.truongdc.android.base.resource.theme.AppColors
import com.truongdc.android.base.ui.components.BaseButton
import com.truongdc.android.base.ui.components.BaseTextField
import com.truongdc.android.base.ui.components.ObserverKeyBoard

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel()
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
                            viewModel.navigateBack()
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

