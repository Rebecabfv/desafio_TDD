package exception;

public class QuantidadeInvalida extends Exception{
    private static final String MSG = "Quantidade inválida.";

    public QuantidadeInvalida(){
        super(MSG);
    }
}
