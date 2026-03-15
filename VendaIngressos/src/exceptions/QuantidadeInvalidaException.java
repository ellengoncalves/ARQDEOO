package VendaIngressos.src.exceptions;

public class QuantidadeInvalidaException extends RuntimeException {
    public QuantidadeInvalidaException(String message) {
        super(message);
    }
}