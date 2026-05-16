package Proyecto.controlador;

import Proyecto.modelo.Inventario;
import Proyecto.modelo.Producto;
import Proyecto.modelo.Usuario;
import Proyecto.modelo.Empleado;
import Proyecto.modelo.Venta; // Importación corregida
import Proyecto.persistencia.ManejadorArchivos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainPruebas {

    public static void main(String[] args) {
        System.out.println("=== SISTEMA TUBOTECH: MÓDULO DE VENTAS ===");

        // 1. Inicializamos el inventario (Carga automáticamente los datos previos de inventario.txt)
        System.out.println("\n--- [1] Inicializando Motor de Inventario ---");
        Inventario inventario = new Inventario();
        
        // Verificamos si hay productos para vender. Si está vacío, registramos uno de prueba para el test.
        if (inventario.getListaProductos().isEmpty()) {
            System.out.println("Inventario vacío. Registrando producto base para la prueba...");
            Producto tuboBase = new Producto(101, "Tubo PVC Presion 1/2", 14500.0, 30, "Tubo RDE 21");
            inventario.agregarProducto(tuboBase);
        }

        // Mostramos el estado actual del producto antes de la venta
        System.out.println("Productos disponibles para la venta:");
        for (Producto p : inventario.getListaProductos()) {
            System.out.println(" > ID: " + p.getId() + " | " + p.getNombre() + " | Stock Actual: " + p.getStock());
        }

        // 2. Simulación de atención en mostrador por parte de un Empleado
        System.out.println("\n--- [2] Iniciando Simulación de Venta (Rol: EMPLEADO) ---");
        Usuario empleado = new Empleado("JuanPerez", "emp123");
        System.out.println("Atendido por: " + empleado.getUsername() + " [" + empleado.getRol() + "]");

        // Intentamos realizar una venta válida: 5 unidades del producto ID 101
        int idFactura1 = 5001;
        int idProductoAVender = 101;
        int cantidadVenta1 = 5;
        
        System.out.println("\nProcesando Venta #" + idFactura1 + ": " + cantidadVenta1 + " unidades del ID " + idProductoAVender);
        boolean exitoVenta1 = inventario.registrarVenta(idFactura1, idProductoAVender, cantidadVenta1);

        if (exitoVenta1) {
            System.out.println("Comprobando actualización de stock en tiempo real:");
            for (Producto p : inventario.getListaProductos()) {
                if (p.getId() == idProductoAVender) {
                    System.out.println(" > Nuevo Stock en memoria RAM para " + p.getNombre() + ": " + p.getStock());
                }
            }
        }

        // 3. Prueba de control de anomalías: Intentar vender más unidades de las disponibles
        System.out.println("\n--- [3] Evaluando Restricciones de Stock (Control de Errores) ---");
        int idFactura2 = 5002;
        int cantidadExcesiva = 999; // Cantidad exagerada para forzar el quiebre de stock
        
        System.out.println("Intentando vender " + cantidadExcesiva + " unidades del ID " + idProductoAVender + "...");
        boolean exitoVenta2 = inventario.registrarVenta(idFactura2, idProductoAVender, cantidadExcesiva);
        
        if (!exitoVenta2) {
            System.out.println(">> Simulación correcta: El sistema denegó la transacción por falta de mercancía.");
        }

        // 4. Verificación y despliegue del reporte de ventas acumuladas
        System.out.println("\n--- [4] Invocando Reporte del Historial de Ventas ---");
        ManejadorArchivos.mostrarHistorialVentas();

        System.out.println("=================================================");
        System.out.println(" PRUEBAS DE TRANSACCIONES FINALIZADAS");
        System.out.println(" Revise 'historial_movimientos.txt' para ver la auditoría.");
        System.out.println("=================================================");
    }
}
