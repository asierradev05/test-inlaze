Feature: User registration
  As a user, I want to register in the platform to access my functionalities.

  Scenario: Successful user registration
    Given The user is on the registration page
    When The user completes the fields "John Smith", "john.smith@example.com", "Passw0rd!"
    And Clicks on the register button
    Then You should see the message "Welcome, John Smith"
