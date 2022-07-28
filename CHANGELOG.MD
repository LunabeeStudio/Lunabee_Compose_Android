# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres
to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased

### LbcTopAppBar

- *Breaking*: remove `LbcNavigationTopAppBar` and `LbcLoadingNavigationTopAppBar` (see documentation for replacement).
- *Breaking*: Change parameters for `LbcTopAppBar` and `LbcLoadingTopAppBar` (see documentation for replacement).
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

- Update Jetpack Compose to `1.2.0`  and Kotlin to `1.7.0`

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