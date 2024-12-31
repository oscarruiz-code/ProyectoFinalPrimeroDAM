package org.example;

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
import java.util.Scanner;

public class ManejadorXML {

    private static File FILE;
    private final String rootTagName;
    private Document archivo;

    {
        rootTagName="Biblioteca";
    }

    public ManejadorXML() throws IOException {
        ManejadorXML.FILE = new File("Biblioteca.xml");
        if(!ManejadorXML.FILE.exists()) ManejadorXML.FILE.createNewFile();
    }
    public ArrayList<Element> listarXML(){
        ArrayList<Element> elements = new ArrayList<>();
        try{
            DocumentBuilderFactory base = DocumentBuilderFactory.newInstance();
            DocumentBuilder estructura = base.newDocumentBuilder();
            archivo = estructura.parse(ManejadorXML.FILE);
            NodeList lista = archivo.getElementsByTagName("Libro");

            for(int i = 0; i<lista.getLength();i++){
                Node node = lista.item(i);
                if(node.getNodeType()==Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    elements.add(element);
                }
            }
        }catch (Exception e){
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

    void crearXML(Libro libro){

        try{
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

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void agregarLibro() {
        try {

            Scanner scanner = new Scanner(System.in);

            System.out.print("Ingrese el título del libro: ");
            String titulo = scanner.nextLine();

            System.out.print("Ingrese el autor del libro: ");
            String autor = scanner.nextLine();

            System.out.print("Ingrese el género del libro: ");
            String genero = scanner.nextLine();

            System.out.print("Ingrese el ISBN del libro: ");
            Integer isbn = scanner.nextInt();

            System.out.print("Ingrese el año de publicación: ");
            Integer publicacion = scanner.nextInt();

            Libro libro = new Libro(titulo, autor, genero, isbn, publicacion);

            crearXML(libro);

            System.out.println("Libro creado correctamente." + "\n" + "-----------------------------"+"\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar() {

        ArrayList<Element> elementos = listarXML();

        System.out.println("Lista de ISBN : ");
        for (int i = 0; i < elementos.size(); i++) {

            String nuestrosIsbn = elementos.get(i).getElementsByTagName("ISBN").item(0).getTextContent();
            System.out.println((i + 1) + ". " + nuestrosIsbn);

        }

        System.out.println("¿Cual quiero modificar?");
        Scanner sc = new Scanner(System.in);
        String isbnSeleccionado = sc.nextLine();

        Element libroSeleccionado = null;
        for (Element elemento : elementos) {
            String isbn = elemento.getElementsByTagName("ISBN").item(0).getTextContent();
            if (isbn.equals(isbnSeleccionado)) {
                libroSeleccionado = elemento;
                break;
            }
        }

        if (libroSeleccionado == null) {
            System.out.println("No se encontró el libro con el isbn seleccionado.");
        }

        System.out.print("Ingrese el nuevo titulo: ");
        String nuevoTitulo = sc.nextLine();
        if (!nuevoTitulo.isEmpty()) {
            libroSeleccionado.getElementsByTagName("Titulo").item(0).setTextContent(nuevoTitulo);
        }

        System.out.print("Ingrese el nuevo autor: ");
        String nuevoAutor = sc.nextLine();
        if (!nuevoAutor.isEmpty()) {
            libroSeleccionado.getElementsByTagName("Autor").item(0).setTextContent(nuevoAutor);
        }

        System.out.print("Ingrese el nuevo género: ");
        String nuevoGenero = sc.nextLine();
        if (!nuevoGenero.isEmpty()) {
            libroSeleccionado.getElementsByTagName("Genero").item(0).setTextContent(nuevoGenero);
        }

        System.out.print("Ingrese el nuevo ISBN: ");
        String nuevoIsbn = sc.nextLine();
        if (!nuevoIsbn.isEmpty()) {
            libroSeleccionado.getElementsByTagName("ISBN").item(0).setTextContent(nuevoIsbn);
        }

        System.out.print("Ingrese el nuevo año de publicación: ");
        String nuevoPublicacion = sc.nextLine();
        if (!nuevoPublicacion.isEmpty()) {
            libroSeleccionado.getElementsByTagName("Publicacion").item(0).setTextContent(nuevoPublicacion);
        }

        subirCambios(archivo);

        System.out.println("Libro actualizado correctamente." + "\n" + "-----------------------------"+"\n");
    }

    public void eliminar() {

        ArrayList<Element> elementos = listarXML();

        System.out.println("Lista de ISBN : ");
        for (int i = 0; i < elementos.size(); i++) {

            String nuestrosIsbn = elementos.get(i).getElementsByTagName("ISBN").item(0).getTextContent();
            System.out.println((i + 1) + ". " + nuestrosIsbn);

        }

        System.out.println("¿Cual quiero modificar?");
        Scanner sc = new Scanner(System.in);
        String isbnSeleccionado = sc.nextLine();

        Element libroSeleccionado = null;
        for (Element elemento : elementos) {
            String isbn = elemento.getElementsByTagName("ISBN").item(0).getTextContent();
            if (isbn.equals(isbnSeleccionado)) {
                libroSeleccionado = elemento;
                break;
            }
        }

        if (libroSeleccionado == null) {
            System.out.println("No se encontró el libro con el isbn seleccionado.");
        }

        Node nodepadre = libroSeleccionado.getParentNode();
        nodepadre.removeChild(libroSeleccionado);

        subirCambios(archivo);

        System.out.println("Libro eliminado correctamente." + "\n" + "-----------------------------"+"\n");

    }

    public void buscarLibro() {

        while (true) {

            System.out.println("Buscar por: ");
            System.out.println("1. Título");
            System.out.println("2. Autor");
            System.out.println("3. Género" + "\n");
            System.out.print("Seleccione por lo que quiere buscar: ");
            Scanner sc = new Scanner(System.in);

            int opcion = sc.nextInt();
            sc.nextLine();
            System.out.print("Ingrese el dato : ");
            String nombre = sc.nextLine();

            ArrayList<Element> elementos = listarXML();
            boolean encontrado = false;

            for (Element libro : elementos) {

                String valor = "";

                if (opcion == 1) {
                    valor = libro.getElementsByTagName("Titulo").item(0).getTextContent();
                } else if (opcion == 2) {
                    valor = libro.getElementsByTagName("Autor").item(0).getTextContent();
                } else if (opcion == 3) {
                    valor = libro.getElementsByTagName("Genero").item(0).getTextContent();
                } else {
                    System.out.println("Opción no válida.");
                }

                if (valor.equalsIgnoreCase(nombre)) {
                    System.out.println("\n" + "Título: " + libro.getElementsByTagName("Titulo").item(0).getTextContent());
                    System.out.println("Autor: " + libro.getElementsByTagName("Autor").item(0).getTextContent());
                    System.out.println("Género: " + libro.getElementsByTagName("Genero").item(0).getTextContent());
                    System.out.println("ISBN: " + libro.getElementsByTagName("ISBN").item(0).getTextContent());
                    System.out.println("Publicación: " + libro.getElementsByTagName("Publicacion").item(0).getTextContent());
                    System.out.println("-----------------------------");
                    encontrado = true;
                }
            }

            if (!encontrado) {
                System.out.println("No se encontraron libros con ese criterio.");
            }

            System.out.println("Libro encontrado correctamente." + "\n" + "-----------------------------"+"\n");
            break;
        }
    }

    public void listarCatalogo() {

        ArrayList<Element> elementos = listarXML();

        elementos.sort((e1, e2) -> {
            int ano1 = Integer.parseInt(e1.getElementsByTagName("Publicacion").item(0).getTextContent());
            int ano2 = Integer.parseInt(e2.getElementsByTagName("Publicacion").item(0).getTextContent());
            return Integer.compare(ano1, ano2);
        });

        for (Element libro : elementos) {

            System.out.println("Título: " + libro.getElementsByTagName("Titulo").item(0).getTextContent());
            System.out.println("Autor: " + libro.getElementsByTagName("Autor").item(0).getTextContent());
            System.out.println("Género: " + libro.getElementsByTagName("Genero").item(0).getTextContent());
            System.out.println("ISBN: " + libro.getElementsByTagName("ISBN").item(0).getTextContent());
            System.out.println("Publicación: " + libro.getElementsByTagName("Publicacion").item(0).getTextContent());
            System.out.println("-----------------------------" + "\n");
        }
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

