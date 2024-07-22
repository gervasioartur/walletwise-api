# Feature: User password recovery

As a user,
I want to be able to recover password

## Scenario: Successful password recovery   []

    Given the user payload with email "anyuser@email.com"
    When I send a POST request to "api/password-recovery"
    Then the response status should be 202
    And the response should contain "If an account with that email exists, we have sent you an email with the steps to recover your password."

## Scenario: Recover password with non-existing email []

    Given the user payload with email "anyuser@email.com"
    When I send a POST request to "api/password-recovery"
    Then the response status should be 202
    And the response should contain "If an account with that email exists, we have sent you an email with the steps to recover your password."

## Scenario: Recover password without email []

    Given the user payload with email "anyuser@email.com"
    When I send a POST request to "api/password-recovery"
    Then the response status should be 400
    And the response should contain "E-mail is required."

## Scenario: Unexpected error during password recovery []

    Given the user payload with email "anyuser@email.com"
    When I send a POST request to "api/password-recovery"
    And an unexpected error occurs
    Then the response status should be 500
    And the response should contain "An unexpected error occurred. Please try again later."