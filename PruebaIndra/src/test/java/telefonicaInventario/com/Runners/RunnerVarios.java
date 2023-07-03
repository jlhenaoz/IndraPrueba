package telefonicaInventario.com.Runners;

import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import telefonicaInventario.com.utilidades.BeforeSuite;
import telefonicaInventario.com.utilidades.DataToFeature;

@RunWith(RunnerPersonalizado.class)
@CucumberOptions(features ="src/test/resources/feature/",
				 glue = "telefonicaInventario.com",
		         tags = {"@Registrar"},
		         monochrome= true)

public class RunnerVarios {
	@BeforeSuite
	public static void test() throws InvalidFormatException, IOException {		
		DataToFeature.overrideFeatureFiles("./src/test/resources/feature/");
		}
}