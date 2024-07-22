# Feature: User sign in

As a user,
I want to be able to sign in

## Scenario: Successful sign in with username  []

    Given the user payload with username "any_user" and password "any_password"
    When I send a POST request to "api/signin"
    Then the response status should be 200
    And the response should contain access token

## Scenario: Successful sign in with email []

    Given the user payload with email "anyuser@email.com" and password "any_password"
    When I send a POST request to "api/signin"
    Then the response status should be 200
    And the response should contain access token

## Scenario: Sign in with non-existing username []

    Given the user payload with username "any_user" and password "any_password"
    When I send a POST request to "api/signin"
    Then the response status should be 401
    And the response should contain "Invalid username or password."

## Scenario: Sign in with non-existing email []

    Given the user payload with email "anyuser@email.com" and password "any_password"
    When I send a POST request to "api/signin"
    Then the response status should be 401
    And the response should contain "Invalid email or password."

## Scenario: Sign in without username when sign in with username []

    Given the user payload with username "" and password "any_password"
    When I send a POST request to "api/signin"
    Then the response status should be 400
    And the response should contain "Username is required."

## Scenario: Sign in without email when sign in with email []

    Given the user payload with email "" and password "any_password"
    When I send a POST request to "api/signin"
    Then the response status should be 400
    And the response should contain "Email is required."

## Scenario: Sign in without password []

    Given the user payload with email "" and password "any_password"
    When I send a POST request to "api/signin"
    Then the response status should be 400
    And the response should contain "Password is required."

## Scenario: Unexpected error during sign in with username []

    Given the user payload with username "any_username" and password "any_password"
    When I send a POST request to "api/signin"
    And an unexpected error occurs
    Then the response status should be 500
    And the response should contain "An unexpected error occurred. Please try again later."

## Scenario: Unexpected error during sign in with email []

    Given the user payload with email "any_email" and password "any_password"
    When I send a POST request to "api/signin"
    And an unexpected error occurs
    Then the response status should be 500
    And the response should contain "An unexpected error occurred. Please try again later."