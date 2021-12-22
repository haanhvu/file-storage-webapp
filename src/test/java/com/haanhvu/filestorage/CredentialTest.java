package com.haanhvu.filestorage;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialTest {
	
	@LocalServerPort
	private int port;
	
	private WebDriver driver;
	private WebDriverWait webDriverWait;
	private JavascriptExecutor js;
	
	@BeforeAll
    public void beforeAll() throws InterruptedException{
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    	webDriverWait = new WebDriverWait(driver, 5);
		js = (JavascriptExecutor) driver;
        signup();
    }
    
    @BeforeEach
	public void beforeEach() throws InterruptedException{
    	login();
	}
    
    @AfterEach
    public void afterAll() {
        driver.quit();
    }
    
    @Test
	@Order(1)
	public void addCredential() {
    	WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialTab));
		credentialTab.click();
		
		WebElement credentialCreateBtn = driver.findElement(By.id("credential-create"));	
		webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialCreateBtn));
		js.executeScript("arguments[0].click();", credentialCreateBtn);
		
		WebElement urlInput = driver.findElement(By.id("credential-url"));		
		webDriverWait.until(ExpectedConditions.visibilityOf(urlInput));
		js.executeScript("arguments[0].value='" + "www.google.com" + "';", urlInput);
		
		WebElement usernameInput = driver.findElement(By.id("credential-username"));		
		webDriverWait.until(ExpectedConditions.visibilityOf(usernameInput));
		js.executeScript("arguments[0].value='" + "testUsername" + "';", usernameInput);
		
		WebElement passwordInput = driver.findElement(By.id("credential-password"));
		webDriverWait.until(ExpectedConditions.visibilityOf(passwordInput));
		js.executeScript("arguments[0].value='" + "testKey" + "';", passwordInput);

		WebElement credentialForm = driver.findElement(By.id("credential-update-form"));
		credentialForm.submit();
		Assertions.assertDoesNotThrow(() -> {
			driver.findElement(By.xpath("//th[text()='www.google.com']"));
			driver.findElement(By.xpath("//td[text()='testUsername']"));
			driver.findElement(By.xpath("//td[text()='testKey']"));
		});
    }
    
    @Test
	@Order(2)
	public void updateCredential(){
		WebElement editButton = driver.findElement(
				By.xpath("//*[@id='credentialTable']/tbody/tr/td[1]/button"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(editButton));
		js.executeScript("arguments[0].click();", editButton);
		
		WebElement credentialUrl = driver.findElement(By.id("credential-url-update"));
		webDriverWait.until(ExpectedConditions.visibilityOf(credentialUrl));
		js.executeScript("arguments[0].value='" + "www.newurl.com" + "';", credentialUrl);

		WebElement credentialPassword = driver.findElement(By.id("credential-password-update"));
		webDriverWait.until(ExpectedConditions.visibilityOf(credentialPassword));
		js.executeScript("arguments[0].value='" + "newPassword" + "';", credentialPassword);		 

		WebElement credentialForm = driver.findElement(By.id("credential-update-form"));
		credentialForm.submit();
		Assertions.assertDoesNotThrow(() -> {
			driver.findElement(By.xpath("//th[text()='www.newurl.com']"));
			driver.findElement(By.xpath("//td[text()='newPassword']"));
		});
	}
    
    @Test
	@Order(3)
	public void deleteCredential(){
		WebElement deleteButton = this.driver.findElement(
				By.xpath("//*[@id='credentialTable']/tbody/tr/td[1]/a"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteButton));
		js.executeScript("arguments[0].click();", deleteButton);
		
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			driver.findElement(By.xpath("//th[text()='www.newurl.com']"));
		});
	}
    
    private void signup() throws InterruptedException{
		driver.get("http://localhost:" + port + "/signup");
		
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		js.executeScript("arguments[0].value='" + "testFirstname" + "';", inputFirstName);
		
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		js.executeScript("arguments[0].value='" + "testLastname" + "';", inputLastName);
		
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		js.executeScript("arguments[0].value='" + "testUser1234" + "';", inputUsername);
		
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		js.executeScript("arguments[0].value='" + "testPassword1234" + "';", inputPassword);
		
		WebElement signupBtn = driver.findElement(By.id("signupBtn"));
		js.executeScript("arguments[0].click();", signupBtn);
	}
    
	private void login() throws InterruptedException{
		driver.get("http://localhost:" + port + "/login");
		
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		js.executeScript("arguments[0].value='" + "testUser1234" + "';", inputUsername);
		
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		js.executeScript("arguments[0].value='" + "testPassword1234" + "';", inputPassword);
		
		WebElement loginButton = driver.findElement(By.id("login-btn"));
		js.executeScript("arguments[0].click();", loginButton);
	}

}
