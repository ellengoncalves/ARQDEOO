package entities;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorFrota {
    private final List<Veiculo> frota = new ArrayList<>();

    public void adicionarVeiculo(Veiculo v) {
        frota.add(v);
    }

    public Veiculo getVeiculo(int index) {
        if (index >= 0 && index < frota.size()) {
            return frota.get(index);
        }
        return null;
    }

    public int getQuantidade() {
        return frota.size();
    }

    public List<Veiculo> getFrota() {
        // Retorna uma cópia para manter o encapsulamento
        return new ArrayList<>(frota);
    }

    public Veiculo removerVeiculo(int index) {
        if (index >= 0 && index < frota.size()) {
            return frota.remove(index);
        }
        return null;
    }
}