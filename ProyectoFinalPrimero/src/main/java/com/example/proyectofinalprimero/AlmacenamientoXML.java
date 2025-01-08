package com.example.proyectofinalprimero;

import org.w3c.dom.Element;

import java.io.*;
import java.util.ArrayList;

public class AlmacenamientoXML {

    ManejadorXML manejadorXML;

    // Constructor
    public AlmacenamientoXML() {
        try {
            manejadorXML = new ManejadorXML();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void guardarEnArchivo(String nombreArchivo) {
        ArrayList<Element> elementos = manejadorXML.listarXML();

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (Element libro : elementos) {
                String titulo = libro.getElementsByTagName("Titulo").item(0).getTextContent();
                String autor = libro.getElementsByTagName("Autor").item(0).getTextContent();
                String genero = libro.getElementsByTagName("Genero").item(0).getTextContent();
                String isbn = libro.getElementsByTagName("ISBN").item(0).getTextContent();
                String publicacion = libro.getElementsByTagName("Publicacion").item(0).getTextContent();

                writer.println(titulo + ";" + autor + ";" + genero + ";" + isbn + ";" + publicacion);
            }
            System.out.println("Datos guardados en archivo correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDesdeArchivo(String nombreArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            ArrayList<Libro> libros = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(";");
                String titulo = datos[0];
                String autor = datos[1];
                String genero = datos[2];
                int isbn = Integer.parseInt(datos[3]);
                int publicacion = Integer.parseInt(datos[4]);

                // Crear un nuevo objeto Libro y agregarlo a la lista
                libros.add(new Libro(titulo, autor, genero, isbn, publicacion));
            }

            // Limpiar los libros actuales en el manejador XML
            manejadorXML.eliminarTodosLosLibros();

            // Agregar los libros cargados al manejador XML
            for (Libro libro : libros) {
                manejadorXML.crearXML(libro);  // Crear XML para cada libro cargado.
            }

            System.out.println("Datos cargados correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
