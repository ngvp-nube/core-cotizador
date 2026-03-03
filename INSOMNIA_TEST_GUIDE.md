# Guía de Prueba - API Seguros de Vida con Insomnia

## Configuración Base
- **URL Base**: `http://localhost:8080`
- **Content-Type**: `application/json`

---

## 1. GET - Listar todos los seguros de vida

**Método**: `GET`
**URL**: `http://localhost:8080/api/seguros-vida`

**Headers**:
```
Content-Type: application/json
```

**Respuesta esperada**:
```json
[]
```

---

## 2. GET - Listar seguros con paginación

**Método**: `GET`
**URL**: `http://localhost:8080/api/seguros-vida/paginados?page=0&size=10`

**Headers**:
```
Content-Type: application/json
```

**Respuesta esperada**:
```json
{
  "data": [],
  "currentPage": 0,
  "pageSize": 10,
  "totalElements": 0,
  "totalPages": 0,
  "first": true,
  "last": true
}
```

---

## 3. POST - Crear un seguro de vida

**Método**: `POST`
**URL**: `http://localhost:8080/api/seguros-vida`

**Headers**:
```
Content-Type: application/json
```

**Body**:
```json
{
  "segCodigo": "SV001",
  "segNombre": "Seguro Vida Básico",
  "segPrecio": 15000.0
}
```

**Respuesta esperada**:
```json
{
  "id": 1,
  "segCodigo": "SV001",
  "segNombre": "Seguro Vida Básico",
  "segPrecio": 15000.0,
  "visible": true
}
```

---

## 4. POST - Carga Masiva de seguros

**Método**: `POST`
**URL**: `http://localhost:8080/api/seguros-vida/carga-masiva`

**Headers**:
```
Content-Type: application/json
```

**Body**:
```json
[
  {
    "segCodigo": "SV002",
    "segNombre": "Seguro Vida Premium",
    "segPrecio": 35000.0
  },
  {
    "segCodigo": "SV003",
    "segNombre": "Seguro Vida Familiar",
    "segPrecio": 28000.0
  },
  {
    "segCodigo": "SV004",
    "segNombre": "Seguro Vida Estudiantil",
    "segPrecio": 8000.0
  }
]
```

**Respuesta esperada**:
```json
{
  "mensaje": "Se cargaron 3 seguros de vida."
}
```

---

## 5. PUT - Actualizar un seguro

**Método**: `PUT`
**URL**: `http://localhost:8080/api/seguros-vida/1`

**Headers**:
```
Content-Type: application/json
```

**Body**:
```json
{
  "segCodigo": "SV001",
  "segNombre": "Seguro Vida Básico Actualizado",
  "segPrecio": 18000.0,
  "visible": true
}
```

**Respuesta esperada**:
```json
{
  "id": 1,
  "segCodigo": "SV001",
  "segNombre": "Seguro Vida Básico Actualizado",
  "segPrecio": 18000.0,
  "visible": true
}
```

---

## 6. PATCH - Cambiar visibilidad de un seguro

**Método**: `PATCH`
**URL**: `http://localhost:8080/api/seguros-vida/1/visibilidad`

**Headers**:
```
Content-Type: application/json
```

**Respuesta esperada**:
```json
{
  "id": 1,
  "segCodigo": "SV001",
  "segNombre": "Seguro Vida Básico Actualizado",
  "segPrecio": 18000.0,
  "visible": false
}
```

---

## 7. DELETE - Eliminar un seguro

**Método**: `DELETE`
**URL**: `http://localhost:8080/api/seguros-vida/1`

**Headers**:
```
Content-Type: application/json
```

**Respuesta esperada**: `204 No Content`

---

## Pasos para probar en Insomnia:

1. **Crear nueva colección**: "Seguros de Vida API"
2. **Crear cada request** con la configuración especificada
3. **Orden sugerido de prueba**:
   - GET todos (debería estar vacío)
   - POST crear uno
   - POST carga masiva
   - GET todos (debería mostrar los creados)
   - GET paginados
   - PUT actualizar
   - PATCH cambiar visibilidad
   - DELETE eliminar

## Notas importantes:
- Los campos `segCodigo` deben ser únicos
- `segPrecio` debe ser un número decimal
- `visible` es opcional (por defecto es `true`)
- El servidor debe estar corriendo en `http://localhost:8080`
