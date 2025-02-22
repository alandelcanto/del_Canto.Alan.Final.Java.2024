/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_productos;

import java.util.Comparator;

/**
 *
 * @author usuario
 */
public class ProductoPedidoComparator implements Comparator<Producto>{
    
    @Override
    public int compare(Producto o1, Producto o2) {
	return Integer.compare(o1.cantidadPedida, o2.cantidadPedida);
    }
    
}
