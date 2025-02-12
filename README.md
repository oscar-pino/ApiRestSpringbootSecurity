# API de Librería con Spring Boot

## Descripción

Esta **API** fue desarrollada utilizando **Java** y el framework **Spring Boot**. Está basada en el modelo de una librería como ejemplo y está protegida mediante **Spring Security**.

## Datos importantes del `pom.xml`

- **Spring Boot**: 3.3.5
- **Java**: 17
- **Spring Security Config**: 6.3.4
- **MySQL Connector**: 8.0.33

## Funcionalidades de la API

La API cuenta con datos registrados al inicio del proyecto a modo de pruebas mediante un **`@Bean CommandLineRunner`**, creado en el archivo `ApiSecurityApplication`, el cual contiene la anotación `@SpringBootApplication`.

## Permisos

Los permisos creados para trabajar el proyecto son los siguientes:

- **CREATE**
- **READ**
- **UPDATE**
- **DELETE**

## Roles

Los roles definidos en la API son los siguientes:

- **ADMIN** => (CREATE, READ, UPDATE, DELETE)
- **DEVELOPER** => (CREATE, READ, UPDATE)
- **INVITED** => READ
- **USER** => (CREATE, READ, UPDATE)

Un **rol** puede contener varios **permisos**, y un **permiso** puede estar en varios **roles**. Por lo tanto, se crea una tabla intermedia llamada **users_roles**.

## Usuarios

Se crean los siguientes usuarios iniciales en la API:

- **Oscar** - Rol: ADMIN
- **Luis** - Rol: DEVELOPER
- **Jose** - Rol: INVITED
- **Pedro** - Rol: USER

**Contraseña de todos los usuarios**: `1234`
