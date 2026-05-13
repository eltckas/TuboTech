/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto.modelo;
import java.util.ArrayList;
import java.util.List;
import Proyecto.interfaces.GestionInventario;
public class Inventario implements GestionInventario {
    private List<Producto> listaProducto;
    
    public Inventario() {
        this.listaProducto= new ArrayList();
    }
    @Override
    public void actualizarStock(Producto  p, int cantidad){
        p.setStock(p.getStock()+cantidad);
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
    }
    
    
    
    
    
    
}

