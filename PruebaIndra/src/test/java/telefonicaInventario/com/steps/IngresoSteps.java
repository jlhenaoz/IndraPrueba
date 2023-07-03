package telefonicaInventario.com.steps;

import net.thucydides.core.annotations.Step;
import telefonicaInventario.com.pageobjects.IngresoPage;

	
public class IngresoSteps {

	IngresoPage ingresoPage;
	/*
	 * Este paso va con los metodos		
	 */
	@Step
	public void ingresoSitioLoginSteps() {
		ingresoPage.open();
	}
			
	@Step
	public void ingresarSitioSteps(String usuario, String password) {		
		ingresoPage.ingresarSitio(usuario, password);
	}
				
	
}
