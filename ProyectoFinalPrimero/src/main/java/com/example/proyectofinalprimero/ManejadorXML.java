package com.example.proyectofinalprimero;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ManejadorXML {

    private static File FILE;
    private final String rootTagName;
    private Document archivo;

    {
        rootTagName = "Biblioteca";
    }

    public ManejadorXML() throws IOException {
        ManejadorXML.FILE = new File("Biblioteca.xml");
        if (!ManejadorXML.FILE.exists()) ManejadorXML.FILE.createNewFile();
    }

    public ArrayList<Element> listarXML() {
        ArrayList<Element> elements = new ArrayList<>();
        try {
            DocumentBuilderFactory base = DocumentBuilderFactory.newInstance();
            DocumentBuilder estructura = base.newDocumentBuilder();
            archivo = estructura.parse(ManejadorXML.FILE);
            NodeList lista = archivo.getElementsByTagName("Libro");

            for (int i = 0; i < lista.getLength(); i++) {
                Node node = lista.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    elements.add(element);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elements;
    }

    private void eliminarEspacioEnBlanco(Node node) {
        NodeList children = node.getChildNodes();
        for (int i = children.getLength() - 1; i >= 0; i--) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE && child.getNodeValue().trim().isEmpty()) {
                node.removeChild(child);
            } else if (child.hasChildNodes()) {
                eliminarEspacioEnBlanco(child);
            }
        }
    }

    public void eliminarTodosLosLibros() {
        try {
            // Asegurarse de que el archivo XML está cargado en el documento
            if (archivo == null) {
                // Si el archivo no está cargado, lo cargamos
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                archivo = builder.parse(ManejadorXML.FILE);  // Cargar el archivo XML
            }

            // Obtener el nodo raíz <Biblioteca>
            Element biblioteca = archivo.getDocumentElement();

            // Borrar todos los elementos <Libro> dentro de <Biblioteca>
            NodeList libros = biblioteca.getElementsByTagName("Libro");
            while (libros.getLength() > 0) {
                Node libro = libros.item(0);
                biblioteca.removeChild(libro);
            }

            // Guardar los cambios
            System.out.println("Todos los libros han sido eliminados.");
            subirCambios(archivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void crearXML(Libro libro) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder plantilla;
            try {
                plantilla = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            }

            if (ManejadorXML.FILE.length() == 0) {
                archivo = plantilla.newDocument();
                Element raiz = archivo.createElement(rootTagName);
                archivo.appendChild(raiz);
            } else {
                archivo = plantilla.parse(ManejadorXML.FILE);
            }

            Element raiz = archivo.getDocumentElement();
            Element nuevoLibro = archivo.createElement("Libro");
            raiz.appendChild(nuevoLibro);

            Element titulo = archivo.createElement("Titulo");
            titulo.appendChild(archivo.createTextNode(libro.getTitulo()));
            nuevoLibro.appendChild(titulo);

            Element autor = archivo.createElement("Autor");
            autor.appendChild(archivo.createTextNode(libro.getAutor()));
            nuevoLibro.appendChild(autor);

            Element genero = archivo.createElement("Genero");
            genero.appendChild(archivo.createTextNode(libro.getGenero()));
            nuevoLibro.appendChild(genero);

            Element isbn = archivo.createElement("ISBN");
            isbn.appendChild(archivo.createTextNode(String.valueOf(libro.getIsbn())));
            nuevoLibro.appendChild(isbn);

            Element publicacion = archivo.createElement("Publicacion");
            publicacion.appendChild(archivo.createTextNode(String.valueOf(libro.getPublicacion())));
            nuevoLibro.appendChild(publicacion);

            subirCambios(archivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean actualizar(Libro libro) {
        ArrayList<Element> elementos = listarXML();

        Element libroSeleccionado = null;
        for (Element elemento : elementos) {
            String isbn = elemento.getElementsByTagName("ISBN").item(0).getTextContent();
            if (isbn.equals(String.valueOf(libro.getIsbn()))) {
                libroSeleccionado = elemento;
                break;
            }
        }

        if (libroSeleccionado == null) {
            return false;
        }

        if (!libro.getTitulo().isEmpty()) {
            libroSeleccionado.getElementsByTagName("Titulo").item(0).setTextContent(libro.getTitulo());
        }
        if (!libro.getAutor().isEmpty()) {
            libroSeleccionado.getElementsByTagName("Autor").item(0).setTextContent(libro.getAutor());
        }
        if (!libro.getGenero().isEmpty()) {
            libroSeleccionado.getElementsByTagName("Genero").item(0).setTextContent(libro.getGenero());
        }
        if (libro.getIsbn() != 0) {
            libroSeleccionado.getElementsByTagName("ISBN").item(0).setTextContent(String.valueOf(libro.getIsbn()));
        }
        if (libro.getPublicacion() != 0) {
            libroSeleccionado.getElementsByTagName("Publicacion").item(0).setTextContent(String.valueOf(libro.getPublicacion()));
        }

        subirCambios(archivo);
        return false;
    }

    public boolean eliminar(int isbn) {
        ArrayList<Element> elementos = listarXML();

        Element libroSeleccionado = null;
        for (Element elemento : elementos) {
            String isbnElemento = elemento.getElementsByTagName("ISBN").item(0).getTextContent();
            if (isbnElemento.equals(String.valueOf(isbn))) {
                libroSeleccionado = elemento;
                break;
            }
        }

        if (libroSeleccionado != null) {
            Node nodepadre = libroSeleccionado.getParentNode();
            nodepadre.removeChild(libroSeleccionado);
            subirCambios(archivo);
        }
        return false;
    }

    public ArrayList<Libro> buscarLibro(String criterio, String valor) {
        ArrayList<Libro> librosEncontrados = new ArrayList<>();
        ArrayList<Element> elementos = listarXML();

        for (Element libro : elementos) {
            String valorBusqueda = "";

            // Usamos if en lugar de switch para comparar el criterio
            if (criterio.equalsIgnoreCase("titulo")) {
                valorBusqueda = libro.getElementsByTagName("Titulo").item(0).getTextContent();
            } else if (criterio.equalsIgnoreCase("autor")) {
                valorBusqueda = libro.getElementsByTagName("Autor").item(0).getTextContent();
            } else if (criterio.equalsIgnoreCase("genero")) {
                valorBusqueda = libro.getElementsByTagName("Genero").item(0).getTextContent();
            }

            // Comprobamos si el valor buscado coincide con el valor introducido
            if (valorBusqueda.equalsIgnoreCase(valor)) {
                // Si el libro coincide con el criterio, creamos el objeto Libro y lo agregamos
                String titulo = libro.getElementsByTagName("Titulo").item(0).getTextContent();
                String autor = libro.getElementsByTagName("Autor").item(0).getTextContent();
                String genero = libro.getElementsByTagName("Genero").item(0).getTextContent();
                Integer isbn = Integer.parseInt(libro.getElementsByTagName("ISBN").item(0).getTextContent());
                Integer publicacion = Integer.parseInt(libro.getElementsByTagName("Publicacion").item(0).getTextContent());

                Libro libroEncontrado = new Libro(titulo, autor, genero, isbn, publicacion);
                librosEncontrados.add(libroEncontrado);
            }
        }

        return librosEncontrados;  // Devolver la lista de libros encontrados
    }

    public ArrayList<Libro> listarCatalogo() {
        // Obtener los elementos (libros) de la lista XML
        ArrayList<Element> elementos = listarXML();

        // Crear una lista para almacenar los libros
        ArrayList<Libro> libros = new ArrayList<>();

        // Convertir los elementos XML a objetos Libro
        for (Element e : elementos) {
            String titulo = e.getElementsByTagName("Titulo").item(0).getTextContent();
            String autor = e.getElementsByTagName("Autor").item(0).getTextContent();
            String genero = e.getElementsByTagName("Genero").item(0).getTextContent();
            Integer isbn = Integer.parseInt(e.getElementsByTagName("ISBN").item(0).getTextContent());
            Integer publicacion = Integer.parseInt(e.getElementsByTagName("Publicacion").item(0).getTextContent());

            // Crear un objeto Libro y añadirlo a la lista
            Libro libro = new Libro(titulo, autor, genero, isbn, publicacion);
            libros.add(libro);
        }

        // Ordenar la lista de libros por año de publicación
        libros.sort((libro1, libro2) -> Integer.compare(libro1.getPublicacion(), libro2.getPublicacion()));

        // Devolver la lista de libros ordenada
        return libros;
    }

    private void subirCambios(Document archivo) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            eliminarEspacioEnBlanco(archivo.getDocumentElement());
            DOMSource source = new DOMSource(archivo);
            StreamResult result = new StreamResult(ManejadorXML.FILE);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
