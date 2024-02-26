import org.gradle.kotlin.dsl.libs

plugins {
    id("azat.android.library")
    id("azat.android.hilt")
}

android {
    namespace = "com.azat.database"
}

dependencies {
    api(libs.room)
    api(libs.room.paging)
    api(libs.room.ktx)
    annotationProcessor(libs.room.kapt)
    kapt(libs.room.kapt)
}
