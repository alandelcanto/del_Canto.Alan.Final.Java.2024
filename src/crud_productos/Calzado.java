/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_productos;

/**
 *
 * @author usuario
 */
public class Calzado extends Producto implements Ajustable{

    public Marca marca;
    public int tamanio;
    private final int TAMANIO_ORIGINAL;

    public Calzado(int codigoProducto, double precio, int cantidadStock, int cantidadPedida, Marca marca, int tamanio) {
	super(codigoProducto, precio, cantidadStock, cantidadPedida);
	this.marca = marca;
	this.tamanio = tamanio;
	this.TAMANIO_ORIGINAL = tamanio;
    }

    public Calzado(int codigoProducto, double precio, int cantidadStock, int cantidadPedida, Marca marca) {
	this(codigoProducto, precio, cantidadStock, cantidadPedida, marca, 40);
    }

    public Calzado(int codigoProducto, double precio, int cantidadStock, int cantidadPedida) {
	this(codigoProducto, precio, cantidadStock, cantidadPedida, Marca.ADIDAS, 40);
    }

    @Override
    public void ajustar(int ajuste) throws AjusteImposibleException {
	int limiteAjuste = 2;
	
	if (Math.abs(this.tamanio + ajuste - TAMANIO_ORIGINAL) > limiteAjuste ) {
	    // Verifica que no asigne un valor más alejado del original que el límite
	    throw new AjusteImposibleException("No se puede ajustar más de dos tamaños de diferencia del original");
	} else {
	    this.tamanio = this.tamanio + ajuste;
	}
    }
    
    
}
