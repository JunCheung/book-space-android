@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application)
    kotlin("android")
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger)
}

android {
    namespace = "com.bookspace.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bookspace.app"
        minSdk = 29
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

hilt {
    enableAggregatingTask = true
}

ksp {
    arg("dagger.formatGeneratedSource", "disabled")
    arg("dagger.fastInit", "enabled")
    arg("dagger.experimentalDaggerErrorMessages", "enabled")
}

dependencies {

    implementation(libs.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.ui.graphics)
    implementation(libs.androidx.material3.android)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    // JDK 11 libs
    coreLibraryDesugaring(libs.android.desugar)

    // Kotlin
    implementation(platform(libs.kotlin.bom))

    // KotlinX
    implementation(platform(libs.kotlinx.coroutines.bom))
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutinesjdk8)
    testImplementation(libs.kotlinx.coroutines.test)

    // Dagger / Dependency Injection
    implementation(libs.google.hilt.android)
    implementation(libs.androidx.hilt.compose)
    ksp(libs.google.hilt.compiler)
    kspTest(libs.google.hilt.compiler)
    kspAndroidTest(libs.google.hilt.compiler)
    compileOnly(libs.glassfish.javax.annotation)
    compileOnly(libs.javax.annotation.jsr250)
    compileOnly(libs.google.findbugs.jsr305)
    testImplementation(libs.google.hilt.testing)
    androidTestImplementation(libs.google.hilt.testing)

    // AndroidX Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // AndroidX UI
    implementation(libs.androidx.webkit)
    implementation(libs.google.material)

    // AndroidX Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.compose.uitoolingpreview)
    debugImplementation(libs.androidx.compose.uimanifest)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.compiler)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.uitooling)
    implementation(libs.google.accompanist.drawablepainter)
    implementation(libs.google.accompanist.swiperefresh)
    androidTestImplementation(libs.androidx.compose.junit)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime)

    // OkIO
    implementation(libs.squareup.okio)

    // OkHTTP
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.okhttp.logging)

    // Retrofit
    implementation(libs.squareup.moshi)
    implementation(libs.squareup.moshi.adapters)
    implementation(libs.squareup.moshi.kotlin)
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.moshi)

    // Other
    implementation(libs.jakewharton.timber)

}
