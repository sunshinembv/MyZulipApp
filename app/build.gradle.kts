@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.myzulipapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myzulipapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle)
    implementation(libs.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.compose.constraintlayout)
    implementation(libs.compose.preview)
    implementation(libs.compose.material)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    //Navigation
    implementation(libs.androidx.navigation)

    //Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    //DI
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    //Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)

    //Okhttp3
    implementation(libs.okhttp3.logging.interceptor)

    //Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    //Accompanist
    implementation(libs.accompanist.systemuicontroller)

    //Coil
    implementation(libs.coil)

    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.uiTooling)
    debugImplementation(libs.compose.ui.test.manifest)
}