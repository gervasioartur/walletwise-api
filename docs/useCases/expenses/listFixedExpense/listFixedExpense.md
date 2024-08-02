# Feature: Add fixed expense

As a logged user,
I want to be able to list my fixed expense

## Scenario: Unexpected error during get fixed expenses [✅]

    Given fixed expense payload with userId "any_user_id"
    When I send a POST request to "api/fixed-expense"
    And an unexpected error occurs
    Then the response status should be 500
    And the response should contain "An unexpected error occurred. Please try again later."

## Scenario: Get fixed expenses with no active session [✅]

    Given fixed expense payload with userId "any_user_id"
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 403
    And the response should contain "Forbidden."

## Scenario: Successful get fixed expenses  []

    Given fixed expense payload with userId "any_user_id"
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 200
    And the response should contain a list of fixed expenses