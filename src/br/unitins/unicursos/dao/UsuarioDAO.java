package br.unitins.unicursos.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.unitins.unicursos.model.Perfil;
import br.unitins.unicursos.model.Telefone;
import br.unitins.unicursos.model.Usuario;

public class UsuarioDAO implements DAO<Usuario>{

	
	public Usuario Login(Usuario usuario) {
		Connection conn = DAO.getConnection();
		
		Usuario usuarioLogado = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("u.id, u.nome, u.cpf, u.datanascimento, u.email, u.senha, u.codarea, u.telefone, u.endereco, u.ativo, u.perfil ");
		sql.append("FROM ");
		sql.append("  usuario u ");
		sql.append("WHERE ");
		sql.append("  u.email = ? ");
		sql.append("  AND u.senha = ? ");
		
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, usuario.getEmail());
			stat.setString(2, usuario.getSenha());
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				usuarioLogado = new Usuario();
				usuarioLogado.setId(rs.getInt("id"));
				usuarioLogado.setNome(rs.getString("nome"));
				usuarioLogado.setCpf(rs.getString("cpf"));
				usuarioLogado.setEmail(rs.getString("email"));
				usuarioLogado.setSenha(rs.getString("senha"));
				Date data = rs.getDate("datanascimento");
				usuarioLogado.setDataNascimento(data == null ? null : data.toLocalDate());
				Telefone telefone = new Telefone();
				telefone.setCodArea(rs.getString("codarea"));
				telefone.setNumero(rs.getString("telefone"));
				usuarioLogado.setTelefone(telefone);
				usuarioLogado.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				usuarioLogado.setAtivo(rs.getBoolean("ativo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			usuarioLogado = null;
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
		
		return usuarioLogado;
	}
	
	@Override
	public boolean create(Usuario obj) {
		Connection conn = DAO.getConnection();
		boolean erro = false;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("usuario ");
		sql.append("  (nome, cpf, datanascimento, email, senha, codarea, telefone, endereco, ativo, perfil) ");
		sql.append("VALUES ");
		sql.append("  ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		PreparedStatement stat = null;
		
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, obj.getNome());
			stat.setString(2, obj.getCpf());
			if(obj.getDataNascimento() == null) {
				stat.setDate(3, null);
			}else {
				stat.setDate(3, Date.valueOf(obj.getDataNascimento()));
			}
			stat.setString(4, obj.getEmail());
			stat.setString(5, obj.getSenha());
			stat.setString(6, obj.getTelefone().getCodArea());
			stat.setString(7, obj.getTelefone().getNumero());
			stat.setString(8, obj.getEndereco());
			stat.setBoolean(9, obj.isAtivo());
			if(obj.getPerfil() != null) {
				stat.setInt(10, obj.getPerfil().getValue());
			}else {
				stat.setObject(10, null);
			}
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
	public boolean update(Usuario obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
