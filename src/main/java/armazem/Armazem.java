package armazem;

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

    public void cadastrarIngrediente(Ingrediente ingrediente, Integer quantidade) {
        if (ingredienteExiste(ingrediente))
            throw new IllegalArgumentException("Ingrediente já cadastrado.");
        estoque.put(ingrediente, quantidade);
    }

    public void descadastrarIngrediente(Ingrediente ingrediente) {
        if (!ingredienteExiste(ingrediente))
            throw new IllegalArgumentException("Ingrediente não encontrado.");
        estoque.remove(ingrediente);
    }

    public int consultarQuantidadeDoIngrediente(Ingrediente ingrediente) {
        if (!ingredienteExiste(ingrediente))
            throw new IllegalArgumentException("Ingrediente não encontrado.");
        return estoque.get(ingrediente);
    }

    public void adicionarquantidadedoIngrediente(Ingrediente ingrediente, Integer quantidade) {
        validaQuantidade(quantidade);
        var ingredienteExiste = modificarQuantidade(ingrediente, quantidade);
        if (!ingredienteExiste)
            throw new IllegalArgumentException("Ingrediente não encontrado.");
    }

    private boolean modificarQuantidade(Ingrediente ingrediente, Integer quantidade) {
        var quantidadeAntiga = consultarQuantidadeDoIngrediente(ingrediente);
        var quantidadeAtualizada = quantidadeAntiga + quantidade;
        return estoque.replace(ingrediente, consultarQuantidadeDoIngrediente(ingrediente), quantidadeAtualizada);
    }

    private boolean validaQuantidade(Integer quantidade) {
        if (quantidade > 0)
            return true;
        throw new IllegalArgumentException("Quantidade inválida.");
    }

    public void reduzirQuantidadeDoIngrediente(Ingrediente ingrediente, Integer quantidade) {
        validaQuantidade(quantidade);
        if (!ingredienteExiste(ingrediente))
            throw new IllegalArgumentException("Ingrediente não encontrado.");

        var quantidadeReduzida = consultarQuantidadeDoIngrediente(ingrediente) - quantidade;
        if (quantidadeReduzida > 0)
            estoque.replace(ingrediente, consultarQuantidadeDoIngrediente(ingrediente), quantidade);
        else
            throw new IllegalArgumentException("Quantidade inválida.");
    }

    public boolean ingredienteExiste(Ingrediente ingrediente) {
        return estoque.containsKey(ingrediente);
    }
}
