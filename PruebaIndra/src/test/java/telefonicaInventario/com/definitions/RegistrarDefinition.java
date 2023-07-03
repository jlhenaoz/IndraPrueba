package telefonicaInventario.com.definitions; 

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import telefonicaInventario.com.steps.RegistrarSteps;

public class RegistrarDefinition {

	@Steps
	RegistrarSteps registrarSteps;
	
	@When("^Ingreso Información del contacto \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
	public void ingreso_Información_del_contacto(String nombre, String apellido, String telefono, String correo) throws Exception {
	    registrarSteps.ingresoInformacionDelContactoSteps(nombre, apellido, telefono, correo);
	}
	
	@And("^Ingreso Información de envío \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
	public void ingreso_Información_de_envío(String direccion, String ciudad, String provincia, String codigoPostal, String pais) throws Exception {
		registrarSteps.ingresoInformacionDeEnvioStep(direccion, ciudad, provincia, codigoPostal, pais);
	    
	}
	
	@And("^Ingreso informacion del usuario (.*), \"([^\"]*)\", (.*)$")
	public void ingreso_informacion_del_usuario(String userName, String password, String confirmPassword) throws Exception {
	    registrarSteps.ingresoInformacionDelUsuarioStep(userName, password, confirmPassword);
	}
	
	@Then("^Se verifica la creacion exitosa del usuario\\.$")
	public void se_verifica_la_creacion_exitosa_del_usuario() throws Exception {
	    registrarSteps.seVerificaLaCreacionExitosaDelUsuarioStep();
	}
	

}





