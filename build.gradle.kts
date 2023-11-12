// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    alias(libs.plugins.ksp) apply false
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            val enableMetricsProvider =
                project.providers.gradleProperty("enableComposeCompilerMetrics")
            val relativePath = projectDir.relativeTo(rootDir)
            val buildDir = layout.buildDirectory.get().asFile
            val enableMetrics = (enableMetricsProvider.orNull == "true")
            if (enableMetrics) {
                val metricsFolder = buildDir.resolve("compose-metrics").resolve(relativePath)
                compilerOptions.freeCompilerArgs.addAll(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" + metricsFolder.absolutePath
                )
            }

            val enableReportsProvider =
                project.providers.gradleProperty("enableComposeCompilerReports")
            val enableReports = (enableReportsProvider.orNull == "true")
            if (enableReports) {
                val reportsFolder = buildDir.resolve("compose-reports").resolve(relativePath)
                compilerOptions.freeCompilerArgs.addAll(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" + reportsFolder.absolutePath
                )
            }
        }
    }
}
