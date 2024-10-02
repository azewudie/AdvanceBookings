package com.aaron.advancebookings.ui.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.aaron.advancebookings.R
import com.aaron.advancebookings.ui.theme.CustomTheme

@Composable
fun ImageViewLoader(
    model: Any,
    modifier: Modifier,
    imageDescription: String,
    placeHolderImage: Painter,
    contentScale: ContentScale,
    alignment: Alignment = Alignment.Center,
    errorPlaceHolder: Painter = painterResource(id = R.drawable.place_holder)

) {
   CustomTheme {
       AsyncImage(
           model = model,
           placeholder = placeHolderImage,
           contentDescription = imageDescription,
           contentScale = contentScale,
           modifier = modifier,
           alignment = alignment,
           error = errorPlaceHolder
       )
   }
}