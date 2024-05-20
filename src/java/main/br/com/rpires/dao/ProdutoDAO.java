package main.br.com.rpires.dao;

import main.br.com.rpires.dao.jdbc.ConnectionFactory;
import main.br.com.rpires.domain.Cliente;
import main.br.com.rpires.domain.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements IProdutoDAO{
    @Override
    public Integer cadastrar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try{
            connection = ConnectionFactory.getConnection();
            String sql = getStringCadastrar();
            stm = connection.prepareStatement(sql);
            adicionarValueCadastrar(stm,produto);
            return stm.executeUpdate();
        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public Integer atualizar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getStringAtualizar();
            stm = connection.prepareStatement(sql);
            adicionarValueAtualizar(stm, produto);
            return stm.executeUpdate();
        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection, stm, null);
        }
    }




    @Override
    public Produto buscar(String codigo) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        Produto produto = new Produto();
        ResultSet rs = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = getStringBuscar();
            stm = connection.prepareStatement(sql);
            adicionarValueBuscar(stm, codigo);
            rs = stm.executeQuery();

            if(rs.next()){
                String nome = rs.getString("NOME");
                Long id = rs.getLong("ID");
                Long preco = rs.getLong("PRECO");

                produto.setNome(nome);
                produto.setCodigo(codigo);
                produto.setId(id);
                produto.setPreco(preco);
            }
            return produto;
        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection,stm, rs);
        }

    }




    @Override
    public Integer remover(String codigo) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try{
            connection = ConnectionFactory.getConnection();
            String sql = getStringRemover();
            stm = connection.prepareStatement(sql);
            adicionarValueRemover(stm, codigo);
            return stm.executeUpdate();
        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection, stm, null);
        }
    }




    @Override
    public List<Produto> buscarTodos() throws SQLException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Produto> listProdutoCadastrado = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getStringBuscarTodos();
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()){
             String nome = rs.getString("nome");
             String codigo = rs.getString("codigo");
             Long id = rs.getLong("id");
             Long preco = rs.getLong("preco");

             Produto produto = new Produto();
             produto.setNome(nome);
             produto.setCodigo(codigo);
             produto.setId(id);
             produto.setPreco(preco);

             listProdutoCadastrado.add(produto);
            }
            return listProdutoCadastrado;
        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection, stm, rs);
        }
    }


    private String getStringCadastrar() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_PRODUTO_2 (ID, NOME, CODIGO, PRECO) ");
        sb.append("VALUES (NEXTVAL('SQ_PRODUTO_2'),?,?,?);");

        return sb.toString();
    }

    private void adicionarValueCadastrar(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1, produto.getNome());
        stm.setString(2, produto.getCodigo());
        stm.setLong(3, produto.getPreco());
    }
    private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) throws SQLException {
        if (connection != null && connection.isClosed()){
            connection.close();
        };
        if (stm != null & stm.isClosed()){
            stm.close();
        };
        if(rs != null && rs.isClosed()){
            rs.close();
        }
    }

    private String getStringBuscar() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM TB_PRODUTO_2 ");
        sb.append("WHERE CODIGO = ?;");
        return sb.toString();
    }
    private void adicionarValueBuscar(PreparedStatement stm, String codigo) throws SQLException {
        stm.setString(1, codigo);
    }
    private String getStringAtualizar() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_PRODUTO_2 SET ");
        sb.append("NOME = ?, CODIGO = ?, PRECO = ? ");
        sb.append("WHERE ID = ?;");

        return sb.toString();
    }
    private void adicionarValueAtualizar(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1, produto.getNome());
        stm.setString(2, produto.getCodigo());
        stm.setLong(3, produto.getPreco());
        stm.setLong(4, produto.getId());
    }
    private String getStringRemover() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM TB_PRODUTO_2 ");
        sb.append("WHERE CODIGO = ?;");
        return sb.toString();
    }
    private void adicionarValueRemover(PreparedStatement stm, String codigo) throws SQLException {
        stm.setString(1, codigo);
    }

    private String getStringBuscarTodos() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM TB_PRODUTO_2");
        return sb.toString();
    }
}


