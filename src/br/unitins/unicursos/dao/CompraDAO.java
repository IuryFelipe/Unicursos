package br.unitins.unicursos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.unitins.unicursos.model.Compra;
import br.unitins.unicursos.model.Curso;
import br.unitins.unicursos.model.ItemCompra;
import br.unitins.unicursos.model.Usuario;

public class CompraDAO implements DAO<Compra> {

	@Override
	public boolean create(Compra obj) {
		Connection conn = DAO.getConnection();
		
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		boolean erro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO compra ");
		sql.append(" (data, id_usuario) ");
		sql.append("VALUES ");
		sql.append(" (?, ?, ?) ");
		
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			stat.setInt(2, obj.getUsuario().getId());
			stat.setDouble(3, obj.getTotalVenda());
			
			stat.execute();
				
			ResultSet rs = stat.getGeneratedKeys();
			if (rs.next()) {
				obj.setId(rs.getInt("id"));
			}
			
			// 
			salvarItens(obj, conn);
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			erro = true;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (erro) {
			return false;
		}
		return true;
	}

	private void salvarItens(Compra compra, Connection conn) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO cursocompra ");
		sql.append(" (quantidade, valor_unitario, id_curso, id_venda) ");
		sql.append("VALUES ");
		sql.append(" (?, ?, ?, ?) ");
		
		PreparedStatement stat = null;
		for (ItemCompra item : compra.getListaItemCompra()) {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, item.getQuantidade());
			stat.setDouble(2, item.getValorUnitario());
			stat.setInt(3, item.getCurso().getId());
			stat.setInt(4, compra.getId());
			
			stat.execute();
		}
		
	}

	@Override
	public boolean update(Compra obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Compra> findAll(Usuario usuario) {
		Connection conn = DAO.getConnection();
		
		List<Compra> listaVenda = new ArrayList<Compra>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  co.id, ");
		sql.append("  co.data, ");
		sql.append("  co.id_usuario ");
		sql.append("FROM ");
		sql.append("  cursocompra co ");
		sql.append("WHERE ");
		sql.append("  co.id_usuario = ? ");
		
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, usuario.getId());
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Compra compra = new Compra();
				compra.setId(rs.getInt("id"));
				compra.setData(rs.getDate("data").toLocalDate());
				compra.setUsuario(usuario);
				compra.setListaItemCompra(obterItensCompra(compra));
				
				listaVenda.add(compra);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaVenda = null;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (listaVenda == null || listaVenda.isEmpty())
			return null;
		
		return listaVenda;
	}

	private List<ItemCompra> obterItensCompra(Compra compra) {
Connection conn = DAO.getConnection();
		
		List<ItemCompra> listaItem = new ArrayList<ItemCompra>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("  i.id, ");
		sql.append("  i.quantidade, ");
		sql.append("  i.valor_unitario, ");
		sql.append("  i.id_curso, ");
		sql.append("  c.nome, ");
		sql.append("  c.descricao, ");
		sql.append("  c.preco ");
		sql.append("FROM ");
		sql.append("  cursocompra i, ");
		sql.append("  curso c ");
		sql.append("WHERE ");
		sql.append("  i.id_curso = c.id ");
		sql.append("  AND i.id_venda = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, compra.getId());
			
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				ItemCompra item = new ItemCompra();
				item.setId(rs.getInt("id"));
				item.setQuantidade(rs.getInt("quantidade"));
				item.setValorUnitario(rs.getDouble("valor_unitario"));
				item.setCurso(new Curso());
				item.getCurso().setId(rs.getInt("id_produto"));
				item.getCurso().setNome(rs.getString("nome"));
				item.getCurso().setDescricao(rs.getString("descricao"));
				item.getCurso().setValor(rs.getDouble("valor"));
				
				listaItem.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			listaItem = null;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (listaItem == null || listaItem.isEmpty())
			return null;
		
		return listaItem;
	}

	@Override
	public Compra findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Compra> findAllById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Compra> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
