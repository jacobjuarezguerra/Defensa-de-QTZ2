package qtz2.controlador;

import qtz2.modelo.Mision;
import qtz2.vista.VistaConsola;

/** Traduce las opciones del operador en acciones del modelo. */
public class Controlador {
    private final Mision mision;
    private final VistaConsola vista;
    public Controlador(Mision mision, VistaConsola vista) {
        this.mision = mision; this.vista = vista;
    }
    public void ejecutar() {
        vista.mostrar("Misión en progreso. Ciclo actual: " + mision.getCiclo());
        vista.mostrar(mision.getRecursos().toString());
        while (true) {
            String opcion = vista.menu();
            if (opcion == null || opcion.equals("0")) { vista.mostrar("Simulación finalizada."); return; }
            switch (opcion) {
                case "1": vista.mostrarModulos(mision.getModulos()); break;
                case "2":
                    String texto = vista.leer("ID exacto o nombre completo:");
                    if (texto == null) return;
                    vista.mostrarModulos(mision.buscar(texto)); break;
                case "3": vista.mostrarModulos(mision.catalogoPorCosto()); break;
                case "4":
                    for (String resultado : mision.avanzarCiclo()) vista.mostrar(resultado);
                    vista.mostrar("Fin del ciclo " + mision.getCiclo());
                    vista.mostrar(mision.getRecursos().toString()); break;
                case "5": vista.mostrar(mision.resumenComunicaciones()); break;
                default: vista.mostrar("Opción inválida. Ingrese 0, 1, 2, 3, 4 o 5.");
            }
        }
    }
}
