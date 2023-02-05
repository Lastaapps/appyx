plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.bumble.appyx.testing.junit4"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    api(project(":libraries:testing-unit-common"))
    api(libs.junit)
    implementation(libs.kotlin.coroutines.test)
}
