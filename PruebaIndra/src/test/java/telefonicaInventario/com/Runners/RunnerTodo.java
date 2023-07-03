package telefonicaInventario.com.Runners;

import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import telefonicaInventario.com.utilidades.BeforeSuite;
import telefonicaInventario.com.utilidades.DataToFeature;


@CucumberOptions(features ="src/test/resources/feature/",
				glue = "telefonicaInventario.com",
				tags = {"@Todo"},
				monochrome= true)

@RunWith(RunnerPersonalizado.class)
public class RunnerTodo {

	@BeforeSuite
	public static void test() throws InvalidFormatException, IOException {		
		DataToFeature.overrideFeatureFiles("./src/test/resources/feature/");
		}
}
 



