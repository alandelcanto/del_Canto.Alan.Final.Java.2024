/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_productos;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 *
 * @author usuario
 */
public class SerializadorProductoJson implements JsonSerializer<Producto>{
    
    @Override
    public JsonElement serialize(Producto t, Type type, JsonSerializationContext jsc) {
	// Asigna los miembros del JsonObject dependiendo de las variables que tienen los objetos
	JsonObject jsonObject = new JsonObject();
	jsonObject.addProperty("precio", t.precio);
	jsonObject.addProperty("cantidadStock", t.cantidadStock);
	jsonObject.addProperty("cantidadPedida", t.cantidadPedida);
	
	switch (t) {
	    case PrendaSuperior prendaSuperior -> {
		jsonObject.addProperty("tamanio", prendaSuperior.tamanio.name());
		jsonObject.addProperty("tipoTela", prendaSuperior.tipoTela.name());
		jsonObject.addProperty("TAMANIO_ORIGINAL_PRENDA_SUPERIOR", prendaSuperior.TAMANIO_ORIGINAL.name());
	    }
	    case Calzado calzado -> {
		jsonObject.addProperty("tamanioCalzado", calzado.tamanio);
		jsonObject.addProperty("marca", calzado.marca.name());
		jsonObject.addProperty("TAMANIO_ORIGINAL_CALZADO", calzado.TAMANIO_ORIGINAL);
	    }
	    case Accesorio accesorio -> {
		jsonObject.addProperty("color", accesorio.color);
		jsonObject.addProperty("tipo", accesorio.tipo.name());
	    }
	    default -> {
	    }
	}
	
	return jsonObject;
    }
    
}
