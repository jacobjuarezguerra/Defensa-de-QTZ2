package qtz2.modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Administra la única lista principal; las consultas no alteran su orden. */
public class Mision {
    private final ArrayList<Modulo> modulos;
    private final RecursosMision recursos;
    private long ciclo;

    public Mision(List<Modulo> modulos, RecursosMision recursos, long ciclo) {
        if (modulos == null || recursos == null || ciclo < 0)
            throw new IllegalArgumentException("Misión inválida");
        Set<String> ids = new HashSet<>();
        for (Modulo modulo : modulos)
            if (modulo == null || !ids.add(modulo.getId()))
                throw new IllegalArgumentException("Módulo nulo o ID duplicado");
        this.modulos = new ArrayList<>(modulos); this.recursos = recursos; this.ciclo = ciclo;
    }
    public List<Modulo> getModulos() { return List.copyOf(modulos); }
    public RecursosMision getRecursos() { return recursos; }
    public long getCiclo() { return ciclo; }
    public List<Modulo> buscar(String texto) {
        List<Modulo> encontrados = new ArrayList<>();
        for (Modulo modulo : modulos)
            if (modulo.getId().equals(texto.trim()) || modulo.getNombre().equalsIgnoreCase(texto.trim()))
                encontrados.add(modulo);
        return encontrados;
    }
    public List<Modulo> catalogoPorCosto() {
        List<Modulo> copia = new ArrayList<>(modulos);
        copia.sort(Comparator.comparingDouble(Modulo::getCosto));
        return copia;
    }
    public List<String> avanzarCiclo() {
        List<String> resultados = new ArrayList<>();
        ciclo++;
        // Despacho dinámico: no se decide la operación por instanceof ni por tipo.
        for (Modulo modulo : modulos)
            resultados.add(modulo.getId() + " - " + modulo.getNombre() + ": "
                    + modulo.procesarCiclo(recursos));
        return resultados;
    }
    public String resumenComunicaciones() {
        int cantidad = 0, activos = 0;
        long capacidad = 0, historico = 0, maximo = -1;
        List<ModuloTierra> ganadores = new ArrayList<>();
        // El filtro es una consulta específica de tierra, no reemplaza el polimorfismo del ciclo.
        for (Modulo modulo : modulos) {
            if (modulo instanceof ModuloTierra) {
                ModuloTierra tierra = (ModuloTierra) modulo;
                cantidad++; historico += tierra.getTotalDescargado();
                if (tierra.isActivo()) { activos++; capacidad += tierra.getCapacidad(); }
                if (tierra.getTotalDescargado() > maximo) {
                    maximo = tierra.getTotalDescargado(); ganadores.clear();
                }
                if (tierra.getTotalDescargado() == maximo) ganadores.add(tierra);
            }
        }
        StringBuilder informe = new StringBuilder("Módulos de tierra: " + cantidad
                + " | activos: " + activos + "\nCapacidad activa: " + capacidad
                + " MB/ciclo\nHistórico descargado: " + historico + " MB\n");
        if (ganadores.isEmpty()) informe.append("No hay módulos de tierra.");
        else {
            informe.append("Mayor descarga histórica: ").append(maximo).append(" MB\n");
            for (ModuloTierra tierra : ganadores)
                informe.append(tierra.getNombre()).append(" | ").append(tierra.getId())
                        .append(" | estación: ").append(tierra.getEstacion()).append('\n');
        }
        return informe.toString();
    }
}
