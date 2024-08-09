# Glance library

## Introduction

This library provides some tools to create widget with Glance. As there is some limitations with
Glance, the main idea here is to provide only Composable or methods that can be useful.

## Create a widget

You can check the demo app to see how to create a widget:
- Package [glance](../app/src/main/kotlin/studio/lunabee/compose/demo/glance)
    - [GlanceWidgetDemo.kt](../app/src/main/kotlin/studio/lunabee/compose/demo/glance/GlanceWidgetDemo.kt): declaration of the widget and the receiver.
    - [GlanceScreen.kt](../app/src/main/kotlin/studio/lunabee/compose/demo/glance/GlanceScreen.kt): demonstration of [PinWidgetToHomeScreenHelper.kt](src/main/kotlin/studio/lunabee/compose/glance/helpers/PinWidgetToHomeScreenHelper.kt)
    - [widget_demo_info.xml](../app/src/main/res/xml/widget_demo_info.xml): provides info about the widget (size, description, preview...)
    - [AndroidManifest.xml](../app/src/main/AndroidManifest.xml): register the receiver.

Note that this demo app does not provide a configuration Activity. You can consult the following [documentation](https://developer.android.com/develop/ui/views/appwidgets/configuration) to implement it.

## Library

### Extensions

This package provides some extensions, as we have some limitation with Glance, i.e we can not use all Compose APIs (like LocalDensity, Modifier):
- [DpExt.kt](src/main/kotlin/studio/lunabee/compose/glance/extensions/DpExt.kt): brings some extensions to create `Spacer` with a `GlanceModifier`
- [GlanceModifierExt.kt](src/main/kotlin/studio/lunabee/compose/glance/extensions/GlanceModifierExt.kt):
    - `appWidgetBackgroundCompat`: can be used to apply rounded corner on your widget on all APIs (supported by default only on API31+)
- [GlanceDensityExt.kt](src/main/kotlin/studio/lunabee/compose/glance/extensions/GlanceDensityExt.kt): extension that replace usage of `LocalDensity.toPx`

### Helpers

This package provides helpful method or classes:
- [GlanceActions.kt](src/main/kotlin/studio/lunabee/compose/glance/helpers/GlanceActions.kt): get `Action` to be execute on any clickable elements of Glance.
- [GlanceStringResources.kt](src/main/kotlin/studio/lunabee/compose/glance/helpers/GlanceStringResources.kt): provides equivalent for `stringResource` and `pluralResource` for Glance.
- [GlanceTextToImage.kt](src/main/kotlin/studio/lunabee/compose/glance/helpers/GlanceTextToImage.kt): create a `Bitmap` from a `String` for API21+
- [PinWidgetToHomeScreenHelper.kt](src/main/kotlin/studio/lunabee/compose/glance/helpers/PinWidgetToHomeScreenHelper.kt): offers the possibility to pin the widget from the application (API26+)

### UI

#### GlanceBackground

With [GlanceBackground.kt](src/main/kotlin/studio/lunabee/compose/glance/ui/GlanceBackground.kt), you can add a XML background behind any other view. 
Check the sample in [GlanceWidgetDemo.kt](../app/src/main/kotlin/studio/lunabee/compose/demo/glance/GlanceWidgetDemo.kt).

⚠️ If you have a background with a simple `Color` or an image, use directly `GlanceModifier.background` on your view.

#### GlanceTypefaceText

With [GlanceTypefaceText.kt](src/main/kotlin/studio/lunabee/compose/glance/ui/GlanceTypefaceText.kt), you can use your custom font to display a text in a widget.
This feature is not supported by the default API (even in pure XML). Your text will be displayed as an image.
Check the sample in [GlanceWidgetDemo.kt](../app/src/main/kotlin/studio/lunabee/compose/demo/glance/GlanceWidgetDemo.kt).

⚠️ If you don't have any specific font to use, use directly `Text` composable.

#### GlanceTypefaceButton

With [GlanceTypefaceButton.kt](src/main/kotlin/studio/lunabee/compose/glance/ui/GlanceTypefaceButton.kt), you can use your custom font to display a button in a widget.
It uses, under the hood, the `GlanceTypefaceText`
Check the sample in [GlanceWidgetDemo.kt](../app/src/main/kotlin/studio/lunabee/compose/demo/glance/GlanceWidgetDemo.kt).

⚠️ If you don't have any specific font to use, use directly `Button` composable.

#### GlanceViewFlipper

With [GlanceViewFlipper.kt](src/main/kotlin/studio/lunabee/compose/glance/ui/GlanceViewFlipper.kt), you can change view with animation.
As the `ViewFlipper` does not expose all its methods to the `RemoteViews`, you need to provide a XML layout with your animations as XML.
Check the sample in [GlanceWidgetDemo.kt](../app/src/main/kotlin/studio/lunabee/compose/demo/glance/GlanceWidgetDemo.kt).
