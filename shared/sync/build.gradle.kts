plugins {
    kotlin("plugin.serialization")
}

setupMultiplatform()

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":shared:database"))
                implementation(Deps.Badoo.Reaktive.Utils)
                implementation(Deps.Badoo.Reaktive.Reaktive)
                implementation(Deps.Badoo.Reaktive.ReaktiveAnnotations)
                implementation(Deps.ArkIvanov.MviKotlin.MviKotlin)
                implementation(Deps.ArkIvanov.MviKotlin.MviKotlinExtensionsReaktive)
                implementation(Deps.Jetbrains.Kotlinx.Serialization.RuntimeCommon)
            }
        }

        androidMain {
            dependencies {
                implementation(Deps.Jetbrains.Kotlinx.Serialization.Runtime)
            }
        }

        iosCommonMain {
            dependencies {
                implementation(Deps.Jetbrains.Kotlinx.Serialization.RuntimeNative)
            }
        }
    }
}
