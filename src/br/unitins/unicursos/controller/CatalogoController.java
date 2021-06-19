package br.unitins.unicursos.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.unicursos.dao.CursoDAO;
import br.unitins.unicursos.dao.DAO;
import br.unitins.unicursos.model.Curso;

@Named
@ViewScoped
public class CatalogoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4459286787069018204L;
	
	private Curso curso = null;
	private List<Curso> courseList = null;
	

	public List<Curso> getCourseList(){
		if (courseList == null) {
			DAO<Curso> dao = new CursoDAO();
			courseList = dao.findAll();
			if (courseList == null)
				courseList = new ArrayList<Curso>();
		}
		System.out.println(courseList);
		return courseList;
		
	}
	
	public void setCourseList(List<Curso> courseList) {
		this.courseList = courseList;
	}

}
