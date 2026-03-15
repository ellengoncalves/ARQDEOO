package VendaIngressos.src.services;

import VendaIngressos.src.entities.Ingresso;

import java.io.*;
import java.util.List;

public class GerenciadorArquivo {

    public static void serializar(List<Ingresso> ingressos, String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(ingressos);
            System.out.println("[LOG] Serialização concluída com sucesso.");
        } catch (IOException e) {
            System.out.println("[ERRO] Falha ao serializar: " + e.getMessage());
        } finally {
            System.out.println("[LOG] Operação de serialização concluída.");
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Ingresso> desserializar(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            List<Ingresso> ingressos = (List<Ingresso>) ois.readObject();
            System.out.println("[LOG] Desserialização concluída com sucesso.");
            return ingressos;
        } catch (FileNotFoundException e) {
            System.out.println("[LOG] Arquivo não encontrado, inicializando lista vazia.");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[ERRO] Falha ao desserializar: " + e.getMessage());
            return null;
        } finally {
            System.out.println("[LOG] Operação de desserialização concluída.");
        }
    }

    public static void exportarTxt(List<Ingresso> ingressos, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("Relatório de Ingressos Vendidos\n");
            writer.write("================================\n");
            if (ingressos != null && !ingressos.isEmpty()) {
                for (Ingresso ingresso : ingressos) {
                    writer.write(ingresso.toString() + "\n");
                }
            } else {
                writer.write("Nenhum ingresso vendido.\n");
            }
            System.out.println("[LOG] Exportação para TXT concluída com sucesso.");
        } catch (IOException e) {
            System.out.println("[ERRO] Falha ao exportar TXT: " + e.getMessage());
        } finally {
            System.out.println("[LOG] Operação de exportação TXT concluída.");
        }
    }
}