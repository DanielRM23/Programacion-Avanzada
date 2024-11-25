import java.io.File;
import java.util.Scanner;

/**
 * Clase que contiene un método para leer y mostrar los archivos y carpetas
 * dentro de una carpeta específica dada por el usuario.
 */
public class Mostrar {

    /**
     * Método que solicita al usuario el nombre de una carpeta, verifica si
     * existe y es un directorio, y luego lista todos los archivos y carpetas
     * dentro de esa carpeta.
     *
     * @param nombreCarpeta El nombre de la carpeta que el usuario desea leer.
     */
    public static void mostrarCarpeta() {

        // Se crea un objeto de tipo Scanner para la lectura de la ruta del archivo
        // Se crea el lector para capturar la entrada del usuario
        Scanner sc = new Scanner(System.in);

        // Se pide un dato al usuario, el nombre de la carpeta a leer
        System.out.println("\n Por favor ingresa la ruta de la carpeta \n");

        // Se lee el nombre de la carpeta utilizando nextLine() que retorna un String
        // con el dato
        String nombreCarpeta = sc.nextLine();

        // Ruta de la carpeta que se desea leer, se obtiene del nombre ingresado por el
        // usuario
        File carpeta = new File(nombreCarpeta);

        // Verificar si la carpeta existe y si es un directorio
        if (carpeta.exists() && carpeta.isDirectory()) {
            // Lista todos los archivos y carpetas dentro de la carpeta
            File[] archivos = carpeta.listFiles();

            // Verificar si la carpeta contiene archivos o carpetas
            if (archivos != null) {
                // Iterar sobre los archivos y carpetas encontrados
                for (File archivo : archivos) {
                    // Verificar si es un archivo
                    if (archivo.isFile()) {
                        System.out.println("Archivo: " + archivo.getName());
                    }
                    // Verificar si es una subcarpeta
                    else if (archivo.isDirectory()) {
                        System.out.println("Carpeta: " + archivo.getName());
                    }
                    // En caso de que sea otro tipo de archivo (poco probable)
                    else {
                        System.out.println("Archivo: " + archivo.getName());
                    }
                }
            }
        } else {
            // Si la carpeta no existe o no es un directorio, se imprime un mensaje
            System.out.println("\n La carpeta no existe o no es un directorio. \n");
        }

        // Se cierra el objeto Scanner para liberar los recursos
        sc.close();

    }
}
