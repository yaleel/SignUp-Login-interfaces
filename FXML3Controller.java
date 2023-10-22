package se.proj;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FXML3Controller implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label welMsg;

    @FXML
    private Button SignupButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button crAccBtn;

    @FXML
    private TextField fName;

    @FXML
    private TextField lName;

    @FXML
    private TextField natID;

    @FXML
    private TextField pass;

    @FXML
    private TextField passConf;

    @FXML
    private TextField phNum;

    @FXML
    private TextField natIDL;

    @FXML
    private TextField passL;

    ObservableList<user> uList = FXCollections.observableArrayList();
    //ObservableList<natids> IDList = FXCollections.observableArrayList();

    //////////////////FUN
    public void initialize(URL url, ResourceBundle rb) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session = HibernateUtil.getSessionFactory().openSession();
        String queryStr = "from user";
        Query query = session.createQuery(queryStr);
        List aList = query.list();
        session.close();
        uList.addAll(aList);
/*
        Session session1 = HibernateUtil.getSessionFactory().openSession();
        session1 = HibernateUtil.getSessionFactory().openSession();
        String queryStr1 = "from natIDs";
        Query query1 = session1.createQuery(queryStr1);
        List aList1 = query1.list();
        session1.close();
        IDList.addAll(aList1);
*/
    }

    @FXML
    void ButtonLogin(ActionEvent event) throws Exception {

        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ButtonSignUp(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void createAcc(ActionEvent event) throws IOException {

        String fNametxt = fName.getText();
        String lNametxt = lName.getText();
        String natIDtxt = natID.getText();
        String passtxt = pass.getText();
        String phNumtxt = phNum.getText();

        Alert a = new Alert(AlertType.ERROR);
        a.setTitle("Invalid data");

        boolean sim = false;
        for (int i = 0; i < uList.size(); i++) {
            if (natIDtxt.equals(uList.get(i).getId())) {
                sim = true;
                break;
            }
        }
        /*int index = -1;
        for (int i = 0; i < IDList.size(); i++) {
            if (natIDtxt.equals(IDList.get(i).getId())) {
                index = i;
                break;
            }
        }
*/

        // fName       lName       natID          pass          passConf        phNum
        if (fNametxt.matches("[0-9]+") || lNametxt.matches("[0-9]+") || natIDtxt.matches("[a-zA-Z]+") || natIDtxt.length() != 10) {
            a.setContentText("You entered wrong data");
            a.show();
        } else if (sim) {
            a.setContentText("User already exists");
            a.show();
        } else if (!passtxt.equals(passConf.getText())) {
            a.setContentText("Passwords don't match");
            a.show();
        } /*else if (index < 0) {
            a.setContentText("You entered wrong ID");
            a.show();
        } */else {

            user user1 = new user(natIDtxt, fNametxt, lNametxt, phNumtxt, passtxt);

            Session session = HibernateUtil.getSessionFactory().openSession();
            session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            String uId = (String) session.save(user1);
            tx.commit();
            session.close();

            uList.add(user1);

            root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }

    @FXML
    void loginFun(ActionEvent event) throws IOException {

        String IDtxt = natIDL.getText();
        String passtxt = passL.getText();

        int index = -1;
        for (int i = 0; i < uList.size(); i++) {
            if (uList.get(i).getId().equals(IDtxt)) {
                index = i;
                break;
            }
        }
        if (index < 0 || !uList.get(index).getPass().equals(passtxt)) {
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Invalid data");
            a.setContentText("There is no such an account, try again or sign up for new account");
            a.show();
        } else {

            root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("FXML3.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
