import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase que proporciona métodos para determinar las dimensiones de un archivo
 * CSV,
 * incluyendo el número de renglones y columnas.
 * También incluye un método interactivo para calcular estas dimensiones.
 */
public class Dimensionar {

    /**
     * Cuenta el número de renglones en un archivo CSV especificado.
     *
     * @param rutaArchivo La ruta del archivo CSV cuya cantidad de renglones se
     *                    desea contar.
     * @return El número total de renglones en el archivo.
     */
    public static int contarRenglonesCSV(String rutaArchivo) {
        int numeroRenglones = 0; // Inicializar el contador de renglones

        // Intentar leer el archivo usando BufferedReader
        try (FileReader objeto = new FileReader(rutaArchivo);
                BufferedReader br = new BufferedReader(objeto)) {

            // Leer cada línea del archivo y aumentar el contador
            while (br.readLine() != null) {
                numeroRenglones++;
            }
        } catch (IOException e) {
            // En caso de error, imprimir el stack trace
            e.printStackTrace();
        }

        // Retornar el total de renglones contados
        return numeroRenglones;
    }

    /**
     * Cuenta el número de columnas en el primer renglón de un archivo CSV
     * especificado.
     *
     * @param rutaArchivo La ruta del archivo CSV cuya cantidad de columnas se desea
     *                    contar.
     * @return El número de columnas en el archivo, basado en la primera línea.
     */
    public static int contarColumnasCSV(String rutaArchivo) {
        int numeroColumnas = 0; // Inicializar el contador de columnas

        // Intentar leer el archivo usando BufferedReader
        try (FileReader objeto = new FileReader(rutaArchivo);
                BufferedReader br = new BufferedReader(objeto)) {

            // Leer la primera línea del archivo
            String primeraLinea = br.readLine();

            // Si la primera línea no es nula, contar las columnas separadas por comas
            if (primeraLinea != null) {
                String[] nombreColumnas = primeraLinea.split(","); // Dividir por comas
                numeroColumnas = nombreColumnas.length; // Contar el número de elementos
            }
        } catch (IOException e) {
            // En caso de error, imprimir el stack trace
            e.printStackTrace();
        }

        // Retornar el número de columnas contadas
        return numeroColumnas;
    }

    /**
     * Método interactivo que solicita al usuario un archivo mediante la clase
     * {@link Buscar},
     * y muestra el número de renglones y columnas del archivo CSV.
     */
    public static void dimensionar() {
        // Solicitar al usuario que seleccione un archivo usando la clase Buscar
        String rutaArchivo = Buscar.buscarArchivo();

        // Contar renglones y columnas en el archivo
        int numeroRenglones = contarRenglonesCSV(rutaArchivo);
        int numeroColumnas = contarColumnasCSV(rutaArchivo);

        // Mostrar las dimensiones del archivo al usuario
        System.out.println("\n---------------------------------------------");
        System.out.println("Cálculo de las dimensiones del archivo");
        System.out.println("---------------------------------------------\n");
        System.out.println("\n Procesando el archivo... Por favor, espera.");
        System.out.println("Número de renglones: " + numeroRenglones);
        System.out.println("Número de columnas: " + numeroColumnas);
    }
}
