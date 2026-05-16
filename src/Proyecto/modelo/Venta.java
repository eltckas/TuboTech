package Proyecto.modelo;

import java.time.LocalDateTime;

public class Venta {
    private int idVenta;
    private int idProducto;
    private int cantidad;
    private double total;
    private LocalDateTime fecha;

    public Venta(int idVenta, int idProducto, int cantidad, double total, LocalDateTime fecha) {
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.total = total;
        this.fecha = fecha;
    }

    // Getters necesarios para la persistencia
    public int getIdVenta() { return idVenta; }
    public int getIdProducto() { return idProducto; }
    public int getCantidad() { return cantidad; }
    public double getTotal() { return total; }
    public LocalDateTime getFecha() { return fecha; }
}