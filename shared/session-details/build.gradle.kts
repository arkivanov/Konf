setupMultiplatform()

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":shared:database"))
                implementation(Deps.Badoo.Reaktive.Reaktive)
                implementation(Deps.Badoo.Reaktive.ReaktiveAnnotations)
                implementation(Deps.ArkIvanov.MviKotlin.MviKotlin)
                implementation(Deps.ArkIvanov.MviKotlin.MviKotlinExtensionsReaktive)
            }
        }
    }
}
