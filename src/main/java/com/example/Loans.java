package com.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;

public class Loans {
    public int y_position = 25;
    public Loans(){

    }
    public void setAccountScene(Stage stage, SceneManager scene_manager){
        // Declare Widgets
        Button home_button = new Button();
        ArrayList<Label> loans_comic = new ArrayList<Label>();
        loans_comic = get_comics_item_list(scene_manager.get_sign_in_scene().getCustomer().return_id());
        ArrayList<Label> loans_movies = new ArrayList<Label>();
        loans_movies = get_movies_item_list(scene_manager.get_sign_in_scene().getCustomer().return_id());

        // Assign properties
        home_button.setTranslateX(0);
        home_button.setTranslateY(0);
        home_button.setMinWidth(600);
        home_button.setText("Home");
        System.out.println("test");
        Group root = new Group(home_button);

        for (int i = 0; i < loans_comic.size(); i ++){
            root.getChildren().add(loans_comic.get(i));
        }

        for (int i = 0; i < loans_movies.size(); i ++){
            root.getChildren().add(loans_movies.get(i));
        }

        Scene scene = new Scene(root, 600, 300);
        stage.setTitle("Comic and Movie Library Loans!");
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
    }

    public ArrayList<Label> get_movies_item_list(int customer_id){
        String url = "jdbc:mysql://localhost:3306/librarydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // location of database – update for your own implementation
        String username = "root"; // username for database – update for your own implementation
        String password = "root"; // password for database – update for your own implementation
        Connection connection = null;
        ArrayList<Label> loans = new ArrayList<Label>();
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement statement =
                    connection.prepareStatement("SELECT movies.name, customer_movies.earliest_return_date FROM movies," +
                            " customer_movies, customers WHERE movies.idMovies = customer_movies.movies_id AND" +
                            " customer_movies.customer_id = customers.idCustomers AND customers.idCustomers = ?");
            System.out.println();
            statement.setInt(1, customer_id);
            resultSet = statement.executeQuery();
            System.out.println();

            while (resultSet.next()) {
                Label temp_label = new Label();
                temp_label.setTranslateY(y_position);
                y_position += 25;
                temp_label.setText("loaning: " + resultSet.getString("name") + " | earliest return date: " +
                        resultSet.getString("earliest_return_date"));
                loans.add(temp_label);
            }

        } catch (SQLException ex) {
            System.out.println("SQL exception discovered");
            System.out.println(ex);
        }

        return loans;
    }

    public ArrayList<Label> get_comics_item_list( int customer_id){
        String url = "jdbc:mysql://localhost:3306/librarydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // location of database – update for your own implementation
        String username = "root"; // username for database – update for your own implementation
        String password = "root"; // password for database – update for your own implementation

        Connection connection = null;
        ArrayList<Label> loans = new ArrayList<Label>();
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url, username, password);

            PreparedStatement statement =
                    connection.prepareStatement("SELECT comics.name, customer_comics.earliest_return_date FROM comics," +
                            " customer_comics, customers WHERE comics.idComics = customer_comics.comics_id AND" +
                            " customer_comics.customer_id = customers.idCustomers AND customers.idCustomers = ?");

            statement.setInt(1, customer_id);
            resultSet = statement.executeQuery();
            System.out.println();
            while (resultSet.next()) {

                Label temp_label = new Label();
                temp_label.setTranslateY(y_position);
                y_position += 25;
                temp_label.setText("loaning: " + resultSet.getString("name") + " | earliest return date: " +
                        resultSet.getString("earliest_return_date"));
                loans.add(temp_label);
            }

        } catch (SQLException ex) {
            System.out.println("SQL exception discovered");
            System.out.println(ex);
        }

        return loans;
    }
}
