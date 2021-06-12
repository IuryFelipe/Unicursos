package br.unitins.unicursos.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.unicursos.model.CategoriaCurso;
import br.unitins.unicursos.model.Perfil;
import br.unitins.unicursos.model.Telefone;

public class CategoriaDAO implements DAO<CategoriaCurso> {

	@Override
	public boolean create(CategoriaCurso obj) {
		Connection conn = DAO.getConnection();
		boolean erro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("categoriacurso ");
		sql.append("  (nome, descricao, ativo) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?, ?) ");
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			stat.setBoolean(3, obj.isAtivo());
			stat.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			erro = true;
		}finally {
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
		if(erro) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean update(CategoriaCurso obj) {
		Connection conn = DAO.getConnection();
		boolean erro = false;
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE categoriacurso SET ");
		sql.append(" nome = ?, ");
		sql.append(" descricao = ?, ");
		sql.append(" ativo = ?, ");
		sql.append("WHERE ");
		sql.append(" id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			stat.setBoolean(3, obj.isAtivo());
			
			stat.setInt(4, obj.getId());
			
			stat.execute();
		} catch (Exception e) {
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

	@Override
	public boolean delete(Integer id) {
		Connection conn = DAO.getConnection();
		boolean erro = false;
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM categoriacurso WHERE id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			erro = true;
		} finally {
			try {
				stat.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (erro) {
			return false;
		}
		return true;
	}

	@Override
	public List<CategoriaCurso> findAll() {
		Connection conn = DAO.getConnection();
		List<CategoriaCurso> courseList = new ArrayList<CategoriaCurso>();
		
		//nome, descricao, categoria, imagem, datainicio, datafim, ativo
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" c.id, ");
		sql.append(" c.nome, ");
		sql.append(" c.descricao, ");
		sql.append(" c.ativo, ");
		sql.append("FROM ");
		sql.append(" categoriacurso c ");
		sql.append(" ORDER BY c.name ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				CategoriaCurso categoriaCurso = new CategoriaCurso();
				categoriaCurso.setId(rs.getInt("id"));
				categoriaCurso.setDescricao(rs.getString("descricao"));
				categoriaCurso.setAtivo(rs.getBoolean("ativo"));
				
				courseList.add(categoriaCurso);
			}
		} catch (Exception e) {
			e.printStackTrace();
			courseList = null;
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
		
		if (courseList == null || courseList.isEmpty()) {
			return null;
		}
		
		return courseList;
	}

	@Override
	public CategoriaCurso findById(Integer id) {
		Connection conn = DAO.getConnection();
		
		CategoriaCurso categoria = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" c.nome, ");
		sql.append(" c.descricao, ");
		sql.append(" c.ativo, ");
		sql.append("FROM ");
		sql.append(" categoriacurso c ");
		sql.append("WHERE ");
		sql.append(" c.id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				categoria = new CategoriaCurso();
				categoria.setId(rs.getInt("id"));
				categoria.setNome(rs.getString("nome"));
				categoria.setDescricao(rs.getString("descricao"));
				categoria.setAtivo(rs.getBoolean("ativo"));
			}
		} catch(Exception e) {
			e.printStackTrace();
			categoria = null;
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
		return categoria;
	}

}
