package br.unitins.unicursos.model;

import java.time.LocalDate;

public class Usuario implements Cloneable{

	private Integer id;
	private String nome;
	private LocalDate dataNascimento;
	private String email;
	private String senha;
	private boolean ativo = Boolean.TRUE; //Seta ativo já no cadastro
	//private TipoUsuario tipoUsuario; //Aqui informamos se ele é administrador do sistema ou um cliente(aluno)
	//private LocalDate dataExpiracaoConta; // A DISCUTIR - É caso queiramos adicionar estruturar para pagamentos mensais
	
	
}
