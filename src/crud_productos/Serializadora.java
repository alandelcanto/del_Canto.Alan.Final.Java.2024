/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crud_productos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author usuario
 */
public class Serializadora {

    public static Gson gson = new GsonBuilder() // Configurar el Gson
	    .registerTypeAdapter(Producto.class, new SerializadorProductoJson())
	    .registerTypeAdapter(Producto.class, new DeserializadorProductoJson())
	    .setPrettyPrinting()
	    .create();

    public static void serializar(ArrayList<? extends Producto> lista, String path) throws IOException {
	try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
	    out.writeObject(lista);
	}
    }

    public static ArrayList<Producto> deserializar(String path) throws IOException, ClassNotFoundException {
	try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
	    ArrayList<Producto> lista = (ArrayList<Producto>) in.readObject();

	    return lista;
	}
    }

    public static void serializarJson(ArrayList<? extends Producto> lista, String path) throws IOException {
	String jsonString = gson.toJson(lista);
	try (FileWriter out = new FileWriter(path)) {
	    out.write(jsonString);
	}
    }

    public static ArrayList<Producto> deserializarJson(String path) throws FileNotFoundException, IOException {
	try (FileReader in = new FileReader(path)) {
	    Type productoType = new TypeToken<ArrayList<Producto>>() {
	    }.getType();
	    ArrayList<Producto> lista = gson.fromJson(in, productoType);

	    return lista;
	}
    }

    public static void serializarCsv(ArrayList<? extends Producto> lista, String path) throws IOException {
	try (BufferedWriter out = new BufferedWriter(new FileWriter(path)); CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader("Precio", "CantidadStock", "CantidadPedida", "TamanioPrendaSuperior", "TipoTela", "TamanioCalzado", "Marca", "Color", "TipoAccesorio"))) {
	    for (Producto producto : lista) { // Escribir cada producto en una línea, y dependiendo de que tipo es, leer de la variables o un espacio en blanco
		ArrayList<String> linea = new ArrayList<>();
		linea.add(Double.toString(producto.precio));
		linea.add(Integer.toString(producto.cantidadStock));
		linea.add(Integer.toString(producto.cantidadPedida));
		if (producto instanceof PrendaSuperior prendaSuperior) {
		    linea.add(prendaSuperior.tamanio.name());
		    linea.add(prendaSuperior.tipoTela.name());
		} else {
		    linea.add("");
		    linea.add("");
		}
		if (producto instanceof Calzado calzado) {
		    linea.add(Integer.toString(calzado.tamanio));
		    linea.add(calzado.marca.name());
		} else {
		    linea.add("");
		    linea.add("");
		}
		if (producto instanceof Accesorio accesorio) {
		    linea.add(accesorio.color);
		    linea.add(accesorio.tipo.name());
		} else {
		    linea.add("");
		    linea.add("");
		}
		csvPrinter.printRecord(linea);
	    }
	}
    }

    public static ArrayList<Producto> deserializarCsv(String path) throws FileNotFoundException, IOException {
	ArrayList<Producto> lista = new ArrayList<>();
	try (BufferedReader in = new BufferedReader(new FileReader(path)); CSVParser csvParser = new CSVParser(in, CSVFormat.DEFAULT.withHeader())) {
	    for (CSVRecord linea : csvParser) {
		Double precio = Double.valueOf(linea.get("Precio"));
		int cantidadStock = Integer.parseInt(linea.get("CantidadStock"));
		int cantidadPedida = Integer.parseInt(linea.get("CantidadPedida"));

		if (!"".equals(linea.get("TamanioPrendaSuperior"))) { // Si el el espacio designado no está en blanco, significa que es una PrendaSuperior
		    TamanioPrendaSuperior tamanio = TamanioPrendaSuperior.valueOf(linea.get("TamanioPrendaSuperior"));
		    Tela tipoTela = Tela.valueOf(linea.get("TipoTela"));
		    lista.add(new PrendaSuperior(precio, cantidadStock, cantidadPedida, tamanio, tipoTela));
		} else if (!"".equals(linea.get("TamanioCalzado"))) { // Lo mismo con Calzado
		    int tamanio = Integer.parseInt(linea.get("TamanioCalzado"));
		    Marca marca = Marca.valueOf(linea.get("Marca"));
		    lista.add(new Calzado(precio, cantidadStock, cantidadPedida, marca, tamanio));
		} else if (!"".equals(linea.get("Color"))) { // Lo mismo con Accesorio
		    String color = linea.get("Color");
		    TipoAccesorio tipo = TipoAccesorio.valueOf(linea.get("TipoAccesorio"));
		    lista.add(new Accesorio(precio, cantidadStock, cantidadPedida, color, tipo));
		}

	    }
	}
	return lista;
    }
    
    public static void exportarTxt(ArrayList<? extends Producto> lista, String path, String listaFiltrosString) throws IOException {
	try (BufferedWriter out = new BufferedWriter(new FileWriter(path))){
	    StringBuilder sb = new StringBuilder();
	    sb.append("Lista de productos con Filtros aplicados\n\n");
	    sb.append("Lista de Filtros:\n");
	    sb.append(listaFiltrosString).append("\n"); // La lista ya con formato de los filtros
	    
	    sb.append("Lista de Productos:\n");
	    for (Producto producto : lista){
		sb.append(producto.toString()).append("\n");
	    }
	    
	    out.write(sb.toString());
	}
    }
}
