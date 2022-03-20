package studio.lunabee.compose.model

import studio.lunabee.compose.navigation.ToDirection

data class MenuEntry(
    val titleRes: Int,
    val subtitleRes: Int,
    val direction: ToDirection,
)
