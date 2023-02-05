plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.bumble.appyx.interop.rx3"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
    api(project(":libraries:core"))
    api(libs.rxjava3)
    api(libs.rxrelay3)

    implementation(libs.androidx.lifecycle.java8)

    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
}
