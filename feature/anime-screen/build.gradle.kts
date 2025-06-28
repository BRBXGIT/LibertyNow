plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // Compose
    alias(libs.plugins.kotlin.compose)
    // Serialization
    alias(libs.plugins.kotlin.serialization)
    // Ksp
    alias(libs.plugins.ksp)
    // Hilt
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.anime_screen"
    compileSdk = 35

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // Core
    implementation(project(":core:design-system"))
    implementation(project(":core:data"))
    implementation(project(":core:network"))
    implementation(project(":core:common"))

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    // Material 3
    implementation(libs.androidx.material3)
    // Nav
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    // Json
    implementation(libs.converter.gson)
}