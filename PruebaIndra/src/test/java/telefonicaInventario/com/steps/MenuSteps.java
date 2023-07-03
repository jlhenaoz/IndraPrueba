package telefonicaInventario.com.steps;

import net.thucydides.core.annotations.Step;
import telefonicaInventario.com.pageobjects.MenuPage;

	
public class MenuSteps {

	MenuPage menuPage;
			
	// Igresa y verifica el ingreso a la aplicacion
	
	@Step
	public void validoIngresoExitosoSteps() {
		menuPage.validoIngresoExitoso();
	}
	
}
