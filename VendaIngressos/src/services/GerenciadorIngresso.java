package VendaIngressos.src.services;

import VendaIngressos.src.entities.Ingresso;
import VendaIngressos.src.enums.SetorEnum;
import VendaIngressos.src.exceptions.SetorEsgotadoException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (quantidade <= 0) {
            throw new VendaIngressos.src.exceptions.QuantidadeInvalidaException("Quantidade deve ser maior que zero.");
        }

        int atual = contadorPorSetor.get(setor);
        if (atual + quantidade > LIMITE_POR_SETOR) {
            throw new SetorEsgotadoException("Limite de ingressos para o setor " + setor + " atingido.");
        }

        for (int i = 0; i < quantidade; i++) {
            Ingresso ingresso = new Ingresso(prox++, setor, getValorSetor(setor));
            ingressos.add(ingresso);
            contadorPorSetor.put(setor, contadorPorSetor.get(setor) + 1);
        }
        return true;
    }

    private double getValorSetor(String setorNome) {
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
        this.ingressos = ingressos;
        // Recalcular contadores
        contadorPorSetor.clear();
        for (SetorEnum setor : SetorEnum.values()) {
            contadorPorSetor.put(setor.getNome(), 0);
        }
        for (Ingresso ingresso : ingressos) {
            contadorPorSetor.put(ingresso.getSetor(), contadorPorSetor.get(ingresso.getSetor()) + 1);
        }
    }

    public void imprimirRelatorio() {
        if (ingressos == null || ingressos.isEmpty()) {
            System.out.println("Nenhum ingresso vendido.");
            return;
        }
        System.out.println("Relatório de Ingressos Vendidos:");
        for (Ingresso ingresso : ingressos) {
            System.out.println(ingresso);
        }
    }
}