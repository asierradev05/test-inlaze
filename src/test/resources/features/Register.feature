Feature: User Registration
  As a new user
  I want to register on the platform
  So that I can access the betting services

  @Register-001
  Scenario: Successful registration with valid data
    Given The user is on the registration page
    When The user enters valid data (name, email, password)
    Then The user registers successfully

    #for this case the correct thing to do is to make the verifications by the field to check if you like to check each one of the fields.
    #it sends you a message for each type of error.
  @Register-Validations-001
  Scenario: Registration form validations
    Given The user is on the registration page
    When The user enters invalid data:
      | field            | value                          |
      | name             | John doe                       |
      | email            | angel.david.sierra.g@gmail.com |
      | password         | Password1232                   |
      | confirm-password | Password123                    |
    Then The system shows appropriate validation messages

