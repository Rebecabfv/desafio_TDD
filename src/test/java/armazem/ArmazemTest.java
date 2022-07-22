package armazem;

import exception.IngredienteJaCadastrado;
import exception.IngredienteNaoEncontrado;
import exception.QuantidadeInvalida;
import ingredientes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArmazemTest {

    Armazem armazem;

    @BeforeEach
    void setup() throws IngredienteJaCadastrado {
        armazem = new Armazem();
        Ingrediente iogurte = new Base(TipoBase.IORGUTE);
        armazem.cadastrarIngrediente(iogurte, 2);
    }

    @Test
    void cadastrarIngredienteEmEstoque_properly() throws IngredienteJaCadastrado {
        Ingrediente mel = new Topping(TipoTopping.MEL);
        armazem.cadastrarIngrediente(mel, 2);

        assertEquals(2, armazem.getItens().size());
    }

    @Test
    void cadastrarIngredienteEmEstoque_IngredienteJaCadastrado(){
        Ingrediente iogurte = new Base(TipoBase.IORGUTE);

        Exception thrown = assertThrows(
                IngredienteJaCadastrado.class,
                () -> armazem.cadastrarIngrediente(iogurte, 0),
                "Excecao nao encontrada"
        );

        assertEquals("Ingrediente já cadastrado.", thrown.getMessage());
    }

    @Test
    void testDescadastrarIngredienteEmEstoque_property() throws IngredienteNaoEncontrado {
        Ingrediente iogurte = new Base(TipoBase.IORGUTE);

        armazem.descadastrarIngrediente(iogurte);
        //TODO: revisar o equals
        assertFalse(armazem.ingredienteExiste(iogurte));
    }

    @Test
    void testDescadastrarIngredienteEmEstoque_IngredienteNaoExiste(){
        Ingrediente sorvete = new Base(TipoBase.SORVETE);

        Exception thrown = assertThrows(
                IngredienteNaoEncontrado.class,
                () -> armazem.descadastrarIngrediente(sorvete),
                "Excecao nao encontrada"
        );

        assertEquals("Ingrediente não encontrado.", thrown.getMessage());
    }

    @Test
    void testAdicionarQuantidadeDoIngredienteEmEstoque_property() throws QuantidadeInvalida, IngredienteNaoEncontrado {
        Ingrediente iogurte = new Base(TipoBase.IORGUTE);

        armazem.adicionarquantidadedoIngrediente(iogurte, 2);

        assertEquals(4, armazem.consultarQuantidadeDoIngrediente(iogurte));
    }

    @Test
    void testAdicionarQuantidadeDoIngredienteEmEstoque_Exceptions(){
        Ingrediente leite = new Base(TipoBase.LEITE);

        Exception thrown = assertThrows(
                IngredienteNaoEncontrado.class,
                () -> armazem.adicionarquantidadedoIngrediente(leite, 2),
                "Excecao nao encontrada"
        );

        Exception thrown2 = assertThrows(
                QuantidadeInvalida.class,
                () -> armazem.adicionarquantidadedoIngrediente(leite, -9),
                "Excecao nao encontrada"
        );

        assertEquals("Ingrediente não encontrado.", thrown.getMessage());
        assertEquals("Quantidade inválida.", thrown2.getMessage());
    }

    @Test
    void testReduzirQuantidadeDoIngredienteEmEstoque_property() throws QuantidadeInvalida, IngredienteNaoEncontrado {
        Ingrediente iogurte = new Base(TipoBase.IORGUTE);

        armazem.reduzirQuantidadeDoIngrediente(iogurte, 1);

        assertEquals(1, armazem.consultarQuantidadeDoIngrediente(iogurte));
    }

    @Test
    void testReduzirQuantidadeDoIngredienteEmEstoque_Exceptions(){
        Ingrediente leite = new Base(TipoBase.LEITE);
        Ingrediente iorgute = new Base(TipoBase.IORGUTE);

        Exception thrown = assertThrows(
                IngredienteNaoEncontrado.class,
                () -> armazem.reduzirQuantidadeDoIngrediente(leite, 2),
                "Excecao nao encontrada"
        );

        Exception thrown2 = assertThrows(
                QuantidadeInvalida.class,
                () -> armazem.reduzirQuantidadeDoIngrediente(leite, -9),
                "Excecao nao encontrada"
        );

        Exception thrown3 = assertThrows(
                QuantidadeInvalida.class,
                () -> armazem.reduzirQuantidadeDoIngrediente(iorgute, 3),
                "Excecao nao encontrada"
        );

        assertEquals("Ingrediente não encontrado.", thrown.getMessage());
        assertEquals("Quantidade inválida.", thrown2.getMessage());
        assertEquals("Quantidade inválida.", thrown3.getMessage());
    }

    @Test
    void testConsultarQuantidadeDoIngredienteEmEstoque_property() throws IngredienteNaoEncontrado {
        Ingrediente iogurte = new Base(TipoBase.IORGUTE);
        var quantidadeDoIngrediente = armazem.consultarQuantidadeDoIngrediente(iogurte);

        assertEquals(2, quantidadeDoIngrediente);
    }

    @Test
    void testConsultarQuantidadeDoIngredienteEmEstoque_Exception(){
        Ingrediente sorvete = new Base(TipoBase.SORVETE);

        Exception thrown = assertThrows(
                IngredienteNaoEncontrado.class,
                () -> armazem.consultarQuantidadeDoIngrediente(sorvete),
                "Excecao nao encontrada");

        assertEquals("Ingrediente não encontrado.", thrown.getMessage());
    }
}
