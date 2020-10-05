plugins {
    kotlin("plugin.serialization")
}

setupMultiplatform()

kotlinCompat {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":shared:common"))
                implementation(project(":shared:database"))
                implementation(Deps.Badoo.Reaktive.Utils)
                implementation(Deps.Badoo.Reaktive.Reaktive)
                implementation(Deps.Badoo.Reaktive.ReaktiveAnnotations)
                implementation(Deps.ArkIvanov.MviKotlin.MviKotlin)
                implementation(Deps.ArkIvanov.MviKotlin.MviKotlinExtensionsReaktive)
                implementation(Deps.ArkIvanov.Decompose.Decompose)
                implementation(Deps.Jetbrains.Kotlinx.Serialization.Json)
            }
        }
    }
}
