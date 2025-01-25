import org.gradle.kotlin.dsl.configureEach
import org.jetbrains.dokka.gradle.DokkaTask
import java.util.Properties
import kotlin.text.set

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.googleDevtoolsKsp)
    alias(libs.plugins.hiltPlugin)
    alias(libs.plugins.serialization)
    alias(libs.plugins.jetbrains.dokka)
    id("kotlin-parcelize")
}

android {
    namespace = "com.rossyn.instagram.reels.story.downloader"
    compileSdk = 35

    defaultConfig {
        applicationId = namespace
        minSdk = 24
        targetSdk = 35
        versionCode = 1
//        versionName = "1.0.0"
        versionName = "0.dev.testing.hilt"
        setProperty("archivesBaseName", "InSaver-$versionName")
        vectorDrawables.useSupportLibrary = true
        renderscriptTargetApi = 24
        renderscriptSupportModeEnabled = true
        multiDexEnabled = true
        resourceConfigurations += setOf()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    packaging {
        jniLibs.useLegacyPackaging = false
//        resources {
//            excludes += listOf("META-INF/LICENSE", "META-INF/NOTICE", "META-INF/java.properties")
//        }
    }

    lint {
        abortOnError = false
        checkReleaseBuilds = false

//        ignoreWarnings = true
//        checkDependencies = true
//        warningsAsErrors = false
    }

    allprojects {
        gradle.projectsEvaluated {
            tasks.withType<JavaCompile> {
                options.compilerArgs.add("-Xlint:unchecked")
            }
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.transition.ktx)
    implementation(libs.browser)
    implementation(libs.dotsindicator)
    implementation(libs.androidx.security.crypto)
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.androidx.multidex)

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)


    implementation(libs.glide)
    ksp(libs.glide)
    annotationProcessor(libs.compiler)
    implementation(libs.glide.transformations)
    implementation(libs.toolargetool)
    implementation(libs.zoomage)
    implementation(libs.lottie)


    // gson
    implementation(libs.gson)
    // retrofit
    implementation(libs.adapter.rxjava3)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    //http clint
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    //    rx java
    implementation(libs.rxandroid)
    implementation(libs.rxjava)


    // Room components
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.androidx.room.testing)

    // Lifecycle components
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.common.java8)



    dokkaPlugin(libs.android.documentation.plugin)

    implementation(libs.timber)

    debugImplementation(libs.leakcanary.android)

    implementation(libs.view)
    implementation(libs.animations)

    implementation(libs.hilt.android)
    ksp(libs.dagger.compiler)
    ksp(libs.hilt.compiler)
}

tasks.withType<DokkaTask>().configureEach {
    moduleName.set("InSaver (${project.android.defaultConfig.versionName})")
    moduleVersion.set(project.version.toString())
    outputDirectory.set(file("../docs"))
    failOnWarning.set(false)

    dokkaSourceSets {
        configureEach {
            displayName.set("InSaver")
            documentedVisibilities.set(
                setOf(
                    org.jetbrains.dokka.DokkaConfiguration.Visibility.PUBLIC,
                    org.jetbrains.dokka.DokkaConfiguration.Visibility.PROTECTED
                )
            )
            skipEmptyPackages.set(true)
        }
    }
}