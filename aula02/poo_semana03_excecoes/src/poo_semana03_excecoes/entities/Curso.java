package poo_semana03_excecoes.entities;

public class Curso {
    private String nome;
    private double duracao;
    private CursoArqEnum nivel;

    public Curso(String nome, double duracao, CursoArqEnum nivel) {
        if (duracao <= 0) throw new DurationException("Erro: Duração " + duracao + " é inválida!");
        this.nome = nome;
        this.duracao = duracao;
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return String.format("Curso: %-15s | Duração: %-5.1f | Nível: %s", nome, duracao, nivel.getDesc());
    }
}
