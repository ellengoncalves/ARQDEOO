package poo_semana03_excecoes.main;

import poo_semana03_excecoes.entities.*;

public class Main {
    public static void main(String[] args) {
        // Para testar, crie um arquivo .txt ou .csv com: Java,40,Graduação
        String path = "/home/junior_marqx27/Documents/cursos_arq.csv";
        
        GerenciadorArquivo ga = new GerenciadorArquivo();
        for (Curso c : ga.ler(path)) {
            System.out.println(c);
        }
    }
}
