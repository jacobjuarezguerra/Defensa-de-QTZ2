package qtz2.pruebas;

import java.util.List;
import qtz2.Main;
import qtz2.modelo.*;

/** Pruebas ejecutables sin dependencias ni necesidad de habilitar assert. */
public class Pruebas {
    private static int verificaciones;
    private static void verificar(boolean condicion, String mensaje) {
        verificaciones++;
        if (!condicion) throw new AssertionError(mensaje);
    }
    private static ModuloVuelo vuelo(boolean activo) {
        return new ModuloVuelo("V", "Cámara", 100, activo, 100, ModuloVuelo.Instrumento.CAMARA, 15, 8, 0);
    }
    private static ModuloTierra tierra(String id, boolean activo, long total) {
        return new ModuloTierra(id, "Antena", 100, activo, 50, "Central", 10, 5, total);
    }
    public static void main(String[] args) {
        RecursosMision r = new RecursosMision(10, 0, 0);
        ModuloEnergia e = new ModuloEnergia("E", "Panel", 100, true, 200, 20, 0);
        ModuloVuelo v = vuelo(true);
        ModuloTierra t = tierra("T", true, 0);
        Mision m = new Mision(List.of(e, v, t), r, 0);
        verificar(m.avanzarCiclo().size() == 3, "Todos participan");
        verificar(r.getEnergia() == 17 && r.getDatosOrbita() == 5 && r.getDatosDescargados() == 10, "Ejemplo oficial");
        verificar(v.getCiclos() == 1 && e.getCiclos() == 1 && v.getTotalRecolectado() == 15, "Históricos efectivos");
        m.avanzarCiclo();
        verificar(r.getEnergia() == 24 && r.getDatosOrbita() == 10 && r.getDatosDescargados() == 20 && m.getCiclo() == 2, "Persistencia");
        RecursosMision poco = new RecursosMision(7, 0, 0);
        ModuloVuelo limitado = vuelo(true);
        verificar(limitado.procesarCiclo(poco).contains("insuficiente"), "Causa de fallo");
        verificar(poco.getEnergia() == 7 && poco.getDatosOrbita() == 0 && limitado.getCiclos() == 0, "Sin recolección parcial");
        RecursosMision parcial = new RecursosMision(5, 3, 0);
        tierra("P", true, 0).procesarCiclo(parcial);
        verificar(parcial.getEnergia() == 0 && parcial.getDatosOrbita() == 0 && parcial.getDatosDescargados() == 3, "Descarga parcial cobra completo");
        RecursosMision vacio = new RecursosMision(10, 0, 0);
        verificar(t.procesarCiclo(vacio).contains("Sin datos") && vacio.getEnergia() == 10, "Sin datos no consume");
        RecursosMision insuficiente = new RecursosMision(4, 20, 0);
        verificar(t.procesarCiclo(insuficiente).contains("insuficiente") && insuficiente.getDatosOrbita() == 20 && insuficiente.getEnergia() == 4, "Tierra sin energía");
        ModuloVuelo inactivo = vuelo(false);
        ModuloEnergia apagado = new ModuloEnergia("X", "Apagado", 50, false, 1, 8, 2);
        inactivo.procesarCiclo(vacio); apagado.procesarCiclo(vacio); tierra("I", false, 0).procesarCiclo(vacio);
        verificar(vacio.getEnergia() == 10 && vacio.getDatosOrbita() == 0 && inactivo.getCiclos() == 0 && apagado.getCiclos() == 2, "Inactivos");
        verificar(m.catalogoPorCosto().get(0) == t && m.getModulos().get(0) == e, "Catálogo preserva orden");
        verificar(m.buscar("V").get(0) == v && m.buscar("cÁmArA").get(0) == v && m.buscar("nadie").isEmpty(), "Búsquedas");
        Mision empate = new Mision(List.of(tierra("A", true, 20), tierra("B", false, 20)), new RecursosMision(0, 0, 40), 1);
        String resumen = empate.resumenComunicaciones();
        verificar(resumen.contains("| A |") && resumen.contains("| B |"), "Todos los empates");
        verificar(resumen.contains("activos: 1") && resumen.contains("Capacidad activa: 10") && resumen.contains("Histórico descargado: 40"), "Resumen");
        Mision orden = new Mision(List.of(tierra("Primero", true, 0), vuelo(true)), new RecursosMision(20, 0, 0), 0);
        orden.avanzarCiclo();
        verificar(orden.getRecursos().getDatosDescargados() == 0 && orden.getRecursos().getDatosOrbita() == 15, "Orden de participación");
        Mision inicial = Main.crearMision();
        verificar(inicial.getModulos().size() == 10, "Diez módulos");
        for (int i = 0; i < 20; i++) {
            long recolectado = 0, descargado = 0;
            for (Modulo modulo : inicial.getModulos()) {
                if (modulo instanceof ModuloVuelo) recolectado += ((ModuloVuelo) modulo).getTotalRecolectado();
                if (modulo instanceof ModuloTierra) descargado += ((ModuloTierra) modulo).getTotalDescargado();
            }
            verificar(recolectado == inicial.getRecursos().getDatosOrbita() + descargado
                && descargado == inicial.getRecursos().getDatosDescargados()
                && inicial.getRecursos().getEnergia() >= 0, "Conservación de recursos");
            inicial.avanzarCiclo();
        }
        boolean rechazo = false;
        try { new Mision(List.of(v, vuelo(false)), r, 0); }
        catch (IllegalArgumentException ex) { rechazo = true; }
        verificar(rechazo, "IDs únicos");
        System.out.println("OK: " + verificaciones + " verificaciones superadas.");
    }
}
