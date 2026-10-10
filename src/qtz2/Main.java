package qtz2;

import java.util.List;
import qtz2.controlador.Controlador;
import qtz2.modelo.*;
import qtz2.vista.VistaConsola;

/** Punto de entrada y datos iniciales deterministas de una misión en progreso. */
public class Main {
    public static Mision crearMision() {
        return new Mision(List.of(
            new ModuloEnergia("E01", "Panel Aurora", 100, true, 500, 20, 10),
            new ModuloVuelo("V01", "Cámara Quetzal", 95, true, 1200, ModuloVuelo.Instrumento.CAMARA, 15, 8, 8),
            new ModuloTierra("T01", "Antena Central", 100, true, 800, "UVG Central", 10, 5, 60),
            new ModuloVuelo("V02", "Sensor Térmico", 90, true, 700, ModuloVuelo.Instrumento.SENSOR, 10, 6, 6),
            new ModuloEnergia("E02", "Panel Horizonte", 85, true, 450, 12, 7),
            new ModuloTierra("T02", "Antena Altiplano", 95, true, 850, "UVG Altiplano", 12, 6, 60),
            new ModuloVuelo("V03", "Cámara Reserva", 80, false, 1100, ModuloVuelo.Instrumento.CAMARA, 20, 12, 2),
            new ModuloEnergia("E03", "Panel Reserva", 70, false, 400, 8, 3),
            new ModuloVuelo("V04", "Sensor Radiación", 100, true, 950, ModuloVuelo.Instrumento.SENSOR, 5, 25, 4),
            new ModuloTierra("T03", "Antena Sur", 90, false, 750, "UVG Sur", 8, 4, 40)
        ), new RecursosMision(20, 80, 160), 10);
        // Histórico de vuelo: 240 MB = 80 pendientes + 160 descargados.
    }
    public static void main(String[] args) {
        new Controlador(crearMision(), new VistaConsola()).ejecutar();
    }
}
