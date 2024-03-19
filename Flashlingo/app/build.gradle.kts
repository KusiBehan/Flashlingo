
buildscript{
    repositories{
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.3.0")
        // Add the Checkstyle plugin dependency
        classpath("com.puppycrawl.tools:checkstyle:10.14.2")
    }
}

plugins {
    alias(libs.plugins.androidApplication)
    id("checkstyle")
}

val checkstyle by tasks.registering(Checkstyle::class) {
    configFile = rootProject.file("app/config/checkstyle/checkstyle.xml")
    source("src/main/java")
    include("**/*.java")
    exclude("**/gen/**")
    classpath = files()
}

tasks.named("check") {
    dependsOn(checkstyle)
}


android {
    namespace = "com.example.flashlingo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.flashlingo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.5")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}