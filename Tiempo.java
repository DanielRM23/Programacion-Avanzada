public class Tiempo {

    private static long inicio; // Variable para almacenar el tiempo de inicio
    private static long fin; // Variable para almacenar el tiempo de fin

    // Método para iniciar el temporizador
    public static void iniciar() {
        inicio = System.currentTimeMillis(); // Captura el tiempo en milisegundos al iniciar
    }

    // Método para detener el temporizador
    public static void detener() {
        fin = System.currentTimeMillis(); // Captura el tiempo en milisegundos al terminar
    }

    // Método para calcular y mostrar el tiempo transcurrido
    public static void mostrarTiempo() {
        long tiempoTranscurrido = fin - inicio; // Calcula la diferencia entre fin e inicio
        long segundos = tiempoTranscurrido / 1000; // Convertir milisegundos a segundos
        long minutos = segundos / 60; // Convertir segundos a minutos
        segundos = segundos % 60; // Obtener los segundos restantes

        System.out.println("\n=====================================================");
        System.out.println("                 TIEMPO DE EJECUCIÓN                 ");
        System.out.println("=====================================================");
        // Mostrar el mensaje con el tiempo transcurrido
        System.out.printf("La tarea ha finalizado con éxito.\nTiempo de procesamiento: %d minutos y %d segundos.\n",
                minutos, segundos);
    }
}
