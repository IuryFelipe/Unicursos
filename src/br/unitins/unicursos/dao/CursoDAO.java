package br.unitins.unicursos.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.unicursos.application.Session;
import br.unitins.unicursos.model.CategoriaCurso;
import br.unitins.unicursos.model.Curso;
import br.unitins.unicursos.model.Perfil;
import br.unitins.unicursos.model.Usuario;
public class CursoDAO implements DAO<Curso> {
	
	@Override
	public boolean create(Curso obj) {
		//System.out.println(Session.getInstance().get("usuarioLogado"));
		Usuario usuarioLogado = (Usuario) Session.getInstance().get("usuarioLogado");
		System.out.println(usuarioLogado.getId().toString());
		Connection conn = DAO.getConnection();
		boolean erro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("curso ");
		sql.append("  (nome, descricao, categoria, imagem, datainicio, datafim, ativo, usuario) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?, ?, ?, ?, ?, ?, ?) ");
		PreparedStatement stat = null;
		
		
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			if(obj.getCategoria() != null) {
				stat.setInt(3, obj.getCategoria().getValue());
			}else {
				stat.setObject(3, null);
			}
			stat.setString(4, obj.getImagem());
			if(obj.getDataInicio() == null) {
				stat.setDate(5, null);
			}else {
				stat.setDate(5, Date.valueOf(obj.getDataInicio()));
			}
			if(obj.getDataFim() == null) {
				stat.setDate(6, null);
			}else {
				stat.setDate(6, Date.valueOf(obj.getDataFim()));
			}
			stat.setBoolean(7, true);
			
			stat.setInt(8, usuarioLogado.getId());
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
	public boolean update(Curso obj) {
		Connection conn = DAO.getConnection();
		boolean erro = false;
		
		//n�o pode alterar o usu�rio(instrutor) do curso - N�o nessa vers�o, pelo menos...
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE curso SET ");
		sql.append(" nome = ?, ");
		sql.append(" descricao = ?, ");
		sql.append(" categoria = ?, ");
		sql.append(" imagem = ?, ");
		sql.append(" datainicio = ?, ");
		sql.append(" datafim = ?, ");
		sql.append(" ativo = ?, ");
		sql.append("WHERE ");
		sql.append(" id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getDescricao());
			if(obj.getCategoria() != null) {
				stat.setInt(3, obj.getCategoria().getValue());
			}else {
				stat.setObject(3, null);
			}
			stat.setString(4, obj.getImagem());
			stat.setDate(5, Date.valueOf(obj.getDataInicio()));
			stat.setDate(6, Date.valueOf(obj.getDataFim()));
			stat.setBoolean(7, obj.isAtivo());
			
			stat.setInt(8, obj.getId());
			
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
		sql.append("DELETE FROM curso WHERE id = ? ");
		
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
	public List<Curso> findAll() {
		Connection conn = DAO.getConnection();
		List<Curso> courseList = new ArrayList<Curso>();
		
		//nome, descricao, categoria, imagem, datainicio, datafim, ativo - por categoria
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" c.id, ");
		sql.append(" c.descricao, ");
		sql.append(" c.categoria, ");
		sql.append(" us.id AS id_usuario, ");
		sql.append(" us.nome AS nome_usuario, ");
		sql.append(" c.imagem, ");
		sql.append(" c.datainicio, ");
		sql.append(" c.datafim, ");
		sql.append(" c.ativo ");
		sql.append("FROM ");
		sql.append(" curso c ");
		sql.append("INNER JOIN usuario us ON (c.usuario = us.id) ");
		sql.append(" ORDER BY c.nome ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				Curso curso = new Curso();
				curso.setId(rs.getInt("id"));
				curso.setDescricao(rs.getString("descricao"));
				curso.setCategoria(CategoriaCurso.valueOf(rs.getInt("categoria")));
				curso.setImagem(rs.getString("imagem"));
				if (rs.getDate("datainicio") != null) {
					Date dataInicio = rs.getDate("datainicio");
					curso.setDataInicio(dataInicio.toLocalDate());
				}
				if (rs.getDate("datafim") != null) {
					Date dataFim = rs.getDate("datafim");
					curso.setDataFim(dataFim.toLocalDate());
				}
				curso.setAtivo(rs.getBoolean("ativo"));
				curso.setUsuario(new Usuario());
				curso.getUsuario().setId(rs.getInt("id_usuario"));//instrutor do curso
				curso.getUsuario().setNome(rs.getString("nome_usuario"));
				
				courseList.add(curso);
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
	
	//nome, descricao, categoria, imagem, datainicio, datafim, ativo

	@Override
	public Curso findById(Integer id) {
		Connection conn = DAO.getConnection();
		
		Curso curso = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append(" c.id, ");
		sql.append(" c.descricao, ");
		sql.append(" c.categoria, ");
		sql.append(" us.id AS id_usuario, ");
		sql.append(" us.nome AS nome_usuario, ");
		sql.append(" c.imagem, ");
		sql.append(" c.datainicio, ");
		sql.append(" c.datafim, ");
		sql.append(" c.ativo ");
		sql.append("FROM ");
		sql.append(" curso c ");
		sql.append("INNER JOI usuario us ON (c.usuario = us.id) ");
		sql.append("WHERE ");
		sql.append(" c.id = ?");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				curso = new Curso();
				curso.setId(rs.getInt("id"));
				curso.setDescricao(rs.getString("descricao"));
				curso.setCategoria(CategoriaCurso.valueOf(rs.getInt("categoria")));
				curso.setImagem(rs.getString("imagem"));
				Date dataInicio = rs.getDate("datainicio");
				curso.setDataInicio(dataInicio.toLocalDate());
				Date dataFim = rs.getDate("datafim");
				curso.setDataFim(dataFim.toLocalDate());
				curso.setAtivo(rs.getBoolean("ativo"));
				curso.setUsuario(new Usuario());
				curso.getUsuario().setId(rs.getInt("id_usuario"));//instrutor do curso
				curso.getUsuario().setNome(rs.getString("nome_usuario"));
			}
		} catch(Exception e) {
			e.printStackTrace();
			curso = null;
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
		return curso;
	}

}
