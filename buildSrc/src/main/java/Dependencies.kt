import org.gradle.api.JavaVersion

object Config {
    val minSdk = 21
    val compileSdk = 28
    val targetSdk = 30
    val buildTools = "30.0.2"
    val javaVersion = JavaVersion.VERSION_1_8
}

object Versions {
    val androidx_activity = "1.1.0"
    val androidx_annotation = "1.1.0"
    val androidx_appcompat = "1.2.0"
    val androidx_arch_core_testing = "2.1.0"
    val androidx_core = "1.3.2"
    val androidx_constraint_layout = "2.0.4"
    val androidx_espresso = "3.3.0"
    val androidx_fragment = "1.2.5"
    val androidx_junit = "1.1.2"
    val androidx_legacy_support = "1.0.0"
    val androidx_lifecycle = "2.2.0"
    val androidx_navigation = "2.3.2"
    val androidx_paging = "3.0.0-alpha11"
    val androidx_preference = "1.1.1"
    val androidx_recyclerview = "1.1.0"
    val androidx_test_core = "1.3.0"
    val androidx_test_junit = "1.1.2"
    val androidXTest = "1.3.0"
    val circular_image_view = "3.1.0"
    val coroutines = "1.4.2"
    val dagger_hilt = "2.28.3-alpha"
    val desugar_libs = "1.1.1"
    val dexMaker = "2.12.1"
    val glide = "4.11.0"
    val google_truth_assertion = "1.0"
    val gradle = "4.1.1"
    val hamcrest_testing = "1.3"
    val hilt = "1.0.0-alpha02"
    val junit = "4.13.1"
    val kotlin = "1.4.20"
    val kotlin_gradle = "1.4.21"
    val material_design_component = "1.2.1"
    val mockito = "2.8.9" // Do not upgrade mockito to v2.8.9+ there is a bug
    val moshi_kotlin = "1.11.0"
    val okhttp = "4.9.0"
    val retrofit = "2.9.0"
    val retrofit_coroutines = "0.9.2"
    val robolectric = "4.4"
    val room = "2.2.6"
    val shimmer = "0.5.0"
    val timber = "4.7.1"
}

object Dependencies {

    // implementation
    val androidx_activity = "androidx.activity:activity-ktx:${Versions.androidx_activity}"
    val androidx_annotation = "androidx.annotation:annotation:${Versions.androidx_annotation}"
    val androidx_espresso = "androidx.test.espresso:espresso-core:${Versions.androidx_espresso}"
    val androidx_espresso_contrib =
        "androidx.test.espresso:espresso-contrib:${Versions.androidx_espresso}"
    val androidx_espresso_idling =
        "androidx.test.espresso:espresso-idling-resource:${Versions.androidx_espresso}"
    val androidx_fragment = "androidx.fragment:fragment-ktx:${Versions.androidx_fragment}"
    val androidx_fragment_testing =
        "androidx.fragment:fragment-testing:${Versions.androidx_fragment}"
    val androidx_appcompat = "androidx.appcompat:appcompat:${Versions.androidx_appcompat}"
    val androidx_arch_core_testing =
        "androidx.arch.core:core-testing:${Versions.androidx_arch_core_testing}"
    val androidx_core = "androidx.core:core-ktx:${Versions.androidx_core}"
    val androidx_constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.androidx_constraint_layout}"
    val androidx_junit = "androidx.test.ext:junit:${Versions.androidx_junit}"
    val androidx_legacy_support =
        "androidx.legacy:legacy-support-v4:${Versions.androidx_legacy_support}"
    val androidx_lifecycle_extensions =
        "androidx.lifecycle:lifecycle-extensions:${Versions.androidx_lifecycle}"
    val androidx_lifecycle_viewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidx_lifecycle}"
    val androidx_lifecycle_common_java8 =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.androidx_lifecycle}"
    val androidx_lifecycle_livedata =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidx_lifecycle}"
    val androidx_lifecycle_savedstate =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.androidx_lifecycle}"
    val androidx_navigation_dynamic_feature_fragment =
        "androidx.navigation:navigation-dynamic-features-fragment:${Versions.androidx_navigation}"
    val androidx_navigation_fragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.androidx_navigation}"
    val androidx_navigation_safeargs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.androidx_navigation}"
    val androidx_navigation_ui =
        "androidx.navigation:navigation-ui-ktx:${Versions.androidx_navigation}"
    val androidx_paging = "androidx.paging:paging-runtime-ktx:${Versions.androidx_paging}"
    val androidx_paging_common = "androidx.paging:paging-common-ktx:${Versions.androidx_paging}"
    val androidx_preference = "androidx.preference:preference-ktx:${Versions.androidx_preference}"
    val androidx_recyclerview =
        "androidx.recyclerview:recyclerview:${Versions.androidx_recyclerview}"
    val androidx_test_core = "androidx.test:core-ktx:${Versions.androidx_test_core}"
    val androidx_test_junit = "androidx.test.ext:junit-ktx:${Versions.androidx_test_junit}"
    val androidXTest_core = "androidx.test:core:${Versions.androidXTest}"
    val androidXTest_runner = "androidx.test:runner:${Versions.androidXTest}"
    val androidXTest_rules = "androidx.test:rules:${Versions.androidXTest}"
    val androidXTest_truth = "androidx.test.ext:truth:${Versions.androidXTest}"
    val circular_image_view = "de.hdodenhof:circleimageview:${Versions.circular_image_view}"
    val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val coroutine_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    val desugar_libs = "com.android.tools:desugar_jdk_libs:${Versions.desugar_libs}"
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    val google_truth_assertion = "com.google.truth:truth:${Versions.google_truth_assertion}"
    val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    val hamcrest_testing = "org.hamcrest:hamcrest-all:${Versions.hamcrest_testing}"
    val hilt_android = "com.google.dagger:hilt-android:${Versions.dagger_hilt}"
    val hilt_compiler = "androidx.hilt:hilt-compiler:${Versions.hilt}"
    val hilt_compiler_android = "com.google.dagger:hilt-android-compiler:${Versions.dagger_hilt}"
    val hilt_compiler_android_test = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    val hilt_gradle = "com.google.dagger:hilt-android-gradle-plugin:${Versions.dagger_hilt}"
    val hilt_testing = "com.google.dagger:hilt-android-testing:${Versions.dagger_hilt}"
    val hilt_viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt}"
    val junit = "junit:junit:${Versions.junit}"
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    val kotlin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_gradle}"
    val material_design_component =
        "com.google.android.material:material:${Versions.material_design_component}"
    val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    val mockito_dexmaker = "com.linkedin.dexmaker:dexmaker-mockito:${Versions.dexMaker}"
    val moshi_converter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi_kotlin}"
    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val okhttp_mockwebserver = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofit_coroutines =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofit_coroutines}"
    val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    val room_ktx = "androidx.room:room-ktx:${Versions.room}"
    val room_testing = "androidx.room:room-testing:${Versions.room}"
    val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}
