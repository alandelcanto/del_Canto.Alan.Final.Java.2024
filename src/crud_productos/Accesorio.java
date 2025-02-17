/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_productos;

/**
 *
 * @author usuario
 */
public class Accesorio extends Producto {

    public String color;
    public TipoAccesorio tipo;

    public Accesorio(int codigoProducto, double precio, int cantidadStock, int cantidadPedida, String color, TipoAccesorio tipo) {
	super(codigoProducto, precio, cantidadStock, cantidadPedida);
	this.color = color;
	this.tipo = tipo;
    }

    public Accesorio(int codigoProducto, double precio, int cantidadStock, int cantidadPedida, String color) {
	this(codigoProducto, precio, cantidadStock, cantidadPedida, color, TipoAccesorio.COLLAR);
    }

    public Accesorio(int codigoProducto, double precio, int cantidadStock, int cantidadPedida) {
	this(codigoProducto, precio, cantidadStock, cantidadPedida, "Blanco");
    }
}
