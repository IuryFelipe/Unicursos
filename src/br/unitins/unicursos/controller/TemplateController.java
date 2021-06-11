package br.unitins.unicursos.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.unicursos.application.Session;
import br.unitins.unicursos.model.Usuario;

@Named
@ViewScoped
public class TemplateController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4456479670207569876L;
	
	private Usuario userLogged;
	
	public String closeSession() {
		Session.getInstance().invalidateSession();
		return "login.xhtml";
	}

	public Usuario getUserLogged() {
		if(userLogged == null) {
			userLogged = (Usuario) Session.getInstance().get("userLogged");
		}
		return userLogged;
	}

	public void setUserLogged(Usuario userLogged) {
		this.userLogged = userLogged;
	}
	
	
}
