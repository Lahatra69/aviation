/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package aviation2;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author andri
**/
public class Login extends Application {

    @Override
    public void start(Stage primaryStage) {
        try{
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            
            Scene scene = new Scene(root);
            
            scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
            primaryStage.setTitle("Compagnie De Vol");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            System.out.println(ex.getCause());
            ex.printStackTrace();
        }
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
