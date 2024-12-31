Feature: User Registration
  In order to access the platform
  As a user
  I want to be able to register with valid data and get proper validation for each field

  @test1
  Scenario: Verify user registration process and validations
    Given The user is on the registration page
    When The user enters valid data (name, email, password)
    Then The user registers successfully

  @test2
  Scenario: Validate registration form
    Given The user is on the registration page
    When The user enters invalid data or leaves mandatory fields empty
    Then The user sees validation messages for the name, email, and password fields
    And The user sees a password mismatch message