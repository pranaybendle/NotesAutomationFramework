Feature: Invalid Login functionality

  Scenario: Invalid login should show error message
    Given user is on Notes application
    When user logs in with invalid credentials
    Then error message should be visible