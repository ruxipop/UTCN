Feature: Test login functionality
  Scenario: Check login is successful
    Given browser is open
    And user is on login page
    When user enters username and password
    And user clicks on login
    Then user stays on the same page