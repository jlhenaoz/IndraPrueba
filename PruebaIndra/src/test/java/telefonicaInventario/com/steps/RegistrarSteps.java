package telefonicaInventario.com.steps;

import net.thucydides.core.annotations.Step;
import telefonicaInventario.com.pageobjects.RegistrarPage;


	
public class RegistrarSteps {

	RegistrarPage registrarPage;	
	
	
	@Step
	public void ingresoInformacionDelContactoSteps (String nombre, String apellido, String telefono, String correo) {
		registrarPage.ingresoInformacionDelContactoPage(nombre, apellido, telefono, correo);
		
	} 
	
	
	@Step
	public void ingresoInformacionDeEnvioStep(String direccion, String ciudad, String provincia, String codigoPostal, String pais) {
		registrarPage.ingresoInformacionDeEnvioPage(direccion, ciudad, provincia, codigoPostal, pais);
	}
	
	
	
	@Step
	public void ingresoInformacionDelUsuarioStep(String userName, String password, String confirmPassword) {
		registrarPage.ingresoInformacionDelUsuarioPage(userName, password, confirmPassword);
	}
	
	@Step
	public void seVerificaLaCreacionExitosaDelUsuarioStep() {
		registrarPage.seVerificaLaCreacionExitosaDelUsuarioPage();
	}

	
	
}
