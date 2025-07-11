# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres    
to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## 2025-07-03

### All

```
LbcAndroidTest 1.12.0
LbcUiFieldCore 1.8.0
LbcUiFieldPhonePicker 1.2.0
LbcUiFieldCountryPicker 1.1.0
LbcPresenter 1.5.0
```

- **Breaking:** Migrate from context receiver to context parameters

## 2025-05-15

### All

- Move to jReleaser to manage releases & snapshots deployment to maven central

### LbcAndroidTest : 1.11.1

- Fix 1.11.0 empty pom deployed

## 2025-05-13

### LbcAndroidTest : 1.11.0

- Add `getScreenshotDir` fun on `LbcPrintRule` to give a direct access to the screenshot directory

## 2025-05-07

### LbcUiField-PhonePicker : 1.1.0

- **Breaking:** Move the code related to the country picker to separate module (more info & documentation
  here : https://www.notion.so/lunabeestudio/Lunabee-Compose-library-42f4d91d463a4e8492a1a4d3d32a243f?pvs=4#1e3770e99af7805c878becb4d1ebfac7)

### LbcUiField-CountryPicker : 1.0.0

- Add new `CountryPickerUiField`

## 2025-04-28

### LbcUiField : 1.7.0

- **Breaking:** Move all module code into a `core` module. Clients should now import  `studio.lunabee.compose:lbcuifield-core`
- **Breaking:** Make the `placeholder` and `label` fields nullable for `UiField`

### LbcUiField-PhonePicker : 1.0.0

- Add new `PhonePickerUiField`

## 2025-03-19

### All

- Add `setRequired` block on signing to disable signing for maven local publishing

### LbcPresenter 1.4.0

- Add specific module `lbcpresenter-koin` to use LbcPresenter with Koin. Provides `PresentScreen` fun to inject presenter as viewmodel
- **Breaking:** Remove `foundation` middle package

## 2025-03-14

### LbcPresenter 1.3.0

- Add verbose param to reducer and presenter to get some logs on presenter states

## LbcUiField 1.6.1

- Make `trailingIcon` nullable
- **Breaking:**
    - `PasswordUiTextField` now use `CorePasswordStyleData` and `PasswordVisibilityOptionData`
    - `KeyboardActions` has been replaced by `KeyboardActionHandler` (due to the usage of the `BasicSecureTextField`)
    - Exposition of  `readOnly` and `enabled`

## 2025-02-12

### All

- Update Compose & navigation

## 2025-02-05

### LbcCore

- Add `uiMode` param to `LbcImageSpec.ImageDrawable` to allow day/night force

### LbcImage

- Handle `uiMode` param to allow day/night force

### LbcPresenter

- New module containing LbcPresenter && LbcReducer

### LbcUiField

- **Breaking**: Add an `interactionSource` param to `ComposeTextField` to fix a focus issue when using a time field as first field
  depending    
  on Android API version (reproducible on API 27)

### LbcAccessibility

- Add currency check and tests to ensure that `cleanForAccessibility` function works properly

### All

- Update dependencies (including compose 1.7 and Android SDK 35)
- Add branch name in version for snapshots

### LbcGlance

- Add new `LbcGlance` module.

### LbcAndroidTest

- Use `takeScreenshotNoSync` instead of `takeScreenshot` in case of test failure
- Add `verbose` param to `LbcPrintRule` to enable verbose logging

## 2024-06-27

``` 
lbcCore = "1.4.0"    
lbcFoundation = "1.3.0"    
lbcAndroidTest = "1.2.0"    
lbcAccessibility = "1.7.0"    
lbcTheme = "1.3.0"    
matieralColorUtilities = "1.2.0"    
lbcHaptic = "1.1.0"    
lbcUiField = "1.0.1"    
lbcImage = "1.0.1"    
```   

### LbcImage

- Fallback to `LocalContentColor.current` if no tint provided on `LbcImage`

### LbcUiField

- Add `onValueChange` callback on UiTextField

## 2024-06-14

- Migrate to K2 🏔️
- Update dependencies
- Fix Kotlin sources not published anymore on Maven
- Add `publishList` Gradle task to get all module to publish from CI
- Rename source dirs from `java` to `kotlin`
- Use precompiled plugin for publishing
- `LbcUiField`: New module for displaying fom easily
- `LbcImage`: New module to display image data from `LbcImageSpec`
- `LbcHapticFeedback`: New module to perform haptics feedback from compose
- `LbcAndroidTest`: Catch root print error more precisely, and don't rethrow when the screenshot failed
- `LbcCore`: Add `fun annotated(context: Context): AnnotatedString` in `LbcTextSpec` to retrieve `AnnotatedString` directly
- `LbcCore`: Add `LbcImageSpec`

``` lbcCore = "1.4.0"    
lbcFoundation = "1.3.0"    
lbcAndroidTest = "1.2.0"    
lbcAccessibility = "1.7.0"    
lbcTheme = "1.3.0"    
matieralColorUtilities = "1.2.0"    
lbcHaptic = "1.1.0"    
lbcUiField = "1.0.0"    
lbcImage = "1.0.0"    
```   

## 2024-03-28

``` lbcCore 1.3.0    
lbcFoundation 1.2.0    
lbcAndroidTest 1.3.0    
lbcAccessibility 1.6.0    
lbcTheme 1.2.0    
materialColorUtilities 1.2.0    
lbcHaptic 1.0.0    
lbcImage 1.0.0    
lbcUiField 1.0.0    
```   

## 1.2.0

### LbcAndroidTest

- Add params `deleteOnSuccess` and `appendTimestamp` to `LbcPrintRule`
- Allow client to choose between internal or public storage for `LbcPrintRule`
- Rework `LbcPrintRule` constructor with `internalStorage` and `publicStorage` factories
- Catch `AssertionError` in print helper in case of error due to multiple root. Fallback to print whole screen
- Add a whole screen screenshot in `LbcComposeTest::invoke` on failure to get the last state of the app
- Use context receiver for `ComposeUiTest` on matcher extension
- Log the unmerged tree on test failed

## 1.1.0

### All

- Update to last stable version of Jetpack Compose.
- Update Gradle Plugin to `7.3.1`.
- **Breaking**: remove `LbcTopAppBar` and `LbcGraph`.
- **Breaking**: remove `lbc` prefix from all packages.
- **Breaking**: rename `LbcMaterial` to `LbcFoundation`.
- **Breaking**: delete accessibility `Modifier` extensions, update `AccessibilityState` class.

### LbcAndroidTest

- New module for Android Compose testing.

### LbcCore

- Add a method to get string with context in `LbcTextSpec`
- Add a sealed class of `LbcTextSpec` to find string in resource by name

## 1.0.0

### LbcCore

- New module `LbcCore`
- Add `LbcTextSpec` to factorize merge text sources

## 1.4.0

### LbcAccessibility

- Add `cleanForAccessibility` to remove unexpected character for Talkback.

## 1.0.0

### LbcTheme

- New module `LbcTheme` to build custom theme with Material3 algorithms.

## 1.0.0

### LbcMaterial

- Update Jetpack Compose compiler to `1.3.0`
- Update Kotlin to `1.7.10`
- Update Jetpack Compose to stable version `1.2.1`
- Add new module `lbcmaterial` for Material Composable that can be reuse.
- Add `StyledTextItem` Composable to make highlighted clickable text.

## 1.3.0

### LbcAccessibility

- Update Jetpack Compose compiler to `1.3.0`
- Update Kotlin to `1.7.10`
- Update Jetpack Compose to stable version `1.2.1`
- **Breaking**: remove `AccessibilityDescription` object
- **Breaking**: remove some `Modifier` extension
- **Breaking**: change name and signature for `addSemantics` method to `setAccessibilityDetails`
- Add some new parameters like `LiveRegion` and `ToggleableDescription`
- See documentation for more details

## 1.4.0

### LbcTopAppBar

- Update Jetpack Compose compiler to `1.3.0`
- Update Kotlin to `1.7.10`
- Update Jetpack Compose to stable version `1.2.1`
- **Breaking**: remove `LbcNavigationTopAppBar` and `LbcLoadingNavigationTopAppBar` (see documentation for replacement).
- **Breaking**: Change parameters for `LbcTopAppBar` and `LbcLoadingTopAppBar` (see documentation for replacement).
- Distinct `TopAppBar` background color and status bar color in parameters.
- Add `LbcSurfaceTopAppBar` to wrap your custom `TopAppBar` if needed.
- Add `LbcMenuIconButton` to create menu icon for your `TopAppBar`.
- Add `LbcTopAppBarAction` to describe the content of your `LbcMenuIconButton`.
- New `LbcSearchTopAppBar` with a search text field.
- New default implementation of `LbcSearchTextField` that can be provided to your `LbcSearchTopAppBar` for standard needs.
- `LbcSearchTopAppBarState` that handles `LbcSearchTextField` states and `LbcSearchTopAppBar` expansion.

## 1.3.0

### LbcTopAppBar

- Update Jetpack Compose to `1.2.0` and Kotlin to `1.7.0`

## 1.2.0

### LbcAccessibility

#### Added

- Update Jetpack Compose to `1.2.0` and Kotlin to `1.7.0`

## 1.2.0

### LbcTopAppBar

- Update Jetpack Compose to `1.2.0-rc02`

## 1.1.0

### LbcAccessibility

#### Added

- `OnClickDescription` for `Composable` without `Modifier.clickable` access.
- Update Jetpack Compose to `1.2.0-rc02`

## 1.1.0

### LbcTopAppBar

- Update Jetpack Compose to `1.2.0-beta01`
- Update Kotlin to `1.6.21`

### 1.0.0

### LbcAccessibility

#### Added

- `Modifier` extensions for accessibility
- `AccessibilityCheckBoxRow` and the stateless version of this `Composable`
- `AccessibilityState` to handle `Talkback` activation

## 1.0.0

### LbcTopAppBar

#### Added

- `LbcTopAppBar` as a simple default `TopAppBar`
- `LbcNavigationTopAppBar` as a `TopAppBar` with a navigation icon
- `LbcLoadingTopAppBar` and `LbcLoadingNavigationTopAppBar` as above equivalent with a `LinearProgressIndicator` that can be shown/hidden.