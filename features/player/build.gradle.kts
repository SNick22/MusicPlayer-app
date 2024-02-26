plugins {
    id("azat.android.feature")
    id("azat.android.library.compose")
}

android {
    namespace = "com.azat.player"
}

dependencies {
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.palette)
    implementation(libs.kotlinx.coroutines.guava)
}
