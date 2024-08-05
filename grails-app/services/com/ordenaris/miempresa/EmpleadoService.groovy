package com.ordenaris.miempresa

import grails.gorm.transactions.Transactional

@Transactional
class EmpleadoService {

    def gestionarEmpleado(data, uuid = null) {

        Empleado.withTransaction { tStatus -> // Asignacion se sesion para hacer un trgistro en la bd
            try {

                def empleado = Empleado.findByUuid( uuid )
                if( uuid && !empleado ) return [success: false, message: "Empleado no encontrado."]
                if( !empleado ) empleado = new Empleado()

                def jefeEmpleado = Empleado.findByUuid(data.uuid) // todo que viene de la data tiene que recibir un valor, en este caso uuid
                if( data.uuid && !jefeEmpleado ) return [success: false, message: "Jefe de empleado no encontrado."]
                empleado.claveJefeInmediato = jefeEmpleado
                
            // objeto.atributo = data.atributo/valor
                empleado.nombres = data.nombres   
                empleado.apellidoPaterno = data.apellidoPaterno 
                empleado.apellidoMaterno = data.apellidoMaterno
                empleado.curp = data.curp
                empleado.puestoEmpresa = data.puestoEmpresa
                empleado.calleNoExterior = data.calleNoExterior
                empleado.calleNoInterior = data.calleNoInterior
                empleado.colonia = data.colonia
                empleado.municipio = data.municipio
                empleado.estado = data.estado
                empleado.pais = data.pais
                empleado.save(
                    flush: true, //validar
                    failOnError : true // se ocupa por defecto
                ) println( empleado.properties ) // properties : vinculacion de datos a traves de paramatros (data)
                return [ success: true, data : empleado.uuid] //
            } catch(e) {
                tStatus.setRollbackOnly()
                println " ${new Date()} | Empleado Service | Gestionar Registro o Edicion Empleado : ${e.getMessage()}"
                return [ success : false, mensaje : "Ocurrio un error, contacte al administrador" ]
            }
        }
    }

    def informacionEmpleado(uuid) {
        try {
            def empleado = Empleado.findByUuid(uuid)
            def informacion = [
                nombres : empleado.nombres,
                apellidoPaterno : empleado.apellidoPaterno,
                apellidoMaterno : empleado.apellidoMaterno,
                curp : empleado.curp,
                puestoEmpresa : empleado.puestoEmpresa,
                claveCurp : empleado.claveCurp,
                direccion : [
                    calleNoExterior : empleado.calleNoExterior,
                    calleNoInterior : empleado.calleNoInterior,
                    colonia : empleado.colonia,
                    municipio : empleado.municipio,
                    estado : empleado.estado,
                    pais : empleado.pais
                ]
            ]
                return [ success : true, data : informacion]
        } catch (e){
            println " Empleado Service | Informacion Empleado Error : ${e.getMessage}"
            return [ success : false, mensaje : " Ocurrio un error, contacte al administrador "]
        }
    }

    def obtenerEmpleados(empleado, listaEmpleados = []){  // esta es una funcion de recursiva una funcion que se llama asi misma
        try {
            def subordinados = Empleado.findAllByClaveJefeInmediato(empleado)
            // validar la lista que este llena
            if( subordinados) {
            // listaEmpleados = asigancion de valor subordinado
                 subordinados.each { _empleado -> // _ : indentica el nombre sin necesidad de modificar _empleado= subordinado
                    listaEmpleados <<  [ //<< asignacion de valores a la listaEmpleado
                        nombres : _empleado.nombres,
                        apellidoPaterno : _empleado.apellidoPaterno,
                        apellidoMaterno : _empleado.apellidoMaterno,
                        curp : _empleado.curp,
                        puestoEmpresa : _empleado.puestoEmpresa,
                        claveCurp : _empleado.claveCurp,
                        direccion : [
                            calleNoExterior : _empleado.calleNoExterior,
                            calleNoInterior : _empleado.calleNoInterior,
                            colonia : _empleado.colonia,
                            municipio : _empleado.municipio,
                            estado : _empleado.estado,
                            pais : _empleado.pais
                        ],
                        //asignaciion de subornidados 
                        subordinados : obtenerEmpleados(_empleado) 
                    ]
                }
            }else{ 
                return listaEmpleados 
            }
            return listaEmpleados
        } catch(e) {
                println " ${new Date()} | Empleado Service | Funcion Obtner Empleado : ${e.getMessage()}"
                return null 
        }
    }

    def listaEmpleados(){
        try {
            def lista_Empleados = Empleado.getAll().collect { empleado ->
                return [
                nombres : empleado.nombres,
                apellidoPaterno : empleado.apellidoPaterno,
                apellidoMaterno : empleado.apellidoMaterno,
                curp : empleado.curp,
                puestoEmpresa : empleado.puestoEmpresa,
                claveCurp : empleado.claveCurp,
                direccion : [
                    calleNoExterior : empleado.calleNoExterior,
                    calleNoInterior : empleado.calleNoInterior,
                    colonia : empleado.colonia,
                    municipio : empleado.municipio,
                    estado : empleado.estado,
                    pais : empleado.pais
                    ],
                    subordinados : obtenerEmpleados (empleado)
                ]
            }
            return [ success : true, empleados : lista_Empleados ]
        }catch(e) {
            println " Empleado Service | Listar Empleados Error : ${e.getMessage()}"
            return [ success : false, mensaje : " Ocurrio un error, contacte al administrador "]
        }
    }
}