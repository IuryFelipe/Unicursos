package br.unitins.unicursos.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Usuario implements Cloneable{

	private Integer id;
	@NotBlank(message = "O nome do usuário deve ser informado")
	private String nome;
	@NotBlank(message = "O CPF do usuário deve ser informado")
	private String cpf;
	private LocalDate dataNascimento;
	
	@NotBlank(message = "O campo Email não pode ser nulo.")
	private String email;
	
	@Size(min = 3, max = 10, message = "A senha deve ter entre 3 e 10 caracteres.")
	@NotBlank(message = "O campo senha não pode ser nulo.")
	private String senha;
	
	@NotNull(message = "O perfil do usuário deve ser informado")
	private Perfil perfil;
	private Telefone telefone;
	private String endereco;
	
	@NotNull(message = "O campo ativo não pode ser nulo")
	private boolean ativo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public Telefone getTelefone() {
		if(telefone == null) {
			telefone = new Telefone();
		}
		return telefone;
	}
	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public Usuario getClone() {
		try {
			return (Usuario) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento
				+ ", email=" + email + ", senha=" + senha + ", perfil=" + perfil + ", telefone=" + telefone
				+ ", endereco=" + endereco + ", ativo=" + ativo + "]";
	}
	
	
	
	
}
