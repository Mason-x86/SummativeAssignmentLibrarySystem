package com.example;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.sql.*;

public class SignIn {

    private Customer customer;
    private Librarian librarian;

    public SignIn(){
        customer = new Customer("not signed in", "", "", "", 0, 0);
        librarian = new Librarian("not signed in", "", "", "", 0, 0);
    }

    public Customer getCustomer(){
        return customer;
    }

    public Librarian getLibrarian(){
        return librarian;
    }

    public void setAccountScene(Stage stage, SceneManager scene_manager) {

        //Declare Widgets
        Button home_button = new Button();
        Button confirm_button = new Button();
        Button create_account_button = new Button();

        Label email_label = new Label();
        Label password_label = new Label();
        Label create_account_label = new Label();

        TextField email_text_field = new TextField();
        TextField password_text_field = new TextField();

        // enter button properties
        home_button.setTranslateX(0);
        home_button.setTranslateY(0);
        home_button.setMinWidth(600);
        home_button.setText(customer.get_first_name());

        confirm_button.setTranslateX(0);
        confirm_button.setTranslateY(75);
        confirm_button.setMinWidth(600);
        confirm_button.setText("Confirm");

        create_account_button.setTranslateX(300);
        create_account_button.setTranslateY(275);
        create_account_button.setMinWidth(300);
        create_account_button.setText("Create Account!");

        // enter label properties
        email_label.setTranslateX(0);
        email_label.setTranslateY(25);
        email_label.setMinWidth(300);
        email_label.setText("Email");

        password_label.setTranslateX(0);
        password_label.setTranslateY(50);
        password_label.setMinWidth(300);
        password_label.setText("Password");

        create_account_label.setTranslateX(0);
        create_account_label.setTranslateY(275);
        create_account_label.setMinWidth(300);
        create_account_label.setText("Don't own an account?");


        // enter text field properties
        email_text_field.setTranslateX(300);
        email_text_field.setTranslateY(25);
        email_text_field.setMinWidth(300);
        email_text_field.setText("enter email here");

        password_text_field.setTranslateX(300);
        password_text_field.setTranslateY(50);
        password_text_field.setMinWidth(300);
        password_text_field.setText("enter password here");

        Group root = new Group(home_button,confirm_button, email_label,
                email_text_field, password_label, password_text_field, create_account_button,
                create_account_label);
        Scene scene = new Scene(root, 600, 300);
        stage.setTitle("Comic and Movie Library Sign In!");
        stage.setScene(scene);
        stage.show();

        home_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setTitle("Comic and Movie Library Home!");
                System.out.println("main scene");
                stage.setScene(scene_manager.get_main_scene());
            }
        });

        create_account_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setTitle("Comic and Movie Library Create Account!");
                System.out.println("create account scene scene");
                scene_manager.get_create_account_scene().setAccountScene(stage, scene_manager);
            }
        });

        confirm_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String email = new String("");
                String password =  new String("");
                email = email_text_field.getText();
                password = password_text_field.getText();
                customer = get_user_from_email(email, password);
                Group root = new Group(home_button,confirm_button, email_label,
                        email_text_field, password_label, password_text_field, create_account_button,
                        create_account_label);

                home_button.setText(customer.get_first_name());
                Scene scene = new Scene(root, 600, 300);
                System.out.println(home_button);
                stage.setScene(scene);
                stage.show();
                stage.setTitle("Comic and Movie Library Home!");
                System.out.println("main scene");
                stage.setScene(scene_manager.get_main_scene());

            }
        });
    }
    public Customer get_user_from_email(String email, String _password){
        String url = "jdbc:mysql://localhost:3306/librarydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // location of database – update for your own implementation
        String username = "root"; // username for database – update for your own implementation
        String password = "root"; // password for database – update for your own implementation
        Customer customer = new Customer("not signed in", "", "", "", 0, 0);
        Connection connection = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(url, username, password);

             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM customers WHERE email = ?");
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {

                int customer_id = resultSet.getInt("idCustomers");
                String first_name = resultSet.getString("FirstName");
                String last_name = resultSet.getString("LastName");
                String true_password = resultSet.getString("Password");
                System.out.println(resultSet.getString("Password"));
                int credit = resultSet.getInt("Credit");

                if (_password.toString().equals(true_password.toString())){
                    customer = new Customer(first_name, last_name, email, true_password, customer_id, credit);
                    return(customer);
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception discovered");
        }

        System.out.println(customer.get_first_name());
        return customer;
    }
}

