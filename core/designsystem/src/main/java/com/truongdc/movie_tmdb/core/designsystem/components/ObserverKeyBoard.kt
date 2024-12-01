package com.truongdc.movie_tmdb.core.designsystem.components

import android.view.View
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun ObserverKeyBoard(view: View, isShowKeyBoard: (Boolean) -> Unit) {
    DisposableEffect(Unit) {
        val viewTreeObserver = view.viewTreeObserver
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            isShowKeyBoard(isKeyboardOpen)
        }
        viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}