import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.chrome.driver","C:\\BrowserDrivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.theguardian.com/tone/news");
		Thread.sleep(1000);
		
// Step1: Accept Cookies on iFrame.
		driver.switchTo().frame("sp_message_iframe_717122");
		By acceptLocator = By.xpath("//button[contains(.,'Yes, I’m happy')]");
		driver.findElement(acceptLocator).click();
	
		// switch back to the parent site
		driver.switchTo().defaultContent();
		
// Step2: Find article to search
		By article = By.className("js-headline-text");
		List<WebElement> articles = driver.findElements(article);
		
		System.out.println(">>>>>>>TOTAL Articles:::"+articles.size());
		String articleToSearch = null;
		for(int i=0;i<articles.size();i++) {
			if(!articles.get(i).getAttribute("innerHTML").isEmpty()) {
				System.out.println("ARTICLE::"+articles.get(i).getAttribute("innerHTML"));
				articleToSearch = articles.get(i).getAttribute("innerHTML");
				break;
			}
		}
		
		System.out.println("ARTICLE to search ::"+articleToSearch); 
		
		
//Step3: Open new tab -> Open google.com -> click on AcceptAll ->  
		
		Thread.sleep(500);
		driver.switchTo().newWindow(WindowType.TAB);
		driver.get("https://google.com");
		System.out.println(">>>>>>>Opening New Tab & Google.com");
		Thread.sleep(500);
		driver.findElement(By.xpath("//button[contains(.,'Accept')]")).click();
		System.out.println(">>>>>>>Handle Google's Popup");
		Thread.sleep(500);
		By inputLocator = By.name("q");
		driver.findElement(inputLocator).sendKeys(articleToSearch);
		System.out.println(">>>>>>>Entered Article to search");
		Thread.sleep(500);
		driver.findElement(inputLocator).sendKeys(Keys.RETURN);
		
//Step4: Get the different sources from the google search results
		List<WebElement> resultsAr = driver.findElements(By.partialLinkText(articleToSearch));
		System.out.println(">>>Number of Similar Articles::"+resultsAr.size());
		if(resultsAr.size()>1) {
			System.out.println("News is Valid");
		}else {
			System.out.println("News is InValid");
		}
		
	}

}
