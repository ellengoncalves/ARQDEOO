package entities;

public class Caminhao implements Veiculo {
    private boolean ligado = false;

    @Override
    public void ligar() {
        if (!ligado) {
            ligado = true;
        }
    }

    @Override
    public void desligar() {
        if (ligado) {
            ligado = false;
        }
    }

    @Override
    public boolean isLigado() {
        return ligado;
    }

    @Override
    public String getTipo() {
        return "Caminhão";
    }
}
