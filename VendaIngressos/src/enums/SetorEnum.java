package VendaIngressos.src.enums;

public enum SetorEnum {
    AMARELO("Amarelo", 50.0),
    AZUL("Azul", 75.0),
    BRANCO("Branco", 100.0),
    VERDE("Verde", 125.0);

    private final String nome;
    private final double valor;

    SetorEnum(String nome, double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return nome;
    }
}