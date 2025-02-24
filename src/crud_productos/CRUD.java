/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package crud_productos;

import java.util.ArrayList;

/**
 *
 * @author usuario
 */
public interface CRUD<T> {
    public void crearObjeto(T objeto);
    
    public ArrayList<T> leerLista();
    
    public void actualizarObjeto(T objeto, T objetoActualizado);
    
    public void eliminarObjeto(T objeto);
    
}
