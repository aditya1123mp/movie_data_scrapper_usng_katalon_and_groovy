import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream
import internal.GlobalVariable as GlobalVariable

// Initialize lists to store the extracted data
List<String> movieTitles = []
List<String> releaseYears = []
List<String> runtimes = []
List<String> ratings = []
List<String> filmGenres = []
List<String> imdbRatings = []
List<String> movieStories = []
List<String> directors = []
List<String> stars = []

// Get the current WebDriver
WebDriver driver = DriverFactory.getWebDriver()

// Loop over all the found elements
for (int i = 0; i < GlobalVariable.Moviecount; i++) {
    try {
        // Re-find elements in each iteration to avoid stale element exception
        List<WebElement> elements = driver.findElements(By.xpath('//button[@class="ipc-icon-button cli-info-icon ipc-icon-button--base ipc-icon-button--onAccent2"]'))

        // Ensure the element is within the viewport
        driver.executeScript("arguments[0].scrollIntoView(true);", elements[i])

        // Wait until the element is clickable
        WebDriverWait wait = new WebDriverWait(driver, 10)
        wait.until(ExpectedConditions.elementToBeClickable(elements[i]))

        // Use JavaScript to click the element
        driver.executeScript("arguments[0].click();", elements[i])

        // Wait for the modal to appear
        WebUI.delay(3)  // Adjust the time if needed

        // Extract movie data
        String movieTitle = driver.findElement(By.xpath("//*[@class='ipc-title__text prompt-title-text']")).getText()
        String releaseYear = driver.findElement(By.xpath("//ul[@data-testid='btp_ml']/li[1]")).getText()
        String runtime = driver.findElement(By.xpath("//ul[@data-testid='btp_ml']/li[2]")).getText()
        String rating = driver.findElement(By.xpath("//ul[@data-testid='btp_ml']/li[3]")).getText()
        String genre = driver.findElement(By.xpath("//ul[@data-testid='btp_gl']/li[1]")).getText()
        String imdbRating = driver.findElement(By.xpath("//span[@class='ipc-rating-star ipc-rating-star--baseAlt ipc-rating-star--imdb btp_rt_ds']/span[1]")).getText()
        String movieStory = driver.findElement(By.xpath("//div[@class='sc-65f72df1-2 geapts']")).getText()
        String director = driver.findElement(By.xpath("//div[@data-testid='p_ct_dr']//ul/li/a")).getText()
        List<WebElement> starElements = driver.findElements(By.xpath("//div[@data-testid='p_ct_cst']//ul/li/a"))

        // Get the names of the stars
        String starNames = starElements.collect { it.getText() }.join(', ')

        // Append the extracted data to the lists
        movieTitles.add(movieTitle)
        releaseYears.add(releaseYear)
        runtimes.add(runtime)
        ratings.add(rating)
        filmGenres.add(genre)
        imdbRatings.add(imdbRating)
        movieStories.add(movieStory)
        directors.add(director)
        stars.add(starNames)

        // Close the modal
        WebElement closeButton = driver.findElement(By.xpath("//button[@title='Close Prompt' and @aria-label='Close Prompt']"))
        driver.executeScript("arguments[0].click();", closeButton)
        WebUI.delay(2)  // Small delay before continuing to the next iteration

    } catch (Exception e) {
        println("Error during iteration ${i + 1}: ${e.message}")
        continue
    }
}

// Close the browser
driver.quit()

// Create a new Excel workbook and sheet
Workbook workbook = new XSSFWorkbook()
Sheet sheet = workbook.createSheet("IMDB Movie Data")

// Create the header row
Row headerRow = sheet.createRow(0)
headerRow.createCell(0).setCellValue("Movie Title")
headerRow.createCell(1).setCellValue("Release Year")
headerRow.createCell(2).setCellValue("Runtime")
headerRow.createCell(3).setCellValue("Rating")
headerRow.createCell(4).setCellValue("Film Genres")
headerRow.createCell(5).setCellValue("IMDB Rating")
headerRow.createCell(6).setCellValue("Movie Story")
headerRow.createCell(7).setCellValue("Director")
headerRow.createCell(8).setCellValue("Stars")

// Populate the sheet with data
for (int i = 0; i < movieTitles.size(); i++) {
    Row row = sheet.createRow(i + 1)
    row.createCell(0).setCellValue(movieTitles[i])
    row.createCell(1).setCellValue(releaseYears[i])
    row.createCell(2).setCellValue(runtimes[i])
    row.createCell(3).setCellValue(ratings[i])
    row.createCell(4).setCellValue(filmGenres[i])
    row.createCell(5).setCellValue(imdbRatings[i])
    row.createCell(6).setCellValue(movieStories[i])
    row.createCell(7).setCellValue(directors[i])
    row.createCell(8).setCellValue(stars[i])
}

// Save the workbook to a file
String outputFilePath = 'C:\\Users\\DELL\\OneDrive\\Desktop\\mail\\IMDB_movie_data_groovy.xlsx'
FileOutputStream fileOut = new FileOutputStream(outputFilePath)
workbook.write(fileOut)
fileOut.close()
workbook.close()

println("Data saved to IMDB_movie_data.xlsx")

//project
//BY ADITYA_AGARWAL
//project_data_scrapping_from_imdb_top_250_movie

