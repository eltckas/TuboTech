/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto.modelo;


public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private String descripcion;
    
    public Producto (int id, String nombre, double precio, int stock, String Descripcion){
        this.id=id;
        this.nombre=nombre;
        this.precio=precio;
        this.stock=stock;
        this.descripcion=Descripcion;
    
    }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getId(){return this.id;}
    public double getPrecio(){return precio; }
    public void setPrecio(int p) {this.precio=p;}
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getDescripcion() { return descripcion; }
    @Override
    public String toString() {
        return nombre + " - Precio: $" + precio + " - Stock: " + stock;
}
}
