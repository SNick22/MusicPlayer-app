@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("azat.android.application.compose")
    id("azat.android.hilt")
    alias(libs.plugins.google.service)
}

android {
    namespace = "com.azat.musicplayer"

    defaultConfig {
        applicationId = "com.azat.musicplayer"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    dependencies {
        implementation(project(":core:navigation"))
        implementation(project(":core:designsystem"))
        implementation(project(":core:presentation"))
        implementation(project(":core:common-impl"))
        implementation(project(":features:home"))
        implementation(project(":features:auth"))
        implementation(project(":features:player"))
    }
}
