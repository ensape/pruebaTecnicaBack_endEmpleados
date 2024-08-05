package com.ordenaris.miempresa
//Crear un clase de dominio, grails create-domain-class com.ordenaris.nombredeproyecto.nombreclase
class Empleado {
    //instancia de la clase actual que pertenece a una instancia de la clase Empleado
    // belongsTo (pertence a) : la instancia de la clase actual pertenece a una instancia de otra clase.
    //static belongsTo = [ claveJefeInmediato : Empleado] 
    // Declaracion de los atributos para la clase empleados 
    // Empleado = clase del atribuo a claveJefeInmediato
    Empleado claveJefeInmediato 
    String uuid = UUID.randomUUID().toString().replaceAll('\\-',"")
    String nombres
    String apellidoPaterno
    String apellidoMaterno
    String curp
    String puestoEmpresa
    String claveCurp
    String calleNoExterior
    String calleNoInterior
    String colonia
    String municipio
    String estado
    String pais

    // Datos no obligatorio: Indentificador Jefe, No Interior, Puesto
    static constraints = { // Definir restricciones y validaciones que se aplican en los atributos o propiedades de la clase
        uuid unique : true
        curp unique : true
        claveCurp unique : true, nullable: true, blank : true // blank : true permite un valor vacio
        claveJefeInmediato nullable: true, blank: true // puede ser nulo para los empleados en el nivel superior
        calleNoInterior nullable : true // Ocional
        puestoEmpresa nullable : true // Opcional
    }

    static mapping = { // Desactiva el control de version y no mostrandolo como columna en bd
        version false
    }
    // Al registrarse el empleado se asigna clave unica los 10 caracteres de curp y 2 numeros extra 
    def afterInsert = { // Este ejecuta después de que una nueva fila ha sido insertada en una tabla de la base de datos o accion a realizar depies de la inserccion de datos
        claveCurp = "${curp.take( 10 )}$id${nombres[nombres.size() - 1]}"
        //curp toma la cantidad de 10 caracteres        // Toma el ultimo caracter del nombre
    }
}


