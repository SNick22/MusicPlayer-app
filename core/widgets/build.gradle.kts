@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("azat.android.library")
    id("azat.android.library.compose")
}

android {
    namespace = "com.azat.widgets"
}

dependencies {
    api(libs.kotlinx.collections.immutable)
    api(libs.coil)
    api(libs.coil.compose)
    api(project(":core:designsystem"))
}