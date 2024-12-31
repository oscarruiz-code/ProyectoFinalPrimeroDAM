package org.example;

import java.io.IOException;
import java.util.Scanner;

public class MenuOpciones {

    ManejadorXML manejadorXML;

    {
        try {
            manejadorXML = new ManejadorXML();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Agregar Libro");
            System.out.println("2. Actualizar Libro");
            System.out.println("3. Eliminar Libro");
            System.out.println("4. Buscar Libro");
            System.out.println("5. Listar por año");
            System.out.println("6. Volver al Menú Principal" + "\n");

            System.out.print("Elijo la opción: ");
            int opcion = sc.nextInt();

            if (opcion == 1) {
                manejadorXML.agregarLibro();
            } else if (opcion == 2) {
                manejadorXML.actualizar();
            } else if (opcion == 3) {
                manejadorXML.eliminar();
            } else if (opcion == 4) {
                manejadorXML.buscarLibro();
            } else if (opcion == 5) {
                manejadorXML.listarCatalogo();
            } else if (opcion == 6) {
                System.out.println("Volviendo al Menú Principal...");
                break;
            } else {
                System.out.println("Opción no válida, por favor ingrese un número válido.");
            }
        }
    }
}
