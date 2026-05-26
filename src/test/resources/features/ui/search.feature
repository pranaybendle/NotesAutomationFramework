Feature: Search Note functionality

  Scenario: User searches a note successfully
    Given user is on Notes application
    When user logs in with valid credentials
    And user creates a note
    And user searches for a note
    Then searched note should be visible