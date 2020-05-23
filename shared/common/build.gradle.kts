setupMultiplatform()

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(Deps.ArkIvanov.MviKotlin.MviKotlin)
            }
        }
    }
}
