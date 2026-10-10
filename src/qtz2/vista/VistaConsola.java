package qtz2.vista;

import java.util.List;
import java.util.Scanner;
import qtz2.modelo.Modulo;

/** Único componente que lee y escribe en la consola. */
public class VistaConsola {
    private final Scanner entrada = new Scanner(System.in);
    public void mostrar(String texto) { System.out.println(texto); }
    public String leer(String pregunta) {
        mostrar(pregunta);
        return entrada.hasNextLine() ? entrada.nextLine().trim() : null;
    }
    public String menu() {
        return leer("\nDEFENSA DE QTZ2\n1. Listar módulos\n2. Buscar por ID o nombre"
                + "\n3. Catálogo por costo\n4. Avanzar ciclo\n5. Resumen de comunicaciones\n0. Salir\nOpción:");
    }
    public void mostrarModulos(List<Modulo> modulos) {
        if (modulos.isEmpty()) mostrar("No se encontraron módulos.");
        for (Modulo modulo : modulos) mostrar(modulo.toString());
    }
}
