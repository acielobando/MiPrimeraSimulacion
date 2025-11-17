package Test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class MiPrimeraSimulacion {

	private WebDriver driver;
	private final String BASE_URL = "https://the-internet.herokuapp.com/";

	@Before
	public void Setup() {

		System.setProperty("webdriver.chrome.driver", "./src/test/resources/Driver/chromedriver.exe");

		ChromeOptions co = new ChromeOptions();
		co.addArguments("--remote-allow-origins=*");

		driver = new ChromeDriver(co);
		driver.manage().window().maximize();
		driver.get(BASE_URL);
	}

	@Test
	public void TC1_InicioDeSesionValido() {
		System.out.println("Ejecutando TC1: Login Válido");
		driver.findElement(By.linkText("Form Authentication")).click();
		driver.findElement(By.id("username")).sendKeys("tomsmith");
		driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
		driver.findElement(By.cssSelector(".fa.fa-2x.fa-sign-in")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));

		String expectedText = "You logged into a secure area!";

		assertTrue("TC1 Falló: El inicio de sesión válido no fue exitoso.",
				successMessage.getText().contains(expectedText));
	}

	

	@Test
	public void TC2_InicioDeSesionInvalido() {
		System.out.println("Ejecutando TC2: Login Inválido");
		driver.findElement(By.linkText("Form Authentication")).click();
		driver.findElement(By.id("username")).sendKeys("user_incorrecto");
		driver.findElement(By.id("password")).sendKeys("pass_invalida");
		driver.findElement(By.cssSelector(".fa.fa-2x.fa-sign-in")).click();


				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				
		        
				WebElement errorMessage = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
		        );
		    
				
				String expectedText = "Your username is invalid!"; 
				
				assertTrue("TC2 Falló: El mensaje de error no coincide con el esperado.", 
				           errorMessage.getText().contains(expectedText));
			}
	@Test
	public void TC3_SeleccionarOpcionDropdown() {
		System.out.println("Ejecutando TC3: Dropdown");
		driver.findElement(By.linkText("Dropdown")).click();

		WebElement dropdownElement = driver.findElement(By.id("dropdown"));
		Select dropdown = new Select(dropdownElement);

		dropdown.selectByValue("2");

		assertEquals("TC3 Falló: La opción seleccionada no fue 'Option 2'.", "Option 2",
				dropdown.getFirstSelectedOption().getText());
	}

	@Test
	public void TC4_SeleccionarCheckbox() {
		System.out.println("Ejecutando TC4: Checkbox");
		driver.findElement(By.linkText("Checkboxes")).click();

		WebElement checkbox1 = driver.findElement(By.xpath("//form[@id='checkboxes']/input[1]"));

		if (!checkbox1.isSelected()) {
			checkbox1.click();
		}

		assertTrue("TC4 Falló: El Checkbox 1 no fue seleccionado correctamente.", checkbox1.isSelected());
	}

	@Test
	public void TC5_VerificarTypos() {
		System.out.println("Ejecutando TC5: Typos");
		driver.findElement(By.linkText("Typos")).click();

		WebElement paragraphElement = driver.findElement(By.xpath("//div[@class='example']/p[2]"));
		String actualText = paragraphElement.getText();

		String expectedPhrase = "Sometimes you'll see a typo, and sometimes you won't.";

		assertTrue("TC5 Falló: El texto del párrafo no es el esperado (el typo podría haber cambiado). Texto real: "
				+ actualText, actualText.contains(expectedPhrase));
	}

	@Test
	public void TC6_AceptarAlertaJavaScript() {
		System.out.println("Ejecutando TC6: Alertas JS");
		driver.findElement(By.linkText("JavaScript Alerts")).click();

		driver.findElement(By.cssSelector("button[onclick='jsAlert()']")).click();
		driver.switchTo().alert().accept();

		WebElement resultText = driver.findElement(By.id("result"));
		String expectedText = "You successfully clicked an alert";

		assertEquals("TC6 Falló: El mensaje de resultado de la alerta no es correcto.", expectedText,
				resultText.getText());
	}

	// ---------------------------------------------------------------------

	@After
	public void after() {
		if (driver != null) {

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			driver.quit();
		}
	}
}