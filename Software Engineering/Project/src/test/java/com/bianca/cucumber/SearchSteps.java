package com.bianca.cucumber;
import java.util.*;
import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.en.*;


public class SearchSteps {

    WebDriver driver=null;
    @Given("browser is open1")
    public void browser_is_open1() throws Throwable{
        System.out.println("Hello\n");

        System.setProperty("webdriver.chrome.driver","E:\\1.UTCN\\Anul_3\\Sem_I\\IS\\FlowerShop\\selenium\\chromedriver.exe");
        driver =new ChromeDriver();

        driver.get("http://localhost:8082");

    }
    @And("user is on catalog page")
    public void user_is_on_catalog_page () throws Throwable{
        driver.navigate().to("http://localhost:8082/catalog?nume=");
    }
    @When("I search for {string}")
    public void search_for(String query) {
        WebElement element = driver.findElement(By.id("nume"));
        // Enter something to search for
        element.sendKeys(query);
        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();
    }

    @And("add first product in the basket")
    public void add_first_product_in_the_basket()  throws InterruptedException{
        driver.findElement(By.className("fa-shopping-cart")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();


    }
    @And("user is on cart page")

    public void user_is_on_cart_page() throws InterruptedException {
        driver.navigate().to("http://localhost:8082/cart?cont=");

        Thread.sleep(3000);
    }


    @And ("add card number {string}")
    public void add_card_nb(String query) throws InterruptedException {
        WebElement dd = driver.findElement(By.id("typeText"));
        dd.sendKeys(query);
        Thread.sleep(2000);
    }
    @And ("add name on card {string}")
    public void add_card_name(String query) throws InterruptedException {
        WebElement dd = driver.findElement(By.id("typeName"));
        dd.sendKeys(query);
        // Thread.sleep(2000);

    }
    @And ("add expiration date {string}")
    public void add_expiration_date(String query) throws InterruptedException {
        WebElement dd = driver.findElement(By.id("typeExp"));
        dd.sendKeys(query);
        // Thread.sleep(2000);

    }
    @And ("add CVV as {string}")
    public void add_cvv(String query) throws InterruptedException {

        WebElement dd = driver.findElement(By.id("typeText1"));
        dd.sendKeys(query);
        Thread.sleep(2000);

    }
    @And ("place the order")
    public void place_order() throws InterruptedException {

        driver.findElement(By.id("comanda")).click();

    }
    @Then ("verify the order")
    public void home_page() throws InterruptedException {

        driver.navigate().to("http://localhost:8082");

    }

    @After
    public void end(){
        // driver.close();
    }
}