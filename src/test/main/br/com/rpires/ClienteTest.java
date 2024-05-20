package main.br.com.rpires;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import main.br.com.rpires.dao.ClienteDAO;
import main.br.com.rpires.dao.IClienteDAO;
import main.br.com.rpires.domain.Cliente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ClienteTest {
	IClienteDAO dao = null;
	Cliente cliente = null;

	@Before
	public void init(){
		dao = new ClienteDAO();
		cliente = new Cliente();
	}

	@Test
	public void cadastrarTest() throws Exception {

		cliente.setCodigo("01");
		cliente.setNome("Rai Jheckinny");
		
		Integer qtdCadastrar = dao.cadastrar(cliente);
		assertTrue(qtdCadastrar == 1);
		
		Cliente clienteBD = dao.consultar(cliente.getCodigo());
		assertNotNull(clienteBD);
		assertNotNull(clienteBD.getId());
		assertEquals(cliente.getCodigo(), clienteBD.getCodigo());
		assertEquals(cliente.getNome(), clienteBD.getNome());
		
		Integer qtdDel = dao.excluir(clienteBD);
		assertNotNull(qtdDel);
	}

	@Test
	public void atualizarTest() throws Exception {

		cliente.setCodigo("02");
		cliente.setNome("Rai Jheckinny");

		Integer qtdCadastrar = dao.cadastrar(cliente);

		Cliente clienteBD = dao.consultar("02");

		clienteBD.setCodigo("10");
		clienteBD.setNome("Daniel");

		Integer qtdAtualizar = dao.atualizar(clienteBD);
		Assert.assertNotNull(qtdAtualizar);
		Assert.assertTrue(qtdAtualizar.equals(1));

		Cliente clienteBDAtualizado = dao.consultar("02");
		Assert.assertNull(clienteBDAtualizado);

		clienteBDAtualizado = dao.consultar("10");
		Assert.assertNotNull(clienteBDAtualizado);

		Assert.assertEquals(clienteBD.getCodigo(), clienteBDAtualizado.getCodigo());
		Assert.assertEquals(clienteBD.getNome(), clienteBDAtualizado.getNome());

		Integer qtdDel = dao.excluir(clienteBD);
		assertNotNull(qtdDel);
	}
	@Test
	public void buscarTodos() throws Exception {
		cliente.setCodigo("02");
		cliente.setNome("Lucas");

		Integer qtdCadastrar1 = dao.cadastrar(cliente);

		cliente.setCodigo("03");
		cliente.setNome("Pedro");

		Integer qtdCadastrar2 = dao.cadastrar(cliente);

		List<Cliente> todosOsCliente = dao.buscarTodos();
		Assert.assertNotNull(todosOsCliente);

		todosOsCliente.forEach(element-> {
            try {
                dao.excluir(element);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

		Assert.assertNull(dao.buscarTodos());
	}

	public static class ProdutoTest {

		public void cadastrarTest(){

		}

	}
}
