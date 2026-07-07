Feature: Login Functionality

  As a registered user
  I want to log in to my account
  So that I can access my account

  @sanity @regression
  Scenario: Successful login with valid credentials
    Given the user navigates to the Login page
    When the user enters email as "testing01a@gmail.com" and password as "Password1!"
    And the user clicks on the Login button
    Then the user should be redirected to the My Account page

  @regression
  Scenario Outline: Login with multiple credentials
    Given the user navigates to the Login page
    When the user enters email as "<email>" and password as "<password>"
    And the user clicks on the Login button
    Then the user should be redirected to the My Account page

    Examples:
      | email                      | password |
      | testing01a@gmail.com         | Password1! |
      | testing01b@gmail.com    | Password1! |