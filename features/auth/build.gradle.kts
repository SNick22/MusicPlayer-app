plugins {
    id("azat.android.feature")
    id("azat.android.library.compose")
}

android {
    namespace = "com.azat.auth"
}

dependencies {
    api(platform(libs.firebase.bom))
    api(libs.firebase.analytics)
    api(libs.firebase.messaging)
}
