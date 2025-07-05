plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // Ksp
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.local"
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

    // Datastore
    implementation(libs.androidx.datastore.preferences)
    // Room
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}