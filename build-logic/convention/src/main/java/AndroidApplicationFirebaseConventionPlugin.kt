import com.android.build.api.dsl.ApplicationExtension
import com.google.firebase.appdistribution.gradle.AppDistributionExtension
import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import dev.dai.android.architecture.template.build_logic.library
import dev.dai.android.architecture.template.build_logic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.google.gms.google-services")
        apply("com.google.firebase.firebase-perf")
        apply("com.google.firebase.crashlytics")
        apply("com.google.firebase.appdistribution")
      }

      dependencies {
        add("implementation", platform(libs.library("firebase-bom")))
        "implementation"(libs.library("firebase.analytics"))
        "implementation"(libs.library("firebase.performance"))
        "implementation"(libs.library("firebase.crashlytics"))
      }

      extensions.configure<ApplicationExtension> {
        buildTypes.configureEach {
          // Disable the Crashlytics mapping file upload.
          // TODO This feature should only be enabled
          //  if a Firebase backend is available and configured in google-services.json.
          configure<CrashlyticsExtension> {
            mappingFileUploadEnabled = false
          }

          if (name == "release") {
            configure<AppDistributionExtension> {
              artifactType = "APK"
              artifactPath = "${project.layout.buildDirectory}/outputs/apk/release/app-release.apk"
            }
          }
        }
      }
    }
  }
}
