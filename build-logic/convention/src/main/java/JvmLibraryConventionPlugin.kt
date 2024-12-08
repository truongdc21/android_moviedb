import com.truongdc.movie.convention.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.jvm")
                apply("movie_tmdb.android.lint")
            }
            configureKotlinJvm()
            dependencies {
                add("testImplementation", kotlin("test"))
            }
        }
    }
}
