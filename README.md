# ⏳ TurnApp: Arquitectura Hexagonal para la Gestión de Turnos

[![Java](https://img.shields.io/badge/Language-Java-007396?style=for-the-badge&logo=java)](https://www.java.com/)
[![Gradle](https://img.shields.io/badge/Build%20Tool-Gradle-02303A?style=for-the-badge&logo=gradle)](https://gradle.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

---

## 🎯 Visión General del Proyecto

**TurnApp** es una solución robusta y desacoplada para la gestión automática de turnos (citas, filas de espera, etc.).

Este proyecto es una demostración práctica de la **Arquitectura Hexagonal (Ports & Adapters)**, asegurando que la lógica de negocio central sea completamente independiente de la tecnología de entrada (UI, API) y de la tecnología de persistencia (Bases de Datos, Mensajería).

Si buscas un ejemplo claro y funcional de cómo construir aplicaciones empresariales escalables y fáciles de mantener, has llegado al repositorio correcto.

## 🚀 Características Principales

* **Gestión de Turnos:** Crea, asigna y finaliza turnos de manera automática.
* **Núcleo de Negocio Puro:** La lógica central opera sin conocimiento de la capa de frameworks o bases de datos.
* **Desacoplamiento Total:** Permite cambiar la base de datos (MongoDB, MySQL, etc.) o la interfaz de usuario (REST, CLI) sin modificar el dominio.
* **Alta Mantenibilidad:** La clara separación de responsabilidades facilita la depuración y la adición de nuevas funcionalidades.

## 🧩 Arquitectura: El Corazón Hexagonal

La Arquitectura Hexagonal se basa en el principio de **Ports & Adapters**. Aquí te explicamos cómo se aplica en este proyecto:

| Componente | Descripción | En el proyecto (Ejemplo) |
| :--- | :--- | :--- |
| **Dominio (Núcleo)** | Contiene las entidades, casos de uso (servicios de aplicación) y puertos de entrada/salida. | `domain/model`, `domain/ports`, `application/services` |
| **Puerto de Entrada (Driving Port)** | Interfaz que define las operaciones que el Core del negocio ofrece. | `TurnServicePort` (Definición de las operaciones de negocio) |
| **Adaptador de Entrada (Driving Adapter)** | Implementa la comunicación externa, invocando el Puerto de Entrada. | **REST Controller** (API expuesta al usuario) |
| **Puerto de Salida (Driven Port)** | Interfaz que el Core necesita para interactuar con la infraestructura externa (BBDD, sistemas de mensajería). | `TurnRepositoryPort` (Definición para guardar/cargar turnos) |
| **Adaptador de Salida (Driven Adapter)** | Implementa la lógica del Puerto de Salida utilizando una tecnología específica. | **JPA/Spring Data Repository** (Conexión a la base de datos) |

## ⚙️ Tecnologías Utilizadas

| Categoría | Tecnología |
| :--- | :--- |
| **Lenguaje** | Java (JDK 17+) |
| **Framework** | Spring Boot (Ideal para el Adaptador de Entrada/Salida) |
| **Build Tool** | Gradle |
| **Base de Datos** | H2 (por defecto, fácilmente configurable a MySQL/PostgreSQL) |

## 🛠️ Configuración y Ejecución

### Requisitos

Asegúrate de tener instalado:

1.  **JDK 17** o superior.
2.  **Gradle** (aunque el proyecto incluye `gradlew` para simplificar).

### Pasos de Instalación

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/SHUNNIORR/turn-app-hexagonal-arch.git](https://github.com/SHUNNIORR/turn-app-hexagonal-arch.git)
    cd turn-app-hexagonal-arch
    ```

2.  **Construir el proyecto:**
    ```bash
    ./gradlew build
    ```

3.  **Ejecutar la aplicación:**
    ```bash
    ./gradlew bootRun
    ```
    La aplicación se iniciará en `http://localhost:8080` (o el puerto configurado en `application.properties`).

## 🗺️ Estructura del Proyecto

La estructura de carpetas refleja la Arquitectura Hexagonal, priorizando el dominio:
