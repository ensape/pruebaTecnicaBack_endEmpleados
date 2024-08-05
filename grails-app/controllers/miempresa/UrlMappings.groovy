package miempresa

class UrlMappings {

    static mappings = {

        group "/empleado",{
            post "/registrar-empleado" (
                controller: "empleado",  
                action: "gestionarEmpleado"
            )
            group "/$uuid",{
                put "/edicion-empleado" (
                    controller:"empleado",
                    action: "gestionarEmpleado"
                )
                get "/informacion-empleado" (
                    controller: "empleado",
                    action: "informacionEmpleado"
                )
            }
            get"/lista-empleados" (
                controller : "empleado",
                action : "listaEmpleados"
            )
        }

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
