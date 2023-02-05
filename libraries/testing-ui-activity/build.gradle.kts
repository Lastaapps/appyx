plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.bumble.appyx.testing.ui.activity"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    api(project(":libraries:core"))
    implementation(libs.androidx.activity.compose)
}
