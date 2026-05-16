/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto.persistencia;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Proyecto.modelo.Producto;
import Proyecto.modelo.Usuario;
import Proyecto.modelo.Administrador;
import Proyecto.modelo.Empleado;
import Proyecto.modelo.Venta;
public class ManejadorArchivos {
    private static final String FILE_PRODUCTOS = "inventario.txt";
    private static final String FILE_USUARIOS = "usuarios.txt";
    private static final String FILE_HISTORIAL = "historial_movimientos.txt";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // --- AUDITORÍA GLOBAL ---
    public static void registrarMovimiento(String modulo, String tipoMovimiento, String identificador, String detalles) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_HISTORIAL, true))) {
            String fecha = LocalDateTime.now().format(FORMATO_FECHA);
            bw.write("[" + fecha + "] [" + modulo.toUpperCase() + "] [" + tipoMovimiento.toUpperCase() + "] - ID/User: " + identificador + " | " + detalles);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el historial: " + e.getMessage());
        }
    }

    // --- PERSISTENCIA DE PRODUCTOS ---
    public void guardarProductos(List<Producto> productos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PRODUCTOS))) {
            for (Producto p : productos) {
                pw.println(p.getId() + ";" + p.getNombre() + ";" + p.getPrecio() + ";" + p.getStock() + ";" + p.getDescripcion());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar productos: " + e.getMessage());
        }
    }

    public List<Producto> cargarProductos() {
        List<Producto> productos = new ArrayList<>();
        File archivo = new File(FILE_PRODUCTOS);
        if (!archivo.exists()) return productos;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(";");
                int id = Integer.parseInt(datos[0]);
                String nombre = datos[1];
                double precio = Double.parseDouble(datos[2]);
                int stock = Integer.parseInt(datos[3]);
                String desc = datos[4];
                productos.add(new Producto(id, nombre, precio, stock, desc));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar productos: " + e.getMessage());
        }
        return productos;
    }

    // --- PERSISTENCIA DE USUARIOS ---
    public void guardarUsuarios(List<Usuario> usuarios) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_USUARIOS))) {
            for (Usuario u : usuarios) {
                // Se almacena la contraseña plana o hash si lo requieres, y su rol
                pw.println(u.getUsername() + ";" + "REPLACE_ME_OR_KEEP_PASS" + ";" + u.getRol());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    public List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        File archivo = new File(FILE_USUARIOS);
        if (!archivo.exists()) {
            // Inicializar un admin por defecto si no existe el archivo para evitar quedarse por fuera
            usuarios.add(new Administrador("admin", "admin123"));
            guardarUsuarios(usuarios);
            return usuarios;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(";");
                String user = datos[0];
                String pass = datos[1];
                String rol = datos[2];

                if (rol.equalsIgnoreCase("ADMINISTRADOR")) {
                    usuarios.add(new Administrador(user, pass));
                } else if (rol.equalsIgnoreCase("EMPLEADO")) {
                    usuarios.add(new Empleado(user, pass));
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar usuarios: " + e.getMessage());
        }
        return usuarios;
    }
    // Al inicio de la clase junto a las otras constantes:
private static final String FILE_VENTAS = "ventas.txt";

// Método para guardar una venta (añadiendo al final del archivo con 'true')
public static void registrarVentaEnDisco(Venta v) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_VENTAS, true))) {
        String fechaStr = v.getFecha().format(FORMATO_FECHA);
        bw.write(v.getIdVenta() + ";" + v.getIdProducto() + ";" + v.getCantidad() + ";" + v.getTotal() + ";" + fechaStr);
        bw.newLine();
    } catch (IOException e) {
        System.err.println("Error al guardar la venta: " + e.getMessage());
    }
}
public static void mostrarHistorialVentas() {
    File archivo = new File("ventas.txt");
    if (!archivo.exists()) {
        System.out.println(">> No se han registrado ventas en el sistema todavía.");
        return;
    }

    System.out.println("\n=======================================================");
    System.out.println("            HISTORIAL DE VENTAS - TUBOTECH             ");
    System.out.println("=======================================================");
    System.out.printf("%-10s %-12s %-10s %-12s %-20s\n", "FACTURA", "PRODUCTO ID", "CANTIDAD", "TOTAL", "FECHA");
    System.out.println("-------------------------------------------------------");

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue;
            String[] datos = linea.split(";");
            
            String idFactura = datos[0];
            String idProducto = datos[1];
            String cantidad = datos[2];
            String total = datos[3];
            String fecha = datos[4];

            System.out.printf("%-10s %-12s %-10s $%-11s %-20s\n", idFactura, idProducto, cantidad, total, fecha);
        }
    } catch (IOException e) {
        System.err.println("Error al leer el historial de ventas: " + e.getMessage());
    }
    System.out.println("=======================================================\n");
}
}
