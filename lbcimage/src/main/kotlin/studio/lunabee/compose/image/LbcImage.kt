package studio.lunabee.compose.image

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import studio.lunabee.compose.core.LbcImageSpec
import studio.lunabee.compose.core.LbcTextSpec

@Composable
fun LbcImage(
    imageSpec: LbcImageSpec,
    modifier: Modifier = Modifier,
    contentDescription: LbcTextSpec? = null,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    colorFilter: ColorFilter? = null,
    errorPainter: Painter? = null,
) {
    when (imageSpec) {
        is LbcImageSpec.Bitmap -> {
            Image(
                bitmap = imageSpec.bitmap.asImageBitmap(),
                contentDescription = contentDescription?.string,
                modifier = modifier,
                contentScale = contentScale,
                alignment = alignment,
                colorFilter = colorFilter,
            )
        }

        is LbcImageSpec.ImageDrawable -> {
            Image(
                painter = painterResource(id = imageSpec.drawableRes),
                contentDescription = contentDescription?.string,
                modifier = modifier,
                contentScale = contentScale,
                alignment = alignment,
                colorFilter = colorFilter,
            )
        }

        is LbcImageSpec.Icon -> {
            val tint = imageSpec.tint.invoke().takeIf { it != Color.Unspecified } ?: LocalContentColor.current
            Icon(
                painter = painterResource(id = imageSpec.drawableRes),
                contentDescription = contentDescription?.string,
                modifier = modifier,
                tint = tint,
            )
        }

        is LbcImageSpec.KtImageVector -> {
            val tint = imageSpec.tint.invoke().takeIf { it != Color.Unspecified } ?: LocalContentColor.current
            Icon(
                imageVector = imageSpec.icon,
                contentDescription = contentDescription?.string,
                modifier = modifier,
                tint = tint,
            )
        }

        is LbcImageSpec.Url -> {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageSpec.url)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = contentDescription?.string,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                error = errorPainter,
                onError = onState,
                onLoading = onState,
                onSuccess = onState,
                colorFilter = colorFilter,
            )
        }

        is LbcImageSpec.ByteArray ->
            AsyncImage(
                model = imageSpec.byteArray,
                contentDescription = contentDescription?.string,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                error = errorPainter,
                onError = onState,
                onLoading = onState,
                onSuccess = onState,
                colorFilter = colorFilter,
            )

        is LbcImageSpec.Uri ->
            AsyncImage(
                model = imageSpec.uri,
                contentDescription = contentDescription?.string,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                error = errorPainter,
                onError = onState,
                onLoading = onState,
                onSuccess = onState,
                colorFilter = colorFilter,
            )
    }
}
