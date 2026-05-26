Feature: Edit Note functionality

  Scenario: User edits a note successfully
    Given user is on Notes application
    When user logs in with valid credentials
    And user deletes all notes
    And user creates a note
    And user edits a note
    Then updated note should be visible