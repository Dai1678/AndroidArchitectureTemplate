import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KoverConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      val koverPlugin = "org.jetbrains.kotlinx.kover"
      pluginManager.apply(koverPlugin)

      rootProject.subprojects {
        if (this@subprojects.name == target.name) return@subprojects

        this@subprojects.beforeEvaluate {
          this@subprojects.pluginManager.apply(koverPlugin)
        }
        target.dependencies.add("kover", this@subprojects)
      }

      configure<KoverProjectExtension> {
        reports {
          filters {
            includes.packages("dev.dai.android.architecture.template.*")
            excludes {
              packages(
                "dagger.hilt.*",
                "hilt_aggregated_deps",
                "dev.dai.android.architecture.template.designsystem",
                "dev.dai.android.architecture.template.provider",
                "dev.dai.android.architecture.template.di",
                "dev.dai.android.architecture.template.*.di"
              )
              classes(
                // classes generated by Hilt
                "Hilt_**",
                "*_HiltModules*",
                "*Factory*",
                "*Module*",
                // classes project specific
                "*App*",
                "BuildConfig",
                "*Provider*",
                "*NavHost*",
                "ComposableSingletons",
                "*Screen*",
              )
              androidGeneratedClasses()
            }
          }
        }
      }
    }
  }
}
