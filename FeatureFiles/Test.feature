Feature: Test-cases for store endpoint

  Scenario: TestCase: TC001: User calls webservice to get the pet inventory.
    Given store inventory is up and running for "http://petstore.swagger.io/"
    When user sends a get request to "v2/store/inventory"
    And user performs the get request
    Then the response code must be 200
    And I should see response with pairs as below
      | HelloWorld      |   1 |
    

  Scenario: TestCase: TC002: Users posts an order for pet with valid details
    Given store inventory is up and running for "http://petstore.swagger.io/"
    When user sends a post request to "v2/store/order" with below details
      | id       |       11 |
      | petId    |       11 |
      | quantity |       11 |
      | status   | complete |
      | complete | true     |
    And perform the post request
    Then the response code must be 200
    And I should see response with pairs as below
      | id       |       11 |
      | petId    |       11 |
      | quantity |       11 |
      | status   | complete |
      | complete | true     |

  Scenario: TestCase: TC003: Users posts an order for pet with invalid details
    Given store inventory is up and running for "http://petstore.swagger.io/"
    When user sends a post request to "v2/store/order" with below details
      | id       | 11A      |
      | petId    |       11 |
      | quantity |       11 |
      | status   | complete |
      | complete | true     |
    And perform the post request
    Then the response code must be 500
    
 Scenario: Testcase: TC004 User performs a get operation for per order by order ID
 		Given store inventory is up and running for "http://petstore.swagger.io/"
    And user performs a delete request on "v2/store/order/" with order id as "9"
    And user performs the get request
    Then the response code must be 200
    And I should see response with pairs as below
      | id       |       9 |
      | petId    |       5 |
      | quantity |       5 |
      | status   | placed  |
      | complete | false   |
 
  Scenario: TestCase: TC005, TC006: User deletes the pet order by ID
    Given store inventory is up and running for "http://petstore.swagger.io/"
    And user performs a delete request on "v2/store/order/" with order id as "11"
    And user performs the delete request
    Then the response code must be 200
    And user performs the get request
    Then the response code must be 404

  Scenario: TestCase: TC007: User tries to delete the pet with invald orderID
    Given store inventory is up and running for "http://petstore.swagger.io/"
    And user performs a delete request on "v2/store/order/" with order id as "-1"
    And user performs the delete request
    Then the response code must be 404
