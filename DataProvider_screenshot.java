package CodingCrasher;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class DataProvider_screenshot {
    private WebDriver driver;
    private Actions actions;
    private Select select;

    @BeforeMethod
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        actions = new Actions(driver);


    }
    @Test(dataProvider = "patient")
    public void Test(String user, String pass, String firstName, String lastName, String gender, int birthDate, String Month, int year, String address, String city, String state, String Country, long postalCode, long number, String relationship, String supervisorName){
        driver.navigate().to("https://demo.openmrs.org/");
        takeScreenshot("loginPage");

        WebElement userName = driver.findElement(By.name("username"));
        userName.sendKeys(user);

        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys(pass);

        WebElement option = driver.findElement(By.id("Inpatient Ward"));
        option.click();

        WebElement login = driver.findElement(By.id("loginButton"));
        login.click();

        WebElement registration = driver.findElement(By.id("referenceapplication-registrationapp-registerPatient-homepageLink-referenceapplication-registrationapp-registerPatient-homepageLink-extension"));
        registration.click();

        WebElement givenName = driver.findElement(By.name("givenName"));
        givenName.sendKeys(firstName);

        WebElement familyName = driver.findElement(By.name("familyName"));
        familyName.sendKeys(lastName);

        WebElement nextButton = driver.findElement(By.id("next-button"));
        nextButton.click();
        takeScreenshot("patientRegistration");

        WebElement genderDD = driver.findElement(By.id("gender-field"));
        select = new Select(genderDD);
        select.selectByVisibleText(gender);
        nextButton.click();
        takeScreenshot("GenderDropDown");

        WebElement birthDay = driver.findElement(By.id("birthdateDay-field"));
        birthDay.sendKeys(String.valueOf(birthDate));

        WebElement birthMonth = driver.findElement(By.id("birthdateMonth-field"));
        select = new Select(birthMonth);
        select.selectByVisibleText(Month);

        WebElement birthYear = driver.findElement(By.id("birthdateYear-field"));
        birthYear.sendKeys(String.valueOf(year));
        nextButton.click();
        takeScreenshot("birthDate");

        WebElement addressInfo = driver.findElement(By.id("address1"));
        addressInfo.sendKeys(address);

        WebElement cityVillage = driver.findElement(By.id("cityVillage"));
        cityVillage.sendKeys(city);

        WebElement stateProvince = driver.findElement(By.id("stateProvince"));
        stateProvince.sendKeys(state);

        WebElement country = driver.findElement(By.id("country"));
        country.sendKeys(Country);

        WebElement postalCodeInfo = driver.findElement(By.id("postalCode"));
        postalCodeInfo.sendKeys(String.valueOf(postalCode));
        nextButton.click();
        takeScreenshot("address");

        WebElement phoneNumber = driver.findElement(By.name("phoneNumber"));
        phoneNumber.sendKeys(String.valueOf(number));
        nextButton.click();

        WebElement relationshipType = driver.findElement(By.id("relationship_type"));
        relationshipType.sendKeys(relationship);

        WebElement personName = driver.findElement(By.xpath("//input[@class='person-typeahead ng-pristine ng-untouched ng-valid ng-empty']"));
        personName.sendKeys(supervisorName);
        nextButton.click();
        takeScreenshot("reviewPersonalInfo");

    }

    @DataProvider(name = "patient")
    public Object[][] getPatientInfo(){
        return new Object[][]{
                {"admin", "Admin123", "Bill", "Smith", "Male", 25,"February", 1990, "2244 n Michigan ave", "Chicago", "Illinois", "USA",60645L, 2233445566l, "Supervisor", "Ann Smith"}
        };

    }


    public void takeScreenshot(String screenshotName) {

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotDirectory = "screenshot/";

        try {
            FileUtils.copyFile(screenshot,
                    new File(screenshotDirectory + screenshotName + "-" + System.currentTimeMillis() + ".png"));
        } catch (IOException ex) {
            System.out.println("Screenshot was not taken");
            ex.printStackTrace();
        }
    }



}
