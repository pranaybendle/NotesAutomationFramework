Feature: Logout functionality

  Scenario: Successful logout
    Given user is logged in
    When user logs out
    Then user should be redirected to login page