github.dismiss_out_of_range_messages

# detekt
class ::CheckstyleError
  alias_method :original_message, :message

  def message
    "`#{source}`\n\n#{original_message}"
  end
end
checkstyle_format.base_path = Dir.pwd
checkstyle_format.report "build/reports/detekt/merge.xml"

# android lint
Dir["**/build/reports/lint-results*.xml"].each do |file|
  android_lint.filtering = true
  android_lint.report_file = file
  android_lint.lint(inline_mode: true)
end

# Unit Test
Dir["**/build/test-results/*/*.xml"].each do |file|
  junit.parse file
  junit.show_skipped_tests = true
  junit.report
end

# Instrumented Test
Dir["**/build/outputs/androidTest-results/connected/*.xml"].each do |file|
  junit.parse file
  junit.show_skipped_tests = true
  junit.report
end

# jacoco
jacoco.minimum_project_coverage_percentage = 80
jacoco.report("app/build/reports/jacoco/*/*.xml")
