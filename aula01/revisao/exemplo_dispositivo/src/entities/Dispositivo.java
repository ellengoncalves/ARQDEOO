package entities;

// Classe abstrata que representa um dispositivo
public abstract class Dispositivo implements SituacaoDispositivo {
    
    // Atributos do dispositivo
    protected String nome;   
    protected boolean ligado; 

    // Construtor que inicializa o nome do dispositivo e o estado como desligado
    public Dispositivo(String nome) {
        this.nome = nome;      
        this.ligado = false;   
    }

    // Método para ligar o dispositivo
    @Override
    public boolean ligar() {
        // Verifica se o dispositivo já está ligado
        if (!ligado) {
            ligado = true;    // Muda o estado para ligado
            return true;      // Retorna true, indicando que o dispositivo foi ligado com sucesso
        }
        return false;         // Caso o dispositivo já estivesse ligado, retorna false
    }

    // Método para desligar o dispositivo
    @Override
    public boolean desligar() {
        // Verifica se o dispositivo já está desligado
        if (ligado) {
            ligado = false;   // Muda o estado para desligado
            return true;      // Retorna true, indicando que o dispositivo foi desligado com sucesso
        }
        return false;         // Caso o dispositivo já estivesse desligado, retorna false
    }

    // Método para obter o nome do dispositivo
    public String getNome() {
        return nome;         
    }

    // Método para verificar se o dispositivo está ligado
    public boolean isLigado() {
        return ligado;       
    }
}
