package exception;

public class IngredienteJaCadastrado extends Exception{
    private static final String MSG = "Ingrediente já cadastrado.";

    public IngredienteJaCadastrado(){
        super(MSG);
    }
}
