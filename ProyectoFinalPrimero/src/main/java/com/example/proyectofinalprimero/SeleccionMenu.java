package org.example;

import java.io.IOException;
import java.util.Scanner;

public class SeleccionMenu {

    public void mostrarMenuPrincipal() throws IOException {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione un menú para comenzar:");
            System.out.println("1. Menú de Gestion");
            System.out.println("2. Menú de Almacenamiento");
            System.out.println("3. Salir" + "\n");

            System.out.print("Elijo la opción: ");
            int opcion = sc.nextInt();

            if (opcion == 1) {
                MenuOpciones menuOpciones = new MenuOpciones();
                menuOpciones.menu();
            } else if (opcion == 2) {
                MenuAlmacenamiento menuAlmacenamiento = new MenuAlmacenamiento();
                menuAlmacenamiento.menu();
            } else if (opcion == 3) {
                System.out.println("Saliendo del programa...");
                break;
            } else {
                System.out.println("Opción no válida, por favor ingrese un número válido.");
            }
        }
    }
}
