package venda_ingresso.services;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import venda_ingresso.entities.Ingresso;

public class GerenciadorArquivo {

    public static void serializar(List<Ingresso> ingressos, String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(ingressos);
            System.out.println("[LOG] Serializacao concluida com sucesso.");
        } catch (IOException e) {
            System.out.println("[ERRO] Falha ao serializar: " + e.getMessage());
        } finally {
            System.out.println("[LOG] Operacao de serializacao concluida.");
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Ingresso> desserializar(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            List<Ingresso> ingressos = (List<Ingresso>) ois.readObject();
            System.out.println("[LOG] Desserializacao concluida com sucesso.");
            return ingressos;
        } catch (FileNotFoundException e) {
            System.out.println("[LOG] Arquivo nao encontrado, inicializando lista vazia.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[ERRO] Falha ao desserializar: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            System.out.println("[LOG] Operacao de desserializacao concluida.");
        }
    }

    public static void exportarTxt(List<Ingresso> ingressos, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("Relatorio de Ingressos Vendidos\n");
            writer.write("================================\n");
            if (ingressos != null && !ingressos.isEmpty()) {
                for (Ingresso ingresso : ingressos) {
                    writer.write(ingresso.toString());
                    writer.newLine();
                }
            } else {
                writer.write("Nenhum ingresso vendido.");
                writer.newLine();
            }
            System.out.println("[LOG] Exportacao para TXT concluida com sucesso.");
        } catch (IOException e) {
            System.out.println("[ERRO] Falha ao exportar TXT: " + e.getMessage());
        } finally {
            System.out.println("[LOG] Operacao de exportacao TXT concluida.");
        }
    }
}
