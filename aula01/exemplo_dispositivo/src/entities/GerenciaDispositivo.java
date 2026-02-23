package entities;

import java.util.ArrayList;
import java.util.List;

public class GerenciaDispositivo {
    private List<Dispositivo> dispositivos;

    public GerenciaDispositivo() {
        dispositivos = new ArrayList<>();
    }

    public void adicionarDispositivo(Dispositivo dispositivo) {
        dispositivos.add(dispositivo);
    }

    public Dispositivo getDispositivo(int index) {
        if (index >= 0 && index < dispositivos.size()) {
            return dispositivos.get(index);
        }
        return null;
    }

    public List<Dispositivo> getDispositivos() {
        return dispositivos;
    }
}