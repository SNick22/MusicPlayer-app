@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("azat.android.library")
    id("azat.android.library.compose")
}

android {
    namespace = "com.azat.designsystem"
}

dependencies {

    api(libs.core.ktx)
    api(libs.material3)
    api(libs.material)
    api(libs.androidx.appcompat)
    api(libs.compose.graphics)
    api(libs.compose.tooling.preview)
    api(libs.compose)
    api(platform(libs.compose.bom))

    debugApi(libs.compose.tooling)

    androidTestApi(platform(libs.compose.bom))
}