plugins {
    id("com.squareup.sqldelight")
}

sqldelight {
    database("KonfDatabase") {
        packageName = "com.arkivanov.konf.database"
    }
}

setupMultiplatform()

kotlinCompat {
    sourceSets {
        commonMain {
            dependencies {
                implementation(Deps.Badoo.Reaktive.Reaktive)
                implementation(Deps.Badoo.Reaktive.ReaktiveAnnotations)
            }
        }

        androidMain {
            dependencies {
                implementation(Deps.Squareup.SqlDelight.AndroidDriver)
            }
        }

        iosCommonMain {
            dependencies {
                implementation(Deps.Squareup.SqlDelight.NativeDriver)
            }
        }
    }
}
