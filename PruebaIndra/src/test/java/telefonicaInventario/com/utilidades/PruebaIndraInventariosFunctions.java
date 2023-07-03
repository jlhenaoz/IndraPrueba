package telefonicaInventario.com.utilidades;

import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.ByAngularBinding.FindBy;
import io.restassured.RestAssured;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.exceptions.SerenityManagedException;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.steps.StepEventBus;

import java.net.URL;


@SuppressWarnings("unused")
public class PruebaIndraInventariosFunctions extends PageObject {
	
	Properties prop = new Properties();
	InputStream input = null;
	static Marker fatal = MarkerFactory.getMarker("FATAL");
	static final Logger logger = LoggerFactory.getLogger(PruebaIndraInventariosFunctions.class);
	static String fechaGeneradaCUPGlobal = "";
		
		    	
	//----------------------------------------Utilidades Indra---------------------------------------------//
			
    /**
     * Método para validar el último archivo descargado
     * <p>
     * Es un método modificado para garantizar que el archivo se descargó sin necesidad de abrir procesos y
     * generar conflictos de archivos abiertos.
     * @param formato Admite los formatos: Excel , PDF, Texto, HTML, ExcelAntiguo, ZIP y CSV
     * @param nombre Ingresa el nombre del archivo que se descarga del sitio
     * @return Retorna el nombre del archivo encontrado
     * @author jlhenao
     * @since 1.1
     * 
     */
    public static String validarExistenciaArchivoExportado(String formato, String nombre)
    {
    	String respuesta = "";
    	logger.info("Comienza proceso de validar el archivo generado");  
    	List<String> archivos = new ArrayList<>();
		String home = System.getProperty("user.name");
		String sDirectorio = "C:\\Users\\"+home +"/Downloads/";
		File f = new File(sDirectorio);		
		File[] ficheros = f.listFiles();
		String archivo = "";		
		try {		
		Files.walk(Paths.get(sDirectorio)).forEach(ruta-> {			  
		        if (Files.isRegularFile(ruta)) {
		            archivos.add(ruta.toAbsolutePath().toString());		            
		        }
		    });		
		}catch (Exception ex) 
		{
			logger.info("No hay archivos en el directorio: "+sDirectorio);
			respuesta = "KO";
		}    
        archivos.removeIf(s -> !s.contains(formato)); 
        archivos.removeIf(s -> !s.contains(nombre));     
        if(archivos.size() > 0)
		{
			if(archivos.size() == 1)
			{
				logger.info("El archivo cumple con el formato: "+archivos.get(0));
				logger.info("Termina el proceso de validar el archivo generado de forma exitosa");
				respuesta = archivos.get(0);
			}
			else
			{	
				
				File[] archivosResultantes = new File[archivos.size()];	
				for(int i=0;i<archivos.size();i++)
				{
					File file = new File(archivos.get(i));
					archivosResultantes[i] = file;
				}		
			
				Arrays.sort(archivosResultantes, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
		        for (File file : archivosResultantes) {     
		        	//archivo = file.getAbsolutePath();
		        	archivo = file.getName();
		              break;        
		        }			
		        logger.info("El archivo cumple con el formato: "+archivo);
		        logger.info("Termina el proceso de validar el archivo generado de forma exitosa");
		        respuesta = archivo;
			}			
		}
		else
		{				
			respuesta = "KO";
		}
		return respuesta;
    } 
    
    public String validarNuevoArchivo(String nombreInicial,String extension)
	 {
		 String archivo = "";
		 try {
		 String ultimoArchivoLuego = validarExistenciaArchivoExportado(extension,"FichaContenido");
		 
			int intento = 0;
			while(nombreInicial.contentEquals(ultimoArchivoLuego))
			{
				Thread.sleep(5000);
				ultimoArchivoLuego = validarExistenciaArchivoExportado(extension,"FichaContenido");
				intento ++;				
				if(intento == 12)
				{
					assertTrue("Error, no se encontró el nuevo archivo descargado", false);
				}
			}
			if(nombreInicial.contains("KO"))
			{
				archivo = ultimoArchivoLuego;
			}else
			{
				String home = System.getProperty("user.name");
				String sDirectorio = "C:\\Users\\"+home +"/Downloads/";
				archivo = sDirectorio+ultimoArchivoLuego;
			}		
		 }catch(Exception ex)
		 {
			 
		 }
		 return archivo;
	 }
    
    public String leerPdfBasico(String ultimoArchivoLuego)
	 {	
		 String resultado = "";
		try (PDDocument document = PDDocument.load(new File(ultimoArchivoLuego))) {
	            document.getClass();
	            if (!document.isEncrypted()) {
	                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
	                stripper.setSortByPosition(true);
	                PDFTextStripper tStripper = new PDFTextStripper();
	                String pdfFileInText = tStripper.getText(document);
	                resultado = pdfFileInText;
	            }

	        } catch (InvalidPasswordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return resultado;
	 }
    
    /**
     * Metodo para PDF en el Navegador 
     * @param archivo
     */
    
    public void abrirPDfNavegador(String archivo)
	 {		 
    	System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");	        	
    	WebDriver driver = new ChromeDriver();
		 try {	    		    	   	
	        	driver.get(archivo);
	        	driver.manage().window().maximize();   	
	        	Thread.sleep(5000);
	        	tomarEvidencia1("PDF");	        	
	        	driver.close();
	        	driver.quit();
	    	}catch(Exception ex)
	    	{
	    		assertTrue("No pude abrir el PDF",false);
	    		driver.close();
	    		driver.quit();
	    	}
	 }
    
    public static void tomarEvidencia1(String description) {
		String path = "target/site/serenity/";
		String folder = "images/";
		File dir = new File(path + folder);
		if (!dir.exists()) {
			dir.mkdir();
		}

		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String fileName = "scrn" + timeStamp + ".png";

		try {
			BufferedImage image = new Robot()
					.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", new File(dir + "/" + fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String text = description + "\r\n" + "\r\n[![evidencia](" + folder + fileName + " \"Evidencia " + description
				+ "\")](" + folder + fileName + ")";

		StepEventBus.getEventBus().updateCurrentStepTitle(text);
	}
    
    /**
     * Método para validar el último archivo descargado con nombre exacto
     * <p>
     * Es un método modificado para garantizar que el archivo se descargó sin necesidad de abrir procesos y
     * generar conflictos de archivos abiertos.
     * @param formato Admite los formatos: Excel , PDF, Texto, HTML, ExcelAntiguo, ZIP y CSV
     * @param nombre Ingresa el nombre del archivo que se descarga del sitio
     * @return Retorna el nombre del archivo encontrado
     * @since 1.0
     * 
     */    
    public static String validarExistenciaArchivoExportadoEstricto(String formato, String nombre)
    {
    	String respuesta = "";
    	logger.info("Comienza proceso de validar el archivo generado");  
    	List<String> archivos = new ArrayList<>();
		String home = System.getProperty("user.name");
		String sDirectorio = "C:\\Users\\"+home +"/Downloads/";		
		File f = new File(sDirectorio);		
		File[] ficheros = f.listFiles();
		String archivo = "";
		String nomenclatura = convertirFormato(formato);
		try {		
		Files.walk(Paths.get(sDirectorio)).forEach(ruta-> {			  
		        if (Files.isRegularFile(ruta)) {
		            archivos.add(ruta.toAbsolutePath().toString());		            
		        }
		    });		
		}catch (Exception ex) 
		{
			logger.info("No hay archivos en el directorio: "+sDirectorio);
			assertTrue("No hay archivos en el directorio: "+sDirectorio, false);
			respuesta = "KO";
		}    
        archivos.removeIf(s -> !s.contentEquals(nomenclatura));  
		archivos.removeIf(s -> !s.contentEquals(nombre));	

		if(archivos.size() > 0)
		{
			if(archivos.size() == 1)
			{
				logger.info("El archivo cumple con el formato: "+archivos.get(0));
				logger.info("Termina el proceso de validar el archivo generado de forma exitosa");
				respuesta = "OK";
			}
			else
			{	
				
				File[] archivosResultantes = new File[archivos.size()];	
				for(int i=0;i<archivos.size();i++)
				{
					File file = new File(archivos.get(i));
					archivosResultantes[i] = file;
				}		
			
				Arrays.sort(archivosResultantes, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
		        for (File file : archivosResultantes) {     
		        	archivo = file.getAbsolutePath();
		              break;        
		        }			
		        logger.info("El archivo cumple con el formato: "+archivo);
		        logger.info("Termina el proceso de validar el archivo generado de forma exitosa");
		        respuesta = "OK";
			}			
		}
		else
		{
			assertTrue("No hay archivos que cumplan con el formato: "+formato+" y el nombre: "+nombre, false);
			respuesta = "KO";
		}
		return respuesta;
    } 
    
    
    
    public static String convertirFormato(String formato)
    {
    	String retorno = "";
    	if(formato.contains("Excel"))
    	{
    		retorno = "xlsx";
    	}
    	
    	if(formato.contains("ExcelAntiguo"))
    	{
    		retorno = "xls";
    	}
    	
    	if(formato.contains("HTML"))
    	{
    		retorno = "html";
    	}
    	if(formato.contains("Texto"))
    	{
    		retorno = "txt";
    	}
    	if(formato.contains("PDF"))
    	{
    		retorno = "pdf";
    	} 	
    	if(formato.contains("ZIP"))
    	{
    		retorno = "zip";
    	} 
    	if(formato.contains("CSV"))
    	{
    		retorno = "csv";
    	} 
    	if(formato.contains("ZIP"))
    	{
    		retorno = "zip";
    	} 
    	
    	if(formato.contains(""))
    	{
    		retorno = "";
    	} 
    	return retorno;
    }
          
    public void bajarPantalla(WebDriver driver)
    {
    	JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("window.scrollBy(0,500)");
    }
    
    public void bajarPantalla(WebDriver driver,int veces)
    {
    	JavascriptExecutor js = (JavascriptExecutor) getDriver();
    	for(int i =0;i<= veces ;i++) {
		js.executeScript("window.scrollBy(0,500)");}
    }
    
       
    /*Funciones de aleatoriedad*/
	 
    /**
     * Función que permite generar un número movil aleatoriamente que siempre comienza por 3
     * @return retorna el número de movil aleatorio
     * @author jlhenao
     */
	public static String GeneraNumTelCelularAleatorio() {
		int min = 100000000;
		int max = 999999990;
		Random random = new Random();
		int resultado = random.nextInt(max - min + 1) + min;
		
		String resultadoConcatenado = "3"+String.valueOf(resultado);
		
		return resultadoConcatenado;			
	}
	
	public static String GeneraNumPuertoAleatorio() {
		int min = 100000000;
		int max = 999999990;
		Random random = new Random();
		int resultado = random.nextInt(max - min + 1) + min;
		
		String resultadoConcatenado = "3"+String.valueOf(resultado);
		
		return resultadoConcatenado;			
	}
	
	
	  
	 //------------------------------------------- Funciones especiales --------------------------------------------//
	 
	 public List<String> listaJurisdicciones()
	 {
		 List<String> codigosDisponibles = new ArrayList<String>();
		 String archivoOrigen = ""; 
		 try {
			 input = new FileInputStream("src/test/java/ekogui/com/utilidades/config.properties");   
			 prop.load(input);	    	
			 archivoOrigen =prop.getProperty("archivoJurisdiccionAccion");			 
			 Fillo fillo=new Fillo();			 
			 com.codoid.products.fillo.Connection connection=fillo.getConnection(archivoOrigen);
			 Recordset recordset=connection.executeQuery("Select * from Jurisdiccion_Accion").where("Proceso_subyacente='NO'");
			 while(recordset.next()){
				 codigosDisponibles.add(recordset.getField("Jurisdicción"));
				 }
			 recordset.close();
			 connection.close();
			 }catch(Exception ex){
			 logger.info("No pude cargar el archivo de jurisdicciones por: "+ex);
		 }		 
	    List<String> listWithoutDuplicates = codigosDisponibles.stream()
		     .distinct()
		     .collect(Collectors.toList());	    
		 return  listWithoutDuplicates;
	 }
	 

	 
	 
	 
	 public List<String> listaAcciones(String jurisdiccion)
	 {
		 List<String> codigosDisponibles = new ArrayList<String>();
		 String archivoOrigen = ""; 
		 try {
			 input = new FileInputStream("src/test/java/ekogui/com/utilidades/config.properties");   
			 prop.load(input);	    	
			 archivoOrigen =prop.getProperty("archivoJurisdiccionAccion");			 
			 Fillo fillo=new Fillo();			 
			 com.codoid.products.fillo.Connection connection=fillo.getConnection(archivoOrigen);
			 Recordset recordset=connection.executeQuery("Select * from Jurisdiccion_Accion").where("Proceso_subyacente='NO'").where("Jurisdicción='"+jurisdiccion+"'");
			 while(recordset.next()){
				 codigosDisponibles.add(recordset.getField("Acción, medio de control, procedimiento o subtipo de proceso"));
				 }
			 recordset.close();
			 connection.close();
			 }catch(Exception ex){
			 logger.info("No pude cargar el archivo de acciones por: "+ex);
		 }		 
		 List<String> listWithoutDuplicates = codigosDisponibles.stream()
		     .distinct()
		     .collect(Collectors.toList());
		 listWithoutDuplicates.removeIf(s -> s.contentEquals("EJECUTIVO")); 
		 listWithoutDuplicates.removeIf(s -> s.contentEquals("EJECUTIVO LABORAL"));
		 logger.info("Lista de acciones disponibles: "+listWithoutDuplicates);
		 return  listWithoutDuplicates;
	 }
	 
	 public List<String> listaCodigosActivos()
	 {
		 List<String> codigosDisponibles = new ArrayList<String>();
		 String archivoOrigen = ""; 
		 try {
			 input = new FileInputStream("src/test/java/ekogui/com/utilidades/config.properties");   
			 prop.load(input);	    	
			 archivoOrigen =prop.getProperty("archivoCorporaciones");			 
			 Fillo fillo=new Fillo();			 
			 com.codoid.products.fillo.Connection connection=fillo.getConnection(archivoOrigen);
			 Recordset recordset=connection.executeQuery("Select * from Hoja2").where("ESTADO_CORP='A'").where("ESTADO_ESP='A'").where("ESTADO_DESP='A'");
			 while(recordset.next()){
				 codigosDisponibles.add(recordset.getField("CODIGO"));
				 }
			 recordset.close();
			 connection.close();
			 }catch(Exception ex){
			 logger.info("No pude cargar el archivo de corporaciones por: "+ex);
		 }
		 return  codigosDisponibles;
	 }
	 
	 public List<String> listaEntidades()
	 {
		 List<String> entidades = new ArrayList<String>();
		 String archivoOrigen = ""; 
		 try {
			 input = new FileInputStream("src/test/java/ekogui/com/utilidades/config.properties");   
			 prop.load(input);	    	
			 archivoOrigen =prop.getProperty("archivoEntidades");			 
			 Fillo fillo=new Fillo();			 
			 com.codoid.products.fillo.Connection connection=fillo.getConnection(archivoOrigen);
			 Recordset recordset=connection.executeQuery("Select * from Hoja1");
			 while(recordset.next()){
				 entidades.add(recordset.getField("ENTI_NOMBRE"));
				 }
			 recordset.close();
			 connection.close();
			 }catch(Exception ex){
			 logger.info("No pude cargar el archivo de corporaciones por: "+ex);
		 }
		 return  entidades;
	 }
	 

	 public static int GeneraNumConsecutivoAleatorio() {
			int min = 10;
			int max = 99;
			Random random = new Random();
			return random.nextInt(max - min + 1) + min;

		}
	 
	 public static String SeleccionarValorAleatorio(List<String> lista, String descripcion) {
		Random aleatorio = new Random();
		int nAleatorio = aleatorio.nextInt(lista.size());
	    String elementoAzar = lista.get(nAleatorio);
	    int limite = 0;
	    while(elementoAzar.isEmpty())
	    {
	    	nAleatorio = aleatorio.nextInt(lista.size());
		    elementoAzar = lista.get(nAleatorio);
		    limite = limite+1;
		    if(limite == 20)
		    {
		    	logger.info("Termine los intentos y no pude extraer un elemento para seleccionar");
		    }
	    }
	    logger.info(String.format("Elemento a seleccionar en la lista '%s' ---> "+elementoAzar,descripcion));	
	    return elementoAzar;
	 }
	 
	 public static String SeleccionarNumeroDocumentoAleatorio(List<String> lista) {
			Random aleatorio = new Random();
			int nAleatorio = aleatorio.nextInt(lista.size());
		    String elementoAzar = lista.get(nAleatorio);
		    int limite = 0;
		    while(elementoAzar.isEmpty())
		    {
		    	nAleatorio = aleatorio.nextInt(lista.size());
			    elementoAzar = lista.get(nAleatorio);
			    limite = limite+1;
			    if(limite == 20)
			    {
			    	logger.info("Termine los intentos y no pude extraer un elemento para seleccionar");
			    }
		    }
		    logger.info(String.format("Número de documento a escribir: %s",elementoAzar));	
		    return elementoAzar;
		 }
	 	 
	 public static String getAnioAleatorio() {
		Random random = new Random();
		int minDay = (int) LocalDate.of(1985, 1, 1).toEpochDay();
		int maxDay = (int) LocalDate.of(Calendar.getInstance().get(Calendar.YEAR), 1, 1).toEpochDay();
		long randomDay = minDay + random.nextInt(maxDay - minDay);
		LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
		logger.info("Año aleatorio generado: "+randomBirthDate);
		fechaGeneradaCUPGlobal = randomBirthDate.toString();
		return randomBirthDate.toString();
	 }
	 
	 public static void cargarArchivoUploadWindowsCombinado(String ruta, String ejecutable)
	 {
		 try { 
			 Toolkit.getDefaultToolkit()
	           .getSystemClipboard()
	           .setContents(
	                   new StringSelection(ruta),
	                   null
	           );
			 logger.info("Ruta copiada");
			 Runtime.getRuntime().exec(ejecutable);
			 logger.info("Carga ejecutada");
		 } catch (Exception e) {
			 assertTrue("Se suspende la prueba, no fue posible encontrar el archivo: "+e,false);
		 }
	 }
	 
	 public static Collection<String> filtrarListaCalidad(Collection<String> listaCalidad, String calidad){
		 listaCalidad.removeIf(x -> !x.contains(calidad));		 
		 return listaCalidad;
	 }
	 
	 /*
	 public HashMap<String,String> listaPersonas()
	 {
		 HashMap<String,String> x = new HashMap<String,String>();
		 String archivoOrigen = ""; 
		 try {
			 input = new FileInputStream("src/test/java/ekogui/com/utilidades/config.properties");   
			 prop.load(input);	    	
			 archivoOrigen =prop.getProperty("archivoPersonas");			 
			 Fillo fillo=new Fillo();			 
			 com.codoid.products.fillo.Connection connection=fillo.getConnection(archivoOrigen);
			 Recordset recordset=connection.executeQuery("Select * from Personas").where("PERS_TIPO_IDENTIFICACION='CC'");
			 while(recordset.next()){				 
				 //Luego validar el ciclo para NIT
				 x.put("Cédula de Ciudadanía",recordset.getField("PERS_IDENTIFICACION"));
				 }
			 recordset.close();
			 connection.close();
			 }catch(Exception ex){
			 logger.info("No pude cargar el archivo de personas por: "+ex);
		 }		    
		 return  x;
	 }
	 
	 public List<String> listaPersonasCedula()
	 {
		 logger.info("Inicio subproceso de cargar las personas del archivo origen");
		 List<String> x = new ArrayList<String>();
		 String archivoOrigen = ""; 
		 try {
			 input = new FileInputStream("src/test/java/ekogui/com/utilidades/config.properties");   
			 prop.load(input);	    	
			 archivoOrigen =prop.getProperty("archivoPersonas");			 
			 Fillo fillo=new Fillo();			 
			 com.codoid.products.fillo.Connection connection=fillo.getConnection(archivoOrigen);
			 Recordset recordset=connection.executeQuery("Select * from Personas").where("PERS_TIPO_IDENTIFICACION='CC'");
			 while(recordset.next()){				 
				 x.add(recordset.getField("PERS_IDENTIFICACION"));
				 }
			 recordset.close();
			 connection.close();
			 }catch(Exception ex){
			 logger.info("No pude cargar el archivo de personas por: "+ex);
		 }	
		 logger.info("Termino subproceso de cargar las personas del archivo origen");
		 return  x;
	 }
	 */
	 
	 public static String seleccionarPersonas(HashMap<String,String> listaPersonas)
	 {
		 Random generator = new Random();
		 Object[] values = listaPersonas.values().toArray();
		 Object randomValue = values[generator.nextInt(values.length)];
		 return randomValue.toString();
	 }
	 
	 public String retornarObservaciones()
	 {
		 /*
		 String texto = ""; 
		 try {
			 input = new FileInputStream("src/test/java/ekogui/com/utilidades/config.properties");   
			 prop.load(input);	    	
			 texto =prop.getProperty("observaciones");		 
			 }catch(Exception ex){
			 logger.info("No pude cargar el archivo de configuracion para las observaciones: "+ex);
		 }	
		 return  texto;
		 */
		 return "Esta observación es ingresada desde el robot";
	 }
	 
	 public static String retornarHechos()
	 {
		 return "1. EL DÍA 1 DE NOVIEMBRE DE 2005 EL INVÍAS SUSCRIBIÓ CON EL CONSORCIO VALLE DEL CAUCA CONTRATO NO. 2490 DE 2005. 2. INDICA LA PARTE CONVOCANTE QUE LA ENTIDAD CONVOCANTE ABUSÓ DEL DERECHO Y DE SU POSICIÓN DOMINANTE AL IMPONER LA1. EL DÍA 1 DE NOVIEMBRE DE 2005 EL INVÍAS SUSCRIBIÓ CON EL CONSORCIO VALLE DEL CAUCA CONTRATO NO. 2490 DE 2005. 2. INDICA LA PARTE CONVOCANTE QUE LA ENTIDAD CONVOCANTE ABUSÓ DEL DERECHO Y DE SU POSICIÓN DOMINANTE AL IMPONER LA1. EL DÍA 1 DE NOVIEMBRE DE 2005 EL INVÍAS SUSCRIBIÓ CON EL CONSORCIO VALLE DEL CAUCA CONTRATO NO. 2490 DE 2005. 2. INDICA LA PARTE CONVOCANTE QUE LA ENTIDAD CONVOCANTE ABUSÓ DEL DERECHO Y DE SU POSICIÓN DOMINANTE AL IMPONER";
		 /*
		 String texto = ""; 
		 try {
			 input = new FileInputStream("src/test/java/ekogui/com/utilidades/config.properties");   
			 prop.load(input);	    	
			 texto =prop.getProperty("hechos");		 
			 }catch(Exception ex){
			 logger.info("No pude cargar el archivo de configuracion para los hechos: "+ex);
		 }	
		 return  texto;
		 */
		 
	 }
	 
	 public static String retornarPretenciones()
	 {
		 /*
		 String texto = ""; 
		 try {
			 input = new FileInputStream("src/test/java/ekogui/com/utilidades/config.properties");   
			 prop.load(input);	    	
			 texto =prop.getProperty("pretensiones");			 
			 }catch(Exception ex){
			 logger.info("No pude cargar el archivo de configuracion para las pretensiones: "+ex);
		 }	
		 return  texto;
		 */
		 return "QUE EN CONSECUENCIA SE CONDENE A TEBSA AL PAGO DE LA SUMA DE ($20.608.649.198) O A LA SUMA MAYOR O MENOR  QUE ENCUENTRE PROBADA EN EL PROCESO";
	 }
	 
	 public static String retornarSubcausas()
	 {
		 /*
		 String texto = ""; 
		 try {
			 input = new FileInputStream("src/test/java/ekogui/com/utilidades/config.properties");   
			 prop.load(input);	    	
			 texto =prop.getProperty("subcausas");		 
			 }catch(Exception ex){
			 logger.info("No pude cargar el archivo de configuracion para las pretensiones: "+ex);
		 }	
		 return  texto;
		 */
		 return "Esta sub causa es ingresada desde el robot";
	 }
	 
	 public String seleccionarValorAletorioLista(Select lista, String descripcionLista)
	 {
		List<String> getElementosLista = lista.getOptions().stream().map(x -> x.getText())
		            .collect(Collectors.toList());
		int flag = 0;
		while(getElementosLista.size() < 1)
		{
			 logger.info("intento: "+flag);
			 getElementosLista = lista.getOptions().stream().map(x -> x.getText())
			            .collect(Collectors.toList());
			 flag = flag +1;			 
			 if(flag == 20)
			 {
				 logger.info("Termine los intentos");
			 }
		 }
		String getElementoAleatorio = SeleccionarValorAleatorio(getElementosLista,descripcionLista);
		//findBy("//input[@class='select2-search__field']").sendKeys(getElementoAleatorio,Keys.ENTER);
		lista.selectByVisibleText(getElementoAleatorio);
		waitFor(2).seconds();
		//logger.info("Elemento seleccionado correctamente");
		//findBy("//*[text()='"+getElementoAleatorio+"']").click();
		//waitFor(2).seconds();
		logger.info("Elemento clickeado correctamente");
		//find(By.xpath("//span[text()='"+getElementoAleatorio+"']")).click();
		return getElementoAleatorio;
	 }
	 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 
	 
	 public String seleccionarValorLista(String selectorLista, String descripcionLista)
	 {
		 
		Select lista = new Select(find(By.id(selectorLista)));
		findBy("//select[@id='"+selectorLista+"']/following-sibling::span").click();
		List<String> getElementosLista = lista.getOptions().stream().map(x -> x.getText())
		            .collect(Collectors.toList());
		int flag = 0;
		while(getElementosLista.size() < 1)
		{
			 logger.info("intento: "+flag);
			 getElementosLista = lista.getOptions().stream().map(x -> x.getText())
			            .collect(Collectors.toList());
			 flag = flag +1;			 
			 if(flag == 20)
			 {
				 logger.info("Termine los intentos");
			 }
		 }
		String getElementoAleatorio = SeleccionarValorAleatorio(getElementosLista,descripcionLista);
		findBy("//input[@class='select2-search__field']").sendKeys(getElementoAleatorio,Keys.ENTER);
		waitFor(2).seconds();
		return getElementoAleatorio;
	 }
	 
	 public void seleccionarValorFijoLista(String selectorLista, String valorSeleccionar){
		 logger.info("Detectar campo: " + selectorLista);
		 /*
		 Select lista = new Select(find(By.id(selectorLista)));
		 logger.info("Seleccionar valor: " + valorSeleccionar);
		 findBy("//select[@id='"+selectorLista+"']/following-sibling::span").click();
		 findBy("//input[@class='select2-search__field']").sendKeys(valorSeleccionar,Keys.ENTER);
		 waitFor(2).seconds();
		 */
		 //WebDriverWait wait =new WebDriverWait(getDriver(), 60);
		 //wait.until(ExpectedConditions.elementToBeClickable(By.id(selectorLista)));
		 logger.info("Seleccionar valor: " + valorSeleccionar);
		 pulsarBoton("//select[@id='"+selectorLista+"']/following-sibling::span");
		 		 //findBy("//select[@id='"+selectorLista+"']/following-sibling::span").click();		 
		 //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class='select2-search__field']")));
		 pulsarBoton("//input[@class='select2-search__field']");
		 findBy("//input[@class='select2-search__field']").sendKeys(valorSeleccionar,Keys.ENTER);
		 waitFor(2).seconds();
	 }
	 
	 public static String getValorPretencion() {
		 int n = (int) (Math.random() * (90000001 - 1000000)) + 1000000;
		 logger.info("Valor económico de la pretención: "+n);
		 return String.valueOf(n);
	 }
	 
	 public void seleccionarCheckAleatorio(List<WebElement> lista) {
		 if(lista.size() > 0)
		 {
			Random aleatorio = new Random();
			int nAleatorio = aleatorio.nextInt(lista.size());
			WebElement elementoAleatorio = lista.get(nAleatorio);
			elementoAleatorio.click();	
			logger.info("El nombre del abogado seleccionado es: "+find(By.xpath("//*[@class='clickable row-selected']/td[2]/span")).getText());
		 }else {
			 logger.info("No se encontraron abogados en la lista, revisar si no cargaron o si cambió el selector");
		 }
	 }
	 
	 // Método para leer el diretorio donde se guardan los archivos descargdos
	 public File abrirArchivoDescargado(File archivoDescargado) {
		 logger.info("Búsqueda de directorio descargas y archivo");
		 String dirUsuario = System.getProperty("user.name");
	     logger.info("Directorio de usuario" + dirUsuario);
	     String dirDescargas = "C:\\Users\\"+dirUsuario +"/Downloads/";
	     logger.info("Directorio de descargas" + dirDescargas);
	     File carpeta = new File(dirDescargas);
	        
	     File[] archivos = carpeta.listFiles();
	     if (archivos == null || archivos.length == 0) {
	    	 logger.info("No hay elementos dentro de la carpeta actual");
	     }
	     else {
	    	 for (int i=0; i< archivos.length; i++) {
	    		 File archivo = archivos[i];
	    		 //logger.info("Archivos " + String.format(archivo.getName()));
	    		 if (archivo == archivoDescargado) {
	    			 return archivo;
	    		 }
	         }
	     }
		return archivoDescargado;
	 }
	 
	 public void ingresarTextoAngular(String selector, String texto) {
		WebElement element = find(ByAngular.model(selector));
		element.sendKeys(texto);
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("arguments[0].focus(); arguments[0].blur(); return true", element);
		waitForAngularRequestsToFinish();
		waitFor(2).seconds();
	 }

	 public void ingresarTextoId(String selector, String texto) {		 
			WebElement element = find(By.id(selector));
			element.sendKeys(texto);
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("arguments[0].focus(); arguments[0].blur(); return true", element);
			waitForAngularRequestsToFinish();
			waitFor(2).seconds();
		 }
	 
	 public void pulsarBoton(String selectorBoton) {
		 
		 boolean bandera = false;
		 int contador = 0;
		 while(!bandera) {
			 try {
				 Wait<WebDriver> wait2 = new FluentWait<WebDriver>(getDriver())
						  .withTimeout(Duration.ofSeconds(30))
						  .pollingEvery(Duration.ofSeconds(5))
						  .ignoring(SerenityManagedException.class)
						  .ignoring(NoSuchElementException.class);
				 
				 WebElement foo = wait2.until(new Function<WebDriver, WebElement>() {
					  public WebElement apply(WebDriver driver) {
					    return driver.findElement(By.xpath(selectorBoton));
					  }
					});
			wait2.until(ExpectedConditions.elementToBeClickable(foo));
			waitFor(1).seconds();
			foo.click();
			 bandera = true;
			 }catch(Exception ex)
			 {
				 contador = contador +1;
			 }
			 if(contador == 100)
			 {
				 assertTrue("Se cumplieron 100 intentos y no estuvo disponible el elemento: "+selectorBoton, false);
			 }
		 }
	 }
}
