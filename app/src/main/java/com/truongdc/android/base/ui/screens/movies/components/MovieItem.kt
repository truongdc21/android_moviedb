package com.truongdc.android.base.ui.screens.movies.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.truongdc.android.base.R
import com.truongdc.android.base.common.constant.Constants
import com.truongdc.android.base.data.model.Movie

import com.truongdc.android.base.resource.theme.AppTheme
import com.truongdc.android.base.resource.theme.BlackCard
import com.truongdc.android.base.resource.theme.MovieDbTheme

@Composable
fun MovieItem(movie: Movie, onClickItem: (Int) -> Unit) {
    Card(
        modifier = Modifier.padding(10.dp),
        onClick = {
            onClickItem(movie.id)
        },
        colors = CardDefaults.cardColors(
            containerColor = BlackCard,
            contentColor = AppTheme.colors.onSurface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 10.dp
        ),
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(Constants.BASE_URL_IMAGE + movie.urlImage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(230.dp),
                contentScale = ContentScale.Crop,
            )
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(AppTheme.colors.surface.copy(alpha = 0.7f))
                    .align(Alignment.BottomCenter)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(10.dp)
                ) {
                    Text(
                        text = movie.title,
                        color = AppTheme.colors.primary,
                        style = AppTheme.styles.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(
                        text = movie.overView,
                        style = AppTheme.styles.labelLarge,
                        color = AppTheme.colors.secondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = movie.vote.toString(),
                            color = AppTheme.colors.secondary,
                            style = AppTheme.styles.titleSmall
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MovieDbTheme(
        androidTheme = false,
        darkTheme = true
    ) {
        MovieItem(
            movie = Movie(
                title = "Name of movie",
                overView = "overview"
            )
        ) {}
    }
}