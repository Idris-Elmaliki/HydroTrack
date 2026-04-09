// Top-level build file where you can add configuration options common to all subprojects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // must include this in order to be able to use ksp for libraries like ROOM
    // (check libs.versions.toml for ksp version)
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false

    // For Firebase
    id("com.google.gms.google-services") version "4.4.4" apply false
}