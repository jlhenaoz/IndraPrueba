##Fecha creación 
#Feature de Ingreso a la aplicación
#Version 1 ()- 

@Ingreso
Feature: Pantalla ingreso 
  Como usuario requiero iniciar sesion

  Scenario Outline: Inicio de sesión exitoso
    Given Ingreso al sitio
    When Ingreso el <usuario>, <password> y pulso el botón Entrar
    Then Valido la autenticación en el sistema

    Examples:
      |usuario          |password           |
  		##@externaldata@./src/test/resources/DataDriven/Ingreso/Ingreso.xlsx@Caso01   
			|"admin"|"admin"|