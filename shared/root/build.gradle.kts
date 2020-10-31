setupMultiplatform()

plugins.apply("kotlin-android-extensions")

kotlinCompat {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":shared:common"))
                implementation(project(":shared:database"))
                implementation(project(":shared:sync"))
                implementation(project(":shared:session-list"))
                implementation(project(":shared:session-details"))
                implementation(Deps.Badoo.Reaktive.Reaktive)
                implementation(Deps.ArkIvanov.MviKotlin.MviKotlin)
                implementation(Deps.ArkIvanov.MviKotlin.MviKotlinMain)
                implementation(Deps.ArkIvanov.Decompose.Decompose)
            }
        }

        androidMain {
            dependencies {
                implementation(Deps.ArkIvanov.Decompose.ExtensionsAndroid)
                implementation(Deps.AndroidX.Transition.TransitionKtx)
            }
        }
    }
}
