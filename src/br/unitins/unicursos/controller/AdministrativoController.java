package br.unitins.unicursos.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class AdministrativoController {
	public String sair() {
		System.out.println("Chamou botao sair");
		return "login.xhtml?faces-redirect=true";
	}
	
	public String gerenciarCursos() {
		System.out.println("Chamou botao cadastro de instrumento");
		return "curso.xhtml?faces-redirect=true";
	}
	
	public String gerenciarUsuarios() {
		System.out.println("Chamou botao cadastro de instrumento");
		return "usuario.xhtml?faces-redirect=true";
	}
	
	public String irParaCatalogo() {
		System.out.println("Chamou botao cadastro de instrumento");
		return "curso.xhtml?faces-redirect=true";
	}
}
