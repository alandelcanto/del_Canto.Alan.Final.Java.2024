/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package crud_productos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

/**
 *
 * @author usuario
 */
public class Main extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
	try {
	FXMLLoader fxml = new FXMLLoader(getClass().getResource("SceneBuilderApp.fxml"));
	TitledPane root = fxml.load();
	Scene scene = new Scene(root);
        stage.setTitle("Gestión de Productos");
        stage.setScene(scene);
	stage.setResizable(false);

        stage.show();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
    }
    
}
