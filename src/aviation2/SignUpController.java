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

import java.sql.*;

/**
 * FXML Controller class
 *
 * @author andri
 */
public class SignUpController implements Initializable {

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
    @FXML
    private PasswordField passwordField1;
    @FXML
    private CheckBox showPasswordCheckBox1;

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
    private void onClickConnex(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmedPassword = passwordField1.getText();
        
        if(username.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty()){
            errorLabel.setText("veuillez remplir tous les champs !");
             return;
        }
        if (userExists(username)) {
             errorLabel.setText("Ce nom d'utilisateur est deja utilisé !");
             return;
        }

    // Vérifier que les deux mots de passe sont identiques
        if (!password.equals(confirmedPassword)) {
            errorLabel.setText("Les mots de passes ne correspondent pas !");
            return;
        }
    // Stocker l'utilisateur et le mot de passe hashé en base de données
        storeUser(username, password);
    }

    @FXML
    private void onclickAnnuler(ActionEvent event) {
        usernameField.setText("");
        passwordField.setText("");
        passwordField1.setText("");
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
    private void showPassword1(ActionEvent event) {
        if (showPasswordCheckBox1.isSelected()) {
            passwordField1.setPromptText(passwordField1.getText());
            passwordField1.setText("");
            passwordField1.setDisable(true);
        } else {
            passwordField1.setText(passwordField1.getPromptText());
            passwordField1.setPromptText("");
            passwordField1.setDisable(false);
        }
    }
    //Verification si l'utilisateur existe
    private boolean userExists(String username) {
        try {
        // Connexion à la base de données (à adapter selon votre code)
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/aviation2", "root", "");

        // Préparer la requête SELECT
            PreparedStatement pstmt = con.prepareStatement("SELECT COUNT(*) FROM users WHERE id_user = ?");
            pstmt.setString(1, username);

        // Exécutez la requête SELECT et obtenez le résultat
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

        // Fermer la connexion à la base de données
            con.close();

        // Si le nombre d'utilisateurs avec ce nom d'utilisateur est supérieur à 0, le nom d'utilisateur existe déjà
            return count > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }
    
    //Inserer l'utilisateur à la base
    private void storeUser(String username, String password) {
        try {
        // Connexion à la base de données (à adapter selon votre code)
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/aviation2", "root", "");

        // Préparer la requête INSERT
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO users (id_user, password) VALUES (?, ?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password);

        // Exécutez la requête INSERT
            pstmt.executeUpdate();

        // Fermer la connexion à la base de données
            con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        
            errorLabel.setText("");
            reussiteLabel.setText("Inscription réussit");
    }
    
}
