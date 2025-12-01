# 💰 GastosApp - Aplicación de Seguimiento de Gastos

Aplicación móvil para Android desarrollada como proyecto de estudio. Permite a los usuarios registrar y llevar un control de sus gastos personales de una manera sencilla y eficiente, con captura de comprobantes mediante cámara nativa.

---

## ✒️ Autores

Este proyecto fue desarrollado por los siguientes estudiantes:

*   **Héctor Olivares**
*   **Rolando Lillo**
*   **Cristian Monsalve** - Líder técnico

**Institución:** DuocUC - Sede Valparaíso  
**Curso:** Aplicaciones Móviles - Semestre 4 (2025)  
**Repositorio:** https://github.com/cristianmonsalve14/gastos-diarios-android

---

## ✨ Funcionalidades Implementadas

La aplicación cuenta actualmente con las siguientes características:

### ✅ Funcionalidades Completadas

*   **📝 Registro de Gastos:** Formulario completo para añadir nuevos gastos con validación de campos
    - Monto (validación de número positivo)
    - Descripción (validación de longitud mínima)
    - Categoría (selector desplegable con 8 categorías)
    - Fecha automática del sistema

*   **📊 Dashboard Principal:** Resumen visual de gastos con estadísticas
    - Total gastado en el período
    - Promedio diario de gastos
    - Cantidad de transacciones
    - Gastos por categoría
    - Lista de gastos recientes (últimos 5)

*   **📸 Captura de Comprobantes (NUEVO):** Integración completa con cámara nativa
    - Captura de fotos usando CameraX
    - Solicitud automática de permisos de cámara
    - Preview de foto capturada antes de guardar
    - Almacenamiento seguro de imágenes
    - Indicador visual "📸 Comprobante adjunto" en gastos

*   **🖼️ Gestión de Comprobantes (NUEVO):** Visualización y edición de fotos
    - Ver foto en pantalla completa al hacer clic
    - **Eliminar** comprobante del gasto
    - **Recapturar** foto sin perder el gasto
    - Información del gasto en el diálogo

*   **📋 Historial de Gastos:** Vista completa de todos los gastos
    - Lista completa ordenada por fecha (más recientes primero)
    - Resumen de estadísticas (total, cantidad, promedio)
    - Eliminación individual de gastos
    - Indicador de comprobantes adjuntos

*   **⚙️ Configuración:** Pantalla de preferencias (base para futuras configuraciones)

*   **🎨 Interfaz Moderna:** Desarrollada completamente con Jetpack Compose
    - Material Design 3
    - Tema adaptable
    - Componentes reutilizables
    - Animaciones y transiciones suaves

*   **🧭 Navegación Intuitiva:** Sistema de navegación completo
    - `NavHost` con rutas tipadas
    - Navegación fluida entre pantallas
    - Botón FAB (+) para agregar gastos
    - Barra de navegación inferior

*   **🏗️ Arquitectura MVVM:** Separación clara de responsabilidades
    - ViewModel para gestión de estado
    - StateFlow para reactividad
    - Modelos de datos bien estructurados
    - Validaciones centralizadas

---

## 🛠️ Herramientas y Tecnologías

### Versiones Utilizadas
*   **Kotlin:** 2.0.21
*   **Android SDK:** Versión 33+
*   **JDK:** 17+

### Librerías Principales
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

### Próximamente
*   **Room** - Persistencia de datos en base de datos local
*   **GPS/Ubicación** - Guardar ubicación de gastos
*   **Gráficos** - Visualización estadística avanzada
*   **Exportar Datos** - CSV/PDF de reportes

---

## 📁 Estructura del Proyecto

    