package armazem;

import exception.IngredienteJaCadastrado;
import exception.IngredienteNaoEncontrado;
import exception.QuantidadeInvalida;
import ingredientes.Ingrediente;

import java.util.Map;
import java.util.TreeMap;

public class Armazem {

    private Map<Ingrediente, Integer> estoque;

    public Armazem() {
        this.estoque = new TreeMap<>();
    }

    public Map<Ingrediente, Integer> getItens() {
        return this.estoque;
    }

    public void cadastrarIngrediente(Ingrediente ingrediente, Integer quantidade) throws IngredienteJaCadastrado {
        if (ingredienteExiste(ingrediente))
            throw new IngredienteJaCadastrado();
        estoque.put(ingrediente, quantidade);
    }

    public void descadastrarIngrediente(Ingrediente ingrediente) throws IngredienteNaoEncontrado {
        if (!ingredienteExiste(ingrediente))
            throw new IngredienteNaoEncontrado();
        estoque.remove(ingrediente);
    }

    public int consultarQuantidadeDoIngrediente(Ingrediente ingrediente) throws IngredienteNaoEncontrado {
        if (!ingredienteExiste(ingrediente))
            throw new IngredienteNaoEncontrado();
        return estoque.get(ingrediente);
    }

    public void adicionarquantidadedoIngrediente(Ingrediente ingrediente, Integer quantidade) throws IngredienteNaoEncontrado, QuantidadeInvalida {
        validaQuantidade(quantidade);
        var ingredienteExiste = modificarQuantidade(ingrediente, quantidade);
        if (!ingredienteExiste)
            throw new IngredienteNaoEncontrado();
    }

    private boolean modificarQuantidade(Ingrediente ingrediente, Integer quantidade) throws IngredienteNaoEncontrado {
        var quantidadeAntiga = consultarQuantidadeDoIngrediente(ingrediente);
        var quantidadeAtualizada = quantidadeAntiga + quantidade;
        return estoque.replace(ingrediente, consultarQuantidadeDoIngrediente(ingrediente), quantidadeAtualizada);
    }

    private boolean validaQuantidade(Integer quantidade) throws QuantidadeInvalida {
        if (quantidade > 0)
            return true;
        throw new QuantidadeInvalida();
    }

    public void reduzirQuantidadeDoIngrediente(Ingrediente ingrediente, Integer quantidade) throws QuantidadeInvalida, IngredienteNaoEncontrado {
        validaQuantidade(quantidade);
        if (!ingredienteExiste(ingrediente))
            throw new IngredienteNaoEncontrado();

        var quantidadeReduzida = consultarQuantidadeDoIngrediente(ingrediente) - quantidade;
        if (quantidadeReduzida > 0)
            estoque.replace(ingrediente, consultarQuantidadeDoIngrediente(ingrediente), quantidade);
        else
            throw new QuantidadeInvalida();
    }

    public boolean ingredienteExiste(Ingrediente ingrediente) {
        return estoque.containsKey(ingrediente);
    }
}
