Feature: standard Management

  Scenario: Successfully save a standard to an existing school
    Given a school exists with ID 1
    And there is no standard 1 for that school 1
    When I save a standard 1 for school with ID 1
    Then the standard should be saved successfully

  Scenario: Unsuccessfully save a standard when the school is not found
    Given a school does not exist with ID 99
    When I try to save a standard 5 for school with ID 1
    Then a ApplicationException should be thrown for school not found

  Scenario: Unsuccessfully save a standard when the standard is conflict
    Given a school exists with ID 1
    And there is standard 1 already exist in given school id 1
    When I try to save a standard 1 already exist in given school 1
    Then a ApplicationException should be thrown for standard already exist