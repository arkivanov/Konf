import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    (this as ExtensionAware).configure<KotlinJvmOptions> {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared:database"))
    implementation(project(":shared:common"))
    implementation(project(":shared:sync"))
    implementation(project(":shared:session-list"))
    implementation(project(":shared:session-details"))

    implementation(Deps.ArkIvanov.MviKotlin.MviKotlin)
    implementation(Deps.ArkIvanov.MviKotlin.MviKotlinMain)
    implementation(Deps.ArkIvanov.MviKotlin.AndroidxLifecycleInterop)
    implementation(Deps.Jetbrains.Kotlin.StdLib.Jdk7)
    implementation(Deps.AndroidX.AppCompat.AppCompat)
    implementation(Deps.AndroidX.RecyclerView.RecyclerView)
    implementation(Deps.AndroidX.ConstraintLayout.ConstraintLayout)
    implementation(Deps.AndroidX.SwipeRefreshLayout.SwipeRefreshLayout)
    implementation(Deps.AndroidX.CardView.CardView)
    implementation(Deps.AndroidX.DrawerLayout.DrawerLayout)
    implementation(Deps.AndroidX.Core.Ktx)
    implementation(Deps.Google.Android.Material.Material)
    implementation(Deps.Squareup.SqlDelight.AndroidDriver)
    implementation(Deps.Squareup.Picasso.Picasso)
}
