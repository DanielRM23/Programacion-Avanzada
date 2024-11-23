import java.io.*;
import java.util.Scanner;

/**
 * Clase que implementa la interfaz IArchivo y proporciona métodos para dividir
 * un archivo CSV
 * en varias partes y copiar un rango de líneas a subarchivos.
 */
public class Dividir implements IArchivo {

    /**
     * Método para dividir un archivo CSV en varias partes. Calcula el número de
     * líneas por parte
     * y crea subarchivos con las líneas correspondientes.
     *
     * @param origen    El nombre del archivo CSV de origen que se va a dividir.
     * @param numPartes El número de partes en las que se dividirá el archivo.
     */
    public void dividirCSV(String origen, int numPartes) {
        // Crear un objeto File para el archivo de origen
        File archivoOrigen = new File(RUTA_DEFAULT, origen);

        // Verificar si el archivo existe
        if (!verificarExistencia(archivoOrigen)) {
            System.out.printf("Archivo %s no existe.\n", origen);
            return;
        }

        // Contar el número total de renglones en el archivo CSV
        int totalRenglones = Dimensionar.contarRenglonesCSV(archivoOrigen.getAbsolutePath());

        // Calcular cuántos renglones debe tener cada subarchivo
        int renglonesPorParte = (int) Math.ceil((double) totalRenglones / numPartes);

        // Crear subarchivos y copiar las líneas correspondientes a cada uno
        for (int i = 0; i < numPartes; i++) {
            int inicio = i * renglonesPorParte + 1;
            int fin = Math.min((i + 1) * renglonesPorParte, totalRenglones);
            String nombreSubarchivo = "subarchivo" + (i + 1) + "_" + origen;
            copiarPorRango(archivoOrigen.getAbsolutePath(), nombreSubarchivo, inicio, fin);
            System.out.printf("Subarchivo creado: %s (Líneas: %d a %d)\n", nombreSubarchivo, inicio, fin);
        }
    }

    /**
     * Método que copia las líneas de un archivo de acuerdo a un rango especificado.
     *
     * @param origen  La ruta del archivo de origen desde donde se copiarán las
     *                líneas.
     * @param destino La ruta del archivo de destino donde se copiarán las líneas.
     * @param inicio  El número de la primera línea a copiar.
     * @param fin     El número de la última línea a copiar.
     */
    private void copiarPorRango(String origen, String destino, int inicio, int fin) {
        try (BufferedReader reader = new BufferedReader(new FileReader(origen));
                BufferedWriter writer = new BufferedWriter(new FileWriter(destino))) {

            int contador = 0;
            String linea;

            // Leer las líneas del archivo de origen y escribir las líneas dentro del rango
            while ((linea = reader.readLine()) != null) {
                contador++;
                // Si la línea está dentro del rango, escribirla en el archivo de destino
                if (contador >= inicio && contador <= fin) {
                    writer.write(linea);
                    writer.newLine();
                }
                // Salir del bucle cuando se hayan copiado todas las líneas del rango
                if (contador > fin)
                    break;
            }

        } catch (IOException e) {
            System.err.printf("Error al copiar líneas %d a %d: %s\n", inicio, fin, e.getMessage());
        }
    }

    @Override
    public void copiar(String origen) {
        // Implementación vacía si no se utiliza
    }

    @Override
    public void copiar(String origen, String destino) {
        // Implementación vacía si no se utiliza
    }

    /**
     * Método principal para dividir el archivo CSV según el número de CPUs
     * detectadas por la JVM.
     * Calcula el número de subarchivos que se crearán y llama a la función
     * dividirCSV para realizar
     * la división del archivo.
     */
    public void dividir() {
        // Crear un objeto Scanner para leer los datos del usuario
        Scanner sc = new Scanner(System.in);

        // Solicitar al usuario el nombre del archivo que se desea dividir
        System.out.println("\n Por favor ingresa el nombre del archivo que deseas dividir: \n");

        // Leer el nombre del archivo ingresado por el usuario
        String nombreArchivoIntroducido = sc.nextLine();

        // Obtener el número de CPUs disponibles en la arquitectura actual
        int numCPUs = NumeroCPUs.numeroCPUs();

        // Calcular el número de partes en las que se dividirá el archivo
        int numPartes = 4 * numCPUs; // Dividir el archivo en al menos 4 veces el número de CPUs

        // Mostrar información sobre las divisiones
        System.out.println("\n---------------------------------------------");
        System.out.println("Cálculo de divisiones del archivo:");
        System.out.println("---------------------------------------------");
        System.out.println("Número de CPU's detectados por la JVM: " + numCPUs);
        System.out.println("Multiplicando por 4 para aprovechar concurrencia: " + (numCPUs * 4));
        System.out.println("El archivo se dividirá en " + numPartes + " subarchivos.");
        System.out.println("---------------------------------------------\n");
        System.out.println("\n Procesando el archivo... Por favor, espera.");
        System.out.println("---------------------------------------------\n");

        // Llamar al método dividirCSV para realizar la división del archivo
        dividirCSV(nombreArchivoIntroducido, numPartes);
    }
}
