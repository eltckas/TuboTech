/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Proyecto.interfaces;
import Proyecto.modelo.Producto;
public interface GestionInventario {
    void actualizarStock(Producto p, int cantidad);
    
    // Método para buscar productos (útil para el administrador y empleado)
    void consultarDisponibilidad(String nombre);
    
}
