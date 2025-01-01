Feature: User Login
  In order to access the platform
  As a registered user
  I want to log in with valid credentials and manage my session properly

  @LoginAndLogout-001
  Scenario: Successful login and logout
    Given The user is on the login page
    When The user logs in with valid credentials
    Then The user is logged in and sees their welcome message on the dashboard
    And The user logs out


  @LoginAndLogout-Validations-002
  Scenario: Validation for incomplete login form
    Given The user is on the login page
    When The user tries to log in with incomplete credentials
    Then The login form is not submitted
