package com.bianca.cucumber;
import io.cucumber.java.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.en.*;

import java.util.*;

public class LoginSteps {
    WebDriver driver=null;
    @Given  ("browser is open")
    public void browser_is_open() throws Throwable{
        System.out.println("Hello\n");
        System.setProperty("webdriver.chrome.driver","E:\\1.UTCN\\Anul_3\\Sem_I\\IS\\FlowerShop\\selenium\\chromedriver.exe");

        driver =new ChromeDriver();

        driver.get("http://localhost:8082");


    }
    @And("user is on login page")
    public void user_is_on_login_page () throws Throwable{
        driver.navigate().to("http://localhost:8082/mylogin");
    }
    @When("user enters username and password")
    public void user_enters_username_and_password()  throws  InterruptedException{
        driver.findElement(By.name("username")).sendKeys("ana");
        driver.findElement(By.name("password")).sendKeys("caine");
        Thread.sleep(2000);
    }

    @And("user clicks on login")
    public void user_clicks_on_login() throws  InterruptedException{
        driver.findElement(By.name("Login")).click();
        Thread.sleep(1000);
    }

    @Then ("user stays on the same page")
    public void user_stays_on_the_same_page () throws InterruptedException{
        driver.findElement(By.name("Login")).isDisplayed();
        Thread.sleep(3000);
    }
    @After
    public void end1(){
        // driver.close();
    }

}