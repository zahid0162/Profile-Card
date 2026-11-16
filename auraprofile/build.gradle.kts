plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")
}

android {
    namespace = "com.example.auraprofile"
    compileSdk { version = release(36) { minorApiLevel = 1 } }

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.core.ktx)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "io.github.auraprofile"
                artifactId = "auraprofile-card"
                version = "1.0.0"

                pom {
                    name.set("AuraProfileCard")
                    description.set("Ultra-creative, animated Jetpack Compose Profile and Info Card with Glassmorphism, 3D Holo Flip, Curtain Reveal, Parallax Nebula, and Titanium Luxe.")
                    url.set("https://github.com/your-username/auraprofile-card")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("auraprofile")
                            name.set("Aura Profile Team")
                            email.set("dev@auraprofile.io")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/your-username/auraprofile-card.git")
                        developerConnection.set("scm:git:ssh://github.com:your-username/auraprofile-card.git")
                        url.set("https://github.com/your-username/auraprofile-card")
                    }
                }
            }
        }
    }
}
