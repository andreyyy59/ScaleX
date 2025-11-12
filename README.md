# 🏍️ ScaleX - Compare Sizes

<div align="center">
  
  ![ScaleX Logo](https://img.shields.io/badge/ScaleX-Compare%20Sizes-red?style=for-the-badge)
  ![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple?style=for-the-badge&logo=kotlin)
  ![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-green?style=for-the-badge&logo=jetpackcompose)
  ![Firebase](https://img.shields.io/badge/Firebase-Ready-orange?style=for-the-badge&logo=firebase)
  
  **La aplicación definitiva para comparar especificaciones de motocicletas** 🏁
  
  [Características](#-características) • [Tecnologías](#-tecnologías) • [Instalación](#-instalación) • [Uso](#-uso) • [Arquitectura](#-arquitectura) • [API](#-api)

</div>

---

## 📱 Sobre el Proyecto

**ScaleX** es una aplicación Android moderna desarrollada con Jetpack Compose que permite a los entusiastas de las motocicletas comparar especificaciones técnicas, dimensiones y características de diferentes modelos de forma visual e intuitiva.

### ✨ Características

- 🔐 **Autenticación completa** - Login y registro de usuarios
- 🔍 **Búsqueda avanzada** - Encuentra motocicletas por marca, modelo o año
- ⚖️ **Comparación detallada** - Compara hasta 2 motocicletas simultáneamente
- 📊 **Especificaciones técnicas** - Más de 30 datos técnicos por motocicleta
- ⭐ **Sistema de favoritos** - Guarda tus motos favoritas
- 🎨 **Diseño moderno** - UI/UX intuitiva con Jetpack Compose
- 🌙 **Tema oscuro** - Diseño elegante con colores contrastantes
- 📡 **Datos en tiempo real** - Integración con API Ninjas Motorcycle Database

---

## 🛠️ Tecnologías

### Core
- **Kotlin** - Lenguaje de programación principal
- **Jetpack Compose** - Framework UI moderno
- **Material Design 3** - Componentes y diseño

### Arquitectura
- **MVVM** (Model-View-ViewModel)
- **Clean Architecture** - Separación de capas
- **StateFlow & Flow** - Manejo reactivo de estados
- **Coroutines** - Programación asíncrona

### Networking
- **Retrofit** - Cliente HTTP
- **Gson** - Serialización JSON
- **OkHttp** - Interceptores y logging

### Firebase (Ready)
- **Firebase Authentication** - Autenticación de usuarios
- **Firebase Firestore** - Base de datos NoSQL

### Navegación
- **Jetpack Navigation Compose** - Navegación declarativa

---

## 📦 Instalación

### Prerequisitos

- Android Studio Hedgehog | 2023.1.1 o superior
- JDK 17
- Android SDK API 24+
- Cuenta en [API Ninjas](https://api-ninjas.com/)

### Pasos

1. **Clona el repositorio**
```bash
   git clone https://github.com/adreyyy59/scalex-app.git
   cd scalex-app
```

2. **Configura la API Key**
   
   En `data/repository/MotorcycleRepository.kt`:
```kotlin
   private val API_KEY = "TU_API_KEY_AQUI"
```
   
   Obtén tu API key gratis en: https://api-ninjas.com/

3. **Configura Firebase (Opcional)**
   
   - Descarga `google-services.json` desde Firebase Console
   - Colócalo en `app/google-services.json`
   - Habilita Email/Password authentication

4. **Sincroniza el proyecto**
```bash
   ./gradlew build
```

5. **Ejecuta la aplicación**
   - Conecta un dispositivo Android o inicia un emulador
   - Haz clic en Run ▶️ en Android Studio

---

## 🎯 Uso

### Pantalla de Login
```
📧 Email: usuario@ejemplo.com
🔒 Contraseña: ******
```

### Pantalla de Registro
```
📧 Email
👤 Nombre de usuario
🔒 Contraseña
🔒 Confirmar contraseña
```

### Comparación de Motocicletas

1. **Selecciona motocicletas**
   - Presiona el botón "COMPARAR" en Home
   - Haz clic en "+ AGREGAR VEHÍCULO"
   - Busca por modelo (ej: "Ninja", "R1", "CBR")
   - Selecciona de los resultados

2. **Visualiza la comparación**
   - Vista lateral y superior de tamaños
   - Especificaciones técnicas completas
   - Motor, transmisión, dimensiones, frenos, etc.

3. **Gestiona favoritos**
   - Agrega motos a favoritos con ⭐
   - Elimina motos con ✖️

---

## 🏗️ Arquitectura
```
me.proyecto.scalex/
│
├── 📂 data/                      → CAPA DE DATOS
│   ├── 📂 model/                 → Modelos de datos (DTOs)
│   ├── 📂 remote/                → Servicios API (Retrofit)
│   └── 📂 repository/            → Repositorios (gestión de datos)
│
├── 📂 domain/                    → LÓGICA DE NEGOCIO (Opcional)
│   ├── 📂 usecase/               → Casos de uso
│   └── 📂 repository/            → Interfaces de repositorios
│
├── 📂 ui/                        → CAPA DE PRESENTACIÓN
│   ├── 📂 theme/                 → Temas y colores
│   ├── 📂 components/            → Componentes reutilizables
│   ├── 📂 screens/               → Pantallas de la app
│   │   ├── 📂 login/
│   │   ├── 📂 register/
│   │   ├── 📂 home/
│   │   ├── 📂 compare/
│   │   ├── 📂 favorites/
│   │   └── 📂 searchsimilar/
│   └── 📂 navigation/            → Navegación entre pantallas
│
├── 📂 util/                      → UTILIDADES
│   ├── Constants.kt
│   └── Extensions.kt
│
└── MainActivity.kt               → PUNTO DE ENTRADA
```

### Flujo de Datos
```
┌─────────────┐         ┌──────────────┐         ┌────────────┐
│    View     │ ◄─────► │  ViewModel   │ ◄─────► │ Repository │
│  (Compose)  │         │   (State)    │         │            │
└─────────────┘         └──────────────┘         └────────────┘
                                                         │
                                                         ▼
                                                  ┌────────────┐
                                                  │  API/Data  │
                                                  │   Source   │
                                                  └────────────┘
```

---

## 🌐 API

### API Ninjas - Motorcycle Database

**Base URL:** `https://api.api-ninjas.com/v1/motorcycles`

**Endpoints utilizados:**
```http
GET /v1/motorcycles?model={model}
GET /v1/motorcycles?make={make}
GET /v1/motorcycles?year={year}
```

**Headers:**
```
X-Api-Key: YOUR_API_KEY
```

**Ejemplo de respuesta:**
```json
{
  "make": "Kawasaki",
  "model": "Ninja 650",
  "year": "2022",
  "type": "Sport",
  "displacement": "649.0 ccm",
  "engine": "Twin, four-stroke",
  "power": "52.3 HP @ 8000 RPM",
  "torque": "56.0 Nm @ 4000 RPM",
  "total_weight": "192.1 kg",
  "total_length": "2055 mm",
  "total_width": "739 mm",
  "total_height": "1146 mm"
}
```

## 🎨 Tema de Colores
```kotlin
val DarkBrown = Color(0xFF3D1410)    // Fondo oscuro
val BrightRed = Color(0xFFE31E24)    // Acento principal
val DarkRed = Color(0xFF8B1E1E)      // Secundario
val White = Color(0xFFFFFFFF)        // Texto principal
val Gray = Color(0xFFCCCCCC)         // Texto secundario
```

---

## 🚀 Roadmap

- [x] Sistema de autenticación
- [x] Búsqueda de motocicletas
- [x] Comparación de especificaciones
- [x] Sistema de favoritos
- [ ] Integración de imágenes reales
- [ ] Comparación visual de tamaños con gráficos
- [ ] Historial de comparaciones
- [ ] Compartir comparaciones
- [ ] Modo offline con caché
- [ ] Filtros avanzados de búsqueda
- [ ] Soporte para más vehículos (autos, camiones)
