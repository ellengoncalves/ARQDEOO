package venda_ingresso.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import venda_ingresso.exceptions.QuantidadeInvalidaException;

public class Ingresso implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numero;
    private String setor;
    private double valorUnitario;
    private int quantidade;
    private double valorTotal;
    private LocalDateTime dataHoraCompra;

    public Ingresso(int numero, String setor, double valorUnitario, int quantidade) {
        if (quantidade <= 0) {
            throw new QuantidadeInvalidaException("Quantidade deve ser maior que zero.");
        }
        this.numero = numero;
        this.setor = setor;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.valorTotal = valorUnitario * quantidade;
        this.dataHoraCompra = LocalDateTime.now();
    }

    public int getNumero() {
        return numero;
    }

    public String getSetor() {
        return setor;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public LocalDateTime getDataHoraCompra() {
        return dataHoraCompra;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "Ingresso{" +
                "numero=" + numero +
                ", setor='" + setor + '\'' +
                ", valorUnitario=" + valorUnitario +
                ", quantidade=" + quantidade +
                ", valorTotal=" + valorTotal +
                ", dataHoraCompra=" + dataHoraCompra.format(formatter) +
                '}';
    }
}
