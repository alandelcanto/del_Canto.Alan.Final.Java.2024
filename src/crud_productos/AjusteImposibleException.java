/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package crud_productos;

/**
 *
 * @author usuario
 */
public class AjusteImposibleException extends Exception{

    /**
     * Creates a new instance of <code>AjusteImposibleException</code> without
     * detail message.
     */
    public AjusteImposibleException() {
    }

    /**
     * Constructs an instance of <code>AjusteImposibleException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AjusteImposibleException(String msg) {
	super(msg);
    }
}
