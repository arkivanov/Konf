setupMultiplatform()

kotlinCompat {
    sourceSets {
        commonMain {
            dependencies {
                implementation(Deps.ArkIvanov.MviKotlin.Rx)
                implementation(Deps.ArkIvanov.MviKotlin.MviKotlin)
                implementation(Deps.ArkIvanov.Decompose.Decompose)
            }
        }

        androidMain {
            dependencies {
                implementation(Deps.AndroidX.AppCompat.AppCompat)
                implementation(Deps.AndroidX.RecyclerView.RecyclerView)
                implementation(Deps.Squareup.Picasso.Picasso)
            }
        }
    }
}
