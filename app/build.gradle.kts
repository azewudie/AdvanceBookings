plugins {
    alias(libs.plugins.androidApplication)
    kotlin("android")
    alias(libs.plugins.ktlint)
    alias(libs.plugins.daggerHiltAndroid)
    alias(libs.plugins.googleKsp)
    kotlin("kapt")
}

android {
    namespace = "com.aaron.advancebookings"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aaron.advancebookings"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "BASE_URL", "\"${project.properties["BASE_URL"]}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // hilt dependencies
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.core.i18n)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
// compose dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    ksp(libs.compose.destinations)
    implementation(libs.compose.destinations.core)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.compose.material3.window.size)
    implementation(libs.androidx.activity.compose)

// retrofit dependencies
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
// android core dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
// json dependency
    implementation(libs.jsoup)
// system ui controller dependency
    implementation(libs.accompanist.systemuicontroller)
// data store
    implementation(libs.androidx.datastore.preferences)

    // calendar and clock
    implementation(libs.core)
    implementation(libs.calendar)
    implementation(libs.clock)
// coil dependency
    implementation(libs.coil.compose)
// test dependencies
    testImplementation(libs.junit)
    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.junit.ext)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.assertions.truth)
    testImplementation(libs.mockk)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)

}