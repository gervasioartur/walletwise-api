# Feature: Add fixed expense

As a logged user,
I want to be able to add fixed expense

## Scenario: Unexpected error during add fixed expense [✅]

    Given fixed expense payload with userId "any_user_id", description "any_description",amount 12, 
    category "FOOD", dueDay 21,startDate 2020-05-12, endDate 2025-10-23 and paymentFrequency "any_invalid_payment_frequency"
    When I send a POST request to "api/fixed-expense"
    And an unexpected error occurs
    Then the response status should be 500
    And the response should contain "An unexpected error occurred. Please try again later."

## Scenario: Add fixed expense with no active session [✅]

    Given fixed expense payload with userId "any_user_id", description "any_description",amount 12,
    category "TRANSPORT", dueDay 21,startDate 2020-05-12, endDate 2025-10-23 and paymentFrequency "DAILY"
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 403
    And the response should contain "Forbidden."

## Scenario: Add fixed expense with no description [✅]

    Given fixed expense payload with userId "any_user_id", description "",amount 12, 
    category "TRANSPORT", dueDay 21,startDate 2020-05-12, endDate 2025-10-23 and paymentFrequency "DAILY"
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 400
    And the response should contain "Description is required."

## Scenario: Add fixed expense with no amount [✅]

    Given fixed expense payload with userId "any_user_id", description "any_description",amount , 
    category "TRANSPORT", dueDay 21,startDate 2020-05-12, endDate 2025-10-23 and paymentFrequency "DAILY"
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 400
    And the response should contain "Amount is required."

## Scenario: Add fixed expense with amount less or equal zero(0)  [✅]

    Given fixed expense payload with userId "any_user_id", description "any_description",amount 0, 
    category "TRANSPORT", dueDay 21,startDate 2020-05-12, endDate 2025-10-23 and paymentFrequency "DAILY"
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 400
    And the response should contain "Amount is should be greater than zero."

## Scenario: Add fixed expense with no category [✅]

    Given fixed expense payload with userId "any_user_id", description "any_description",amount 12, 
    category "", dueDay 21,startDate 2020-05-12, endDate 2025-10-23 and paymentFrequency "DAILY"
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 400
    And the response should contain "Category is required."

## Scenario: Add fixed expense with invalid category [✅]

    Given fixed expense payload with userId "any_user_id", description "any_description",amount 12, 
    category "any_invalid_category", dueDay 21,startDate 2020-05-12, endDate 2025-10-23 and paymentFrequency "DAILY"
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 400
    And the response should contain "Invalid category! You must choose a category between FOOD,
    TRANSPORT,RENT,ENTERTAINMENT,SCHOOL or OTHERS."

## Scenario: Add fixed expense with no due day [✅]

    Given fixed expense payload with userId "any_user_id", description "any_description",amount 12, 
    category "FOOD", dueDay 21,startDate 2020-05-12, endDate 2025-10-23 and paymentFrequency "DAILY"
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 400
    And the response should contain "Due day is required."

## Scenario: Add fixed expense with invalid due day [✅]

    Given fixed expense payload with userId "any_user_id", description "any_description",amount 12, 
    category "FOOD", dueDay 41,startDate 2020-05-12, endDate 2025-10-23 and paymentFrequency "DAILY"
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 400
    And the response should contain "Invalid due day! Expiration day must be between 1 to 31."

# Scenario: Add fixed expense with no start date []

    Given fixed expense payload with userId "any_user_id", description "any_description",amount 12, 
    category "FOOD", dueDay 21,startDate , endDate 2025-10-23 and paymentFrequency "DAILY"
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 400
    And the response should contain "Start date is required."

# Scenario: Add fixed expense with start date after endDate []

    Given fixed expense payload with userId "any_user_id", description "any_description",amount 12, 
    category "FOOD", dueDay 21,startDate 2025-10-24  , endDate 2025-10-23 and paymentFrequency "DAILY"
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 400
    And the response should contain "The sart date must be before the end date."

## Scenario: Add fixed expense with no payment frequency []

    Given fixed expense payload with userId "any_user_id", description "any_description",amount 12, 
    category "FOOD", dueDay 21,startDate 2020-05-12, endDate 2025-10-23 and paymentFrequency ""
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 400
    And the response should contain "Payment frequency is required."

## Scenario: Add fixed expense with invalid payment frequency []

    Given fixed expense payload with userId "any_user_id", description "any_description",amount 12, 
    category "FOOD", dueDay 21,startDate 2020-05-12, endDate 2025-10-23 and paymentFrequency "any_invalid_payment_frequency"
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 400
    And the response should contain "Invalid Payment frequency! You must choose payment frequency between 
    DAILY,WEEKLY,MONTHLY or YEARLY."

## Scenario: Successful add fixed expense  []

    Given fixed expense payload with userId "any_user_id", description "any_description",amount 12, 
    category "TRANSPORT", dueDay 21,startDate 2020-05-12, endDate 2025-10-23 and paymentFrequency "DAILY"
    When I send a POST request to "api/fixed-expense"
    Then the response status should be 201
    And the response should contain "Expense successful added."