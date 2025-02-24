package crud_productos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ControladorJFX {

    @FXML
    private Button botonAceptarPedido;

    @FXML
    private Button botonActualizarObjeto;

    @FXML
    private Button botonAgregarObjeto;

    @FXML
    private Button botonEliminarObjeto;

    @FXML
    private Button botonRealizarPedido;

    @FXML
    private Button botonRealizarVenta;
    
    @FXML
    private TableColumn<Producto, String> columnaColor;

    @FXML
    private TableColumn<Producto, String> columnaTipoAccesorio;

    @FXML
    private TableColumn<Producto, String> columnaMarca;

    @FXML
    private TableColumn<Producto, Integer> columnaPedido;

    @FXML
    private TableColumn<Producto, Double> columnaPrecio;

    @FXML
    private TableColumn<Producto, Integer> columnaStock;

    @FXML
    private TableColumn<Producto, Integer> columnaTamañoCalzado;

    @FXML
    private TableColumn<Producto, String> columnaTamañoPrendaSuperior;

    @FXML
    private TableColumn<Producto, String> columnaTela;

    @FXML
    private TableColumn<Producto, String> columnaTipoObjeto;

    @FXML
    private TableView<Producto> tablaObjetos;

    public static Producto productoSeleccionado = null;
    public static Inventario<Producto> inventario = new Inventario<>();
    private ArrayList<Predicate<? super Producto>> listaFiltros = new ArrayList<>();
    private ArrayList<Producto> listaFiltrada = inventario.filtrar(inventario.leerLista(), listaFiltros);
    
    @FXML
    private void initialize() {
	columnaTipoObjeto.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getClass().getSimpleName()));
	columnaPrecio.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().precio).asObject());
	columnaStock.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().cantidadStock).asObject());
	columnaPedido.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().cantidadPedida).asObject());
	columnaTamañoPrendaSuperior.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof PrendaSuperior prendaSuperior) {
                TamanioPrendaSuperior tamanio = prendaSuperior.tamanio;
                return new javafx.beans.property.SimpleStringProperty(tamanio.name());  
            }
            return new javafx.beans.property.SimpleStringProperty("-");
        });
	columnaTela.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof PrendaSuperior prendaSuperior) {
                Tela tela = prendaSuperior.tipoTela;
                return new javafx.beans.property.SimpleStringProperty(tela.name());  
            }
            return new javafx.beans.property.SimpleStringProperty("-");
        });
	columnaMarca.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Calzado calzado) {
                Marca marca = calzado.marca;
                return new javafx.beans.property.SimpleStringProperty(marca.name());  
            }
            return new javafx.beans.property.SimpleStringProperty("-");
        });
	columnaTamañoCalzado.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Calzado calzado) {
                return new javafx.beans.property.SimpleIntegerProperty(calzado.tamanio).asObject();  
            }
            return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
        });
	columnaTipoAccesorio.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Accesorio accesorio) {
                TipoAccesorio tipo = accesorio.tipo;
                return new javafx.beans.property.SimpleStringProperty(tipo.name());  
            }
            return new javafx.beans.property.SimpleStringProperty("-");
        });
	columnaColor.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Accesorio accesorio) {
                return new javafx.beans.property.SimpleStringProperty(accesorio.color);  
            }
            return new javafx.beans.property.SimpleStringProperty("-");
        });
	
	inventario.crearObjeto(new PrendaSuperior(950, 20, 30, TamanioPrendaSuperior.M, Tela.LINO));
	inventario.crearObjeto(new PrendaSuperior(600, 40, 30, TamanioPrendaSuperior.S, Tela.ALGODON));
	inventario.crearObjeto(new Calzado(500, 10, 20));
	inventario.crearObjeto(new Calzado(900, 15, 20, Marca.CONVERSE, 43));
	inventario.crearObjeto(new Accesorio(500, 10, 20));
	inventario.crearObjeto(new Accesorio(900, 15, 20, "Rojo", TipoAccesorio.GORRA));
	actualizarTabla();
    }
    
    public void actualizarTabla() {
	listaFiltrada = inventario.filtrar(inventario.leerLista(), listaFiltros);
	tablaObjetos.setItems(FXCollections.observableArrayList(listaFiltrada));
    }
    
    @FXML
    void aceptarPedido(ActionEvent event) {

    }

    @FXML
    void actualizarObjeto(ActionEvent event) {
	try {
	productoSeleccionado = tablaObjetos.getSelectionModel().getSelectedItem();
	    
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("ActualizarObjetoDialogo.fxml"));
        Scene escena = new Scene(fxml.load());

	ActualizarObjetoControlador controladorDialogo = fxml.getController();
	Stage dialogo = new Stage();
        dialogo.setTitle("Actualizar Producto");
        dialogo.setScene(escena);

        dialogo.showAndWait();
	actualizarTabla();
	} catch (IOException e) {
    }
    }

    @FXML
    void agregarObjeto(ActionEvent event) {
	try {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("CrearObjetoDialogo.fxml"));
        Scene escena = new Scene(fxml.load());

	CrearObjetoControlador controladorDialogo = fxml.getController();
	Stage dialogo = new Stage();
        dialogo.setTitle("Crear Producto");
        dialogo.setScene(escena);

        dialogo.showAndWait();
	actualizarTabla();
	} catch (IOException e) {
    }
    }

    @FXML
    void eliminarObjeto(ActionEvent event) {
	productoSeleccionado = tablaObjetos.getSelectionModel().getSelectedItem();
	if (productoSeleccionado == null) {
	    mostrarAlerta("No hay Producto seleccionado", "Seleccione un Producto para poder eliminarlo");
	    return;
	}
	
	Alert confirmacion = new Alert(AlertType.CONFIRMATION);
	confirmacion.setTitle("Confirmación de eliminación");
	confirmacion.setHeaderText("¿Seguro que desea eliminar este Producto?");
	confirmacion.setContentText("Producto: " + productoSeleccionado.toString());
	
	Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            inventario.eliminarObjeto(productoSeleccionado);
        }
	actualizarTabla();
    }

    @FXML
    void realizarPedido(ActionEvent event) {

    }

    @FXML
    void realizarVenta(ActionEvent event) {

    }
    
    public static void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
