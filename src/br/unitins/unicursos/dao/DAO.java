package br.unitins.unicursos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import br.unitins.unicursos.model.Curso;

public interface DAO<T> {
	
	public boolean create(T obj);
	public boolean update(T obj);
	public boolean delete(Integer id);
	public List<T> findAll();
	public T findById(Integer id);
	public List<T> findAllById(Integer id);

	public static Connection getConnection() {
	    try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager
					.getConnection("jdbc:postgresql://127.0.0.1:5432/unicursosdb", "root", "root");
			return connection;
	    } catch (ClassNotFoundException e) {
			System.out.println("O Driver não foi encontrado.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Falha na conexao com o banco de dados.");
			e.printStackTrace();
		}
	    return null;
	}
}
