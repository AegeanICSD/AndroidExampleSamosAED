import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

// Το πραγματικό κλειδί μένει σε τοπικό αρχείο εκτός Git.
val secrets = Properties()
val secretsFile = rootProject.file("secrets.properties")
if (secretsFile.exists()) {
    secretsFile.inputStream().use { secrets.load(it) }
}
val mapsApiKey = secrets.getProperty("MAPS_API_KEY", "").trim()
val mapsConfigured = mapsApiKey.isNotEmpty() && mapsApiKey != "YOUR_API_KEY"

android {
    namespace = "gr.edu.samosaed"
    compileSdk = 35

    defaultConfig {
        applicationId = "gr.edu.samosaed"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["MAPS_API_KEY"] = if (mapsConfigured) mapsApiKey else ""
        buildConfigField("boolean", "MAPS_CONFIGURED", mapsConfigured.toString())
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2025.04.01"))
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.maps.android:maps-compose:6.4.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
    testImplementation("junit:junit:4.13.2")
}
