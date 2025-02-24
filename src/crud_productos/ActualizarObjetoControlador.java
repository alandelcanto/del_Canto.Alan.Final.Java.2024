package crud_productos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ActualizarObjetoControlador {

    @FXML
    private Button botonCancelar;

    @FXML
    private Button botonCrearObjeto;

    @FXML
    private TextField elegirColor;

    @FXML
    private ComboBox<Marca> elegirMarca;

    @FXML
    private TextField elegirPedido;

    @FXML
    private TextField elegirPrecio;

    @FXML
    private ComboBox<String> elegirProducto;

    @FXML
    private TextField elegirStock;

    @FXML
    private TextField elegirTamanioCalzado;

    @FXML
    private ComboBox<TamanioPrendaSuperior> elegirTamanioPrendaSuperior;

    @FXML
    private ComboBox<Tela> elegirTela;

    @FXML
    private ComboBox<TipoAccesorio> elegirTipo;

    @FXML
    private void initialize() {
	elegirProducto.getItems().setAll("PrendaSuperior", "Calzado", "Accesorio");
	elegirTamanioPrendaSuperior.getItems().setAll(TamanioPrendaSuperior.values());
	elegirTela.getItems().setAll(Tela.values());
	elegirMarca.getItems().setAll(Marca.values());
	elegirTipo.getItems().setAll(TipoAccesorio.values());
	
	elegirProducto.setValue(ControladorJFX.productoSeleccionado.getClass().getName().replace("crud_productos.", ""));
	activarElecciones();
	elegirPrecio.setText(String.valueOf(ControladorJFX.productoSeleccionado.precio));
	elegirStock.setText(String.valueOf(ControladorJFX.productoSeleccionado.cantidadStock));
	elegirPedido.setText(String.valueOf(ControladorJFX.productoSeleccionado.cantidadPedida));
	if (ControladorJFX.productoSeleccionado instanceof PrendaSuperior prendaSuperior) {
	    elegirTamanioPrendaSuperior.setValue(prendaSuperior.tamanio);
	    elegirTela.setValue(prendaSuperior.tipoTela);
	}
	if (ControladorJFX.productoSeleccionado instanceof Calzado calzado) {
	    elegirMarca.setValue(calzado.marca);
	    elegirTamanioCalzado.setText(String.valueOf(calzado.tamanio));
	}
	if (ControladorJFX.productoSeleccionado instanceof Accesorio accesorio) {
	    elegirColor.setText(accesorio.color);
	    elegirTipo.setValue(accesorio.tipo);
	}
    }
    private void desactivarElecciones() {
	elegirTamanioPrendaSuperior.setVisible(false);
	elegirTela.setVisible(false);
	elegirTamanioCalzado.setVisible(false);
	elegirMarca.setVisible(false);
	elegirColor.setVisible(false);
	elegirTipo.setVisible(false);
    }
    private void confirmarDialogo(boolean exito, Producto objeto) {
	if (exito) {
	    ControladorJFX.inventario.actualizarObjeto(ControladorJFX.productoSeleccionado, objeto);
	} 
	((javafx.stage.Stage) botonCrearObjeto.getScene().getWindow()).close();
    }
    
    private void activarElecciones() {
	switch (elegirProducto.getValue()) {
	    case "PrendaSuperior" -> {
		desactivarElecciones();
		elegirTamanioPrendaSuperior.setVisible(true);
		elegirTela.setVisible(true);
	    }
	    case "Calzado" -> {
		desactivarElecciones();
		elegirTamanioCalzado.setVisible(true);
		elegirMarca.setVisible(true);
	    }
	    case "Accesorio" -> {
		desactivarElecciones();
		elegirColor.setVisible(true);
		elegirTipo.setVisible(true);
	    }
	    default -> desactivarElecciones();
	}
    }
    
    @FXML
    void onBotonCancelar(ActionEvent event) {
	confirmarDialogo(false, null);
    }

    @FXML
    void onBotonCrearObjeto(ActionEvent event) {
	try {
	String tipoProducto = elegirProducto.getValue();
	Double precio = Double.valueOf(elegirPrecio.getText());
	int cantidadStock = Integer.parseInt(elegirStock.getText());
	int cantidadPedida = Integer.parseInt(elegirPedido.getText());
	
	
	
	if (tipoProducto == null) {
	    ControladorJFX.mostrarAlerta("Tipo de Producto no Válido", "Seleccione un producto válido");
	}
	
	switch (elegirProducto.getValue()) {
	    case "PrendaSuperior" -> {
		TamanioPrendaSuperior tamanio = elegirTamanioPrendaSuperior.getValue();
		Tela tela = elegirTela.getValue();
		PrendaSuperior producto = new PrendaSuperior(precio, cantidadStock, cantidadPedida, tamanio, tela);
		confirmarDialogo(true, producto);
	    }
	    case "Calzado" -> {
		int tamanio = Integer.parseInt(elegirTamanioCalzado.getText());
		Marca marca = elegirMarca.getValue();
		Calzado producto = new Calzado(precio, cantidadStock, cantidadPedida, marca, tamanio);
		confirmarDialogo(true, producto);
	    }
	    case "Accesorio" -> {
		String color = elegirColor.getText();
		TipoAccesorio tipo = elegirTipo.getValue();
		Accesorio producto = new Accesorio(precio, cantidadStock, cantidadPedida, color, tipo);
		confirmarDialogo(true, producto);
	    }
	    default -> throw new Exception();
	}
	
	    
	} catch (Exception ex) {
	    ControladorJFX.mostrarAlerta("Producto no Válido", "Verifique los campos introducidos");
	}
	
    }

    
    @FXML
    void onElegirProducto(ActionEvent event) {
	activarElecciones();
    }

}
