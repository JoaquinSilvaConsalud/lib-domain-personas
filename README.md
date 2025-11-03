# lib-domain-personas

Librería compartida del **Dominio Personas** que centraliza las definiciones de clases y mapeos necesarios para mantener consistencia entre aplicaciones del dominio.

Esta librería **no implementa lógica de aplicación ni servicios**, sino que expone **modelos reutilizables** para garantizar igualdad semántica y técnica en todas las capas que interactúan con el dominio.

---

## 📦 Contenido

La librería incluye:

- **Clases de dominio (POJOs enriquecidos)**
    - Entidades, agregados y value objects con lógica de negocio.
    - Constructores específicos y validaciones en setters.
    - Tests unitarios asociados para validar invariantes de dominio.

- **Clases de persistencia en MongoDB**
    - Documentos con anotaciones de Spring Data Mongo.
    - Getters y setters simples.
    - Orientadas a almacenamiento y consultas, sin lógica de negocio.

- **Mappers de conversión**
    - Transformación entre clases de dominio ↔ documentos Mongo.
    - Implementados con librerías como MapStruct para garantizar mantenibilidad y trazabilidad.

- **Clases de entidad JPA (opcional)**
    - Definiciones de entidades para uso con bases relacionales (Oracle, PostgreSQL).
    - Acompañadas de mappers hacia/desde el modelo de dominio.

---

## 🎯 Objetivo

El propósito de `lib-domain-personas` es:

- Mantener **una única fuente de verdad** para el modelo de datos del dominio Personas.
- Evitar la duplicación y divergencia entre aplicaciones.
- Asegurar que todas las aplicaciones que usen este dominio hablen el **mismo lenguaje** (ubiquitous language en DDD).
- Facilitar la evolución futura del dominio sin romper contratos internos.

---

## 🚫 Qué NO contiene

- Lógica de aplicación o casos de uso.
- Endpoints REST, controladores o servicios.
- Configuración de infraestructura.
- Dependencias a frameworks de presentación o integración.

---

## 🛠 Uso esperado

1. Agregar la dependencia en tu `build.gradle` o `pom.xml`.
2. Importar las clases de dominio para manipular lógica de negocio.
3. Usar las clases de documento/entidad según la base de datos correspondiente.
4. Aplicar los mappers provistos para convertir entre representaciones.

Ejemplo (pseudo-código):

```java
Persona persona = new Persona("Juan", "Pérez");
PersonaDocument doc = personaMapper.toDocument(persona);
mongoRepository.save(doc);
