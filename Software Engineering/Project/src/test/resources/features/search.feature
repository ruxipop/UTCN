Feature: Test search functionality
  Scenario: Check search is successful
    Given browser is open1
    And user is on catalog page
    When I search for "Aranjament"
    And add first product in the basket
    And user is on cart page
    And add card number "1121 4563 2011 0470"
    And add name on card "Ruxandra"
    And add expiration date "03/29"
    And add CVV as "233"
    And place the order
    Then verify the order