package com.example;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.*;

public class CreateAccount {
    public void setAccountScene(Stage stage, SceneManager scene_manager) {

        //Declare Widgets
        Button home_button = new Button();
        Button confirm_button = new Button();

        Label email_label = new Label();
        Label password_label = new Label();
        Label confirm_password_label = new Label();
        Label first_name = new Label();
        Label last_name = new Label();

        TextField email_text_field = new TextField();
        TextField password_text_field = new TextField();
        TextField confirm_password_text_field = new TextField();
        TextField first_name_text_field = new TextField();
        TextField last_name_text_field = new TextField();

        // enter button properties
        home_button.setTranslateX(0);
        home_button.setTranslateY(0);
        home_button.setMinWidth(600);
        home_button.setText("Home");

        confirm_button.setTranslateX(0);
        confirm_button.setTranslateY(150);
        confirm_button.setMinWidth(600);
        confirm_button.setText("Confirm");

        // enter label properties
        email_label.setTranslateX(0);
        email_label.setTranslateY(25);
        email_label.setMinWidth(300);
        email_label.setText("Email");

        password_label.setTranslateX(0);
        password_label.setTranslateY(50);
        password_label.setMinWidth(300);
        password_label.setText("Password");

        confirm_password_label.setTranslateX(0);
        confirm_password_label.setTranslateY(75);
        confirm_password_label.setMinWidth(300);
        confirm_password_label.setText("Confirm Password");

        first_name.setTranslateX(0);
        first_name.setTranslateY(100);
        first_name.setMinWidth(300);
        first_name.setText("First Name");

        last_name.setTranslateX(0);
        last_name.setTranslateY(125);
        last_name.setMinWidth(300);
        last_name.setText("Last Name");


        // enter text field properties
        email_text_field.setTranslateX(300);
        email_text_field.setTranslateY(25);
        email_text_field.setMinWidth(300);
        email_text_field.setText("enter email here");

        password_text_field.setTranslateX(300);
        password_text_field.setTranslateY(50);
        password_text_field.setMinWidth(300);
        password_text_field.setText("enter password here");

        confirm_password_text_field.setTranslateX(300);
        confirm_password_text_field.setTranslateY(75);
        confirm_password_text_field.setMinWidth(300);
        confirm_password_text_field.setText("enter confirm password here");

        first_name_text_field.setTranslateX(300);
        first_name_text_field.setTranslateY(100);
        first_name_text_field.setMinWidth(300);
        first_name_text_field.setText("first name");

        last_name_text_field.setTranslateX(300);
        last_name_text_field.setTranslateY(125);
        last_name_text_field.setMinWidth(300);
        last_name_text_field.setText("last name");

        Group root = new Group(home_button,confirm_button, email_label,
                email_text_field, password_label, password_text_field, first_name_text_field, first_name, last_name,
                last_name_text_field, confirm_password_label, confirm_password_text_field);

        Scene scene = new Scene(root, 600, 300);
        stage.setTitle("Comic and Movie Library Create Account!");
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

        confirm_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                insert_database_customer(first_name_text_field.getText(), last_name_text_field.getText(),
                        email_text_field.getText(), password_text_field.getText(), 0);
                stage.setTitle("Comic and Movie Library Home!");
                System.out.println("main scene");
                stage.setScene(scene_manager.get_main_scene());
            }
        });
    }

    public static void insert_database_customer(String first_name, String last_name, String email, String password, int credit)
    {
        String url = "jdbc:mysql://localhost:3306/librarydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // location of database – update for your own implementation
        String db_username = "root"; // username for database – update for your own implementation
        String db_password = "root"; // password for database – update for your own implementation
        Connection connection = null;
        try{


            connection = DriverManager.getConnection(url, db_username, db_password);

            String sql = "INSERT INTO customers (FirstName, LastName, Email, Credit, Password) "
                    + "VALUES( ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setInt(4, credit);
            pstmt.setString(5, password);

            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1)
            {

                int idCustomers = 0;
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    idCustomers = rs.getInt(1);

                // process further here
            }
            // create a Statement from the connection
            // Statement statement = connection.createStatement();

            // insert the data
            //statement.executeUpdate();
        }catch (SQLException sqlE)
        {
            System.out.println("failure to connect db");
            System.out.println(sqlE.toString());
        }
        finally
        {
            // step 5: close connection
            System.out.println("Closing connection...");
            if(connection != null)
            {
                try
                {
                    connection.close();
                }
                catch (SQLException ignore)
                {}
                connection = null;
            }
        }

    }
}
