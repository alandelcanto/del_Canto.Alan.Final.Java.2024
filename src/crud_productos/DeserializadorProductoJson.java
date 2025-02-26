/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_productos;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/**
 *
 * @author usuario
 */
public class DeserializadorProductoJson implements JsonDeserializer<Producto> {

    @Override
    public Producto deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
	JsonObject jsonObject = je.getAsJsonObject(); // Asigna cada variable a partir de un miembro del jsonObject
	Double precio = jsonObject.get("precio").getAsDouble();
	int cantidadStock = jsonObject.get("cantidadStock").getAsInt();
	int cantidadPedida = jsonObject.get("cantidadPedida").getAsInt();

	if (jsonObject.has("tipoTela")) { // Quiere decir que es tipo PrendaSuperior
	    TamanioPrendaSuperior tamanio = TamanioPrendaSuperior.valueOf(jsonObject.get("tamanio").getAsString());
	    Tela tipoTela = Tela.valueOf(jsonObject.get("tipoTela").getAsString());
	    TamanioPrendaSuperior tamanioOriginal = TamanioPrendaSuperior.valueOf(jsonObject.get("TAMANIO_ORIGINAL").getAsString());
	    return new PrendaSuperior(precio, cantidadStock, cantidadPedida, tamanio, tipoTela, tamanioOriginal);
	} else if (jsonObject.has("tamanio")) { // Quiere decir que es Calzado
	    int tamanio = jsonObject.get("tamanio").getAsInt();
	    Marca marca = Marca.valueOf(jsonObject.get("marca").getAsString());
	    int tamanioOriginal = jsonObject.get("TAMANIO_ORIGINAL").getAsInt();
	    return new Calzado(precio, cantidadStock, cantidadPedida, marca, tamanio, tamanioOriginal);
	} else if (jsonObject.has("color")) { // Quiere decir que es Accesorio
	    String color = jsonObject.get("color").getAsString();
	    TipoAccesorio tipo = TipoAccesorio.valueOf(jsonObject.get("tipo").getAsString());
	    return new Accesorio(precio, cantidadStock, cantidadPedida, color, tipo);
	}
	return null;
    }

}
