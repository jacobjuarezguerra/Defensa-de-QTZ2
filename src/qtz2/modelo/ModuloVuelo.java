package qtz2.modelo;

/** Instrumento científico con recolección indivisible por ciclo. */
public class ModuloVuelo extends Modulo {
    public enum Instrumento { CAMARA, SENSOR }
    private final Instrumento instrumento;
    private final long datosPorCiclo;
    private final long consumo;
    private long ciclos;

    public ModuloVuelo(String id, String nombre, int salud, boolean activo, double costo,
            Instrumento instrumento, long datosPorCiclo, long consumo, long ciclos) {
        super(id, nombre, salud, activo, costo);
        if (instrumento == null || datosPorCiclo <= 0 || consumo < 0 || ciclos < 0)
            throw new IllegalArgumentException("Parámetros de vuelo inválidos");
        this.instrumento = instrumento; this.datosPorCiclo = datosPorCiclo;
        this.consumo = consumo; this.ciclos = ciclos;
    }
    public Instrumento getInstrumento() { return instrumento; }
    public long getDatosPorCiclo() { return datosPorCiclo; }
    public long getConsumo() { return consumo; }
    public long getCiclos() { return ciclos; }
    public long getTotalRecolectado() { return datosPorCiclo * ciclos; }
    @Override public String procesarCiclo(RecursosMision recursos) {
        if (!isActivo()) return "Inactivo: no recolecta";
        if (recursos.getEnergia() < consumo) return "Energía insuficiente: no recolecta";
        recursos.recolectar(consumo, datosPorCiclo); ciclos++;
        return "Recolectó " + datosPorCiclo + " MB; consumió " + consumo + " de energía";
    }
    @Override public String toString() {
        return super.toString() + " | Vuelo " + instrumento + " | MB/ciclo=" + datosPorCiclo
                + " | consumo=" + consumo + " | ciclos=" + ciclos
                + " | histórico=" + getTotalRecolectado() + " MB";
    }
}
