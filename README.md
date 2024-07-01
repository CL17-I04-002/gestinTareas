# Nombre de proyecto

Este es un proyecto desarrollado por ing. Daniel Larín para gestionar tareas usando Spring Boot y Java.

## Descripción

Este proyecto incluye una API RESTful para la gestión de tareas, autenticación JWT incluyendo un firmador y documentación Swagger. Inspirado y aplicando buenas practicas de Clean Code
y SOLID, donde se aplicaron patrones de diseños en los cuales están repository y builder.

## Instalación

A continuación se detalla cómo instalar y configurar el proyecto.

### Prerrequisitos

- Java 17
- Maven
- Git

### Pasos de instalación

1. Clona el repositorio: https://github.com/CL17-I04-002/gestinTareas.git
2. Realice un Reload project para que se descarguen todas las dependencias
3. Compile la solución y listo

### NOTA
Se encuentran datos precargados dentro de la solución por ejemplo en Usuario: username = test, password = password.
El endpoint a acceder para el login es: http:///localhost:8080/api/v1/authenticate usando el método POST y
la URL para acceder a la documentación de Postman es: http://localhost:8080/swagger-ui/index.html