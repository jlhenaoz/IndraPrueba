package telefonicaInventario.com.pageobjects;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import telefonicaInventario.com.utilidades.PruebaIndraInventariosFunctions;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.serenitybdd.core.pages.PageObject;


public class MenuPage extends PageObject{
	
	protected static final Logger logger = LoggerFactory.getLogger(MenuPage.class);
	PruebaIndraInventariosFunctions funcionesGenerales = new PruebaIndraInventariosFunctions();
	PruebaIndraInventariosFunctions telfonicaInventariosFunctions;
		 				
	@FindBy (xpath="//h3[contains(.,'Login Successfully')]")
	public WebElementFacade lblBienvenida;
	
	
	
	// Verifica el ingreso a la aplicación
	
	public void validoIngresoExitoso(){
		
		logger.info("Inicio el paso: Valido la autenticación en el sistema");
		
		if(lblBienvenida.isPresent())
		{
			logger.info("Ingreso correcto");
		}else
			{
			assertTrue("No se realizo login, revise las credenciales de acceso", false);
		}
		logger.info("Termino el paso exitosamente: Valido la autenticación en el sistema");
	}
}