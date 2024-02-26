plugins {
    id("azat.android.library")
    id("azat.android.library.compose")
}

android {
    namespace = "com.azat.presentation"
}

dependencies {
    implementation(libs.activity.compose)
    api(project(":core:designsystem"))
    implementation(project(":core:common-impl"))
}