package qtz2.modelo;

/** Generador solar de capacidad fija, sin modelar baterías. */
public class ModuloEnergia extends Modulo {
    private final long energiaPorCiclo;
    private long ciclos;

    public ModuloEnergia(String id, String nombre, int salud, boolean activo, double costo,
            long energiaPorCiclo, long ciclos) {
        super(id, nombre, salud, activo, costo);
        if (energiaPorCiclo <= 0 || ciclos < 0)
            throw new IllegalArgumentException("Parámetros de energía inválidos");
        this.energiaPorCiclo = energiaPorCiclo; this.ciclos = ciclos;
    }
    public long getEnergiaPorCiclo() { return energiaPorCiclo; }
    public long getCiclos() { return ciclos; }
    public long getTotalGenerado() { return energiaPorCiclo * ciclos; }
    @Override public String procesarCiclo(RecursosMision recursos) {
        if (!isActivo()) return "Inactivo: no genera energía";
        recursos.generar(energiaPorCiclo); ciclos++;
        return "Generó " + energiaPorCiclo + " de energía";
    }
    @Override public String toString() {
        return super.toString() + " | Energía | generación/ciclo=" + energiaPorCiclo
                + " | ciclos=" + ciclos + " | histórico=" + getTotalGenerado();
    }
}
