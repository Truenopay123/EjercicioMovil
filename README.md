# Café Console (Java)

Consola en Java que registra usuarios, lista cafés y calcula precios con variabilidad (tamaños, toppings) aplicando patrones: Singleton (`Catalog`, `UserRegistry`), Proxy (`PricingServiceProxy`) y Mediator (`CafeMediator`).

## Requisitos
- JDK 17+ (o compatible con su entorno)
- VS Code con Extensiones de Java (opcional para ejecutar/debug)

## Ejecutar en Windows (CMD/PowerShell)

Compilar:

```powershell
# Desde la carpeta del proyecto (c:\EjercicioMovil)
# Compila todas las clases a la carpeta bin
Remove-Item -Recurse -Force bin -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Path bin | Out-Null
javac -d bin src\com\cafe\**\*.java
```

Ejecutar:

```powershell
java -cp bin com.cafe.app.App
```

## Notas de diseño
- **Singleton**:
  - `Catalog`: catálogo único de cafés, tamaños y toppings.
  - `UserRegistry`: registro único de usuarios.
- **Proxy**:
  - `PricingServiceProxy`: protege/ajusta el cálculo (10% descuento si hay ≥3 toppings).
- **Mediator**:
  - `CafeMediator`: coordina registro, catálogo, creación de órdenes y precios.

## Flujo
1. Registrar usuario.
2. Ver catálogo.
3. Crear orden: elegir café, tamaño y toppings.
4. Ver total calculado y resumen de orden.
