package armazem;

import exception.IngredienteJaCadastrado;
import exception.IngredienteNaoEncontrado;
import exception.QuantidadeInvalida;
import ingredientes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArmazemTest {

    Armazem armazem;

    @BeforeEach
    void setup() throws IngredienteJaCadastrado, QuantidadeInvalida, IngredienteNaoEncontrado {
        armazem = new Armazem();
        Ingrediente iogurte = new Base(TipoBase.IORGUTE);
        armazem.cadastrarIngrediente(iogurte);
        armazem.adicionarquantidadedoIngrediente(iogurte, 2);
    }

    @Test
    @DisplayName("Cadastrar ingrediente")
    void cadastrarIngredienteEmEstoque() throws IngredienteJaCadastrado, IngredienteNaoEncontrado {
        Ingrediente mel = new Topping(TipoTopping.MEL);
        armazem.cadastrarIngrediente(mel);

        assertEquals(0, armazem.consultarQuantidadeDoIngrediente(mel));
    }

    @Test
    @DisplayName("Cadastrar ingrediente quando ingrediente já cadastrado")
    void cadastrarIngredienteEmEstoqueQuandoIngredienteJaEstiverCadastrado(){
        Ingrediente iogurte = new Base(TipoBase.IORGUTE);

        Exception thrown = assertThrows(
                IngredienteJaCadastrado.class,
                () -> armazem.cadastrarIngrediente(iogurte),
                "Excecao nao encontrada"
        );

        assertEquals("Ingrediente já cadastrado.", thrown.getMessage());
    }

    @Test
    @DisplayName("Descadastrar ingrediente")
    void descadastrarIngredienteEmEstoque() throws IngredienteNaoEncontrado {
        Ingrediente iogurte = new Base(TipoBase.IORGUTE);

        armazem.descadastrarIngrediente(iogurte);

        assertFalse(armazem.ingredienteExiste(iogurte));
    }

    @Test
    @DisplayName("Descadastrar ingrediente quando ingrediente não existe")
    void descadastrarIngredienteEmEstoqueQuandoIngredienteNaoExisteNoEstoque(){
        Ingrediente sorvete = new Base(TipoBase.SORVETE);

        Exception thrown = assertThrows(
                IngredienteNaoEncontrado.class,
                () -> armazem.descadastrarIngrediente(sorvete),
                "Excecao nao encontrada"
        );

        assertEquals("Ingrediente não encontrado.", thrown.getMessage());
    }

    @Test
    @DisplayName("Adicionar quantidade do ingrediente")
    void AdicionarQuantidadeDoIngredienteEmEstoque() throws QuantidadeInvalida, IngredienteNaoEncontrado {
        Ingrediente iogurte = new Base(TipoBase.IORGUTE);

        armazem.adicionarquantidadedoIngrediente(iogurte, 2);

        assertEquals(4, armazem.consultarQuantidadeDoIngrediente(iogurte));
    }

    @Test
    @DisplayName("Adicionar quantidade do ingrediente quando ingrediente não existe")
    void adicionarQuantidadeDoIngredienteEmEstoqueQuandoIngredienteNaoExisteNoEstoque(){
        Ingrediente leite = new Base(TipoBase.LEITE);

        Exception thrown = assertThrows(
                IngredienteNaoEncontrado.class,
                () -> armazem.adicionarquantidadedoIngrediente(leite, 2),
                "Excecao nao encontrada"
        );

        assertEquals("Ingrediente não encontrado.", thrown.getMessage());
    }

    @Test
    @DisplayName("Adicionar quantidade do ingrediente como quantidade inválida")
    void adicionarQuantidadeDoIngredienteEmEstoqueQuandoQuantidadeMenorQueZero(){
        Ingrediente leite = new Base(TipoBase.LEITE);

        Exception thrown = assertThrows(
                QuantidadeInvalida.class,
                () -> armazem.adicionarquantidadedoIngrediente(leite, -9),
                "Excecao nao encontrada"
        );

        assertEquals("Quantidade inválida.", thrown.getMessage());
    }

    @Test
    @DisplayName("Reduzir quantidade do ingrediente")
    void reduzirQuantidadeDoIngredienteEmEstoque() throws QuantidadeInvalida, IngredienteNaoEncontrado {
        Ingrediente iogurte = new Base(TipoBase.IORGUTE);

        armazem.reduzirQuantidadeDoIngrediente(iogurte, 1);

        assertEquals(1, armazem.consultarQuantidadeDoIngrediente(iogurte));
    }

    @Test
    @DisplayName("Reduzir quantidade do ingrediente quando ingrediente não existe no estoque")
    void reduzirQuantidadeDoIngredienteEmEstoqueQuandoIngredienteNaoExisteNoEstoque(){
        Ingrediente leite = new Base(TipoBase.LEITE);

        Exception thrown = assertThrows(
                IngredienteNaoEncontrado.class,
                () -> armazem.reduzirQuantidadeDoIngrediente(leite, 2),
                "Excecao nao encontrada"
        );

        assertEquals("Ingrediente não encontrado.", thrown.getMessage());
    }

    @Test
    @DisplayName("Reduzir quantidade do ingrediente quando quantidade inválida")
    void reduzirQuantidadeDoIngredienteEmEstoqueQuandoQuantidadeMenorQueZero(){
        Ingrediente iorgute = new Base(TipoBase.IORGUTE);

        Exception thrown = assertThrows(
                QuantidadeInvalida.class,
                () -> armazem.reduzirQuantidadeDoIngrediente(iorgute, -9),
                "Excecao nao encontrada"
        );

        assertEquals("Quantidade inválida.", thrown.getMessage());
    }

    @Test
    @DisplayName("Reduzir quantidade do ingrediente quando quantidade em estoque insuficiente")
    void testReduzirQuantidadeDoIngredienteEmEstoque_QuantidadeInsuficiente(){
        Ingrediente iorgute = new Base(TipoBase.IORGUTE);

        Exception thrown = assertThrows(
                QuantidadeInvalida.class,
                () -> armazem.reduzirQuantidadeDoIngrediente(iorgute, 3),
                "Excecao nao encontrada"
        );

        assertEquals("Quantidade inválida.", thrown.getMessage());
    }

    @Test
    @DisplayName("Consultar quantidade do ingrediente")
    void testConsultarQuantidadeDoIngredienteEmEstoque_property() throws IngredienteNaoEncontrado {
        Ingrediente iogurte = new Base(TipoBase.IORGUTE);
        var quantidadeDoIngrediente = armazem.consultarQuantidadeDoIngrediente(iogurte);

        assertEquals(2, quantidadeDoIngrediente);
    }

    @Test
    @DisplayName("Consultar quantidade do ingrediente quando ingrediente não existe")
    void testConsultarQuantidadeDoIngredienteEmEstoque_Exception(){
        Ingrediente sorvete = new Base(TipoBase.SORVETE);

        Exception thrown = assertThrows(
                IngredienteNaoEncontrado.class,
                () -> armazem.consultarQuantidadeDoIngrediente(sorvete),
                "Excecao nao encontrada");

        assertEquals("Ingrediente não encontrado.", thrown.getMessage());
    }
}
