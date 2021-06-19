package br.unitins.unicursos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.unicursos.application.Util;
import br.unitins.unicursos.dao.CursoDAO;
import br.unitins.unicursos.dao.DAO;
import br.unitins.unicursos.dao.UsuarioDAO;
import br.unitins.unicursos.model.CategoriaCurso;
import br.unitins.unicursos.model.Curso;
import br.unitins.unicursos.model.Perfil;
import br.unitins.unicursos.model.Usuario;

@Named
@ViewScoped
public class CursoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2992064761681670352L;
	
	private Curso curso = null;
	private List<Curso> listaCurso = null;
	
	public CategoriaCurso[] getListaCategoriaCurso() {
		return CategoriaCurso.values();
	}
	
	public List<Curso> getListaCurso() {
		if (listaCurso == null) {
			DAO<Curso> dao = new CursoDAO();
			listaCurso = dao.findAll();
			if (listaCurso == null) {
				listaCurso = new ArrayList<Curso>();
			}
		}
		return listaCurso;
	}
	
	public void setListaCurso(List<Curso> listaCurso) {
		this.listaCurso = listaCurso;
	}
	
	public Curso getCurso() {
		if(curso == null) {
			curso = new Curso();
		}
		return curso;
	}
	
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public CursoController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("cursoFlash");
		setCurso((Curso)flash.get("cursoFlash"));
	}
	
	public void create() {
		DAO<Curso> dao = new CursoDAO();
		if (dao.create(getCurso())) {
			Util.addInfoMessage("O curso foi adicionado com sucesso!");
			limparFormulario();
		} else {
			Util.addErrorMessage("Houve um problema ao tentar inserir o curso");
		}
	}
	
	public void update() {
		DAO<Curso> dao = new CursoDAO();
		if (dao.update(getCurso())) {
			Util.addInfoMessage("O curso foi alterado com sucesso.");
			limparFormulario();
		} else {
			Util.addErrorMessage("Houve um problema ao tentar modificar o curso");
		}
	}
	
	public void delete() {
		delete(getCurso());
	}
	
	public void delete(Curso curso) {
		DAO<Curso> dao = new CursoDAO();
		if (dao.delete(curso.getId())) {
			Util.addInfoMessage("O curso foi excluído com sucesso!");
		} else {
			Util.addErrorMessage("Houve um problema ao tentar excluir o curso.");
		}
	}
	
	public void edit(Curso c) {
		DAO<Curso> dao = new CursoDAO();
		setCurso(dao.findById(c.getId()));
	}

	public void limparFormulario() {
		setCurso(null);
		setListaCurso(null);
	}

}
