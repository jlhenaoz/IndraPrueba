package telefonicaInventario.com.definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Steps;
import telefonicaInventario.com.steps.MenuSteps;

public class MenuDefinition {

	@Steps
	MenuSteps menuSteps;
	
	
	@Then("^Valido la autenticación en el sistema$") 
	public void valido_la_autenticacion_en_el_sistema(){
		menuSteps.validoIngresoExitosoSteps(); 
	}
	
}
