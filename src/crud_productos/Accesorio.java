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

    public Accesorio(double precio, int cantidadStock, int cantidadPedida, String color, TipoAccesorio tipo) {
	super(precio, cantidadStock, cantidadPedida);
	this.color = color;
	this.tipo = tipo;
    }

    public Accesorio(double precio, int cantidadStock, int cantidadPedida, String color) {
	this(precio, cantidadStock, cantidadPedida, color, TipoAccesorio.COLLAR);
    }

    public Accesorio(double precio, int cantidadStock, int cantidadPedida) {
	this(precio, cantidadStock, cantidadPedida, "Blanco");
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	// Sumar al toString() de Producto
	sb.append(super.toString()).append(" - Tipo: ").append("Accesorio") 
		.append(" - Color: ").append(color)
		.append(" - TipoAccesorio: ").append(tipo);
	
	return sb.toString();
    }
    
    
}
