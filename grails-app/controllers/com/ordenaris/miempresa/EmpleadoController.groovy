package com.ordenaris.miempresa


import grails.rest.*
import grails.converters.*

class EmpleadoController {
	static responseFormats = ['json', 'xml']

    def EmpleadoService // declaramos la funcionn para poder ocuparla en en controller y service
	
    def gestionarEmpleado() {

        def data = request.JSON 

        if (!data.nombres) return respond ([ success: false, mensaje: "Es necesarion el parametro nombres" ])
        if (!data.apellidoPaterno) return respond ([ success: false, mensaje: "Es necesarion el parametro apellido paterno" ])
        if (!data.apellidoMaterno) return respond ([ success: false, mensaje: "Es necesarion el parametro apellido materno" ])
        if (!data.curp) return respond ([ success: false, mensaje: "Es necesarion el parametro curp" ])
        // if (!data.puestoEmpresa) return respond ([ success: false, mensaje: "Es necesarion el parametro puesto de empresa" ])
        // if (!data.claveJefeInmediato) return respond ([ success: false, mensaje: "Es necesarion el parametro clave de jefe inmediato" ])
        if (!data.calleNoExterior) return respond ([ success: false, mensaje: "Es necesarion el parametro calle no exterior" ])
        // if (!data.calleNoInterior) return respond ([ success: false, mensaje: "Es necesarion el parametro calle no interior" ])
        if (!data.colonia) return respond ([ success: false, mensaje: "Es necesarion el parametro colonia" ])
        if (!data.municipio) return respond ([ success: false, mensaje: "Es necesarion el parametro municipio" ])
        if (!data.estado) return respond ([ success: false, mensaje: "Es necesarion el parametro estado" ])
        if (!data.pais) return respond ([ success: false, mensaje: "Es necesarion el parametro pais" ])

        return respond ( EmpleadoService.gestionarEmpleado(data, params.uuid) )
        //params proviene de la soicitud http accediendo al valor del parametreo uuid
        // Declaracion de EmpleadoService en el controller y en el service 
    }

    def informacionEmpleado(){
        return respond( EmpleadoService.informacionEmpleado(params.uuid) )
    }

    def listaEmpleados(){
        return respond ( EmpleadoService.listaEmpleados() )
    }

}
