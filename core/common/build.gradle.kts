plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // Ksp
    alias(libs.plugins.ksp)
    // Hilt
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.common"
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

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    // Paging
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime.ktx)
    // Testing
    testImplementation(libs.junit)
}