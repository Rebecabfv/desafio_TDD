package exception;

public class IngredienteNaoEncontrado extends Exception{
    private static final String MSG = "Ingrediente não encontrado.";

    public IngredienteNaoEncontrado(){
        super(MSG);
    }
}
