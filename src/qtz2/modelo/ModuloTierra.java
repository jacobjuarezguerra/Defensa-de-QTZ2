package qtz2.modelo;

/** Antena: una descarga parcial también paga el consumo completo. */
public class ModuloTierra extends Modulo {
    private final String estacion;
    private final long capacidad;
    private final long consumo;
    private long totalDescargado;

    public ModuloTierra(String id, String nombre, int salud, boolean activo, double costo,
            String estacion, long capacidad, long consumo, long totalDescargado) {
        super(id, nombre, salud, activo, costo);
        if (estacion == null || estacion.isBlank() || capacidad <= 0 || consumo < 0 || totalDescargado < 0)
            throw new IllegalArgumentException("Parámetros de tierra inválidos");
        this.estacion = estacion; this.capacidad = capacidad;
        this.consumo = consumo; this.totalDescargado = totalDescargado;
    }
    public String getEstacion() { return estacion; }
    public long getCapacidad() { return capacidad; }
    public long getConsumo() { return consumo; }
    public long getTotalDescargado() { return totalDescargado; }
    @Override public String procesarCiclo(RecursosMision recursos) {
        if (!isActivo()) return "Inactivo: no descarga";
        if (recursos.getEnergia() < consumo) return "Energía insuficiente: no descarga";
        if (recursos.getDatosOrbita() == 0) return "Sin datos en órbita: no descarga";
        long datos = Math.min(capacidad, recursos.getDatosOrbita());
        recursos.descargar(consumo, datos); totalDescargado += datos;
        return "Descargó " + datos + " MB; consumió " + consumo + " de energía";
    }
    @Override public String toString() {
        return super.toString() + " | Tierra | estación=" + estacion + " | MB/ciclo=" + capacidad
                + " | consumo=" + consumo + " | histórico=" + totalDescargado + " MB";
    }
}
