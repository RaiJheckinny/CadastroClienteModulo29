package main.br.com.rpires;
import main.br.com.rpires.dao.IProdutoDAO;
import main.br.com.rpires.dao.ProdutoDAO;

import main.br.com.rpires.domain.Cliente;
import main.br.com.rpires.domain.Produto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Optional;

public class PodutoTest {
    IProdutoDAO dao = null;
    Produto produto = null;

    @Before
    public void init(){
        dao = new ProdutoDAO();
        produto = new Produto();
    }
    @Test
    public void cadastrarTest() throws Exception {
        produto.setNome("Blusa Azul");
        produto.setCodigo("01");
        produto.setPreco(100l);

        Integer qtdCadastrar =  dao.cadastrar(produto);
        Assert.assertNotNull(qtdCadastrar);
        Assert.assertTrue(qtdCadastrar.equals(1));

        Produto produtoBD = dao.buscar("01");
        Assert.assertNotNull(produtoBD);

        Assert.assertEquals(produto.getNome(), produtoBD.getNome());
        Assert.assertEquals(produto.getCodigo(), produtoBD.getCodigo());
        Assert.assertEquals(produto.getPreco(), produtoBD.getPreco());

        produtoBD.setNome("Blusa Preta");
        produtoBD.setCodigo("02");
        produtoBD.setPreco(200l);

        Integer qtdAtualizar = dao.atualizar(produtoBD);
        Assert.assertNotNull(qtdAtualizar);
        Assert.assertTrue(qtdAtualizar.equals(1));

        Produto produtoBDCodigo01 = dao.buscar("01");
        Assert.assertEquals(produtoBDCodigo01.getNome(), null);
        Assert.assertEquals(produtoBDCodigo01.getCodigo(), null);
        Assert.assertEquals(produtoBDCodigo01.getId(), null);
        Assert.assertEquals(produtoBDCodigo01.getPreco(), null);

        Produto produtoBDCodigo02 = dao.buscar("02");
        Assert.assertNotNull(produtoBDCodigo02);

        Assert.assertEquals(produtoBDCodigo02.getNome(), produtoBD.getNome());
        Assert.assertEquals(produtoBDCodigo02.getCodigo(), produtoBD.getCodigo());
        Assert.assertEquals(produtoBDCodigo02.getId(), produtoBD.getId());
        Assert.assertEquals(produtoBDCodigo02.getPreco(), produtoBD.getPreco());

        Integer qtdRemover = dao.remover("02");
        Assert.assertTrue(qtdRemover.equals(1));

        Produto produtoRemover = dao.buscar("02");
        Assert.assertEquals(produtoRemover.getNome(), null);
        Assert.assertEquals(produtoRemover.getCodigo(), null);
        Assert.assertEquals(produtoRemover.getId(), null);
        Assert.assertEquals(produtoRemover.getPreco(), null);
    }
    @Test
    public void buscarTodosTest() throws Exception {
        Produto produto1 = new Produto();
        Produto produto2 = new Produto();

        produto1.setNome("Rai Jheckinny");
        produto1.setCodigo("01");
        produto1.setPreco(100l);

        produto2.setNome("Jakeline");
        produto2.setCodigo("02");
        produto2.setPreco(200l);

        Integer qtdCadastrar1 = dao.cadastrar(produto1);
        Assert.assertTrue(qtdCadastrar1.equals(1));

        Integer qtdCadastrar2 = dao.cadastrar(produto2);
        Assert.assertTrue(qtdCadastrar2.equals(1));

        List<Produto> listProdutoCadastrado = dao.buscarTodos();

        listProdutoCadastrado.forEach(element->{
                Assert.assertNotNull(element.getNome());
                Assert.assertNotNull(element.getCodigo());
                Assert.assertNotNull(element.getId());
                Assert.assertNotNull(element.getPreco());
        });

        listProdutoCadastrado.forEach(element-> {
            try {
                dao.remover(element.getCodigo());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        listProdutoCadastrado = dao.buscarTodos();

        listProdutoCadastrado.forEach(element->{
            Assert.assertNull(element.getNome());
            Assert.assertNull(element.getCodigo());
            Assert.assertNull(element.getId());
            Assert.assertNull(element.getPreco());
        });
    }
}
