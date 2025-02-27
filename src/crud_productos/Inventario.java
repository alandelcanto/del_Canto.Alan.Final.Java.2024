/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_productos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author usuario
 */
public class Inventario<T> implements CRUD<T>, Iterable<T>, Iterator<T> {

    public ArrayList<T> inventario = new ArrayList<>();
    public int posicion;

    @Override
    public void crearObjeto(T objeto) { // Todos los parámetros de estos métodos ya deberian estar verificados antes de llamarlos
	this.inventario.add(objeto);
    }

    @Override
    public ArrayList<T> leerLista() {
	return this.inventario;
    }

    @Override
    public void actualizarObjeto(T objeto, T objetoActualizado) { // Elimina el objeto viejo y reañade el ya actualizado
	this.eliminarObjeto(objeto);
	this.crearObjeto(objetoActualizado);
    }

    @Override
    public void eliminarObjeto(T objeto) {
	this.inventario.remove(objeto);
    }



    @Override
    public Iterator<T> iterator() {
	this.posicion = 0;
	return this;
    }

    @Override
    public boolean hasNext() {
	return this.posicion < this.inventario.size();
    }

    @Override
    public T next() {
	T objeto = this.inventario.get(posicion);
	posicion++;
	return objeto;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
	Iterable.super.forEach(action);
    }

    public ArrayList<T> filtrar(ArrayList<T> lista, ArrayList<Predicate<? super T>> listaFiltros) {
	if (listaFiltros.isEmpty()) {
	    return lista;
	} else {
	    // Por cada filtro en la lista itera sobre ella ysi cumple con el filtro pasa a la siguiente lista
	    ArrayList<T> listaFiltrada = new ArrayList<>();
	    for (T objeto : lista) {
		if (listaFiltros.get(0).test(objeto)){
		    listaFiltrada.add(objeto);
		}
	    }
	    listaFiltros.remove(0);
	    
	    return this.filtrar(listaFiltrada, listaFiltros);
	}
    }
}
