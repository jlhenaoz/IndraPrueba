##Fecha creación 27/02/2023 
#Feature de Ingreso a la aplicación
#Version 1 (27/02/2023)- 

 @Todo
Feature: Prueba Indra

	@Registrar
	Scenario Outline: Registro usuario nuevo
    Given Ingreso al sitio
    When Ingreso Información del contacto <nombre>, <apellido>, <telefono>, <correo>
    And Ingreso Información de envío <direccion>, <ciudad>, <provincia>, <codigoPostal>, <pais>
    And Ingreso informacion del usuario <userName>, <password>, <confirmPassword>
    Then Se verifica la creacion exitosa del usuario.

    Examples:
      |usuario|password|nombre  |apellido|telefono    |correo            |direccion                  |ciudad  |provincia|codigoPostal|pais      |userName|password     |confirmPassword|
  		##@externaldata@./src/test/resources/DataDriven/Ingreso/Ingreso.xlsx@Caso01   
			|"admin"|"admin" |"Javier"|"Henao" |"3102301953"|"prueba@prueba.com"|"Avenida Siempre Viva 123"|"Bogotá"|"Bogotá"|"111211"     |"COLOMBIA"|jhenao   |"Pruebas123$"|"Pruebas123$"|
	
	
	@Ingreso
	Scenario Outline: Inicio de sesión exitoso
    Given Ingreso al sitio
    When Ingreso el <usuario>, <password> y pulso el botón Entrar
    Then Valido la autenticación en el sistema

    Examples:
      |usuario          |password           |
  		##@externaldata@./src/test/resources/DataDriven/Ingreso/Ingreso.xlsx@Caso01   
		