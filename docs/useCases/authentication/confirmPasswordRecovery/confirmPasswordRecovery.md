# Feature: Confirm password recovery

As a user,
I want to be able to confirm password recovery

## Scenario: Unexpected error during confirm password recovery [✅]

    Given the user payload with token "any_token" newPassword "any_new_password"
    When I send a POST request to "api/confirm-password-recovery"
    And an unexpected error occurs
    Then the response status should be 500
    And the response should contain "An unexpected error occurred. Please try again later."

## Scenario: Confirm password recovery with non-existing token [✅]

    Given the user payload with token "any_token" newPassword "any_new_password"
    When I send a POST request to "api/confirm-password-recovery"
    Then the response status should be 404
    And the response should contain "User not found."

## Scenario: Confirm password recovery with expired token [✅]

    Given the user payload with token "any_token" newPassword "any_new_password"
    When I send a POST request to "api/confirm-password-recovery"
    Then the response status should be 400
    And the response should contain "Invalid or expired token."

## Scenario: Confirm password without token [✅]

    Given the user payload with token "" newPassword "any_new_password"
    When I send a POST request to "api/confirm-password-recovery"
    Then the response status should be 400
    And the response should contain "Token is required."

## Scenario: Confirm password without newPassword [✅]

    Given the user payload with token "any_token" newPassword ""
    When I send a POST request to "api/confirm-password-recovery"
    Then the response status should be 400
    And the response should contain "New Password is required."

## Scenario: Confirm password with weak newPassword [✅]

    Given the user payload with token "any_token" newPassword "any_weak_password"
    When I send a POST request to "api/confirm-password-recovery"
    Then the response status should be 400
    And the response should contain "Password too weak! Must contain at least 8 characters, one uppercase letter, a special character and a number."

## Scenario: Successful confirm password recovery   [✅]

    Given the user payload with token "any_token" newPassword "any_new_password"
    When I send a POST request to "api/confirm-password-recovery"
    Then the response status should be 200
    And the response should contain "Password successfully reset."