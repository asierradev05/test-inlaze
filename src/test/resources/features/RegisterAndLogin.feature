@Register_and_login-001
Feature: Registration and Login Flow with Generated Data
  As a user
  I want to register and then login with my created credentials
  So that I can verify the complete authentication flow

  Scenario: Complete Registration and Login Flow with Generated Data
    Given I am on the registration page for new account
    When I complete the registration form with generated data
    Then I should see a successful registration message

    When I navigate to the login page
    And I login with my registered credentials
    Then I should see my dashboard with correct user information
    And I should be able to logout successfully