# Feature: Generate fixed expenses report

As a logged user,
I want to be able to generate report for my fixed expense

## Scenario: Unexpected error during generate fixed expenses report[✅]

    Given fixed expense payload with userId "any_user_id"
    When I send a POST request to "api/fixed-expenses/report"
    And an unexpected error occurs
    Then the response status should be 500
    And the response should contain "An unexpected error occurred. Please try again later."

## Scenario: Generate fixed expenses report with no active session [✅]

    Given fixed expense payload with userId "any_user_id"
    When I send a POST request to "api/fixed-expenses/report"
    Then the response status should be 403
    And the response should contain "Forbidden."

## Scenario: Successful generate fixed expenses  [✅]

    Given fixed expense payload with userId "any_user_id"
    When I send a POST request to "api/fixed-expenses/report"
    Then the response status should be 200
    And the response should contain report of fixed expenses