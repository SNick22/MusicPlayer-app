plugins {
    id("azat.android.library")
    id("azat.android.library.compose")
}

android {
    namespace = "com.azat.common_impl"
}

dependencies {
    api(project(":core:common"))
    implementation(platform(libs.compose.bom))
    implementation(libs.compose)
}