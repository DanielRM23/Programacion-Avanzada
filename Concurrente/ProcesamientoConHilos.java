import java.io.*;
import java.util.concurrent.*;

public class ProcesamientoConHilos {

    private static final int NUM_HILOS = 8; // Número de hilos (ajustar según tu máquina)
    private static final String DIRECTORIO_ARCHIVOS = ".";

    // Método que procesa los archivos con hilos
    public static void procesarArchivos() {
        // Crear un ExecutorService con un número fijo de hilos
        ExecutorService executor = Executors.newFixedThreadPool(NUM_HILOS);

        // Obtener los archivos a procesar (asegurarse de que estén en un directorio
        // conocido)
        File directorio = new File(DIRECTORIO_ARCHIVOS);
        File[] archivos = directorio.listFiles((dir, name) -> name.endsWith(".csv"));

        if (archivos != null) {
            // Asignar a cada archivo un hilo en el pool
            for (File archivo : archivos) {
                ProcesarArchivo tarea = new ProcesarArchivo(archivo.getAbsolutePath());
                executor.submit(tarea);
            }
        }

        // Apagar el executor después de que todas las tareas se hayan completado
        executor.shutdown();

        try {
            // Esperar hasta que todas las tareas terminen
            if (!executor.awaitTermination(60, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
