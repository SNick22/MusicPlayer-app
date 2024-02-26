@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("azat.android.library")
    id("azat.android.library.compose")
}

android {
    namespace = "com.azat.navigation"
}

dependencies {
    api(libs.voyager.core)
    api(libs.voyager.tab)
    api(libs.voyager.hilt)
    api(libs.voyager.bs)
    api(libs.voyager.transitions)

    implementation(libs.compose)
    implementation(platform(libs.compose.bom))

    implementation(libs.androidx.lifecycle.runtime)
}
