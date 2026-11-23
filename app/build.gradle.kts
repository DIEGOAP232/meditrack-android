plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.meditrack2"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.meditrack2"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.cardview)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(libs.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    // GENERACIÓN DE CÓDIGOS QR
    implementation("com.google.zxing:core:3.5.3")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    // Registro de solicitudes HTTP
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // RETROFIT: comunicación con el backend
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

// GSON: conversión JSON a objetos Java
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
}