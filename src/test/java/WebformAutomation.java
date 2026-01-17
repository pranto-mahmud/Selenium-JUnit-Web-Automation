import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebformAutomation {
    WebDriver driver;
    @BeforeAll
    public void set() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }
    @DisplayName(" WebForm Automation")
//    @Order(1)
    @Test
    public void formsubmission() throws InterruptedException {
        driver.get("https://www.digitalunite.com/practice-webform-learners");
        driver.findElement(By.id("ccc-notify-accept")).click();
        Faker f=new Faker();
        String name=f.name().firstName();
        String name2=f.name().lastName();
        driver.findElement(By.id("edit-name")).sendKeys(name+" "+name2);
        driver.findElement(By.id("edit-number")).sendKeys("01773467525");
        WebElement date = driver.findElement(By.id("edit-date"));
        date.click();
        date.sendKeys("16","01","2025");
        driver.findElement(By.id("edit-email")).sendKeys(name+"33@gmail.com");
        driver.findElement(By.id("edit-tell-us-a-bit-about-yourself-")).sendKeys("Hi, I am "+name+"!");
        Utils.doscroll(driver,800);
        WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(40));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-uploadocument-upload")));
        driver.findElement(By.id("edit-uploadocument-upload")).sendKeys(System.getProperty("user.dir")+"/src/test/resources/pranto 2025.jpg");
        driver.findElement(By.cssSelector("[type='checkbox']")).click();
        System.out.println("Please solve the CAPTCHA manually...");
        Thread.sleep(30000);
        driver.findElement(By.id("edit-submit")).click();
        driver.getCurrentUrl();
        String Succesfull=driver.findElement(By.tagName("h1")).getText();
        String Expected="Thank you for your submission!";
        Assertions.assertTrue(Succesfull.contains(Expected));

    }

    @DisplayName("Scraping Table Data")
//    @Order(2)
    @Test
    public void ScrapingTableData() throws IOException {
        FileWriter fileWriter = new FileWriter("./src/test/resources/Scrapped Table Data.txt");
        driver.get("https://dsebd.org/latest_share_price_scroll_by_value.php");
        WebElement Table = driver.findElement(By.className("floatThead-wrapper"));
        List<WebElement>  Allrows = Table.findElements(By.tagName("tr"));
        for (WebElement row : Allrows) {
            List<WebElement> Columns = row.findElements(By.tagName("td"));
            for(WebElement Cell : Columns){
                System.out.print(Cell.getText()+" | ");
                fileWriter.write(Cell.getText()+" | ");
            }
            System.out.println("\n");
            fileWriter.write(System.lineSeparator());

        }
        fileWriter.close();
        System.out.println("Scrapped data's are stored in text file.");


    }
    //    @AfterAll
    public void teardown(){

        driver.quit();
    }
}

