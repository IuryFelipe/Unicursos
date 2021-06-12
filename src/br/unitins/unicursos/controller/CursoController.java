package br.unitins.unicursos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.unicursos.application.Util;
import br.unitins.unicursos.dao.CategoriaDAO;
import br.unitins.unicursos.dao.CursoDAO;
import br.unitins.unicursos.dao.DAO;
import br.unitins.unicursos.model.CategoriaCurso;
import br.unitins.unicursos.model.Curso;

@Named
@ViewScoped
public class CursoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2992064761681670352L;
	
	private Curso curso = null;
	private List<Curso> courseList = null;
	private List<CategoriaCurso> categoryList = null;
	
	public List<CategoriaCurso> getListaCategoria() {
		if (categoryList == null) {
			DAO<CategoriaCurso> dao = new CategoriaDAO();
			categoryList = dao.findAll();
			if (categoryList == null) {
				categoryList = new ArrayList<CategoriaCurso>();
			}
		}
		return categoryList;
	}
	
	public List<Curso> getListaCurso() {
		if (courseList == null) {
			DAO<Curso> dao = new CursoDAO();
			courseList = dao.findAll();
			if (courseList == null) {
				courseList = new ArrayList<Curso>();
			}
		}
		return courseList;
	}
	
	public void setCourseList(List<Curso> courseList) {
		this.courseList = courseList;
	}
	
	public Curso getCurso() {
		if(curso == null) {
			curso = new Curso();
			curso.setCategoria(new CategoriaCurso());
		}
		return curso;
	}
	
	public void setCurso(Curso course) {
		this.curso = course;
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
	
	public void delete(Curso course) {
		DAO<Curso> dao = new CursoDAO();
		if (dao.delete(course.getId())) {
			Util.addInfoMessage("O curso foi excluído com sucesso!");
		} else {
			Util.addErrorMessage("Houve um problema ao tentar excluir o curso.");
		}
	}

	public void limparFormulario() {
		setCurso(null);
		setCourseList(null);
	}

}
