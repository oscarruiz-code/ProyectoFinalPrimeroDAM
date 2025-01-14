package com.example.proyectofinalprimero.model.repository.xml;

import com.example.proyectofinalprimero.model.entity.Libro;
import org.w3c.dom.Element;
import java.io.*;
import java.util.ArrayList;

/**
 * Clase que gestiona el almacenamiento y la recuperación de datos en archivos XML.
 *
 * @version 1.0
 * @autor oscarruiz-code
 */
public class AlmacenamientoXML {

    ManejadorXML manejadorXML;

    /**
     * Constructor de la clase AlmacenamientoXML.
     * Inicializa una nueva instancia de ManejadorXML.
     */
    public AlmacenamientoXML() {
        try {
            manejadorXML = new ManejadorXML();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Guarda los datos en un archivo de texto en el formato especificado.
     *
     * @param nombreArchivo Nombre del archivo donde se guardarán los datos.
     */
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

    /**
     * Carga los datos desde un archivo de texto.
     *
     * @param nombreArchivo Nombre del archivo desde donde se cargarán los datos.
     */
    public void cargarDesdeArchivo(String nombreArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            ArrayList<Libro> libros = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(";");
                String titulo = datos[0];
                String autor = datos[1];
                String genero = datos[2];
                Long isbn = Long.parseLong(datos[3]);
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
