plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.1.20"
    id("kotlin-parcelize")
    id("vkid.manifest.placeholders")
    id("org.jetbrains.kotlin.kapt")

}

android {
    namespace = "com.example.composeapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.composeapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
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
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.kotlinx.serialization)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.gson)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.android.sdk.core)
    implementation(libs.android.sdk.api)
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    implementation(libs.vkid)
    implementation(libs.onetap.compose)
    implementation(libs.vk.sdk.support)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)
    implementation(libs.androidx.security.crypto)
    implementation (libs.retrofit)

    // Converter (Gson - популярный вариант, но есть и другие)
    implementation (libs.converter.gson)

    // Coroutines Adapter (для удобной работы с корутинами) - Optional, but highly recommended
    implementation (libs.retrofit2.kotlin.coroutines.adapter)

    // OkHttp (Retrofit использует OkHttp под капотом, но иногда полезно иметь его явно)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor) // Для логирования запросов (полезно при отладке)

    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    debugImplementation( libs.library)
    releaseImplementation( libs.library.no.op)

}

kapt {
    correctErrorTypes = true
}


