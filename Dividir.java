import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        // Crear subdirectorio "resultados" si no existe
        File directorioResultados = new File(archivoOrigen.getParent(), "resultados");
        if (!directorioResultados.exists()) {
            directorioResultados.mkdirs();
        }

        // Obtener la fecha actual solo una vez para nombrar todos los archivos
        String fechaActual = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());

        // Crear subarchivos y copiar las líneas correspondientes a cada uno
        for (int i = 0; i < numPartes; i++) {
            // Inicia un bucle para procesar y crear cada subarchivo, de acuerdo al número
            // total de partes.

            int inicio = i * renglonesPorParte + 1;
            // Calcula el número de la primera línea que debe copiarse al subarchivo actual.
            // Por ejemplo, si `i` es 0, `inicio` será 1 (la primera línea).

            int fin = Math.min((i + 1) * renglonesPorParte, totalRenglones);
            // Calcula el número de la última línea que debe copiarse al subarchivo actual.
            // Usa `Math.min` para evitar que el rango exceda el número total de líneas.

            String nombreSubarchivo = generarNombreArchivo(origen, fechaActual, i + 1);
            // Genera el nombre del subarchivo, incluyendo el número de parte y la fecha
            // actual.

            File archivoDestino = new File(directorioResultados, nombreSubarchivo);
            // Crea un objeto `File` que apunta al subarchivo que se guardará en el
            // directorio `resultados`.

            copiarPorRango(archivoOrigen.getAbsolutePath(), archivoDestino.getAbsolutePath(), inicio, fin);
            // Llama al método `copiarPorRango` para copiar las líneas del rango
            // especificado (`inicio` a `fin`)
            // desde el archivo de origen al subarchivo destino.

            System.out.printf("Subarchivo creado: %s (Líneas: %d a %d)\n", archivoDestino.getName(), inicio, fin);
            // Imprime en consola un mensaje confirmando la creación del subarchivo,
            // incluyendo su nombre y el rango de líneas copiadas.
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

    /**
     * Método para generar el nombre del archivo con la nomenclatura especificada.
     *
     * @param origen El nombre del archivo de origen.
     * @param parte  El número de la parte actual.
     * @return El nombre del archivo con la fecha actual en el formato requerido.
     */
    private String generarNombreArchivo(String origen, String fechaActual, int parte) {
        // Obtener el nombre base del archivo origen
        String nombreBase = obtenerNombreBase(origen);

        // Construir el nombre del archivo destino
        return String.format("%s_filtered(%s)_parte%d.csv", nombreBase, fechaActual, parte);
    }

    /**
     * Método para obtener el nombre base del archivo (sin extensión).
     *
     * @param nombreArchivo El nombre completo del archivo.
     * @return El nombre base del archivo, sin la extensión.
     */
    private String obtenerNombreBase(String nombreArchivo) {
        // Verificar si el nombre contiene un punto para separar la extensión
        int indiceExtension = nombreArchivo.lastIndexOf('.');
        if (indiceExtension > 0) {
            return nombreArchivo.substring(0, indiceExtension);
        }
        return nombreArchivo; // Si no hay extensión, devolver el nombre completo
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

        // Método para iniciar el temporizador
        Tiempo.iniciar();
        // Llamar al método dividirCSV para realizar la división del archivo
        dividirCSV(nombreArchivoIntroducido, numPartes);
        // Método para detener el temporizador
        Tiempo.detener();
    }
}
