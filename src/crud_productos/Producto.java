/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_productos;

import java.io.Serializable;

/**
 *
 * @author usuario
 */
public abstract class Producto implements Comparable<Producto>, Serializable{

    public double precio;
    public int cantidadStock;
    public int cantidadPedida;

    public Producto(double precio, int cantidadStock, int cantidadPedida) {
	this.precio = precio;
	this.cantidadStock = cantidadStock;
	this.cantidadPedida = cantidadPedida;
    }

    public void realizarPedido(int cantidadAPedir) {
	this.cantidadPedida += cantidadAPedir;
    }

    public boolean aceptarPedido(int cantidadAAceptar) {
	if (cantidadAAceptar > this.cantidadPedida) {
	    // En caso de aceptar más productos de los que se pidieron, se asume aceptar todos
	    this.cantidadStock += this.cantidadPedida;
	    this.cantidadPedida = 0;
	    return false;

	} else {
	    this.cantidadStock += cantidadAAceptar;
	    this.cantidadPedida -= cantidadAAceptar;
	    return true;
	}

    }

    public double realizarVenta(int cantidadAVender) throws ProductosInsuficientesException {
	if (this.cantidadStock < cantidadAVender) {
	    throw new ProductosInsuficientesException("No hay suficientes productos para realizar la venta");
	} else {
	    this.cantidadStock -= cantidadAVender;
	    return this.precio * cantidadAVender;
	}
    }

    @Override
    public int compareTo(Producto o) {
	return Double.compare(this.precio, o.precio);
    }
    
    
}
