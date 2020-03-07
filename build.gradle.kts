plugins {
    `kotlin-dsl`
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/badoo/maven")
        maven("https://dl.bintray.com/arkivanov/maven")
    }
}
