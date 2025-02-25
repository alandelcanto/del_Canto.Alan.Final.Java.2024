/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_productos;

/**
 *
 * @author usuario
 */
public class PrendaSuperior extends Producto implements Ajustable{
    public TamanioPrendaSuperior tamanio;
    public Tela tipoTela;
    public final TamanioPrendaSuperior TAMANIO_ORIGINAL;

    public PrendaSuperior(double precio, int cantidadStock, int cantidadPedida, TamanioPrendaSuperior tamanio, Tela tipoTela, TamanioPrendaSuperior TAMANIO_ORIGINAL) { // Solo para deserializar
	super(precio, cantidadStock, cantidadPedida);
	this.tamanio = tamanio;
	this.TAMANIO_ORIGINAL = TAMANIO_ORIGINAL;
	this.tipoTela = tipoTela;
    }
    
    public PrendaSuperior(double precio, int cantidadStock, int cantidadPedida, TamanioPrendaSuperior tamanio, Tela tipoTela) {
	super(precio, cantidadStock, cantidadPedida);
	this.tamanio = tamanio;
	this.TAMANIO_ORIGINAL = tamanio;
	this.tipoTela = tipoTela;
    }

    public PrendaSuperior(double precio, int cantidadStock, int cantidadPedida, TamanioPrendaSuperior tamanio) {
	this(precio, cantidadStock, cantidadPedida, tamanio, Tela.ALGODON);
    }

    public PrendaSuperior(double precio, int cantidadStock, int cantidadPedida) {
	this(precio, cantidadStock, cantidadPedida, TamanioPrendaSuperior.M, Tela.ALGODON);
    }

    @Override
    public void ajustar(int ajuste) throws AjusteImposibleException {
	int limiteAjuste = 1;
	
	if (this.tamanio.ordinal() + ajuste < 0 | this.tamanio.ordinal() + ajuste > TamanioPrendaSuperior.values().length) {
	    // Verifica que no intente asignar un valor que no exista en el Enum
	    throw new AjusteImposibleException("El tamaño objetivo no existe en el sistema");
	} else if (Math.abs(this.tamanio.ordinal() + ajuste - this.TAMANIO_ORIGINAL.ordinal()) > limiteAjuste) {
	    // Verifica que no intente asignar un valor más alto que el límite de ajuste del original
	    throw new AjusteImposibleException("No se puede ajustar más de un tamaño de diferencia del original");
	} else {
	    this.tamanio = TamanioPrendaSuperior.values()[this.tamanio.ordinal() + ajuste];
	}
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(super.toString()).append(" - Tipo: ").append("Prenda Superior")
		.append(" - Tamaño: ").append(tamanio)
		.append(" - Tela: ").append(tipoTela);
	
	return sb.toString();
    }
    
    
}
