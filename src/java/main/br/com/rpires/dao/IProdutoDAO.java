package main.br.com.rpires.dao;

import main.br.com.rpires.domain.Produto;

import java.sql.SQLException;
import java.util.List;

public interface IProdutoDAO {
    public Integer cadastrar(Produto produto) throws Exception;
    public Integer atualizar(Produto produto) throws Exception;
    public Produto buscar(String codigo) throws Exception;
    public Integer remover(String codigo) throws Exception;
    public List<Produto> buscarTodos() throws SQLException;
}
