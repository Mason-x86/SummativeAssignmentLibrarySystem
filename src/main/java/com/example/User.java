package com.example;

public class User {
    private String first_name;
    private String last_name;
    private String email;
    private String password;

    public User(String _first_name, String _last_name, String _email, String _password){
        first_name = _first_name;
        last_name = _last_name;
        email = _email;
        password = _password;
    }

    public void change_first_name(String new_name){
        first_name = new_name;
    }

    public void change_last_name(String new_name){
        last_name = new_name;
    }

    public void change_email(String new_email){
        email = new_email;
    }

    public void change_password(String new_password){
        password = new_password;
    }

    public String get_first_name(){return first_name;}

    public String get_last_name(){return last_name;}

    public String get_email(){return email;}

    public String get_password(){return password;}
}

class Customer extends User{
    private int credit;
    private int customer_id;
    public Customer(String _first_name, String _last_name, String _email,
                    String _password, int _customer_id, int _credit){
        super(_first_name, _last_name, _email, _password);
        customer_id = _customer_id;
        credit = _credit;
    }
    public int return_credit(){ return credit;}
    public int return_id(){return customer_id;}
    public void change_credit(int new_credit){ credit = new_credit;}
}

class Librarian extends User{
    private int salary;
    private int librarian_id;
    public Librarian(String _first_name, String _last_name, String _email,
                     String _password, int _librarian_id, int _salary){
        super(_first_name, _last_name, _email, _password);
        librarian_id = _librarian_id;
        salary = _salary;
    }

    public void change_salary(int new_salary){
        salary = new_salary;
    }

}
