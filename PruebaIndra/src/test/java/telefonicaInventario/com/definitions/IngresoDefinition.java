package telefonicaInventario.com.definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import telefonicaInventario.com.steps.IngresoSteps;

public class IngresoDefinition {

	@Steps
	IngresoSteps ingresoSteps;
	
	@Given("^Ingreso al sitio$") 
	public void ingresoSitioLogin(){
		ingresoSteps.ingresoSitioLoginSteps(); 
	}	
	
	/*
	@When("^Ingreso el \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" y pulso el botón Entrar$")
	public void ingresarSitio(String tipoDocumento, String numeroDocumento, String clave){
		ingresoSteps.ingresarSitioSteps(tipoDocumento,numeroDocumento, clave);
	}
	*/
	
	@When("^Ingreso el \"([^\"]*)\", \"([^\"]*)\" y pulso el botón Entrar$")
	public void ingresarSitio(String usuario, String password) throws Exception {
		ingresoSteps.ingresarSitioSteps(usuario,password);
		 
	}
	
}
