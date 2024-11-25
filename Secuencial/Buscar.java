import java.io.File;
import java.util.Scanner;

/**
 * Clase que contiene métodos para buscar un archivo en un directorio y
 * subdirectorios, dado el nombre del archivo y la ruta del directorio
 * dados por el usuario.
 */
public class Buscar {

    /**
     * Método que solicita al usuario el nombre del archivo y la ruta del directorio
     * donde desea realizar la búsqueda, y luego llama a la función recursiva
     * para realizar la búsqueda.
     *
     * @return El path absoluto del archivo encontrado, o null si no se encuentra.
     */
    public static String buscarArchivo() {
        try (Scanner sc = new Scanner(System.in)) {
            // Se pide al usuario el nombre del archivo
            System.out.println("\nPor favor ingresa el nombre del archivo que deseas buscar:");
            String nombreArchivoIntroducido = sc.nextLine();

            // Se pide al usuario la ruta del directorio
            System.out.println("Por favor ingresa la ruta del directorio donde deseas buscar:");
            String nombreCarpetaIntroducido = sc.nextLine();

            // Crear un objeto File para la ruta del directorio
            File carpeta = new File(nombreCarpetaIntroducido);

            // Método para iniciar el temporizador
            Tiempo.iniciar();

            // Llamada a la función recursiva para buscar el archivo
            String resultado = buscarArchivoRecursivo(carpeta, nombreArchivoIntroducido);
            // Método para iniciar el temporizador

            if (resultado == null) {
                System.out.println("\nNo se encontró el archivo, revisa si está bien escrito.");
            }

            // Método para detener el temporizador
            Tiempo.detener();

            return resultado;
        }
    }

    /**
     * Método recursivo que busca el archivo en el directorio y sus subdirectorios.
     *
     * @param carpeta       El directorio donde se iniciará la búsqueda.
     * @param nombreArchivo El nombre del archivo que se desea buscar.
     * @return El path absoluto del archivo encontrado, o null si no se encuentra.
     */
    public static String buscarArchivoRecursivo(File carpeta, String nombreArchivo) {
        // Verificar si la carpeta es válida
        if (carpeta.exists() && carpeta.isDirectory()) {
            // Listar los archivos y directorios dentro de la carpeta
            File[] archivos = carpeta.listFiles();

            // Si hay archivos en la carpeta
            if (archivos != null) {
                // Iterar sobre todos los archivos y subdirectorios
                for (File archivo : archivos) {
                    // Si es un archivo y contiene el nombre buscado
                    if (archivo.isFile() && archivo.getName().toLowerCase().contains(nombreArchivo.toLowerCase())) {
                        System.out.println("\nArchivo encontrado en:\n " + archivo.getAbsolutePath());
                        return archivo.getAbsolutePath();
                    }

                    // Si es un subdirectorio, hacer la búsqueda recursiva
                    else if (archivo.isDirectory()) {
                        String resultado = buscarArchivoRecursivo(archivo, nombreArchivo);
                        if (resultado != null) {
                            return resultado;
                        }
                    }
                }
            }
        } else {
            System.out.println("\nLa carpeta no existe o no es un directorio válido.");
        }
        // Retornar null si no se encuentra el archivo
        return null;
    }
}
