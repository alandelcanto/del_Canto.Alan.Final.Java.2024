package crud_productos;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ControladorJFX {

    public static Producto productoSeleccionado = null;
    public static Inventario<Producto> inventario = new Inventario<>();
    private static ArrayList<Predicate<? super Producto>> listaFiltros = new ArrayList<>();
    private static String listaFiltrosString = "";

    public static void mostrarInfo(String titulo, String mensaje) {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setTitle(titulo);
	alert.setContentText(mensaje);
	alert.showAndWait();
    }

    public static void mostrarAlerta(String titulo, String mensaje) {
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle(titulo);
	alert.setContentText(mensaje);
	alert.showAndWait();
    }

    public static boolean verificarIntPositivo(String intString) {
	try {
	    int valor = Integer.parseInt(intString);
	    return valor >= 0;
	} catch (NumberFormatException e) {
	    return false;
	}
    }

    @FXML
    private Button botonAceptarPedido;

    @FXML
    private Button botonActualizarObjeto;

    @FXML
    private Button botonAgregarObjeto;

    @FXML
    private Button botonAjustarProducto;

    @FXML
    private Button botonEliminarObjeto;
    
    @FXML
    private Button botonCargarCsv;

    @FXML
    private Button botonCargarDat;

    @FXML
    private Button botonCargarJson;

    @FXML
    private Button botonExportarTxt;

    @FXML
    private Button botonGuardarCsv;

    @FXML
    private Button botonGuardarDat;

    @FXML
    private Button botonGuardarJson;
    
    @FXML
    private Button botonFuncionAumento;

    @FXML
    private Button botonFuncionDescuento;

    @FXML
    private Button botonMantenerStock;

    @FXML
    private Button botonOrdenPedidoAsc;

    @FXML
    private Button botonOrdenPedidoDesc;

    @FXML
    private Button botonOrdenPrecioAsc;

    @FXML
    private Button botonOrdenPrecioDesc;

    @FXML
    private Button botonOrdenStockAsc;

    @FXML
    private Button botonOrdenStockDesc;

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

    @FXML
    private CheckBox checkboxFiltroColor;

    @FXML
    private CheckBox checkboxFiltroMarca;

    @FXML
    private CheckBox checkboxFiltroPedidoMayor;

    @FXML
    private CheckBox checkboxFiltroPedidoMenor;

    @FXML
    private CheckBox checkboxFiltroPrecioMayor;

    @FXML
    private CheckBox checkboxFiltroPrecioMenor;

    @FXML
    private CheckBox checkboxFiltroStockMayor;

    @FXML
    private CheckBox checkboxFiltroStockMenor;

    @FXML
    private CheckBox checkboxFiltroTalla;

    @FXML
    private CheckBox checkboxFiltroTamanio;

    @FXML
    private CheckBox checkboxFiltroTela;

    @FXML
    private CheckBox checkboxFiltroTipoAccesorio;

    @FXML
    private CheckBox checkboxFiltroTipoObjeto;

    @FXML
    private ComboBox<Marca> comboboxFiltroMarca;

    @FXML
    private ComboBox<TamanioPrendaSuperior> comboboxFiltroTalla;

    @FXML
    private ComboBox<Tela> comboboxFiltroTela;

    @FXML
    private ComboBox<TipoAccesorio> comboboxFiltroTipoAccesorio;

    @FXML
    private ComboBox<String> comboboxFiltroTipoObjeto;

    @FXML
    private TextField textfieldFiltroColor;

    @FXML
    private TextField textfieldFiltroPedido;

    @FXML
    private TextField textfieldFiltroPrecio;

    @FXML
    private TextField textfieldFiltroStock;

    @FXML
    private TextField textfieldFiltroTamanio;

    private ArrayList<Producto> listaFiltrada = inventario.filtrar(inventario.leerLista(), listaFiltros);

    public void actualizarTabla() {
	listaFiltrada = inventario.filtrar(inventario.leerLista(), new ArrayList<>(listaFiltros));
	tablaObjetos.setItems(FXCollections.observableArrayList(listaFiltrada));
	tablaObjetos.refresh();
    }

    public int pedirIntProductoSeleccionado(String alertaSeleccion, String headerAlerta, int modo) {
	productoSeleccionado = tablaObjetos.getSelectionModel().getSelectedItem();
	if (productoSeleccionado == null) {
	    mostrarAlerta("No hay Producto seleccionado", alertaSeleccion);
	    return -1;
	}

	TextInputDialog dialogo = new TextInputDialog();
	dialogo.setTitle("Ventana de ingreso de información");
	dialogo.setHeaderText(headerAlerta);
	dialogo.setContentText("Introduzca un número válido:");

	Optional<String> resultado = dialogo.showAndWait();

	if (resultado.isPresent()) {
	    try {
		int valor = Integer.parseInt(resultado.get());
		if (valor > 0 && modo == 1) {
		    return valor;
		} else if (modo == 0) {
		    return valor;
		} else if (valor < 0 && modo == -1) {
		    return valor;
		} else {
		    throw new Exception();
		}
	    } catch (Exception e) {
		mostrarAlerta("Error de ingreso", "No ingresó un número válido");
		return -1;
	    }
	}
	return -1;
    }

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

	comboboxFiltroTipoObjeto.getItems().setAll("PrendaSuperior", "Calzado", "Accesorio");
	comboboxFiltroTalla.getItems().setAll(TamanioPrendaSuperior.values());
	comboboxFiltroTela.getItems().setAll(Tela.values());
	comboboxFiltroMarca.getItems().setAll(Marca.values());
	comboboxFiltroTipoAccesorio.getItems().setAll(TipoAccesorio.values());

	actualizarTabla();
    }

    @FXML
    void aceptarPedido(ActionEvent event) {
	int valor = pedirIntProductoSeleccionado("Seleccione un producto para poder aceptar un pedido", "Ingrese un número de productos a recibir", 1);
	if (valor != -1) {
	    productoSeleccionado.aceptarPedido(valor);
	}
	actualizarTabla();
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
	    dialogo.setResizable(true);

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
	    dialogo.setResizable(false);

	    dialogo.showAndWait();
	    actualizarTabla();
	} catch (IOException e) {
	}
    }

    @FXML
    void ajustarProducto(ActionEvent event) {
	productoSeleccionado = tablaObjetos.getSelectionModel().getSelectedItem();
	if (productoSeleccionado instanceof Ajustable ajustable) {
	    int valor = pedirIntProductoSeleccionado("Seleccione un producto para ajustar", "Ingrese el número de veces a ajustar el producto: (+) Agrandar producto, (-) Achicar producto", 0);
	    try {
		ajustable.ajustar(valor);
	    } catch (AjusteImposibleException ex) {
		mostrarAlerta("Ajuste Imposible", "No es posible ajustar ese producto al valor pedido");
	    }
	} else {
	    mostrarAlerta("Producto no Ajustable", "El Producto seleccionado no es ajustable");
	}
	actualizarTabla();
    }

    @FXML
    void aumentarGlobal(ActionEvent event) {
	int valor = pedirIntProductoSeleccionado("Seleccione un producto para aumentar a todos", "Ingrese el porcentaje de aumento", 1);

	Consumer<Producto> consumer = (Producto producto) -> {
	    double precioAumentado = producto.precio * (1 + (valor / 100.0));

	    BigDecimal precioRedondeado = new BigDecimal(precioAumentado).setScale(2, RoundingMode.HALF_UP);

	    producto.precio = precioRedondeado.doubleValue();

	};

	inventario.forEach(consumer);
	actualizarTabla();
    }

    @FXML
    void descuentoGlobal(ActionEvent event) {
	int valor = pedirIntProductoSeleccionado("Seleccione un producto para descontar a todos", "Ingrese el porcentaje de descuento", 1);

	if (valor > 100) {
	    mostrarAlerta("Descuento Imposible", "El descuento seleccionado supera el 100%");
	    return;
	}

	Consumer<Producto> consumer = (Producto producto) -> {
	    double precioAumentado = producto.precio * (1 - (valor / 100.0));

	    BigDecimal precioRedondeado = new BigDecimal(precioAumentado).setScale(2, RoundingMode.HALF_UP);

	    producto.precio = precioRedondeado.doubleValue();

	};

	inventario.forEach(consumer);
	actualizarTabla();
    }

    @FXML
    void mantenerStock(ActionEvent event) {
	int valor = pedirIntProductoSeleccionado("Seleccione un producto para mantener a todos", "Ingrese el nivel a mantener: Si la cantidad es menor, se piden más", 1);

	Consumer<Producto> consumer = (Producto producto) -> {
	    if (producto.cantidadPedida + producto.cantidadStock < valor) {
		producto.realizarPedido(valor - (producto.cantidadPedida + producto.cantidadStock));
	    }
	};

	inventario.forEach(consumer);
	actualizarTabla();
    }

    @FXML
    void activarFiltros(ActionEvent event) {
	listaFiltrosString = "";
	listaFiltros.clear();
	
	StringBuilder sb = new StringBuilder();
	
	if (checkboxFiltroTipoObjeto.isSelected() && comboboxFiltroTipoObjeto.getValue() != null) {
	    Predicate<Producto> filtroTipoObjeto = (objeto) -> objeto.getClass().getName().replace("crud_productos.", "").equals(comboboxFiltroTipoObjeto.getValue());
	    listaFiltros.add(filtroTipoObjeto);
	    sb.append("TipoObjeto = ").append(comboboxFiltroTipoObjeto.getValue()).append("\n");
	}
	if (checkboxFiltroPrecioMenor.isSelected() && verificarIntPositivo(textfieldFiltroPrecio.getText())) {
	    Predicate<Producto> filtro = (objeto) -> objeto.precio < Integer.parseInt(textfieldFiltroPrecio.getText());
	    listaFiltros.add(filtro);
	    sb.append("Precio < ").append(Integer.parseInt(textfieldFiltroPrecio.getText())).append("\n");
	}
	if (checkboxFiltroPrecioMayor.isSelected() && verificarIntPositivo(textfieldFiltroPrecio.getText())) {
	    Predicate<Producto> filtro = (objeto) -> objeto.precio > Integer.parseInt(textfieldFiltroPrecio.getText());
	    listaFiltros.add(filtro);
	    sb.append("Precio > ").append(Integer.parseInt(textfieldFiltroPrecio.getText())).append("\n");
	}
	if (checkboxFiltroStockMenor.isSelected() && verificarIntPositivo(textfieldFiltroStock.getText())) {
	    Predicate<Producto> filtro = (objeto) -> objeto.cantidadStock < Integer.parseInt(textfieldFiltroStock.getText());
	    listaFiltros.add(filtro);
	    sb.append("Stock < ").append(Integer.parseInt(textfieldFiltroStock.getText())).append("\n");
	}
	if (checkboxFiltroStockMayor.isSelected() && verificarIntPositivo(textfieldFiltroStock.getText())) {
	    Predicate<Producto> filtro = (objeto) -> objeto.cantidadStock > Integer.parseInt(textfieldFiltroStock.getText());
	    listaFiltros.add(filtro);
	    sb.append("Stock > ").append(Integer.parseInt(textfieldFiltroStock.getText())).append("\n");
	}
	if (checkboxFiltroPedidoMenor.isSelected() && verificarIntPositivo(textfieldFiltroPedido.getText())) {
	    Predicate<Producto> filtro = (objeto) -> objeto.cantidadPedida < Integer.parseInt(textfieldFiltroPedido.getText());
	    listaFiltros.add(filtro);
	    sb.append("Pedido < ").append(Integer.parseInt(textfieldFiltroPedido.getText())).append("\n");
	}
	if (checkboxFiltroPedidoMayor.isSelected() && verificarIntPositivo(textfieldFiltroPedido.getText())) {
	    Predicate<Producto> filtro = (objeto) -> objeto.cantidadPedida > Integer.parseInt(textfieldFiltroPedido.getText());
	    listaFiltros.add(filtro);
	    sb.append("Pedido > ").append(Integer.parseInt(textfieldFiltroPedido.getText())).append("\n");
	}
	if (checkboxFiltroTalla.isSelected() && comboboxFiltroTalla.getValue() != null) {
	    Predicate<Producto> filtro = (objeto) -> objeto instanceof PrendaSuperior && ((PrendaSuperior) objeto).tamanio == comboboxFiltroTalla.getValue();
	    listaFiltros.add(filtro);
	    sb.append("Talla = ").append(comboboxFiltroTalla.getValue()).append("\n");
	}
	if (checkboxFiltroTela.isSelected() && comboboxFiltroTela.getValue() != null) {
	    Predicate<Producto> filtro = (objeto) -> objeto instanceof PrendaSuperior && ((PrendaSuperior) objeto).tipoTela == comboboxFiltroTela.getValue();
	    listaFiltros.add(filtro);
	    sb.append("Tela = ").append(comboboxFiltroTela.getValue()).append("\n");
	}
	if (checkboxFiltroMarca.isSelected() && comboboxFiltroMarca.getValue() != null) {
	    Predicate<Producto> filtro = (objeto) -> objeto instanceof Calzado && ((Calzado) objeto).marca == comboboxFiltroMarca.getValue();
	    listaFiltros.add(filtro);
	    sb.append("Marca = ").append(comboboxFiltroMarca.getValue()).append("\n");
	}
	if (checkboxFiltroTamanio.isSelected() && verificarIntPositivo(textfieldFiltroTamanio.getText())) {
	    Predicate<Producto> filtro = (objeto) -> objeto instanceof Calzado && ((Calzado) objeto).tamanio == Integer.parseInt(textfieldFiltroTamanio.getText());
	    listaFiltros.add(filtro);
	    sb.append("Tamaño = ").append(textfieldFiltroTamanio.getText()).append("\n");
	}
	if (checkboxFiltroTipoAccesorio.isSelected() && comboboxFiltroTipoAccesorio.getValue() != null) {
	    Predicate<Producto> filtro = (objeto) -> objeto instanceof Accesorio && ((Accesorio) objeto).tipo == comboboxFiltroTipoAccesorio.getValue();
	    listaFiltros.add(filtro);
	    sb.append("TipoAccesorio = ").append(comboboxFiltroTipoAccesorio.getValue()).append("\n");
	}
	if (checkboxFiltroColor.isSelected()) {
	    Predicate<Producto> filtro = (objeto) -> objeto instanceof Accesorio && ((Accesorio) objeto).color.equals(textfieldFiltroColor.getText());
	    listaFiltros.add(filtro);
	    sb.append("Color = ").append(textfieldFiltroColor.getText()).append("\n");
	}

	actualizarTabla();
	listaFiltrosString = sb.toString();
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
	int valor = pedirIntProductoSeleccionado("Seleccione un producto para poder hacer un pedido", "Ingrese un número de productos a pedir", 1);
	if (valor != -1) {
	    productoSeleccionado.realizarPedido(valor);
	}
	actualizarTabla();
    }

    @FXML
    void realizarVenta(ActionEvent event) {
	int valor = pedirIntProductoSeleccionado("Seleccione un producto para poder hacer una venta", "Ingrese un número de productos a vender", 1);
	if (valor != -1) {
	    try {
		double venta = productoSeleccionado.realizarVenta(valor);
		mostrarInfo("Información de venta", "Se vendieron " + String.valueOf(valor) + " productos por un total de $" + String.valueOf(venta));
	    } catch (ProductosInsuficientesException e) {
		mostrarAlerta("Error al vender", "No hay suficientes productos para realizar una venta");
	    }

	}
	actualizarTabla();
    }

    @FXML
    void ordenarPedidoAsc(ActionEvent event) {
	Collections.sort(inventario.leerLista(), new ProductoPedidoComparator());
	actualizarTabla();
    }

    @FXML
    void ordenarPedidoDesc(ActionEvent event) {
	Collections.sort(inventario.leerLista(), Collections.reverseOrder(new ProductoPedidoComparator()));
	actualizarTabla();
    }

    @FXML
    void ordenarPrecioAsc(ActionEvent event) {
	Collections.sort(inventario.leerLista());
	actualizarTabla();
    }

    @FXML
    void ordenarPrecioDesc(ActionEvent event) {
	Collections.sort(inventario.leerLista(), Collections.reverseOrder());
	actualizarTabla();
    }

    @FXML
    void ordenarStockAsc(ActionEvent event) {
	Collections.sort(inventario.leerLista(), new ProductoStockComparator());
	actualizarTabla();
    }

    @FXML
    void ordenarStockDesc(ActionEvent event) {
	Collections.sort(inventario.leerLista(), Collections.reverseOrder(new ProductoStockComparator()));
	actualizarTabla();
    }
    
    @FXML
    void cargarArchivoCsv(ActionEvent event) {
	FileChooser fc = new FileChooser();
	
	FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("Archivo CSV", "*.csv");
	
	fc.getExtensionFilters().add(csvFilter);
	
	File archivo = fc.showOpenDialog(botonCargarCsv.getScene().getWindow());
	if (archivo == null) {
	    return;
	}
	
	try {
	    inventario.inventario = Serializadora.deserializarCsv(archivo.getAbsolutePath());
	} catch (IOException ex) {
	    mostrarAlerta("Error al cargar archivo", "No se pudo cargar correctamente el archivo");
	}
	
	actualizarTabla();
    }

    @FXML
    void cargarArchivoDat(ActionEvent event) {
	FileChooser fc = new FileChooser();
	
	FileChooser.ExtensionFilter datFilter = new FileChooser.ExtensionFilter("Archivo de datos", "*.dat");
	
	fc.getExtensionFilters().add(datFilter);
	
	File archivo = fc.showOpenDialog(botonCargarDat.getScene().getWindow());
	if (archivo == null) {
	    return;
	}
	
	try {
	    inventario.inventario = Serializadora.deserializar(archivo.getAbsolutePath());
	} catch (IOException | ClassNotFoundException ex) {
	    mostrarAlerta("Error al cargar archivo", "No se pudo cargar correctamente el archivo");
	}
	
	actualizarTabla();
    }

    @FXML
    void cargarArchivoJson(ActionEvent event) {
	FileChooser fc = new FileChooser();
	
	FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("Archivo JSON", "*.json");
	
	fc.getExtensionFilters().add(jsonFilter);
	
	File archivo = fc.showOpenDialog(botonCargarJson.getScene().getWindow());
	if (archivo == null) {
	    return;
	}
	
	try {
	    inventario.inventario = Serializadora.deserializarJson(archivo.getAbsolutePath());
	} catch (IOException ex) {
	    mostrarAlerta("Error al cargar archivo", "No se pudo cargar correctamente el archivo");
	}
	
	actualizarTabla();
    }
    
    @FXML
    void exportarArchivoTxt(ActionEvent event) {
	FileChooser fc = new FileChooser();
	
	FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Archivo de texto", "*.txt");
	
	fc.getExtensionFilters().add(txtFilter);
	
	File archivo = fc.showSaveDialog(botonExportarTxt.getScene().getWindow());
	if (archivo == null) {
	    return;
	}
	
	
	try {
	    Serializadora.exportarTxt(listaFiltrada, archivo.getAbsolutePath(), listaFiltrosString);
	} catch (IOException ex) {
	    mostrarAlerta("Error al exportar archivo", "No se pudo exportar correctamente el archivo");
	}
    }

    @FXML
    void guardarArchivoCsv(ActionEvent event) {
	FileChooser fc = new FileChooser();
	
	FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("Archivo CSV", "*.csv");
	
	fc.getExtensionFilters().add(csvFilter);
	
	File archivo = fc.showSaveDialog(botonGuardarCsv.getScene().getWindow());
	if (archivo == null) {
	    return;
	}
	
	try {
	    Serializadora.serializarCsv(inventario.leerLista(), archivo.getAbsolutePath());
	} catch (IOException ex) {
	    mostrarAlerta("Error al guardar archivo", "No se pudo guardar correctamente el archivo");
	}
    }

    @FXML
    void guardarArchivoDat(ActionEvent event) {
	FileChooser fc = new FileChooser();
	
	FileChooser.ExtensionFilter datFilter = new FileChooser.ExtensionFilter("Archivo de datos", "*.dat");
	
	fc.getExtensionFilters().add(datFilter);
	
	File archivo = fc.showSaveDialog(botonGuardarDat.getScene().getWindow());
	if (archivo == null) {
	    return;
	}
	
	try {
	    Serializadora.serializar(inventario.leerLista(), archivo.getAbsolutePath());
	} catch (IOException ex) {
	    mostrarAlerta("Error al guardar archivo", "No se pudo guardar correctamente el archivo");
	}
    }

    @FXML
    void guardarArchivoJson(ActionEvent event) {
	FileChooser fc = new FileChooser();
	
	FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("Archivo JSON", "*.json");
	
	fc.getExtensionFilters().add(jsonFilter);
	
	File archivo = fc.showSaveDialog(botonGuardarJson.getScene().getWindow());
	if (archivo == null) {
	    return;
	}
	
	try {
	    Serializadora.serializarJson(inventario.leerLista(), archivo.getAbsolutePath());
	} catch (IOException ex) {
	    mostrarAlerta("Error al guardar archivo", "No se pudo guardar correctamente el archivo");
	}
    }

}
