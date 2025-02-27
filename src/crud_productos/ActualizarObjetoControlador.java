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
	// Popular los ComboBoxes con opciones
	elegirProducto.getItems().setAll("PrendaSuperior", "Calzado", "Accesorio"); 
	elegirTamanioPrendaSuperior.getItems().setAll(TamanioPrendaSuperior.values());
	elegirTela.getItems().setAll(Tela.values());
	elegirMarca.getItems().setAll(Marca.values());
	elegirTipo.getItems().setAll(TipoAccesorio.values());
	
	// Rellenar los espacios con las variables del objeto a actualizar
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
    private void desactivarElecciones() { // Esconde los botones de todas las clases
	elegirTamanioPrendaSuperior.setVisible(false);
	elegirTela.setVisible(false);
	elegirTamanioCalzado.setVisible(false);
	elegirMarca.setVisible(false);
	elegirColor.setVisible(false);
	elegirTipo.setVisible(false);
    }
    private void confirmarDialogo(boolean exito, Producto objeto) { // Crea el objeto si se confirma un exito
	if (exito) {
	    ControladorJFX.inventario.actualizarObjeto(ControladorJFX.productoSeleccionado, objeto);
	} 
	((javafx.stage.Stage) botonCrearObjeto.getScene().getWindow()).close();
    }
    
    private void activarElecciones() { // Activa las opciones de la clase elegida
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
    void onBotonCrearObjeto(ActionEvent event) { // Asigna los datos introducidos a variables
	try {
	    String tipoProducto = elegirProducto.getValue();
	    Double precio = Double.valueOf(elegirPrecio.getText());
	    int cantidadStock = Integer.parseInt(elegirStock.getText());
	    int cantidadPedida = Integer.parseInt(elegirPedido.getText());

	    if (precio < 0 || cantidadStock < 0 || cantidadPedida < 0) {
		ControladorJFX.mostrarAlerta("Números Negativos", "Ingrese únicamente números positivos");
		return;
	    }

	    if (tipoProducto == null) {
		ControladorJFX.mostrarAlerta("Tipo de Producto no Válido", "Seleccione un producto válido");
	    }

	    switch (elegirProducto.getValue()) {
		case "PrendaSuperior" -> {
		    TamanioPrendaSuperior tamanio = elegirTamanioPrendaSuperior.getValue();
		    Tela tela = elegirTela.getValue();
		    if (tamanio == null && tela == null || tamanio == null) { // Verificaciones para que no crashee el programa
			PrendaSuperior producto = new PrendaSuperior(precio, cantidadStock, cantidadPedida);
			confirmarDialogo(true, producto);
		    } else if (tela == null) {
			PrendaSuperior producto = new PrendaSuperior(precio, cantidadStock, cantidadPedida, tamanio);
			confirmarDialogo(true, producto);
		    } else {
			PrendaSuperior producto = new PrendaSuperior(precio, cantidadStock, cantidadPedida, tamanio, tela);
			confirmarDialogo(true, producto);
		    }

		}
		case "Calzado" -> {
		    int tamanio = Integer.parseInt(elegirTamanioCalzado.getText());
		    if (tamanio < 0) {
			ControladorJFX.mostrarAlerta("Números Negativos", "Ingrese únicamente números positivos");
			return;
		    }
		    Marca marca = elegirMarca.getValue();
		    if (marca == null) { // Verificaciones
			Calzado producto = new Calzado(precio, cantidadStock, cantidadPedida);
			confirmarDialogo(true, producto);
		    } else {
			Calzado producto = new Calzado(precio, cantidadStock, cantidadPedida, marca, tamanio);
			confirmarDialogo(true, producto);
		    }
		}
		case "Accesorio" -> {
		    String color = elegirColor.getText();
		    TipoAccesorio tipo = elegirTipo.getValue();
		    if (tipo == null) { // Verificaciones
			Accesorio producto = new Accesorio(precio, cantidadStock, cantidadPedida, color);
			confirmarDialogo(true, producto);
		    } else {
			Accesorio producto = new Accesorio(precio, cantidadStock, cantidadPedida, color, tipo);
			confirmarDialogo(true, producto);
		    }
		}
		default ->
		    throw new Exception();
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
