/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package aviation2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author andri
 */
public class AdminLoginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private CheckBox showPasswordCheckBox;
    @FXML
    private Label reussiteLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onclickLogOutbtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

    // Créer une nouvelle scène avec la vue chargée 
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
    // Obtenir la référence de la scène actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    // Changer la scène pour la nouvelle scène 
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onClickConnex(ActionEvent event)throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if(username.isEmpty() || password.isEmpty())
        {
            errorLabel.setText("Veuillez remplir tous les champs !");
        }
        else
        {
            if (username.equals("admin") && password.equals("admin")) {
            // Login successful
                errorLabel.setText("");
                reussiteLabel.setText("Connexion réussie . . .");
                System.out.println("Login successful!");
                showAdminHome(event);
            // Open the admin window or do other tasks as an administrator
            } else {
            // Login failed
                errorLabel.setText("Nom d'Admin Ou Mot de passe Invalide !");
                System.out.println("Invalid username or password");
            }
        }
    }

    @FXML
    private void showPassword(ActionEvent event) {
        if (showPasswordCheckBox.isSelected()) {
            passwordField.setPromptText(passwordField.getText());
            passwordField.setText("");
            passwordField.setDisable(true);
        } else {
            passwordField.setText(passwordField.getPromptText());
            passwordField.setPromptText("");
            passwordField.setDisable(false);
        }
    }

    @FXML
    private void onclickAnnuler(ActionEvent event) {
        usernameField.setText("");
        passwordField.setText("");
    }
    
    private void showAdminHome(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminHome.fxml"));
        Parent root = loader.load();

    // Créer une nouvelle scène avec la vue chargée 
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("adminHome.css").toExternalForm());
    // Obtenir la référence de la scène actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    // Changer la scène pour la nouvelle scène 
        stage.setScene(scene);
        stage.show();
    }
    
}
