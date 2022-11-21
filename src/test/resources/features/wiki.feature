Feature: Wikipedia example tests

  Scenario: Search for Hungary
    Given the home page is opened
    When the search field is filled with 'Hungary'
    And I click the search button
    Then I see 'Hungary' in the heading
