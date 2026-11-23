# MediTrack Android

**Aplicación móvil para la gestión y el seguimiento de la atención médica de pacientes.**

MediTrack es una aplicación móvil desarrollada para facilitar la gestión de información médica, la comunicación con los servicios del sistema y el seguimiento de la atención de los pacientes.

El proyecto forma parte de una solución integral compuesta por una aplicación Android, un backend REST y una base de datos SQL Server.

---

## 1. Descripción del proyecto

MediTrack busca centralizar las principales actividades relacionadas con la atención médica mediante una aplicación móvil intuitiva y organizada por módulos.

La aplicación contempla distintos perfiles de usuario, entre ellos pacientes, tutores, médicos y personal de enfermería, con funcionalidades orientadas a sus respectivas responsabilidades.

El objetivo es facilitar el acceso a la información, mejorar el seguimiento de los pacientes y contribuir a una gestión más organizada de los procesos de atención médica.

## 2. Objetivos

- Facilitar el acceso de los pacientes a los servicios de MediTrack.
- Gestionar el registro y la autenticación de usuarios.
- Facilitar la consulta y gestión de citas médicas.
- Permitir la vinculación entre pacientes y tutores.
- Centralizar la información relacionada con tratamientos y medicamentos.
- Incorporar herramientas de seguimiento y recordatorios médicos.
- Proporcionar una interfaz móvil organizada y accesible.

## 3. Tecnologías utilizadas

| Tecnología | Aplicación |
|---|---|
| Java | Lenguaje de programación |
| Android Studio | Entorno de desarrollo |
| Android SDK | Desarrollo de la aplicación móvil |
| Retrofit 2 | Comunicación con el backend REST |
| OkHttp | Cliente HTTP |
| Gson | Serialización y deserialización JSON |
| Gradle | Gestión de dependencias y compilación |
| Git y GitHub | Control de versiones |

## 4. Arquitectura del sistema

MediTrack utiliza una arquitectura dividida en tres componentes independientes:

```text
┌──────────────────────────────┐
│       MEDITrack Android      │
│       Aplicación móvil       │
│           Java               │
└──────────────┬───────────────┘
               │
               │ HTTP / JSON
               ▼
┌──────────────────────────────┐
│       MEDITrack Backend      │
│         API REST             │
│       Java + Quarkus         │
└──────────────┬───────────────┘
               │
               │ JDBC
               ▼
┌──────────────────────────────┐
│      MEDITrack Database      │
│         SQL Server           │
└──────────────────────────────┘
```

Cada componente tiene una responsabilidad específica:

- **Android:** interfaz de usuario e interacción con los usuarios.
- **Backend:** procesamiento de solicitudes, autenticación y reglas de negocio.
- **Base de datos:** almacenamiento y administración de la información.

## 5. Módulos de la aplicación

La aplicación contempla los siguientes módulos:

| Módulo | Descripción |
|---|---|
| Autenticación | Inicio de sesión y gestión de acceso. |
| Registro | Registro de pacientes y tutores. |
| Citas médicas | Consulta y gestión de citas. |
| Vinculación familiar | Vinculación de tutores con pacientes mediante códigos y QR. |
| Tratamientos | Interfaz de gestión de tratamientos médicos. |
| Medicamentos | Consulta y seguimiento de medicamentos. |
| Recordatorios | Configuración de recordatorios relacionados con la salud. |
| Historial médico | Consulta de información médica del paciente. |

**Estado de desarrollo:** los módulos no tienen todos el mismo nivel de implementación. La autenticación, el registro y las citas cuentan con integración con el backend; otros módulos todavía requieren completar su lógica, persistencia e integración.

## 6. Estructura general del proyecto

La organización del código contempla paquetes destinados a separar las responsabilidades de la aplicación:

```text
app/
└── src/
    └── main/
        ├── java/
        │   └── com.example.meditrack2/
        │       ├── Citas/
        │       ├── Entity/
        │       ├── Network/
        │       └── Service/
        │
        └── res/
            ├── drawable/
            ├── layout/
            ├── mipmap/
            └── values/
```

Los paquetes principales cumplen las siguientes funciones:

- **Citas:** actividades e interfaces relacionadas con la atención y los módulos asociados.
- **Entity:** modelos de datos y objetos de transferencia.
- **Network:** configuración de comunicación HTTP, API y gestión de sesión.
- **Service:** componentes principales y actividades de navegación de la aplicación.

## 7. Requisitos de desarrollo

Para trabajar con el proyecto se necesita:

- Windows, Linux o macOS.
- Android Studio.
- JDK 21.
- Android SDK compatible con la configuración del proyecto.
- Acceso al repositorio del backend para probar las funcionalidades integradas.

## 8. Instalación y ejecución

### Clonar el repositorio

```bash
git clone https://github.com/DIEGOAP232/meditrack-android.git
```

### Abrir el proyecto

1. Iniciar Android Studio.
2. Seleccionar **Open**.
3. Ubicar la carpeta `meditrack-android`.
4. Esperar la sincronización de Gradle.
5. Configurar un emulador Android o conectar un dispositivo físico.
6. Ejecutar la aplicación.

### Conexión con el backend

La aplicación utiliza Retrofit para comunicarse con los servicios REST de MediTrack.

Para las pruebas locales realizadas con el emulador y el backend ejecutándose en el equipo de desarrollo, se utiliza la configuración de conexión correspondiente al entorno local.

La dirección del servidor debe ajustarse según el entorno de ejecución.

## 9. Repositorios relacionados

El proyecto se organiza en repositorios independientes:

- **Android:** https://github.com/DIEGOAP232/meditrack-android
- **Backend:** https://github.com/DIEGOAP232/meditrack-backend
- **Database:** repositorio independiente previsto para scripts y documentación técnica, sin información clínica real.

## 10. Estado del proyecto

MediTrack se encuentra en desarrollo progresivo.

Las funcionalidades integradas se irán ampliando y validando conforme avance la implementación de los módulos móviles y su comunicación con el backend.

## 11. Seguridad y privacidad

Debido a que MediTrack contempla información relacionada con la salud, el proyecto considera la importancia de:

- Proteger la información personal de los pacientes.
- Gestionar el acceso según los roles de usuario.
- Evitar la exposición de credenciales y claves privadas.
- Mantener separadas las responsabilidades entre frontend, backend y base de datos.

No se deben publicar datos médicos reales, credenciales ni archivos de claves privadas en el repositorio.

---

**MediTrack — Tecnología orientada a una atención médica más organizada.**
