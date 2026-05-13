/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto.controlador;

import Proyecto.modelo.*;
public class MainPruebas {
    public static void main(String[] args) {
        // 1. Creamos el inventario
        Inventario miInventario = new Inventario();

        // 2. Creamos productos de PVC (como en tu idea de proyecto)
        Producto p1 = new Producto(1, "Tubo PVC 1/2", 15000, 50, "Tubo de agua presión");
        Producto p2 = new Producto(2, "Codo PVC 90", 2500, 100, "Accesorio para tubería");

        // 3. Los agregamos al sistema
        miInventario.agregarProducto(p1);
        miInventario.agregarProducto(p2);

        // 4. Probamos la consulta (el objetivo de tu proyecto)
        System.out.println("--- CONSULTA DE PRODUCTOS ---");
        miInventario.consultarDisponibilidad("Tubo PVC 1/2");

        // 5. Probamos el Login (la seguridad de tu storyboard)
        Administrador admin = new Administrador("eltckas", "pvc2024");
        System.out.println("\n--- PRUEBA DE SEGURIDAD ---");
        boolean pudoEntrar = admin.login("eltckas", "pvc2024");
        System.out.println("¿Acceso concedido?: " + pudoEntrar);
    }
}
    

