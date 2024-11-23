import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * La clase {@code Filtrar} proporciona métodos para filtrar los renglones de un
 * archivo CSV
 * basándose en el valor de una columna específica y guardar los resultados en
 * otro archivo.
 */
public class Filtrar {

    /**
     * Filtra las líneas de un archivo CSV y escribe en un nuevo archivo solo
     * aquellas líneas
     * donde el valor en la columna especificada coincide con el valor de filtro
     * dado.
     * 
     * @param archivoEntrada El nombre del archivo de entrada CSV a procesar.
     * @param archivoSalida  El nombre del archivo de salida donde se guardarán las
     *                       líneas filtradas.
     * @param nombreColumna  El nombre de la columna en la que se desea filtrar.
     * @param valorFiltro    El valor que debe coincidir en la columna indicada para
     *                       que la línea sea incluida.
     */
    public static void filtrarPorColumna(String archivoEntrada, String nombreColumna, String valorFiltro) {

        String archivoSalida = nombreColumna + valorFiltro + archivoEntrada;

        try (
                BufferedReader brEntrada = new BufferedReader(new FileReader(archivoEntrada));
                BufferedWriter bwSalida = new BufferedWriter(new FileWriter(archivoSalida))) {
            // Leer el encabezado y determinar el índice de la columna a filtrar
            String encabezado = brEntrada.readLine();
            if (encabezado == null) {
                System.out.println("El archivo está vacío.");
                return;
            }

            String[] columnas = encabezado.split(",");
            int indiceColumna = obtenerIndiceColumna(columnas, nombreColumna);

            if (indiceColumna == -1) {
                System.out.println("La columna '" + nombreColumna + "' no existe en el archivo.");
                return;
            }

            // Escribir el encabezado en el archivo de salida
            bwSalida.write(encabezado); // Escribe la primera línea del archivo CSV (el encabezado) en el archivo de
                                        // salida.
            bwSalida.newLine(); // Agrega un salto de línea después del encabezado, lo que asegura que las
                                // líneas nuevas comienzan en una nueva línea en el archivo.

            // Leer el resto del archivo y filtrar las líneas
            String linea;
            while ((linea = brEntrada.readLine()) != null) { // Lee línea por línea el archivo de entrada hasta llegar
                                                             // al final del archivo.
                String[] valores = linea.split(","); // Divide la línea en partes separadas por comas y las almacena en
                                                     // un arreglo de cadenas llamado 'valores'.

                // Verificar si el valor en la columna específica coincide con el valor de
                // filtro
                if (valores[indiceColumna].equals(valorFiltro)) { // Si el valor en la columna especificada coincide con
                                                                  // el valor de filtro proporcionado:
                    bwSalida.write(linea); // Escribe la línea original en el archivo de salida.
                    bwSalida.newLine(); // Agrega un salto de línea después de escribir la línea para separar los
                                        // registros correctamente.
                }
            }

            System.out.println("Filtrado completado. Archivo de salida: " + archivoSalida);

        } catch (IOException e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
        }
    }

    /**
     * Obtiene el índice de una columna dentro del encabezado del archivo CSV.
     * 
     * @param columnas      El arreglo de nombres de columna (encabezado).
     * @param nombreColumna El nombre de la columna cuya posición se desea obtener.
     * @return El índice de la columna, o -1 si no se encuentra.
     */
    private static int obtenerIndiceColumna(String[] columnas, String nombreColumna) {
        String nombreColumnaLimpiado = nombreColumna.trim().toLowerCase(); // Limpia espacios y convierte a minúsculas
        for (int i = 0; i < columnas.length; i++) {
            String columnaLimpiada = columnas[i].replace("\"", "").trim().toLowerCase(); // Limpia comillas y espacios
            if (columnaLimpiada.equals(nombreColumnaLimpiado)) {
                return i;
            }
        }
        return -1; // Retornar -1 si no se encuentra la columna
    }

    public static void filtrar() {
        // Crear un objeto Scanner para leer los datos del usuario
        Scanner sc = new Scanner(System.in);

        // Mostrar un mensaje inicial
        System.out.println("=====================================================");
        System.out.println("                 FILTRADO DE ARCHIVOS                ");
        System.out.println("=====================================================");

        // Solicitar al usuario el nombre del archivo de entrada
        System.out.println("\n Por favor, ingresa el nombre del archivo que deseas filtrar (incluye la extensión):");
        String nombreArchivoIntroducido = sc.nextLine();

        // Solicitar el nombre de la columna a filtrar
        System.out.println("\n Ingresa el nombre de la columna que deseas filtrar:");
        String nombreColumnaIntroducida = sc.nextLine();

        // Solicitar el valor del filtro
        System.out.println("\n Ingresa el valor que deseas buscar en la columna '" + nombreColumnaIntroducida + "':");
        String nombreValorFiltroIntroducido = sc.nextLine();

        // Formatear el nombre del archivo de salida
        String archivoSalida = nombreColumnaIntroducida + nombreValorFiltroIntroducido + "_" + nombreArchivoIntroducido;

        // Llamar al método para filtrar
        System.out.println("\n Procesando el archivo... Por favor, espera.");
        filtrarPorColumna(nombreArchivoIntroducido, nombreColumnaIntroducida, nombreValorFiltroIntroducido);

        // Mostrar mensaje de éxito
        System.out.println("\n ¡Filtrado completado!");
        System.out.println(" El archivo filtrado se ha guardado como: " + archivoSalida);
        System.out.println("=====================================================");
    }

}
