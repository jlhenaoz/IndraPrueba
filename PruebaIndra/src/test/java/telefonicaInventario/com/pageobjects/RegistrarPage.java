package telefonicaInventario.com.pageobjects;

import static org.junit.Assert.assertTrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import telefonicaInventario.com.utilidades.PruebaIndraInventariosFunctions;


public class RegistrarPage extends PageObject{

	protected static final Logger logger = LoggerFactory.getLogger(RegistrarPage.class);
	private static final String WebElementFacade = null;
	PruebaIndraInventariosFunctions funcionesGenerales = new PruebaIndraInventariosFunctions();
	PruebaIndraInventariosFunctions pruebaIndraInventariosFunctions;
	

	@FindBy(xpath="//td/table/tbody/tr/td[2]/table/tbody/tr/td/img")
	public WebElementFacade tituloRegistro;

	
	@FindBy (xpath="//a[contains(text(),'REGISTER')]")
	public WebElementFacade linkRegistrer;
	
	@FindBy (name="firstName")
	public WebElementFacade campoNombreTxt;
	
	@FindBy (name="lastName")
	public WebElementFacade campoApellidoTxt;
	
	@FindBy (name="phone")
	public WebElementFacade campoTelefonoTxt;
	
	@FindBy (name="userName")
	public WebElementFacade campoCorreoTxt;
	
	@FindBy(name="address1")
	public WebElementFacade campoDireccionTxt ;
	
	@FindBy(name="city")
	public WebElementFacade campoCiudadTxt;
	
	@FindBy(name="state")
	public WebElementFacade campoProvinciaTxt;
	
	@FindBy(name="postalCode")
	public WebElementFacade campoCodigoPostalTxt;
	
	@FindBy(name="country")
	public WebElementFacade campoPaisTxt;
	
	@FindBy(id="email")
	public WebElementFacade campoUserNameTxt;
	
	@FindBy(name="password")
	public WebElementFacade campoPasswordTxt;
	
	@FindBy(name="confirmPassword")
	public WebElementFacade campoCanfirmarPasswordTxt;
	
	@FindBy(name="submit")
	public WebElementFacade botonEnviar;
	
	@FindBy (xpath="//input[@name='submit']")
	public WebElementFacade botonAceptar;
	
	@FindBy(xpath="//tr[3]/td/p[2]/font")
	public WebElementFacade mensaje;
	
	
//////////////////////////////////////////////////////////
	
	public void ingresoInformacionDelContactoPage(String nombre, String apellido, String telefono, String correo) {
		logger.info("Inicia el paso: Ingreso Información del contacto");
		
		linkRegistrer.click();
		
		if (tituloRegistro.isPresent()) {
			
			logger.info(String.format("Se ingresó información en el campo nombre: '%s'", nombre));
			campoNombreTxt.sendKeys(nombre);
			
			logger.info(String.format("Se ingresó información en el campo apellido: '%s'", apellido));
			campoApellidoTxt.sendKeys(apellido);
			
			logger.info(String.format("Se ingresó información en el campo telefono: '%s'", telefono));
			campoTelefonoTxt.sendKeys(telefono);
			
			logger.info(String.format("Se ingresó información en el campo: correo'%s'", correo));
			campoCorreoTxt.sendKeys(correo);
			
			waitFor(1).seconds();
		}else {
			assertTrue("No se ingresa al modulo de Registro", false);
		}
		logger.info("Termina el paso: Ingreso Información del contacto");
	}
	
	
	public void ingresoInformacionDeEnvioPage(String direccion, String ciudad, String provincia, String codigoPostal, String pais) {
		
		logger.info("Inicia el paso: Ingreso Información de envío");
		
		if (tituloRegistro.isPresent()) {
			
			logger.info(String.format("Se ingresó información en el campo direccion: '%s'", direccion));
			campoDireccionTxt.sendKeys(direccion); 
			
			logger.info(String.format("Se ingresó información en el campo ciudad: '%s'", ciudad));
			campoCiudadTxt.sendKeys(ciudad);
			
			logger.info(String.format("Se ingresó información en el campo provincia: '%s'", provincia));
			campoProvinciaTxt.sendKeys(provincia);
			
			logger.info(String.format("Se ingresó información en el campo codigoPostal: '%s'", codigoPostal));
			campoCodigoPostalTxt.sendKeys(codigoPostal);
			
			campoPaisTxt.sendKeys(pais);
			
			waitFor(2).seconds();
		}else {
			assertTrue("No se ingresa al modulo de Registro", false);
		}
		logger.info("Inicia el paso: Ingreso Información de envío");
	}
	
	public void ingresoInformacionDelUsuarioPage(String userName, String password, String confirmPassword) {
		
		logger.info("Inicia el paso: Ingreso informacion del usuario");
		
		if (tituloRegistro.isPresent()) {
			
			logger.info(String.format("Se ingresó información en el campo userName: '%s'", userName));
			campoUserNameTxt.sendKeys(userName);
			
			logger.info(String.format("Se ingresó información en el campo password: '%s'", password));
			campoPasswordTxt.sendKeys(password);
			
			logger.info(String.format("Se ingresó información en el campo confirmPassword: '%s'", confirmPassword));
			campoCanfirmarPasswordTxt.sendKeys(confirmPassword);
			
			botonEnviar.click();
			botonAceptar.click();
		}else {
			assertTrue("La aplicación no ingresa al modulo registro", false);
		}
		logger.info("Termina el paso: Ingreso informacion del usuario");
	}
	
	
	public void seVerificaLaCreacionExitosaDelUsuarioPage() {
		logger.info("Inica el Paso: Se verifica la creacion exitosa del usuario.");
		
		if(mensaje.isPresent()) {
			logger.info("Se creo el usuario con éxito");
		}else {
			assertTrue("No se creo el usuario", false);
		}
		logger.info("Termina el Paso: Se verifica la creacion exitosa del usuario.");
	}
	
	
	
}