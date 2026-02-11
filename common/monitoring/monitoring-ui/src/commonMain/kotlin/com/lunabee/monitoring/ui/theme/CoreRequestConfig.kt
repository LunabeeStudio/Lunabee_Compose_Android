package studio.lunabee.monitoring.ui.theme

enum class CoreRequestConfig(
    val isChevronDisplayed: Boolean,
    val ellipsizeUrl: Boolean,
    val showCurrentTimeZoneDate: Boolean,
    val areQueryParametersDisplayed: Boolean,
) {
    List(
        isChevronDisplayed = true,
        ellipsizeUrl = true,
        showCurrentTimeZoneDate = false,
        areQueryParametersDisplayed = false,
    ),
    Details(
        isChevronDisplayed = false,
        ellipsizeUrl = false,
        showCurrentTimeZoneDate = true,
        areQueryParametersDisplayed = true,
    ),
}
