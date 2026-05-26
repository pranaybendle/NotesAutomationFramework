Feature: Create Note functionality

  Scenario: User creates a new note successfully
    Given user is on Notes application
    When user logs in with valid credentials
    And user creates a note
    Then note should be visible