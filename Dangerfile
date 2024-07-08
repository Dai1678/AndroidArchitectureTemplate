github.dismiss_out_of_range_messages

# Unit Test
unit_test_dir_pattern = "**/build/test-results/*/*.xml"
Dir.glob(unit_test_dir_pattern) do |file|
  junit.parse file
  junit.show_skipped_tests = true
  junit.report
end

# Instrumented Test
instrumented_test_dir_pattern = "**/build/outputs/androidTest-results/connected/*.xml"
Dir.glob(instrumented_test_dir_pattern) do |file|
  junit.parse file
  junit.show_skipped_tests = true
  junit.report
end

# jacoco
jacoco.minimum_project_coverage_percentage = 80
jacoco.report("**/build/reports/jacoco/*/*.xml", fail_no_coverage_data_found: false)
