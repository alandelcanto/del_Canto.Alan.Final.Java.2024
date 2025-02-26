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
    public final int TAMANIO_ORIGINAL;
    
    public Calzado(double precio, int cantidadStock, int cantidadPedida, Marca marca, int tamanio, int TAMANIO_ORIGINAL) { // Solo para deserializar
	super(precio, cantidadStock, cantidadPedida);
	this.marca = marca;
	this.tamanio = tamanio;
	this.TAMANIO_ORIGINAL = TAMANIO_ORIGINAL;
    }
    
    public Calzado(double precio, int cantidadStock, int cantidadPedida, Marca marca, int tamanio) {
	super(precio, cantidadStock, cantidadPedida);
	this.marca = marca;
	this.tamanio = tamanio;
	this.TAMANIO_ORIGINAL = tamanio;
    }

    public Calzado(double precio, int cantidadStock, int cantidadPedida, Marca marca) {
	this(precio, cantidadStock, cantidadPedida, marca, 40);
    }

    public Calzado(double precio, int cantidadStock, int cantidadPedida) {
	this(precio, cantidadStock, cantidadPedida, Marca.ADIDAS, 40);
    }

    @Override
    public void ajustar(int ajuste) throws AjusteImposibleException {
	int limiteAjuste = 2; // Si tamaño es 40, entonces 38-42
	
	if (Math.abs(this.tamanio + ajuste - TAMANIO_ORIGINAL) > limiteAjuste ) {
	    // Verifica que no asigne un valor más alejado del original que el límite
	    throw new AjusteImposibleException("No se puede ajustar más de dos tamaños de diferencia del original");
	} else {
	    this.tamanio = this.tamanio + ajuste;
	}
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	// Sumar al toString() de Producto
	sb.append(super.toString()).append(" - Tipo: ").append("Calzado")
		.append(" - Tamaño: ").append(tamanio)
		.append(" - Marca: ").append(marca);
	
	return sb.toString();
    }
    
    
}
