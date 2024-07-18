# Feature: User sign-up and authentication

As a new user,
I want to be able to sign up

## Scenario: Successful sign-up  [✅]

    Given the user payload with firstname "new", lastname "user", username "new_user", email "newuser@gmail.com" and password "new_user@password"
    When I send a POST request to "api/signup"
    Then the response status should be 201
    And the response should contain "Sign-up successful"

## Scenario: Sign up with existing username [✅]

    Given the user payload with firstname "new", lastname "user", username "new_user", email "newuser@gmail.com" and password "newuser@password"
    When I send a POST request to "api/signup"
    Then the response status should be 409
    And the response should contain "Username already exists."

## Scenario: Sign up with existing email [✅]

    Given the user payload with firstname "new", lastname "user", username "new_user", email "newuser@gmail.com" and password "newuser@password"
    When I send a POST request to "api/signup"
    Then the response status should be 409
    And the response should contain "Email already in use."

## Scenario: Sign up without firstname [✅]

    Given the user payload with firstname "", lastname "user", username "new_user", email "newuser@gmail.com" and password "newuser@password"
    When I send a POST request to "api/signup"
    Then the response status should be 400
    And the response should contain "Firstname is required."

## Scenario: Sign up without lastname [✅]

    Given the user payload with firstname "new", lastname "", username "new_user", email "newuser@gmail.com" and password "newuser@password"
    When I send a POST request to "api/signup"
    Then the response status should be 400
    And the response should contain "Lastname is required."

## Scenario: Sign up without username [✅]

    Given the user payload with firstname "new", lastname "user", username "", email "newuser@gmail.com" and password "newuser@password"
    When I send a POST request to "api/signup"
    Then the response status should be 400
    And the response should contain "Username is required."

## Scenario: Sign up without email [ ]

    Given the user payload with firstname "new", lastname "user", username "new_user", email "" and password "newuser@password"
    When I send a POST request to "api/signup"
    Then the response status should be 400
    And the response should contain "Email is required."

## Scenario: Sign up without password [ ]

    Given the user payload with firstname "new", lastname "user", username "@invalid_username", email "newuser@gmail.com" and password ""
    When I send a POST request to "api/signup"
    Then the response status should be 400
    And the response should contain "Password is required."

## Scenario: Sign up with invalid username [ ]

    Given the user payload with firstname "new", lastname "user", username "@invalid_username", email "newuser@gmail.com" and password "newuser@password"
    When I send a POST request to "api/signup"
    Then the response status should be 400
    And the response should contain "Invalid username! The username should not start with special character."

## Scenario: Sign up with weak password [ ]

    Given the user payload with firstname "new", lastname "user", username "new_user", email "newuser@gmail.com" and password "123"
    When I send a POST request to "api/signup"
    Then the response status should be 400
    And the response should contain "Password too weak! Must contain at least 8 characters, one uppercase letter, a special character and a number."

## Scenario: Sign up with invalid email [ ]

    Given the user payload with firstname "new", lastname "user", username "new_user", email "invalid-email" and password "newuser@password"
    When I send a POST request to "api/signup"
    Then the response status should be 400
    And the response should contain "Invalid email!"

## Scenario: Unexpected error during sign-up [ ]

    Given the user payload with firstname "new", lastname "user", username "new_user", email "newuser@gmail.com" and password "new_user@password"
    When I send a POST request to "api/signup"
    And an unexpected error occurs
    Then the response status should be 500
    And the response should contain "An unexpected error occurred. Please try again later."