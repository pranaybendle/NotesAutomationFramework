Feature: Login functionality

  Scenario: Successful login
    Given user is on Notes application
    When user logs in with valid credentials
    Then note should be visible