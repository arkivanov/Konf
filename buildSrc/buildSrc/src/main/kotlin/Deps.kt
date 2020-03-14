object Deps {

    object Jetbrains {
        object Kotlin : Group(name = "org.jetbrains.kotlin") {
            private const val version = "1.3.61"

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
                private const val version = "0.20.0"

                object RuntimeCommon : Dependency(group = Kotlinx, name = "kotlinx-serialization-runtime-common", version = version)
                object Runtime : Dependency(group = Kotlinx, name = "kotlinx-serialization-runtime", version = version)
                object RuntimeNative : Dependency(group = Kotlinx, name = "kotlinx-serialization-runtime-native", version = version)
            }
        }
    }

    object Android {
        object Tools {
            object Build : Group(name = "com.android.tools.build") {
                object Gradle : Dependency(group = Build, name = "gradle", version = "3.5.2")
            }
        }
    }

    object AndroidX {
        object Core : Group(name = "androidx.core") {
            object Ktx : Dependency(group = Core, name = "core-ktx", version = "1.1.0")
        }

        object AppCompat : Group(name = "androidx.appcompat") {
            object AppCompat : Dependency(group = AndroidX.AppCompat, name = "appcompat", version = "1.1.0")
        }

        object RecyclerView : Group(name = "androidx.recyclerview") {
            object RecyclerView : Dependency(group = AndroidX.RecyclerView, name = "recyclerview", version = "1.1.0")
        }

        object ConstraintLayout : Group(name = "androidx.constraintlayout") {
            object ConstraintLayout : Dependency(group = AndroidX.ConstraintLayout, name = "constraintlayout", version = "1.1.3")
        }

        object DrawerLayout : Group(name = "androidx.drawerlayout") {
            object DrawerLayout : Dependency(group = AndroidX.DrawerLayout, name = "drawerlayout", version = "1.0.0")
        }

        object SwipeRefreshLayout : Group(name = "androidx.swiperefreshlayout") {
            object SwipeRefreshLayout : Dependency(group = AndroidX.SwipeRefreshLayout, name = "swiperefreshlayout", version = "1.0.0")
        }

        object CardView : Group(name = "androidx.cardview") {
            object CardView : Dependency(group = AndroidX.CardView, name = "cardview", version = "1.0.0")
        }
    }

    object Badoo {
        object Reaktive : Group(name = "com.badoo.reaktive") {
            private const val version = "1.1.10"

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
            private const val version = "2.0.0-preview1"

            object MviKotlin : Dependency(group = ArkIvanov.MviKotlin, name = "mvikotlin", version = version)
            object MviKotlinMain : Dependency(group = ArkIvanov.MviKotlin, name = "mvikotlin-main", version = version)

            object MviKotlinExtensionsReaktive :
                Dependency(group = ArkIvanov.MviKotlin, name = "mvikotlin-extensions-reaktive", version = version)
        }
    }

    object Squareup {
        object SqlDelight : Group(name = "com.squareup.sqldelight") {
            private const val version = "1.2.2"

            object GradlePlugin : Dependency(group = SqlDelight, name = "gradle-plugin", version = version)
            object AndroidDriver : Dependency(group = SqlDelight, name = "android-driver", version = version)
            object NativeDriver : Dependency(group = SqlDelight, name = "native-driver", version = version)
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
