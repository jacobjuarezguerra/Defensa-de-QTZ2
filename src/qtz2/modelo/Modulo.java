package qtz2.modelo;

import java.util.Objects;

/** Base de los componentes: la operación concreta se resuelve por polimorfismo. */
public abstract class Modulo {
    private final String id;
    private final String nombre;
    private final int salud;
    private boolean activo;
    private final double costo;

    protected Modulo(String id, String nombre, int salud, boolean activo, double costo) {
        if (id == null || id.isBlank() || nombre == null || nombre.isBlank()
                || salud < 0 || salud > 100 || !Double.isFinite(costo) || costo < 0) {
            throw new IllegalArgumentException("Datos de módulo inválidos");
        }
        this.id = id; this.nombre = nombre; this.salud = salud;
        this.activo = activo; this.costo = costo;
    }

    public abstract String procesarCiclo(RecursosMision recursos);
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getSalud() { return salud; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public double getCosto() { return costo; }

    @Override public String toString() {
        return String.format("%s | %s | salud=%d | activo=%s | costo=%.2f",
                id, nombre, salud, activo ? "sí" : "no", costo);
    }
    @Override public final boolean equals(Object otro) {
        return otro instanceof Modulo && id.equals(((Modulo) otro).id);
    }
    @Override public final int hashCode() { return Objects.hash(id); }
}
