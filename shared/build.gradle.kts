import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-48"
    id("co.touchlab.skie") version "0.10.8"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    val xcf = XCFramework("Shared")
    listOf(
        iosArm64(),
        iosX64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
            export("com.rickclephas.kmp:kmp-nativecoroutines-core:1.0.0-ALPHA-48")
            xcf.add(this)
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
            implementation(libs.bundles.ktor)
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.11")
            api("com.rickclephas.kmp:kmp-nativecoroutines-core:1.0.0-ALPHA-48")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
    }
}

skie {
    features {
        coroutinesInterop.set(false)
    }
}

android {
    namespace = "org.ramees.kmp.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
