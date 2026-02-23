package entities;  // Define o pacote no qual a interface está localizada

// Define a interface SituacaoDispositivo, que descreve o comportamento de dispositivos que podem ser ligados ou desligados.
public interface SituacaoDispositivo {
    
    // Método para ligar o dispositivo. Ele retorna um valor booleano indicando se a operação de ligar foi bem-sucedida.
    boolean ligar();
    
    // Método para desligar o dispositivo. Ele retorna um valor booleano indicando se a operação de desligar foi bem-sucedida.
    boolean desligar();
}
