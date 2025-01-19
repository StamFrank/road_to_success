buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
        maven(url = "https://jitpack.io")
        maven(url = "https://developer.huawei.com/repo/")
    }
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
    }
}