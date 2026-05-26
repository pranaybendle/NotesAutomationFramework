Feature: Delete Note functionality

  Scenario: User deletes a note successfully
    Given user is on Notes application
    When user logs in with valid credentials
    And user creates a note
    And user deletes a note
    Then note should not be visible