package VendaIngressos.src.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ingresso implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numero;
    private String setor;
    private double valor;
    private LocalDateTime dataHoraCompra;

    public Ingresso(int numero, String setor, double valor) {
        this.numero = numero;
        this.setor = setor;
        this.valor = valor;
        this.dataHoraCompra = LocalDateTime.now();
    }

    public int getNumero() {
        return numero;
    }

    public String getSetor() {
        return setor;
    }

    public double getValor() {
        return valor;
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
                ", valor=" + valor +
                ", dataHoraCompra=" + dataHoraCompra.format(formatter) +
                '}';
    }
}