import co.touchlab.kotlinxcodesync.SyncExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

fun Project.setupMultiplatform() {
    plugins.apply("kotlin-multiplatform")
    plugins.apply("com.android.library")

    setupAndroidSdkVersions()

    kotlin {
        android {
            publishLibraryVariants("release", "debug")
        }

        iosX64()
        iosArm64()

        sourceSets {
            commonMain {
                dependencies {
                    implementation(Deps.Jetbrains.Kotlin.StdLib.Common)
                }
            }

            commonTest {
                dependencies {
                    implementation(Deps.Jetbrains.Kotlin.Test.Common)
                    implementation(Deps.Jetbrains.Kotlin.TestAnnotations.Common)
                }
            }

            androidMain {
                dependsOn(commonMain())

                dependencies {
                    implementation(Deps.Jetbrains.Kotlin.StdLib.Jdk7)
                }
            }

            androidTest {
                dependsOn(commonTest())

                dependencies {
                    implementation(Deps.Jetbrains.Kotlin.Test.Junit)
                }
            }

            iosCommonMain().dependsOn(commonMain())
            iosCommonTest().dependsOn(commonTest())

            iosX64Main().dependsOn(iosCommonMain())
            iosX64Test().dependsOn(iosCommonTest())

            iosArm64Main().dependsOn(iosCommonMain())
            iosArm64Test().dependsOn(iosCommonTest())
        }

        val iosX64Test: Task by tasks.creating {
            val device = findProperty("iosDevice")?.toString() ?: "iPhone 8"
            val testExecutable = iosX64().binaries.getTest(NativeBuildType.DEBUG)
            dependsOn(testExecutable.linkTaskName)
            group = JavaBasePlugin.VERIFICATION_GROUP
            description = "Runs tests for target 'iosX64' on an iOS simulator"

            println("Path: testExecutable.outputFile.absolutePath")

            doLast {
                exec {
                    commandLine("xcrun", "simctl", "spawn", "--standalone", device, testExecutable.outputFile.absolutePath)
                }
            }
        }

        tasks.getByName("check").dependsOn(iosX64Test)
    }
}

fun Project.setupAndroidSdkVersions() {
    android {
        compileSdkVersion(29)

        defaultConfig {
            targetSdkVersion(29)
            minSdkVersion(15)
        }
    }
}

fun Project.android(block: BaseExtension.() -> Unit) {
    extensions.getByType<BaseExtension>().block()
}

fun Project.kotlin(block: KotlinMultiplatformExtension.() -> Unit) {
    extensions.getByType<KotlinMultiplatformExtension>().block()
}

fun Project.setupXcodeSync() {
    plugins.apply("co.touchlab.kotlinxcodesync")

    extensions.getByType<SyncExtension>().run {
        projectPath = "../todo-app-ios/todo-app-ios.xcodeproj"
        target = "todo-app-ios"
    }
}

typealias SourceSets = NamedDomainObjectContainer<KotlinSourceSet>

fun KotlinMultiplatformExtension.sourceSets(block: SourceSets.() -> Unit) {
    sourceSets.block()
}

private fun SourceSets.getOrCreate(name: String): KotlinSourceSet = findByName(name) ?: create(name)

// common

fun SourceSets.commonMain(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("commonMain").apply(block)

fun SourceSets.commonTest(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("commonTest").apply(block)

// android

fun SourceSets.androidMain(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("androidMain").apply(block)

fun SourceSets.androidTest(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("androidTest").apply(block)

// iosCommon

fun SourceSets.iosCommonMain(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("iosCommonMain").apply(block)

fun SourceSets.iosCommonTest(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("iosCommonTest").apply(block)

// iosX64

fun SourceSets.iosX64Main(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("iosX64Main").apply(block)

fun SourceSets.iosX64Test(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("iosX64Test").apply(block)

// iosArm64

fun SourceSets.iosArm64Main(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("iosArm64Main").apply(block)

fun SourceSets.iosArm64Test(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("iosArm64Test").apply(block)
