package qtz2.modelo;

/** Estado compartido; las operaciones evitan saldos negativos. */
public class RecursosMision {
    private long energia;
    private long datosOrbita;
    private long datosDescargados;

    public RecursosMision(long energia, long datosOrbita, long datosDescargados) {
        if (energia < 0 || datosOrbita < 0 || datosDescargados < 0)
            throw new IllegalArgumentException("Los recursos no pueden ser negativos");
        this.energia = energia; this.datosOrbita = datosOrbita;
        this.datosDescargados = datosDescargados;
    }
    public long getEnergia() { return energia; }
    public long getDatosOrbita() { return datosOrbita; }
    public long getDatosDescargados() { return datosDescargados; }
    public void generar(long cantidad) {
        if (cantidad < 0) throw new IllegalArgumentException("Generación negativa");
        energia += cantidad;
    }
    public void recolectar(long consumo, long datos) {
        if (consumo < 0 || datos < 0 || energia < consumo)
            throw new IllegalArgumentException("Recolección inválida");
        energia -= consumo; datosOrbita += datos;
    }
    public void descargar(long consumo, long datos) {
        if (consumo < 0 || datos <= 0 || energia < consumo || datosOrbita < datos)
            throw new IllegalArgumentException("Descarga inválida");
        energia -= consumo; datosOrbita -= datos; datosDescargados += datos;
    }
    @Override public String toString() {
        return "Energía: " + energia + " | Datos en órbita: " + datosOrbita
                + " MB | Descargados: " + datosDescargados + " MB";
    }
}
