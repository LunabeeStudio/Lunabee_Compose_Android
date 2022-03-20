package studio.lunabee.compose.lbctopappbar.material.error

internal object ExceptionThrown {
    internal object LbcTopAppBarNoTitleException : IllegalArgumentException(
        "No title to display.\n" +
            "Either specify a title or provide your own titleComposable implementation"
    )

    internal object LbcTopAppBarNoNavigationException : IllegalArgumentException(
        "You either need to:\n" +
            "- Provide onNavigationClicked action\n" +
            "- Provide your implementation of navigationIconComposable\n" +
            "- Use LbcTopAppBar directly"
    )
}
