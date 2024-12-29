Feature: User registration
  As a user, I want to register in the platform to access my functionalities.

  Feature: User registration

  @test1
  Scenario: Successful user registration
    Given The user is on the registration page
    When The user completes the fields "John Smith", "john.smith@example.com", "Passw0rd!"
    And Clicks on the register button
    Then You should see the message "Welcome, John Smith"

  @test2
  Scenario: Registration with invalid email
    Given The user is on the registration page
    When The user completes the fields "John Smith", "invalid-email", "Passw0rd!"
    And Clicks on the register button
    Then You should see the message "Invalid email address"
