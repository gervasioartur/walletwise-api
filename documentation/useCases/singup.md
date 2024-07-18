# Feature: User sign-up and authentication
As a new user,
I want to be able to sign up

## Scenario: Successful sign-up  [?]
    Given the user payload with firstname "new", lastname "user", username "new_user", email "newuser@gmail.com" and password "new_user@password"
    When I send a POST request to "api/signup"
    Then the response status should be 201
    And the response should contain "Sign-up successful"

## Scenario: Sign up with invalid username [ ]
    Given the user payload with firstname "new", lastname "user", username "@invalid_username", email "newuser@gmail.com" and password "newuser@password"
    When I send a POST request to "api/signup"
    Then the response status should be 400
    And the response should contain "Invalid username! The username should not start with special character."

## Scenario: Sign up with invalid email [ ]
    Given the user payload with firstname "new", lastname "user", username "new_user", email "invalid-email" and password "newuser@password"
    When I send a POST request to "api/signup"
    Then the response status should be 400
    And the response should contain "Invalid email!"

## Scenario: Sign up with weak password [ ]
    Given the user payload with firstname "new", lastname "user", username "new_user", email "newuser@gmail.com" and password "123"
    When I send a POST request to "api/signup"
    Then the response status should be 400
    And the response should contain "Password too weak! Must contain at least 8 characters, one uppercase letter, a special character and a number."

## Scenario: Sign up with existing username [?]
    Given the user payload with firstname "new", lastname "user", username "new_user", email "newuser@gmail.com" and password "newuser@password"
    When I send a POST request to "api/signup"
    Then the response status should be 409
    And the response should contain "Username already exists."

## Scenario: Sign up with existing email [?]
    Given the user payload with firstname "new", lastname "user", username "new_user", email "newuser@gmail.com" and password "newuser@password"
    When I send a POST request to "api/signup"
    Then the response status should be 409
    And the response should contain "Email already in use."
