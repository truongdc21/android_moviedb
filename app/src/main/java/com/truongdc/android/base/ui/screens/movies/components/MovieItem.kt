package com.truongdc.android.base.ui.screens.movies.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import com.truongdc.android.base.R
import com.truongdc.android.base.common.constant.Constants
import com.truongdc.android.base.data.model.Movie
import com.truongdc.android.base.resource.dimens.DpSize
import com.truongdc.android.base.resource.dimens.SpSize
import com.truongdc.android.base.resource.theme.AppColors

@Composable
fun MovieItem(movie: Movie, onClickItem: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(start = DpSize.dp16, end = DpSize.dp16, top = DpSize.dp8, bottom = DpSize.dp8)
            .background(Color.White)
            .fillMaxWidth(),
        onClick = {
            onClickItem(movie.id)
        },
        colors = CardDefaults.cardColors(
            containerColor = AppColors.BlackCard,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = DpSize.dp8,
            pressedElevation = DpSize.dp10
        )
    ) {
        Column(
            modifier = Modifier.padding(DpSize.dp10)
        ) {
            Row {
                Image(
                    painter = rememberAsyncImagePainter(Constants.BASE_URL_IMAGE + movie.urlImage),
                    contentDescription = null,
                    modifier = Modifier
                        .width(DpSize.dp100)
                        .height(DpSize.dp100)
                        .clip(
                            RoundedCornerShape(DpSize.dp50)
                        )
                        .shadow(elevation = DpSize.dp50, spotColor = AppColors.White),
                    contentScale = ContentScale.FillBounds,
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = DpSize.dp16)
                ) {
                    Text(
                        text = movie.title,
                        fontSize = SpSize.sp16,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.size(DpSize.dp6))
                    Text(
                        text = movie.overView,
                        fontSize = SpSize.sp14,
                        color = Color.White,
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
                            color = Color.White,
                            fontSize = SpSize.sp14,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.size(DpSize.dp6))
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