plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.android.ksp)
}

android {
    namespace = "com.aya.todoapp_jetpackcompose"
    compileSdk = 35


    defaultConfig {
        applicationId = "com.aya.todoapp_jetpackcompose"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)


    // Hilt main dependency
    implementation( "com.google.dagger:hilt-android:2.56.2")

    // Hilt compiler (annotation processor)
    ksp( "com.google.dagger:hilt-android-compiler:2.56.2")

    // (اختياري) لو بتستخدم Hilt مع ViewModel
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")


    //  coroutines
    implementation ("androidx.room:room-ktx:2.7.2")
    // implementation("androidx.room:room-runtime:2.7.2")
    //  ksp ("androidx.room:room-compiler:2.7.2")

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}