import java.util.Scanner;

public class Menu {

    /**
     * Muestra un menú interactivo con opciones para realizar diversas tareas.
     * El usuario puede elegir entre mostrar archivos de una carpeta, buscar un
     * archivo,
     * obtener las dimensiones de un archivo, o dividir un archivo en subarchivos.
     */
    public static void menuInteractivo() {
        // Título y opciones de manera más estilizada
        System.out.println("\n=========================");
        System.out.println("    O P C I O N E S");
        System.out.println("=========================");
        System.out.println("1. Mostrar los archivos de una carpeta");
        System.out.println("2. Buscar un archivo en una carpeta");
        System.out.println("3. Obtener las dimensiones de un archivo");
        System.out.println("4. Dividir un archivo en subarchivos");
        System.out.println("5. Filtrar un archivo");
        System.out.println("=========================\n ");

        // Crear un objeto Scanner para leer la entrada del usuario
        Scanner sc = new Scanner(System.in);

        // Solicitar al usuario que ingrese la opción deseada
        System.out.print("Por favor ingresa el número de la opción que deseas elegir: \n");
        int opcionSeleccionada = sc.nextInt();

        // Lógica para ejecutar la opción seleccionada
        switch (opcionSeleccionada) {
            case 1:
                Tiempo.iniciar();
                Mostrar.mostrarCarpeta();
                Tiempo.detener();
                Tiempo.mostrarTiempo();
                break;
            case 2:
                Buscar.buscarArchivo();
                Tiempo.mostrarTiempo();
                break;
            case 3:
                Dimensionar.dimensionar();
                Tiempo.mostrarTiempo();
                break;
            case 4:
                Dividir division = new Dividir();
                division.dividir();
                // Método para calcular y mostrar el tiempo transcurrido
                Tiempo.mostrarTiempo();
                break;
            case 5:
                Filtrar.filtrar();
                Tiempo.mostrarTiempo();
                break;
            default:
                // Mensaje de despedida si se ingresa una opción inválida
                System.out.println("\n¡Hasta luego! Opción no válida.");
                break;
        }

        sc.close(); // Cerrar el Scanner para evitar fugas de recursos
    }
}
