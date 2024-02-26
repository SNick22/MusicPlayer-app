@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("azat.android.feature")
    id("azat.android.library.compose")
}

android {
    namespace = "com.azat.home"
}

dependencies {
    implementation(libs.paging)
    implementation(libs.paging.compose)
}
