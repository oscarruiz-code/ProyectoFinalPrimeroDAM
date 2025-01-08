package com.example.proyectofinalprimero;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Libro {

    private StringProperty titulo;
    private StringProperty autor;
    private StringProperty genero;
    private LongProperty isbn;
    private IntegerProperty publicacion;

    public Libro(String titulo, String autor, String genero, Long isbn, Integer publicacion) {
        this.titulo = new SimpleStringProperty(titulo);
        this.autor = new SimpleStringProperty(autor);
        this.genero = new SimpleStringProperty(genero);
        this.isbn = new SimpleLongProperty(isbn);
        this.publicacion = new SimpleIntegerProperty(publicacion);
    }

    public String getTitulo() {
        return titulo.get();
    }

    public void setTitulo(String titulo) {
        this.titulo.set(titulo);
    }

    public StringProperty tituloProperty() {
        return titulo;
    }

    public String getAutor() {
        return autor.get();
    }

    public void setAutor(String autor) {
        this.autor.set(autor);
    }

    public StringProperty autorProperty() {
        return autor;
    }

    public String getGenero() {
        return genero.get();
    }

    public void setGenero(String genero) {
        this.genero.set(genero);
    }

    public StringProperty generoProperty() {
        return genero;
    }

    public Long getIsbn() {
        return isbn.get();
    }

    public void setIsbn(Long isbn) {
        this.isbn.set(isbn);
    }

    public LongProperty isbnProperty() {
        return isbn;
    }

    public Integer getPublicacion() {
        return publicacion.get();
    }

    public void setPublicacion(Integer publicacion) {
        this.publicacion.set(publicacion);
    }

    public IntegerProperty publicacionProperty() {
        return publicacion;
    }
}
