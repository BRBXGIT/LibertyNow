// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Basic plugins
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false

    // Hilt
    alias(libs.plugins.hilt.android) apply false
    // Ksp
    alias(libs.plugins.ksp) apply false
    // Serialization
    alias(libs.plugins.kotlin.serialization) apply false
}