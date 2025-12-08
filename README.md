# 💰 GastosApp - Aplicación de Gestión de Gastos

**Versión:** 1.0.0 COMPLETA ✅  
**Plataforma:** Android (Jetpack Compose)  
**Estado:** 100% FUNCIONAL - Listo para Play Store

Aplicación móvil para Android desarrollada como proyecto integrador. 
Permite a los usuarios registrar y llevar un control de sus gastos personales 
de una manera sencilla y eficiente, con captura de comprobantes mediante
cámara nativa, integración con microservicios backend y datos meteorológicos en 
tiempo real.

---

## 👥 Autores

Este proyecto fue desarrollado por:

*   **Cristian Monsalve** - Desarrollador y Líder Técnico
*   **Héctor Olivares** - Colaborador
*   **Rolando Lillo** - Colaborador

**Institución:** DuocUC - Sede Valparaíso  
**Curso:** Aplicaciones Móviles - Semestre 4 (2025)  
**Período:** Diciembre 2025

---

## 🎯 Estado del Proyecto - 100% COMPLETADO ✅

### 📊 Requisitos Implementados

#### ✅ IE 3.1.1 - Diseño y Navegación (15%)
- Dashboard con resumen de gastos y clima en tiempo real
- Formulario completo para crear/editar gastos
- Historial de gastos con eliminación individual
- Pantalla de cámara para capturar comprobantes
- Navegación fluida sin errores o cierres inesperados
- Material Design 3 + Jetpack Compose

#### ✅ IE 3.1.2 - Microservicios (15%)
- Backend Spring Boot en puerto 8081
- Base de datos MySQL con tablas normalizadas
- Endpoints: GET, POST, PUT, DELETE completamente funcionales
- CRUD de gastos con sincronización

#### ✅ IE 3.1.3 - Integración HTTP (15%)
- Retrofit 2.11.0 para comunicación
- Sincronización en tiempo real
- Manejo robusto de errores
- Arquitectura Repository para acceso a datos
- Coroutines para operaciones asincrónicas

#### ✅ IE 3.1.4 - API Externa (15%)
- Open-Meteo para datos meteorológicos (sin API key)
- Temperatura, descripción de clima, humedad, velocidad del viento
- 5 ciudades configurables: Santiago, Valparaiso, Concepción, La Serena, Punta Arenas
- Actualización automática al iniciar la app
- Card dedicada en Dashboard

#### ✅ IE 3.2.1 - Pruebas Unitarias (15%)
- **13/13 Tests PASSING ✅**
- GastoRepositoryTest: 5 tests (creación, validación, precisión decimal)
- GastoTest: 7 tests (modelos, emojis, igualdad)
- Cobertura de lógica crítica
- JUnit 4.13.2 + Mockito 5.2.0

#### ✅ IE 3.3.1 - APK Firmado (10%)
- Keystore: GastosApp.jks (25 años de validez)
- Alias: key0
- Ubicación: app/release/app-release.apk
- Firmas V1 + V2 (máxima compatibilidad)
- Listo para Play Store

---

## ✨ Funcionalidades Detalladas

### 📝 Registro de Gastos
- Formulario completo con validaciones
    - Monto (validación de número positivo)
    - Descripción (validación de longitud mínima)
    - Categoría (selector desplegable con 8 categorías)
    - Fecha automática del sistema
- Sincronización con backend en tiempo real

### 📊 Dashboard Principal
- Resumen visual de gastos con estadísticas
    - Total gastado en el período
    - Promedio diario de gastos
    - Cantidad de transacciones
    - Gastos por categoría con emojis
    - Lista de gastos recientes (últimos 5)
- **Card de Clima Integrado**
    - Temperatura actual
    - Descripción del clima con emoji
    - Humedad relativa
    - Velocidad del viento
    - Actualización automática

### 📸 Captura de Comprobantes
- Integración completa con cámara nativa (CameraX)
- Solicitud automática de permisos de cámara
- Preview de foto capturada antes de guardar
- Almacenamiento seguro de imágenes
- Indicador visual "📸 Comprobante adjunto" en gastos

### 🖼️ Gestión de Comprobantes
- Ver foto en pantalla completa al hacer clic
- Opción de **Eliminar** comprobante del gasto
- Opción de **Recapturar** foto sin perder el gasto
- Visualización de información del gasto en diálogo

### 📋 Historial de Gastos
- Lista completa ordenada por fecha (más recientes primero)
- Resumen de estadísticas (total, cantidad, promedio)
- Eliminación individual de gastos
- Indicador de comprobantes adjuntos
- Sincronización automática con backend

### ⚙️ Configuración
- Pantalla de preferencias (base para futuras configuraciones)

### 🎨 Interfaz Moderna
- Desarrollada completamente con Jetpack Compose
- Material Design 3
- Tema adaptable
- Componentes reutilizables
- Animaciones y transiciones suaves

### 🧭 Navegación Intuitiva
- `NavHost` con rutas tipadas
- Navegación fluida entre pantallas
- Botón FAB (+) para agregar gastos
- Barra de navegación inferior

### 🏗️ Arquitectura MVVM
- Separación clara de responsabilidades
- ViewModel para gestión de estado
- StateFlow para reactividad
- Modelos de datos bien estructurados
- Validaciones centralizadas

---

## 🛠️ Tecnologías Utilizadas

### Versiones Utilizadas
*   **Kotlin:** 2.0.21
*   **Android SDK:** Versión 33+
*   **JDK:** 17+
*   **Gradle:** 8.x

### Librerías Principales - Frontend
*   **Jetpack Compose** - UI declarativa moderna
*   **Material 3** - Componentes de diseño
*   **Lifecycle & ViewModel** - Gestión del estado y ciclo de vida
*   **Navigation Compose** - Navegación entre pantallas
*   **Coil** (v2.4.0) - Carga y visualización de imágenes
*   **CameraX** (v1.3.0) - Captura de fotos con cámara nativa
    - `camera-core`
    - `camera-camera2`
    - `camera-lifecycle`
    - `camera-view`
*   **Retrofit** (v2.11.0) - Cliente HTTP
*   **Gson** - Serialización JSON
*   **Coroutines** (v1.7.3) - Programación asincrónica

### Librerías de Testing
*   **JUnit** (v4.13.2) - Framework de testing
*   **Mockito** (v5.2.0) - Mocking de objetos
*   **kotlinx-coroutines-test** (v1.7.3) - Testing de coroutines
*   **androidx.arch.core:core-testing** (v2.2.0) - Testing de ViewModel

### Backend
*   **Spring Boot** (v3.x)
*   **MySQL** 
*   **JPA/Hibernate**
*   **Spring Data JPA**

---

## 📦 Requisitos Previos

### Software Requerido
- **Android Studio:** Jellyfish o superior
- **Java Development Kit (JDK):** 17+
- **Android SDK:** Nivel 33+ (Android 13+)
- **Gradle:** 8.x (incluido en Android Studio)
- **Git:** Para control de versiones

### Para Backend (Opcional)
- **Java 17+**
- **MySQL**
- **Maven 3.8+** o **Gradle 8.x**

### Permisos Requeridos
- Cámara (para capturar comprobantes)
- Lectura/Escritura de almacenamiento
- Acceso a Internet

---

## 🚀 Cómo Compilar y Ejecutar

### 1. Clonar el Repositorio
```bash
git clone https://github.com/cristianmonsalve14/gastos-diarios-android.git
cd GastosApp3

    