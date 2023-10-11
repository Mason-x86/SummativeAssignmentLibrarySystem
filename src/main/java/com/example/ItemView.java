package com.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.io.FileWriter;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;

import java.security.spec.ECField;
import java.sql.*;
import java.util.ArrayList;
import javafx.scene.image.Image;

import java.util.Calendar;
import java.util.Date;
import java.time.Month;
import java.time.LocalDate;
import java.util.TimeZone;

public class ItemView {
    public void setAccountScene(Stage stage, SceneManager scene_manager){
        //Declare Widgets
        Button home_button = new Button();
        //Declare Text Field
        TextField search_text_field = new TextField();
        TextField Item_name_text_field = new TextField();
        TextField description_text_field = new TextField();

        // enter button properties
        home_button.setTranslateX(0);
        home_button.setTranslateY(0);
        home_button.setMinWidth(600);
        home_button.setText("Home");

        // enter text field
        search_text_field.setTranslateX(0);
        search_text_field.setTranslateY(25);
        search_text_field.setMinWidth(600);
        search_text_field.setText("Search Title");

        Group root = new Group(home_button, search_text_field);
        Scene scene = new Scene(root, 600, 300);
        stage.setTitle("Comic and Movie Item View");
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

    public void set_account_scene_comic(Stage stage, SceneManager scene_manager, Comic comic){
        //Creating an image
        Image image = new Image(Integer.toString(comic.get_id())+ ".jpg");


        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(400);
        imageView.setY(25);

        //setting the fit height and width of the image view
        imageView.setFitHeight(300);
        imageView.setFitWidth(200);
        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        //Declare Text Field
        Label comic_id = new Label();
        Label comic_name_text_field = new Label();
        Label description_text_field = new Label();
        Label first_name = new Label();
        Label last_name = new Label();
        Label serial = new Label();
        Label publisher = new Label();
        Label price = new Label();
        Label instock = new Label();

        Button home_button = new Button();
        Button rent_button = new Button();

        comic_id.setText("");
        comic_name_text_field.setText(comic.get_name());
        description_text_field.setText("summary \n" + comic.get_description());
        first_name.setText("author first name; " + comic.get_author_fn());
        last_name.setText("author last name: " + comic.get_author_ln());
        serial.setText("serial; " + comic.get_serial());
        publisher.setText("publisher; " + comic.get_publisher());
        price.setText("price; " + Integer.toString(comic.get_price()));
        instock.setText("instock; " + Integer.toString(comic.get_instock()));

        // enter button properties
        home_button.setTranslateX(0);
        home_button.setTranslateY(0);
        home_button.setMinWidth(600);
        home_button.setText("Home");
        rent_button.setText("Rent");

        // enter text fields

        comic_id.setTranslateX(0);
        comic_id.setTranslateY(25);
        comic_name_text_field.setTranslateX(0);
        comic_name_text_field.setTranslateY(50);
        description_text_field.setTranslateX(0);
        description_text_field.setTranslateY(75);
        description_text_field.setMaxWidth(600);
        first_name.setTranslateX(0);
        first_name.setTranslateY(200);
        last_name.setTranslateX(0);
        last_name.setTranslateY(225);
        serial.setTranslateX(0);
        serial.setTranslateY(250);
        publisher.setTranslateX(0);
        publisher.setTranslateY(275);
        price.setTranslateX(0);
        price.setTranslateY(300);
        instock.setTranslateX(0);
        instock.setTranslateY(325);
        instock.setMaxWidth(600);


        rent_button.setTranslateX(0);
        rent_button.setTranslateY(350);

        Group root = new Group(home_button, comic_id, comic_name_text_field, description_text_field, first_name,
                last_name, serial, publisher, price, rent_button, instock, imageView);
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Comic and Movie Item View");
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
        rent_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (scene_manager.get_sign_in_scene().getCustomer().return_credit() > 0){
                    insert_relation_comics(scene_manager.get_sign_in_scene().getCustomer().return_id(),
                            comic.get_id());
                    remove_credit(comic.get_price(), scene_manager.get_sign_in_scene().getCustomer().return_id());
                    scene_manager.get_sign_in_scene().getCustomer().change_credit(scene_manager.get_sign_in_scene().getCustomer().return_credit() - comic.get_price());
                    reduce_comic_stock(comic.get_id());
                    create_rent_popup();
                    stage.setTitle("Comic and Movie Library Home!");
                    System.out.println("main scene");
                    stage.setScene(scene_manager.get_main_scene());
                    try {
                        FileWriter myWriter = new FileWriter("receipt.txt");

                        myWriter.append("-------------------------------\n");
                        myWriter.append("name: " + comic.get_name() + "\n");
                        myWriter.append("description: " + comic.get_description() + "\n");
                        myWriter.append("author first name: " + comic.get_author_fn() + "\n");
                        myWriter.append("author last name: " + comic.get_author_ln() + "\n");

                        Date date = new Date(); // your date
                        // Choose time zone in which you want to interpret your Date
                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                        cal.setTime(date);
                        String year = Integer.toString(cal.get(Calendar.YEAR));
                        String month = Integer.toString(cal.get(Calendar.MONTH));
                        String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
                        myWriter.append("return date: " + year + month + day + "\n");
                        myWriter.append("price: " + comic.get_price() + "\n");
                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }

            }
        });
    }
    public void set_account_scene_movie(Stage stage, SceneManager scene_manager, Movie movie) {

        Image image = new Image(Integer.toString(movie.get_id() + 2)+ ".jpg");
        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(400);
        imageView.setY(25);

        //setting the fit height and width of the image view
        imageView.setFitHeight(300);
        imageView.setFitWidth(200);
        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        //Declare Text Field
        Label movie_id = new Label();
        Label movie_name_text_field = new Label();
        Label description_text_field = new Label();
        Label directors = new Label();
        Label studio = new Label();
        Label release = new Label();
        Label price = new Label();
        Label instock = new Label();

        Button home_button = new Button();
        Button rent_button = new Button();

        movie_id.setText("");
        movie_name_text_field.setText(movie.get_name());
        description_text_field.setText("summary \n" + movie.get_description());
        directors.setText("directors; " + movie.get_directors());
        studio.setText("studio; " + movie.get_studio());
        release.setText("release; " + movie.get_release());
        price.setText("price; " + Integer.toString(movie.get_price()));
        instock.setText("instock: " + Integer.toString(movie.get_instock()));

        // enter button properties
        home_button.setTranslateX(0);
        home_button.setTranslateY(0);
        home_button.setMinWidth(600);
        home_button.setText("Home");
        rent_button.setText("Rent");
        // enter text fields
        movie_id.setTranslateX(0);
        movie_id.setTranslateY(25);
        movie_name_text_field.setTranslateX(0);

        movie_name_text_field.setTranslateY(50);

        description_text_field.setTranslateX(0);
        description_text_field.setTranslateY(75);
        description_text_field.setMaxWidth(600);
        directors.setTranslateX(0);
        directors.setTranslateY(200);
        studio.setTranslateX(0);
        studio.setTranslateY(250);
        release.setTranslateX(0);
        release.setTranslateY(275);
        price.setTranslateX(0);
        price.setTranslateY(300);
        instock.setTranslateX(0);
        instock.setTranslateY(325);
        rent_button.setTranslateX(0);
        rent_button.setTranslateY(350);


        Group root = new Group(home_button,movie_id, movie_name_text_field, description_text_field, directors,
                studio, release, price, rent_button, instock, imageView);
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Comic and Movie Item View");
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
        rent_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (scene_manager.get_sign_in_scene().getCustomer().return_credit() > 0){
                    //insert_database_relation;
                    insert_database_relation_movie(scene_manager.get_sign_in_scene().getCustomer().return_id(),
                            movie.get_id());
                    remove_credit(movie.get_price(), scene_manager.get_sign_in_scene().getCustomer().return_id());
                    scene_manager.get_sign_in_scene().getCustomer().change_credit(scene_manager.get_sign_in_scene().getCustomer().return_credit() - movie.get_price());
                    reduce_movie_stock(movie.get_id());
                    create_rent_popup();
                    stage.setTitle("Comic and Movie Library Home!");
                    System.out.println("main scene");
                    stage.setScene(scene_manager.get_main_scene());
                    try {
                        FileWriter myWriter = new FileWriter("receipt.txt");

                        myWriter.write("-------------------------------\n");
                        myWriter.append("name: " + movie.get_name() + "\n");
                        myWriter.append("description: " + movie.get_description() + "\n");
                        myWriter.append("directors: " + movie.get_directors() + "\n");
                        myWriter.append("studio: " + movie.get_studio() + "\n");
                        Date date = new Date(); // the date
                        // the time zone in which to interpret the Date
                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                        cal.setTime(date);
                        String year = Integer.toString(cal.get(Calendar.YEAR));
                        String month = Integer.toString(cal.get(Calendar.MONTH));
                        String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
                        myWriter.append("return date: " + year + month + day + "\n");
                        myWriter.append("price: " + movie.get_price() + "\n");
                        myWriter.close();
                        System.out.println("Successfully wrote to the file.");

                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }
                else{

                }
            }
        });
    }
    public void reduce_movie_stock(int movie_id){
        String url = "jdbc:mysql://localhost:3306/librarydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // location of database – update for your own implementation
        String db_username = "root"; // username for database – update for your own implementation
        String db_password = "root"; // password for database – update for your own implementation
        Connection connection = null;


        try{

            connection = DriverManager.getConnection(url, db_username, db_password);
            String sql = "UPDATE movies SET movies.instock = movies.instock - 1 WHERE movies.idMovies = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, movie_id);
            int rowAffected = pstmt.executeUpdate();

            if (rowAffected == 1)
            {
                int idCustomers = 0;
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    idCustomers = rs.getInt(1);
            }

        }catch (SQLException sqlE)
        {
            System.out.println("failure to connect db");
            System.out.println(sqlE.toString());
        }
        finally
        {
            // close connection
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

    public void reduce_comic_stock(int comic_id){
        String url = "jdbc:mysql://localhost:3306/librarydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // location of database – update for your own implementation
        String db_username = "root"; // username for database – update for your own implementation
        String db_password = "root"; // password for database – update for your own implementation
        Connection connection = null;
        try{

            connection = DriverManager.getConnection(url, db_username, db_password);
            String sql = "UPDATE comics SET comics.instock = comics.instock - 1 WHERE comics.idComics = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, comic_id);
            int rowAffected = pstmt.executeUpdate();

            if (rowAffected == 1)
            {
                int idCustomers = 0;
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    idCustomers = rs.getInt(1);
            }

        }catch (SQLException sqlE)
        {
            System.out.println("failure to connect db");
            System.out.println(sqlE.toString());
        }
        finally
        {
            // close connection
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

    public void insert_relation_comics(int customer_id, int comic_id){
        String url = "jdbc:mysql://localhost:3306/librarydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // location of database – update for your own implementation
        String db_username = "root"; // username for database – update for your own implementation
        String db_password = "root"; // password for database – update for your own implementation
        Connection connection = null;
        try{

            connection = DriverManager.getConnection(url, db_username, db_password);
            String sql = "INSERT INTO customer_comics (comics_id, customer_id, earliest_return_date) "
                    + "VALUES( ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            Date date = new Date(); // date
            // Choose time zone
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            java.sql.Date current_date = new java.sql.Date(year, month + 1, day);
            pstmt.setInt(1, comic_id);
            pstmt.setInt(2, customer_id);
            pstmt.setDate(3, current_date);
            System.out.println(year);
            int rowAffected = pstmt.executeUpdate();

            if (rowAffected == 1)
            {
                int idCustomers = 0;
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    idCustomers = rs.getInt(1);
            }

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

    public static void insert_database_relation_movie(int customer_id, int item_id)
    {
        String url = "jdbc:mysql://localhost:3306/librarydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // location of database – update for your own implementation
        String db_username = "root"; // username for database – update for your own implementation
        String db_password = "root"; // password for database – update for your own implementation
        Connection connection = null;
        try{

            connection = DriverManager.getConnection(url, db_username, db_password);
            String sql = "INSERT INTO customer_movies (movies_id, customer_id, earliest_return_date) "
                    + "VALUES( ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            Date date = new Date(); // date
            // Choose time zone
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            java.sql.Date current_date = new java.sql.Date(year, month + 1, day);
            pstmt.setInt(1, item_id);
            pstmt.setInt(2, customer_id);
            pstmt.setDate(3, current_date);
            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1)
            {

                int idCustomers = 0;
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    idCustomers = rs.getInt(1);
            }

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

    static void remove_credit(int added, int customer_id){
        String url = "jdbc:mysql://localhost:3306/librarydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; // location of database – update for your own implementation
        String db_username = "root"; // username for database – update for your own implementation
        String db_password = "root"; // password for database – update for your own implementation
        Connection connection = null;

        try{

            connection = DriverManager.getConnection(url, db_username, db_password);
            String sql = "UPDATE customers SET customers.Credit = customers.Credit - ? WHERE customers.idCustomers = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, added);
            pstmt.setInt(2, customer_id);
            int rowAffected = pstmt.executeUpdate();

            if (rowAffected == 1)
            {

                int idCustomers = 0;
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    idCustomers = rs.getInt(1);
            }

        }catch (SQLException sqlE)
        {
            System.out.println("failure to connect db");
            System.out.println(sqlE.toString());
        }
        finally
        {
            // close connection
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

    public void create_rent_popup(){
        Stage stage = new Stage();
        Label info_label = new Label();
        info_label.setText("loan complete");
        Group root = new Group(info_label);
        Scene scene = new Scene(root, 300, 300);
        stage.setTitle("Comic and Movie Library Rentals!");
        stage.setScene(scene);
        stage.show();
    }

    public void create_fail_popup(){
        Stage stage = new Stage();
        Label info_label = new Label();
        info_label.setText("need more credit");
        Group root = new Group(info_label);
        Scene scene = new Scene(root, 300, 300);
        stage.setTitle("Comic and Movie Library Rentals!");
        stage.setScene(scene);
        stage.show();
    }
}
