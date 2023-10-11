package com.example;

public class Item {
    private String name;
    private String description;
    private int price;
    private int instock;
    public Item(String _name, String _description, int _price,int in_stock){
        name = _name;
        description = _description;
        price = _price;
        instock = in_stock;
    }

    public void change_name(String new_name){
        name = new_name;
    }
    public void change_description(String new_description){
        description = new_description;
    }
    public void change_price(int new_price){
        price = new_price;
    }

    public void change_instock(int in_stock){
        instock = in_stock;
    }

    public String get_name(){return name;}
    public String get_description(){return description;}
    public int get_price(){return price;}
    public int get_instock(){return instock;}
}

class Comic extends Item{
    private String author_fn;
    private String author_ln;
    private String publisher;
    private String serial;
    private int comic_id;

    public Comic(String _name, String _description,String _author_fn,
                 String _author_ln, String _serial, String _publisher, int _price, int _instock, int _comic_id){
        super (_name, _description, _price, _instock);
        author_fn = _author_fn;
        author_ln = _author_ln;
        publisher = _publisher;
        serial = _serial;
        comic_id = _comic_id;
    }
    public void change_author_fn(String new_name){
           author_fn = new_name;
    }
    public void change_author_ln(String new_name){
        author_ln = new_name;
    }
    public void change_publisher(String new_publisher){
        publisher = new_publisher;
    }
    public void change_serial(String new_serial){
        publisher = new_serial;
    }
    public String get_author_fn(){return author_fn;}
    public String get_author_ln(){return author_ln;}
    public String get_publisher(){return publisher;}
    public String get_serial(){return serial;}
    public int get_id(){return comic_id;}
}

class Movie extends Item{
    private String directors;
    private String studio;
    private String release;
    private int movie_id;

        public Movie(String _name, String _description,
                      String _directors, String _studio, String _release, int _price, int in_stock, int _movie_id){
            super(_name, _description, _price, in_stock);
            directors = _directors;
            studio = _studio;
            release = _release;
            movie_id = _movie_id;
        }
        public void change_directors(String new_directors){
            directors = new_directors;
        }
        public void change_studio(String new_studio){
            studio = new_studio;
        }
        public void change_release(String new_release){
            release = new_release;
        }
    public String get_directors(){return directors;}
    public String get_studio(){return studio;}
    public String get_release(){return release;}
    public int get_id(){return movie_id;}

}