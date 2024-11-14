package com.truongdc.android.base.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.truongdc.android.base.resource.theme.AppColors
import com.truongdc.android.base.resource.dimens.DpSize
import com.truongdc.android.base.resource.dimens.SpSize

@Composable
fun BaseButton(
    label: String,
    isEnable: Boolean = true,
    onClick: () -> Unit = {},
    roundedCornerShape: Dp = DpSize.dp24,
    paddingValues: PaddingValues = PaddingValues(top = DpSize.dp8, bottom = DpSize.dp8)
) {
    Button(
        enabled = isEnable,
        onClick = { onClick() },
        shape = RoundedCornerShape(roundedCornerShape),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppColors.Black,
            contentColor = AppColors.White,
            disabledBackgroundColor = AppColors.BlackCard
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = DpSize.dp10,
            pressedElevation = DpSize.dp15,
            disabledElevation = DpSize.dp0
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            color = if (isEnable) AppColors.White else AppColors.Gray,
            modifier = Modifier.padding(paddingValues),
            fontWeight = FontWeight.Bold,
            fontSize = SpSize.sp18
        )
    }
}