// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false

    alias(libs.plugins.googleDevtoolsKsp) apply false
    alias(libs.plugins.jetbrains.dokka) apply false

    alias(libs.plugins.hiltPlugin) apply false
    alias(libs.plugins.serialization) apply false
}