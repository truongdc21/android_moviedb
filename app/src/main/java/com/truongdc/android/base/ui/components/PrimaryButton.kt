package com.truongdc.android.base.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.truongdc.android.base.resource.theme.Black
import com.truongdc.android.base.resource.theme.BlackCard
import com.truongdc.android.base.resource.theme.Gray
import com.truongdc.android.base.resource.theme.White

@Composable
fun PrimaryButton(
    label: String,
    isEnable: Boolean = true,
    onClick: () -> Unit = {},
    roundedCornerShape: Dp = 24.dp,
    paddingValues: PaddingValues = PaddingValues(top = 8.dp, bottom = 8.dp)
) {
    Button(
        enabled = isEnable,
        onClick = { onClick() },
        shape = RoundedCornerShape(roundedCornerShape),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Black,
            contentColor = White,
            disabledBackgroundColor = BlackCard
        ),
        elevation = ButtonDefaults.elevation(
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