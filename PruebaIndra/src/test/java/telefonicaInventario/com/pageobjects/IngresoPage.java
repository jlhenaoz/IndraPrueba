package telefonicaInventario.com.pageobjects;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import telefonicaInventario.com.utilidades.PruebaIndraInventariosFunctions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertTrue;
import net.serenitybdd.core.pages.PageObject;

public class IngresoPage extends PageObject{	
	protected static final Logger logger = LoggerFactory.getLogger(IngresoPage.class);
	PruebaIndraInventariosFunctions funcionesGenerales = new PruebaIndraInventariosFunctions();
	PruebaIndraInventariosFunctions telfonicaInventariosFunctions;
			 				
	@FindBy(name="userName")
	public WebElementFacade txtUsuario;
	
	@FindBy(name="password")
	public WebElementFacade txtPasswordo;
	
	@FindBy(name="submit")
	public WebElementFacade bntIngresar;
			
	
	public void ingresarSitio(String usuario, String password) {		
		logger.info("Inicio el ingreso básico de datos de ingreso a la aplicacion");
		logger.info(getTitle());
		if(txtUsuario.isPresent()) {
			/*
			Select listaTipoDocumento = new Select(cmbTipoDocumento);
			listaTipoDocumento.selectByVisibleText(tipoDocumento);
			logger.info(String.format("Seleccione en la lista el tipo de documento: '%s'",tipoDocumento));
			*/
			txtUsuario.sendKeys(usuario);
			logger.info(String.format("Escribí en el campo usuario el nombre de usuario: '%s'",usuario));
			txtPasswordo.sendKeys(password);		
			logger.info(String.format("Escribí en el campo Contraseña la clave: '%s'",password));
			Serenity.takeScreenshot();
			bntIngresar.click();
			logger.info("Pulse el botón Ingresar");		
			logger.info("Finalizo el ingreso básico de datos de registro");			
		}else {
			assertTrue("No se encuentra el sitio para realizar login", false);
		}
		logger.info("Termina el ingreso básico de datos de ingreso a la aplicacion");
	}
		
}