Feature: Category Filter

  Scenario: Filter notes by Work category
    Given user is on Notes application
    When user logs in with valid credentials
    And user creates a note
    When user clicks category "Home"
    Then note "Capgemini Note" should be visible