import java.io.*;
import java.util.concurrent.Callable;

public class ProcesarArchivo implements Callable<Void> {
    private final String archivoEntrada; // El archivo a procesar
    private static final String CARPETA_SALIDA = "./resultados_concurrentes"; // Ruta de la carpeta de salida

    // Constructor
    public ProcesarArchivo(String archivoEntrada) {
        this.archivoEntrada = archivoEntrada;
    }

    @Override
    public Void call() throws Exception {

        // Crear la carpeta de salida si no existe
        File carpetaSalida = new File(CARPETA_SALIDA);
        if (!carpetaSalida.exists()) {
            if (carpetaSalida.mkdir()) {
                System.out.println("Carpeta de salida creada: " + CARPETA_SALIDA);
            } else {
                System.err.println("No se pudo crear la carpeta de salida.");
                return null;
            }
        }

        // Lógica para procesar el archivo
        try (BufferedReader br = new BufferedReader(new FileReader(archivoEntrada))) {

            // Obtener el nombre del archivo de entrada
            String nombreArchivo = new File(archivoEntrada).getName();
            // Crear el archivo de salida en la carpeta especificada
            File archivoSalida = new File(CARPETA_SALIDA, "resultado_" + nombreArchivo);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))) {

                // Leer el encabezado y obtener el índice de las columnas de interés
                String encabezado = br.readLine();
                if (encabezado == null) {
                    System.out.println("Archivo vacío: " + archivoEntrada);
                    return null;
                }

                String[] columnas = encabezado.split(",");
                int[] indices = obtenerIndicesColumnas(columnas);

                // Escribir el encabezado en el archivo de salida
                bw.write("rank,region,streams,danceability,energy,valence,tempo");
                bw.newLine();

                // Procesar cada línea del archivo
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] valores = linea.split(",");
                    // Extraer solo las columnas que necesitamos
                    String filaFiltrada = String.format("%s,%s,%s,%s,%s,%s,%s",
                            valores[indices[0]], valores[indices[1]], valores[indices[2]],
                            valores[indices[3]], valores[indices[4]], valores[indices[5]], valores[indices[6]]);
                    bw.write(filaFiltrada);
                    bw.newLine();
                }
                System.out.println("Archivo procesado: " + archivoEntrada);

            } catch (IOException e) {
                System.err.println("Error al escribir el archivo de salida: " + archivoSalida.getAbsolutePath() + ": "
                        + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error al procesar el archivo " + archivoEntrada + ": " + e.getMessage());
        }

        return null;
    }

    // Obtener los índices de las columnas de interés
    private int[] obtenerIndicesColumnas(String[] columnas) {
        int[] indices = new int[7];
        String[] columnasDeseadas = { "rank", "region", "streams", "danceability", "energy", "valence", "tempo" };

        for (int i = 0; i < columnasDeseadas.length; i++) {
            for (int j = 0; j < columnas.length; j++) {
                if (columnas[j].equalsIgnoreCase(columnasDeseadas[i])) {
                    indices[i] = j;
                    break;
                }
            }
        }
        return indices;
    }
}
