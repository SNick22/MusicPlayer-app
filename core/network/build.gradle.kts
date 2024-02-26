plugins {
    id("azat.android.library")
    id("azat.android.hilt")
}

android {
    namespace = "com.azat.network"

    buildTypes.onEach {
        it.buildConfigField("String", "BASE_URL", "\"http://192.168.0.181:8080/\"")
    }
}

dependencies {
    api(libs.ktor.core)
    api(libs.ktor.cio)
    api(libs.ktor.logging)
    api(libs.ktor.serialization)
    api(libs.ktor.content.negotiation)
    api(libs.ktor.auth)

    implementation(project(":core:database"))
    implementation(project(":core:common-impl"))
}
