Feature: Login Data Driven Testing

  @regression
  Scenario Outline: Verify login using Excel test data
    Given the user navigates to the Login page
    Then the user should be redirected to the My Account page by passing email and password with excel row "<row_index>"

    Examples:
      | row_index |
      | 1 |
      | 2 |
      | 3 |
      | 4 |
      | 5 |
      | 6 |