import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id("com.android.application")
    kotlin("android")
}

setupAndroid()

androidCompat {
    defaultConfig {
        applicationId = "com.arkivanov.konf.android"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    (this as ExtensionAware).configure<KotlinJvmOptions> {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared:common"))
    implementation(project(":shared:database"))
    implementation(project(":shared:root"))
    implementation(Deps.ArkIvanov.Decompose.Decompose)
    implementation(Deps.ArkIvanov.Decompose.ExtensionsAndroid)
    implementation(Deps.Jetbrains.Kotlin.StdLib.Jdk7)
    implementation(Deps.AndroidX.AppCompat.AppCompat)
    implementation(Deps.Google.Android.Material.Material)
}
