package br.unitins.unicursos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.unicursos.application.Session;
import br.unitins.unicursos.application.Util;
import br.unitins.unicursos.dao.CursoDAO;
import br.unitins.unicursos.model.Curso;
import br.unitins.unicursos.model.ItemCompra;

@Named
@ViewScoped
public class CompraController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3807679968481222366L;
	
	private Integer tipoFiltro;
	private String filtro;
	private List<Curso> listaCurso;
	
	public void pesquisar() {
		CursoDAO dao = new CursoDAO();
		if (getTipoFiltro() == 1)
			setListaCurso(dao.findByName(filtro));
		else
			setListaCurso(dao.findByDescription(filtro));
	}
	
	public void addCarrinho(Curso curso) {
		@SuppressWarnings("unchecked")
		List<ItemCompra> carrinho = (List<ItemCompra>) Session.getInstance().get("carrinho");
		if (carrinho == null) 
			carrinho = new ArrayList<ItemCompra>();
		
		
		ItemCompra iv = new ItemCompra();
		iv.setCurso(curso);
		iv.setQuantidade(1);
		iv.setValorUnitario(curso.getValor());
	
		if (carrinho.contains(iv)) {
			int index = carrinho.indexOf(iv);
			int quantidade = carrinho.get(index).getQuantidade();
			carrinho.get(index).setQuantidade(++ quantidade );
			
		} else {
			carrinho.add(iv);
		}
		
		Session.getInstance().set("carrinho", carrinho);
		
		Util.addInfoMessage("Item adicionado no carrinho.");
		
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Curso> getListaCurso() {
		return listaCurso;
	}

	public void setListaCurso(List<Curso> listaCurso) {
		this.listaCurso = listaCurso;
	}

	public Integer getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(Integer tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

}
