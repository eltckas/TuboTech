/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto.modelo;
import java.util.ArrayList;
import java.util.List;
import Proyecto.interfaces.GestionInventario;
import Proyecto.persistencia.ManejadorArchivos;
public class Inventario implements GestionInventario {
    private List<Producto> listaProducto;
    private final ManejadorArchivos manejador;
    
    public Inventario() {
        this.manejador = new ManejadorArchivos();
        // Carga los productos automáticamente desde el archivo plano .txt
        this.listaProducto = manejador.cargarProductos();
    }
    
    @Override
    public void actualizarStock(Producto p, int cantidad) {
        int stockInicial = p.getStock();
        p.setStock(stockInicial + cantidad);
        manejador.guardarProductos(listaProducto);
        
        ManejadorArchivos.registrarMovimiento(
            "INVENTARIO", 
            "ACTUALIZACION", 
            String.valueOf(p.getId()), 
            "Modificación stock de " + p.getNombre() + ". Inicial: " + stockInicial + " -> Final: " + p.getStock()
        );
    }
    
    @Override
    public void consultarDisponibilidad(String nombre) {
        for (Producto p : listaProducto) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Encontrado: " + p.toString());
                return;
            }
        }
        System.out.println("El producto " + nombre + " no está en inventario.");
    }
    
    public void agregarProducto(Producto p) {
        listaProducto.add(p);
        manejador.guardarProductos(listaProducto);
        
        ManejadorArchivos.registrarMovimiento(
            "INVENTARIO", 
            "REGISTRO", 
            String.valueOf(p.getId()), 
            "Producto: " + p.getNombre() + " | Stock inicial: " + p.getStock() + " | Precio: $" + p.getPrecio()
        );
    }
    
    // Método necesario para poder gestionar la lista completa desde controladores externos
    public List<Producto> getListaProductos() {
        return this.listaProducto;
    }
    public boolean registrarVenta(int idVenta, int idProducto, int cantidadVendida) {
    for (Producto p : listaProducto) {
        if (p.getId() == idProducto) {
            // Validación crítica: Verificar si hay suficiente mercancía
            if (p.getStock() < cantidadVendida) {
                System.out.println(">> ERROR: Stock insuficiente para " + p.getNombre() + ". Stock disponible: " + p.getStock());
                return false; 
            }

            // 1. Descontar el stock (pasamos la cantidad en negativo usando tu interfaz)
            actualizarStock(p, -cantidadVendida); 

            // 2. Calcular el total de la transacción
            double total = p.getPrecio() * cantidadVendida;

            // 3. Crear el objeto Venta y mandarlo al archivo ventas.txt
            Venta nuevaVenta = new Venta(idVenta, idProducto, cantidadVendida, total, java.time.LocalDateTime.now());
            ManejadorArchivos.registrarVentaEnDisco(nuevaVenta);

            // 4. Dejar rastro en el historial de auditoría centralizado
            ManejadorArchivos.registrarMovimiento(
                "VENTAS", 
                "REGISTRO_VENTA", 
                String.valueOf(idVenta), 
                "Venta de " + cantidadVendida + " unidades de " + p.getNombre() + " | Total: $" + total
            );

            System.out.println(">> ÉXITO: Venta #" + idVenta + " registrada correctamente.");
            return true;
        }
    }
    System.out.println(">> ERROR: El producto con ID " + idProducto + " no existe.");
    return false;
}
    
}
    
    
    
    
 