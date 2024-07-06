github.dismiss_out_of_range_messages

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
Dir["**/build/reports/jacoco/*/*.xml"].each do |file|
    jacoco.report(file)
end

