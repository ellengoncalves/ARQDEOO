package venda_ingresso.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import venda_ingresso.entities.Ingresso;
import venda_ingresso.enums.SetorEnum;
import venda_ingresso.exceptions.SetorEsgotadoException;

public class GerenciadorIngresso {
    private static final int LIMITE_POR_SETOR = 10;

    private int prox = 1;
    private List<Ingresso> ingressos;
    private Map<String, Integer> contadorPorSetor;

    public GerenciadorIngresso() {
        this.ingressos = new ArrayList<>();
        this.contadorPorSetor = new HashMap<>();
        for (SetorEnum setor : SetorEnum.values()) {
            contadorPorSetor.put(setor.getNome(), 0);
        }
    }

    public boolean comprarIngresso(String setor, int quantidade) {
        int vendidosNoSetor = contadorPorSetor.getOrDefault(setor, 0);
        if (vendidosNoSetor + quantidade > LIMITE_POR_SETOR) {
            throw new SetorEsgotadoException("Limite de ingressos para o setor " + setor + " atingido.");
        }

        Ingresso ingresso = new Ingresso(prox++, setor, getValorSetor(setor), quantidade);
        ingressos.add(ingresso);
        contadorPorSetor.put(setor, vendidosNoSetor + quantidade);
        return true;
    }

    public List<String> getSetoresDisponiveis() {
        List<String> setores = new ArrayList<>();
        for (SetorEnum setor : SetorEnum.values()) {
            setores.add(setor.getNome());
        }
        return setores;
    }

    public double getValorSetor(String setorNome) {
        for (SetorEnum setor : SetorEnum.values()) {
            if (setor.getNome().equals(setorNome)) {
                return setor.getValor();
            }
        }
        return 0.0;
    }

    public List<Ingresso> getIngressos() {
        return ingressos;
    }

    public void setIngressos(List<Ingresso> ingressos) {
        this.ingressos = ingressos == null ? new ArrayList<>() : new ArrayList<>(ingressos);
        contadorPorSetor.clear();
        for (SetorEnum setor : SetorEnum.values()) {
            contadorPorSetor.put(setor.getNome(), 0);
        }
        for (Ingresso ingresso : this.ingressos) {
            int atual = contadorPorSetor.getOrDefault(ingresso.getSetor(), 0);
            contadorPorSetor.put(ingresso.getSetor(), atual + ingresso.getQuantidade());
        }
    }

    public void imprimirRelatorio() {
        if (ingressos == null || ingressos.isEmpty()) {
            System.out.println("Nenhum ingresso vendido.");
            return;
        }

        System.out.println("Relatorio de Ingressos Vendidos:");
        for (Ingresso ingresso : ingressos) {
            System.out.println(ingresso);
        }
    }
}
