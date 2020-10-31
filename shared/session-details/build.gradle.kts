setupMultiplatform()

kotlinCompat {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":shared:database"))
                implementation(project(":shared:common"))
                implementation(Deps.Badoo.Reaktive.Reaktive)
                implementation(Deps.Badoo.Reaktive.ReaktiveAnnotations)
                implementation(Deps.ArkIvanov.MviKotlin.MviKotlin)
                implementation(Deps.ArkIvanov.MviKotlin.MviKotlinExtensionsReaktive)
                implementation(Deps.ArkIvanov.Decompose.Decompose)
            }
        }

        androidMain {
            dependencies {
                implementation(Deps.ArkIvanov.Decompose.ExtensionsAndroid)
                implementation(Deps.Google.Android.Material.Material)
                implementation(Deps.AndroidX.ConstraintLayout.ConstraintLayout)
                implementation(Deps.AndroidX.CoordinatorLayout.CoordinatorLayout)
                implementation(Deps.AndroidX.Core.Ktx)
            }
        }
    }
}
