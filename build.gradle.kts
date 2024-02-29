// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    kotlin("android") version (libs.versions.kotlin.get()) apply false
    kotlin("jvm") version (libs.versions.kotlin.get()) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.dagger) apply false
}
true // Needed to make the Suppress annotation work for the plugins block