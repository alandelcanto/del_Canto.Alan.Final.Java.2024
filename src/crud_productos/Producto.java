/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_productos;

/**
 *
 * @author usuario
 */
public abstract class Producto {

    public int codigoProducto;
    public double precio;
    public int cantidadStock;
    public int cantidadPedida;

    public Producto(int codigoProducto, double precio, int cantidadStock, int cantidadPedida) {
	this.codigoProducto = codigoProducto;
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
}
