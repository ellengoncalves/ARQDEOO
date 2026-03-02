package entities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorArquivo {
    public List<Curso> ler(String path) {
        List<Curso> lista = new ArrayList<>();
        
        try (Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNextLine()) {
                String linha = sc.nextLine();
                String[] dados = linha.split(","); // Supondo arquivo separado por vírgula
                
                try {
                    String nome = dados[0].trim();
                    double duracao = Double.parseDouble(dados[1].trim());
                    CursoArqEnum nivel = CursoArqEnum.parse(dados[2].trim());
                    
                    lista.add(new Curso(nome, duracao, nivel));
                } catch (DurationException e) {
                    System.out.println("Erro na linha: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Erro de formatação na linha: " + linha);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao abrir arquivo: " + e.getMessage());
        }
        return lista;
    }
}
