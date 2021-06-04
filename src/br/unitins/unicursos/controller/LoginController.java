package br.unitins.unicursos.controller;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.unitins.unicursos.model.Usuario;

@Named
@RequestScoped
public class LoginController {
	private Usuario usuario;
	private String usuario_correto ="iuryfelipe";
	private String senha_correta = "123456";

	public String logar() {
		System.out.println("Usuário: "+getUsuario().getEmail());
		System.out.println("Senha: "+getUsuario().getSenha());
		if(getUsuario().getEmail().equals(usuario_correto) && getUsuario().getSenha().equals(senha_correta)) {
			//permite logar;
			
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public Usuario getUsuario() {
		if(usuario==null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
