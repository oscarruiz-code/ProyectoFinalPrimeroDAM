package org.example;

import java.util.Scanner;

public class MenuAlmacenamiento {

    AlmacenamientoXML almacenamientoXML;

    {
        almacenamientoXML = new AlmacenamientoXML();
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Guardar en Archivo");
            System.out.println("2. Cargar desde Archivo");
            System.out.println("3. Volver al Menú Principal" + "\n");

            System.out.print("Elijo la opción: ");
            int opcion = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer de entrada

            if (opcion == 1) {
                System.out.print("Ingrese el nombre del archivo para guardar (con extensión .txt): ");
                String nombreArchivo = sc.nextLine();
                almacenamientoXML.guardarEnArchivo(nombreArchivo);
            } else if (opcion == 2) {
                System.out.print("Ingrese el nombre del archivo para cargar (con extensión .txt): ");
                String nombreArchivo = sc.nextLine();
                almacenamientoXML.cargarDesdeArchivo(nombreArchivo);
            } else if (opcion == 3) {
                System.out.println("Volviendo al Menú Principal...");
                break;
            } else {
                System.out.println("Opción no válida, por favor ingrese un número válido.");
            }
        }
    }
}
