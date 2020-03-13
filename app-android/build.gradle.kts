plugins {
    id("com.android.application")
    kotlin("android")
}

setupAndroidSdkVersions()

android {
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
}

dependencies {
    implementation(project(":shared:sync"))
    implementation(project(":shared:session-list"))
    implementation(project(":shared:session-details"))
    implementation(project(":shared:speaker-profile"))

    implementation(Deps.Jetbrains.Kotlin.StdLib.Jdk7)
    implementation(Deps.AndroidX.AppCompat.AppCompat)
    implementation(Deps.AndroidX.RecyclerView.RecyclerView)
    implementation(Deps.AndroidX.ConstraintLayout.ConstraintLayout)
    implementation(Deps.AndroidX.DrawerLayout.DrawerLayout)
    implementation(Deps.AndroidX.Core.Ktx)
}
