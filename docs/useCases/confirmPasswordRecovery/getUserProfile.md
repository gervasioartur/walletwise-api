# Feature: User password recovery

As a user,
I want to be able to access my profile

## Scenario: Successful access profile  []

    Given the user payload accessToken "any_access_token"
    When I send a POST request to "api/auth/profile"
    Then the response status should be 200
    And the response should contain Profile info

## Scenario: Access profile without accessToken or invalid accessToken []

    Given the user payload with accessToken "" or "invali_access_token" 
    When I send a POST request to "api/confirm-password-recovery"
    Then the response status should be 403
    And the response should contain "Forbidden."

## Scenario: Unexpected error while trying to access profile []

    Given the user payload accessToken "any_access_token"
    When I send a POST request to "api/confirm-password-recovery"
    And an unexpected error occurs
    Then the response status should be 500
    And the response should contain "An unexpected error occurred. Please try again later."