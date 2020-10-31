object Deps {

    object Jetbrains {
        object Kotlin : Group(name = "org.jetbrains.kotlin") {
            private const val version = "1.4.10"

            object Plugin {
                object Gradle : Dependency(group = Kotlin, name = "kotlin-gradle-plugin", version = version)
                object Serialization : Dependency(group = Kotlin, name = "kotlin-serialization", version = version)
            }

            object StdLib {
                object Common : Dependency(group = Kotlin, name = "kotlin-stdlib-common", version = version)
                object Jdk7 : Dependency(group = Kotlin, name = "kotlin-stdlib-jdk7", version = version)
            }

            object Test {
                object Common : Dependency(group = Kotlin, name = "kotlin-test-common", version = version)
                object Junit : Dependency(group = Kotlin, name = "kotlin-test-junit", version = version)
            }

            object TestAnnotations {
                object Common : Dependency(group = Kotlin, name = "kotlin-test-annotations-common", version = version)
            }
        }

        object Kotlinx : Group(name = "org.jetbrains.kotlinx") {
            object Serialization {
                object Json : Dependency(group = Kotlinx, name = "kotlinx-serialization-json", version = "1.0.0-RC2")
            }
        }
    }

    object Android {
        object Tools {
            object Build : Group(name = "com.android.tools.build") {
                object Gradle : Dependency(group = Build, name = "gradle", version = "4.0.1")
            }
        }
    }

    object AndroidX {
        object Core : Group(name = "androidx.core") {
            object Ktx : Dependency(group = Core, name = "core-ktx", version = "1.3.2")
        }

        object AppCompat : Group(name = "androidx.appcompat") {
            object AppCompat : Dependency(group = AndroidX.AppCompat, name = "appcompat", version = "1.2.0")
        }

        object RecyclerView : Group(name = "androidx.recyclerview") {
            object RecyclerView : Dependency(group = AndroidX.RecyclerView, name = "recyclerview", version = "1.1.0")
        }

        object ConstraintLayout : Group(name = "androidx.constraintlayout") {
            object ConstraintLayout : Dependency(group = AndroidX.ConstraintLayout, name = "constraintlayout", version = "2.0.0")
        }

        object DrawerLayout : Group(name = "androidx.drawerlayout") {
            object DrawerLayout : Dependency(group = AndroidX.DrawerLayout, name = "drawerlayout", version = "1.1.1")
        }

        object SwipeRefreshLayout : Group(name = "androidx.swiperefreshlayout") {
            object SwipeRefreshLayout : Dependency(group = AndroidX.SwipeRefreshLayout, name = "swiperefreshlayout", version = "1.1.0")
        }

        object CardView : Group(name = "androidx.cardview") {
            object CardView : Dependency(group = AndroidX.CardView, name = "cardview", version = "1.0.0")
        }

        object CoordinatorLayout : Group(name = "androidx.coordinatorlayout") {
            object CoordinatorLayout : Dependency(group = AndroidX.CoordinatorLayout, name = "coordinatorlayout", version = "1.1.0")
        }

        object Transition : Group(name = "androidx.transition") {
            object TransitionKtx : Dependency(group = Transition, name = "transition-ktx", version = "1.4.0-beta01")
        }
    }

    object Google {
        object Android {
            object Material : Group(name = "com.google.android.material") {
                object Material : Dependency(group = Android.Material, name = "material", version = "1.2.1")
            }
        }
    }

    object Badoo {
        object Reaktive : Group(name = "com.badoo.reaktive") {
            private const val version = "1.1.18"

            object Reaktive : Dependency(group = Badoo.Reaktive, name = "reaktive", version = version)
            object ReaktiveAnnotations : Dependency(group = Badoo.Reaktive, name = "reaktive-annotations", version = version)
            object ReaktiveTesting : Dependency(group = Badoo.Reaktive, name = "reaktive-testing", version = version)
            object Utils : Dependency(group = Badoo.Reaktive, name = "utils", version = version)
        }
    }

    object TouchLab : Group(name = "co.touchlab") {
        object KotlinXcodeSync : Dependency(group = TouchLab, name = "kotlinxcodesync", version = "0.2")
    }

    object ArkIvanov {
        object MviKotlin : Group(name = "com.arkivanov.mvikotlin") {
            private const val version = "2.0.0"

            object Rx : Dependency(group = ArkIvanov.MviKotlin, name = "rx", version = version)
            object MviKotlin : Dependency(group = ArkIvanov.MviKotlin, name = "mvikotlin", version = version)
            object MviKotlinMain : Dependency(group = ArkIvanov.MviKotlin, name = "mvikotlin-main", version = version)

            object MviKotlinExtensionsReaktive :
                Dependency(group = ArkIvanov.MviKotlin, name = "mvikotlin-extensions-reaktive", version = version)
        }

        object Decompose : Group(name = "com.arkivanov.decompose") {
            private const val version = "0.1.0"

            object Decompose : Dependency(group = ArkIvanov.Decompose, name = "decompose", version = version)
            object ExtensionsAndroid : Dependency(group = ArkIvanov.Decompose, name = "extensions-android", version = version)
        }
    }

    object Squareup {
        object SqlDelight : Group(name = "com.squareup.sqldelight") {
            private const val version = "1.4.3"

            object GradlePlugin : Dependency(group = SqlDelight, name = "gradle-plugin", version = version)
            object AndroidDriver : Dependency(group = SqlDelight, name = "android-driver", version = version)
            object NativeDriver : Dependency(group = SqlDelight, name = "native-driver", version = version)
        }

        object Picasso : Group(name = "com.squareup.picasso") {
            private const val version = "2.71828"

            object Picasso : Dependency(group = Squareup.Picasso, name = "picasso", version = version)
        }
    }

    open class Group(val name: String)

    open class Dependency private constructor(
        private val notation: String
    ) : CharSequence by notation {
        constructor(group: Group, name: String, version: String) : this("${group.name}:$name:$version")

        override fun toString(): String = notation
    }
}
