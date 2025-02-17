/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package crud_productos;

/**
 *
 * @author usuario
 */
public class ProductosInsuficientesException extends Exception{

    /**
     * Creates a new instance of <code>ProductosInsuficientesException</code>
     * without detail message.
     */
    public ProductosInsuficientesException() {
    }

    /**
     * Constructs an instance of <code>ProductosInsuficientesException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ProductosInsuficientesException(String msg) {
	super(msg);
    }
}
