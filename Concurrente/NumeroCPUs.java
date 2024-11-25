//package hilos;

import java.lang.Runtime;

/**
 * Clase que proporciona un método para obtener el número de CPUs disponibles
 * en la arquitectura del sistema donde se está ejecutando el programa.
 */
public class NumeroCPUs {

    /**
     * Método que obtiene el número de CPUs disponibles en el sistema.
     *
     * @return El número de CPUs disponibles en el sistema.
     */
    public static int numeroCPUs() {
        // Obtener el número de CPUs disponibles utilizando el método de Runtime
        int CPUs = Runtime.getRuntime().availableProcessors();

        // Retornar el número de CPUs
        return CPUs;
    }

}
