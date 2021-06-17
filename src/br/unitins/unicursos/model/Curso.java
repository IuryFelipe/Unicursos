package br.unitins.unicursos.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Curso implements Cloneable {
	
	private Integer id;
	
	@NotBlank(message = "O campo Nome não pode ser vazio.")
	private String nome;
	
	private String descricao;
	@NotNull(message = "O campo Categoria não pode ser nulo")
	private CategoriaCurso categoria;
	
	@NotNull(message = "Deve-se informar o instrutor do curso")
	private Usuario instrutor; //instrutor do curso
	private String imagem;
	
	private LocalDate dataInicio;
	private LocalDate dataFim;
	@NotNull(message = "O campo Ativo não pode ser nulo")
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
	public CategoriaCurso getCategoria() {
		return categoria;
	}
	public void setCategoria(CategoriaCurso categoria) {
		this.categoria = categoria;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDate getDataFim() {
		return dataFim;
	}
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}	
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public Usuario getInstrutor() {
		return instrutor;
	}
	public void setInstrutor(Usuario instrutor) {
		this.instrutor = instrutor;
	}
	
	public Curso getClone() {
		try {
			return (Curso) this.clone();
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
		Curso other = (Curso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Curso [nome=" + nome + ", descricao=" + descricao + ", categoria=" + categoria + ", instrutor="
				+ instrutor + ", imagem=" + imagem + ", dataInicio=" + dataInicio + ", dataFim=" + dataFim + ", ativo="
				+ ativo + "]";
	}
	
	

	
}
