
Feature: Verify SwagLabs

  @tag1
  Scenario: Able to login and add product to cart
    Given open chrome browser
    And open swaglabs site "https://www.saucedemo.com/"
    When Enter "standard_user" and "secret_sauce" credentials
    And click on signin
    Then it will redirect to homepage
    And verify the homepage has 6 products
    When add the Product with the highest price to the Cart
    Then verify that Product is successfully added to the Cart
    Then close site
