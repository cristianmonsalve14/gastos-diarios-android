# 💰 GastosApp - Control de Gastos Personales

Aplicación móvil Android para el control y gestión de gastos personales desarrollada con Jetpack Compose y Kotlin.

## 👥 Equipo de Desarrollo

- **Cristián Monsalve** - Líder del proyecto
- **Rolando Lillo**
- **Héctor Olivares**

**Institución:** DuocUC - Sede Valparaíso  
**Curso:** Aplicaciones Móviles - Semestre 4 (2025)  
**Repositorio:** https://github.com/cristianmonsalve14/gastos-diarios-android

---

## 🚀 Inicio Rápido

### Prerrequisitos

- **Android Studio** (última versión recomendada)
- **JDK 17** o superior
- **Git** instalado
- **SDK Android** versión 33+

### Clonar el Repositorio

```bash
# Clonar el proyecto
git clone https://github.com/cristianmonsalve14/gastos-diarios-android.git

# Entrar al directorio
cd gastos-diarios-android

# Abrir en Android Studio
# File > Open > Seleccionar la carpeta del proyecto
```

### Configuración Inicial

1. Abrir el proyecto en **Android Studio**
2. Esperar a que Gradle sincronice las dependencias
3. **Build > Clean Project**
4. **Build > Rebuild Project**
5. Ejecutar en emulador o dispositivo físico

---

## 📁 Estructura del Proyecto

```
MyAPP/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/cl/duoc/valparaiso/gastosapp/
│   │   │   │   ├── model/              # Modelos de datos
│   │   │   │   │   └── Gasto.kt
│   │   │   │   ├── navigation/         # Sistema de navegación
│   │   │   │   │   ├── Routes.kt
│   │   │   │   │   └── AppNavigation.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/        # Pantallas de la app
│   │   │   │   │   │   ├── DashboardScreen.kt
│   │   │   │   │   │   ├── AgregarGastoScreen.kt
│   │   │   │   │   │   ├── HistorialScreen.kt
│   │   │   │   │   │   └── ConfiguracionScreen.kt
│   │   │   │   │   └── theme/          # Tema y colores
│   │   │   │   ├── viewmodel/          # Lógica de negocio
│   │   │   │   │   └── GastosViewModel.kt
│   │   │   │   └── MainActivity.kt
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle.kts
│   └── build.gradle.kts
└── README.md
```

---

## 🌿 Flujo de Trabajo con Git

### Ramas del Proyecto

- **`main`** → Código en producción (protegida, solo merge con PR)
- **`desarrollo`** → Rama principal de desarrollo
- **`feature/nombre-funcionalidad`** → Ramas individuales por funcionalidad

### Comandos Esenciales

#### 1️⃣ Comenzar a Trabajar

```bash
# Cambiarse a desarrollo
git checkout desarrollo

# Actualizar con los últimos cambios
git pull origin desarrollo

# Crear tu rama personal para la funcionalidad
git checkout -b feature/tu-nombre-funcionalidad
```

#### 2️⃣ Hacer Cambios

```bash
# Ver archivos modificados
git status

# Agregar todos los cambios
git add .

# Hacer commit con mensaje descriptivo
git commit -m "feat: Agrego funcionalidad de exportar datos"

# Subir tu rama al repositorio
git push origin feature/tu-nombre-funcionalidad
```

#### 3️⃣ Crear Pull Request

1. Ve a: https://github.com/cristianmonsalve14/gastos-diarios-android
2. Click en **"Pull requests"** → **"New pull request"**
3. Selecciona: `feature/tu-rama` → `desarrollo`
4. Escribe una descripción clara de los cambios
5. Asigna revisores (compañeros del equipo)
6. Click **"Create pull request"**

#### 4️⃣ Actualizar tu Rama con Desarrollo

```bash
# Cambiarse a desarrollo
git checkout desarrollo

# Traer últimos cambios
git pull origin desarrollo

# Volver a tu rama
git checkout feature/tu-nombre-funcionalidad

# Traer cambios de desarrollo a tu rama
git merge desarrollo

# Si hay conflictos, resolverlos manualmente y luego:
git add .
git commit -m "merge: Resuelvo conflictos con desarrollo"
git push origin feature/tu-nombre-funcionalidad
```

---

## 📝 Convenciones del Proyecto

### Mensajes de Commit

Usar prefijos estándar:

- `feat:` Nueva funcionalidad
- `fix:` Corrección de bugs
- `refactor:` Refactorización de código
- `docs:` Cambios en documentación
- `style:` Formato, espacios (sin cambios de lógica)
- `test:` Agregar o modificar tests

**Ejemplos:**
```bash
git commit -m "feat: Agrego pantalla de gráficos estadísticos"
git commit -m "fix: Corrijo validación de monto en formulario"
git commit -m "refactor: Mejoro estructura de ViewModel"
```

### Nombres de Ramas

```bash
feature/nombre-descriptivo    # Nueva funcionalidad
fix/descripcion-bug          # Corrección de error
refactor/area-codigo         # Refactorización
```

---

## 🎯 Funcionalidades Actuales

✅ **Dashboard** - Resumen de gastos con estadísticas  
✅ **Agregar Gastos** - Formulario validado con categorías  
✅ **Historial** - Lista completa con filtros y búsqueda  
✅ **Configuración** - Preferencias de usuario  
✅ **Navegación** - Sistema completo entre pantallas  
✅ **Validaciones** - Formularios con mensajes visuales  
✅ **MVVM** - Arquitectura con ViewModel y StateFlow

---

## 🚧 Funcionalidades en Desarrollo

🔄 **Persistencia Room** - Base de datos local  
🔄 **GPS/Ubicación** - Guardar ubicación de gastos  
🔄 **Gráficos** - Visualización estadística avanzada  
🔄 **Exportar Datos** - CSV/PDF de reportes

---

## 🛠️ Tecnologías Utilizadas

- **Kotlin** 2.0.21
- **Jetpack Compose** - UI declarativa
- **Material 3** - Componentes de diseño
- **Navigation Compose** - Navegación entre pantallas
- **ViewModel + StateFlow** - Gestión de estado
- **Gradle Kotlin DSL** - Build system

---

## 📱 Capturas de Pantalla

*(Agregar capturas cuando el equipo lo considere necesario)*

---

## 🤝 Contribuir al Proyecto

### Antes de Empezar

1. Lee este README completo
2. Asegúrate de tener acceso como colaborador
3. Configura Git con tu nombre y email:
   ```bash
   git config --global user.name "Tu Nombre"
   git config --global user.email "tu.email@ejemplo.com"
   ```

### Pasos para Contribuir

1. **Actualiza** siempre antes de empezar: `git pull origin desarrollo`
2. **Crea una rama** para tu funcionalidad
3. **Trabaja** en tu rama local
4. **Prueba** que todo funcione antes de hacer commit
5. **Haz commit** con mensajes claros
6. **Sube** tu rama al repositorio
7. **Crea Pull Request** para revisión del equipo
8. **Espera aprobación** antes de hacer merge

### Reglas del Equipo

- ❌ **NUNCA** hacer push directo a `main`
- ❌ **NUNCA** hacer merge sin Pull Request
- ✅ **SIEMPRE** actualizar antes de empezar a trabajar
- ✅ **SIEMPRE** probar el código antes de hacer commit
- ✅ **SIEMPRE** resolver conflictos antes de hacer PR
- ✅ **SIEMPRE** revisar el código de tus compañeros en los PR

---

## 🐛 Reporte de Errores

Si encuentras un bug:

1. Verifica que no esté ya reportado en **Issues**
2. Crea un nuevo **Issue** con:
    - Descripción clara del problema
    - Pasos para reproducirlo
    - Comportamiento esperado vs actual
    - Screenshots si es posible

---

## 📞 Contacto

**Cristián Monsalve** - Líder del Proyecto  
GitHub: [@cristianmonsalve14](https://github.com/cristianmonsalve14)

---

## 📄 Licencia

Proyecto académico - DuocUC 2025

---

## 🎓 Créditos

Desarrollado por estudiantes de **Aplicaciones Móviles**  
**DuocUC - Sede Valparaíso**  
**Semestre 4 - 2025**

---

**¡Gracias por contribuir a GastosApp! 🚀**