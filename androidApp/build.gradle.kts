plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.whenyourapprun.elevator.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.whenyourapprun.elevator.android"
        minSdk = 23
        targetSdk = 33
        versionCode = 6
        versionName = "1.5"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // shared
    implementation(project(":shared"))
    // default compose
    implementation("androidx.compose.ui:ui:1.3.1")
    implementation("androidx.compose.ui:ui-tooling:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.1")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
    // gson
    implementation("com.google.code.gson:gson:2.9.0")
    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    // ads
    implementation("com.google.android.gms:play-services-ads:21.3.0")
    implementation("androidx.lifecycle:lifecycle-runtime:2.5.1")
    implementation("androidx.lifecycle:lifecycle-process:2.5.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.5.1")
    // test
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.3.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.3.1")
}