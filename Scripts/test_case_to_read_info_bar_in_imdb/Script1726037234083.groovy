import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import internal.GlobalVariable as GlobalVariable

// Get the current WebDriver
WebDriver driver = DriverFactory.getWebDriver()

// Wait for the elements to be visible (up to 10 seconds)
WebDriverWait wait = new WebDriverWait(driver, 10)
wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath('//button[@class="ipc-icon-button cli-info-icon ipc-icon-button--base ipc-icon-button--onAccent2"]')))

// Find elements by XPath
List<WebElement> elements = driver.findElements(By.xpath('//button[@class="ipc-icon-button cli-info-icon ipc-icon-button--base ipc-icon-button--onAccent2"]'))

// Get the count of elements
int count = elements.size()
println("Number of elements found: " + count)

// Assign the count to the global variable
GlobalVariable.Moviecount = count

// Print the global variable value
println(GlobalVariable.Moviecount)


//project
//BY ADITYA_AGARWAL
//project_data_scrapping_from_imdb_top_250_movie
