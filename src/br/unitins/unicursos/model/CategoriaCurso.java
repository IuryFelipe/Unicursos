package br.unitins.unicursos.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoriaCurso implements Cloneable {
	
	private Integer id;
	@NotBlank(message = "Deve-se informar o campo nome da categoria do curso")
	private String nome;
	private String descricao;
	@NotNull(message = "O campo ativo não pode ser vazio")
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public CategoriaCurso getClone() {
		try {
			return (CategoriaCurso) this.clone();
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
		CategoriaCurso other = (CategoriaCurso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CategoriaCurso [nome=" + nome + ", descricao=" + descricao + ", ativo=" + ativo + "]";
	}
	
	

}
