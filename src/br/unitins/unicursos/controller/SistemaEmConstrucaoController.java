package br.unitins.unicursos.controller;

import javax.inject.Named;

@Named
public class SistemaEmConstrucaoController {

	public String sair() {
		System.out.println("Chamou botao sair");
		return "login.xhtml?faces-redirect=true";
	}
}
