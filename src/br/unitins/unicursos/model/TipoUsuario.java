package br.unitins.unicursos.model;

public enum TipoUsuario {
	ADMINISTRADOR(1, "Administrador"),
	CLIENTE(2, "Cliente");//A DISCUTIR -  pensei em colocar Aluno
	
	private int value;
	private String label;
	
	TipoUsuario (int value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getLabel() {
		return label;
	}
	
	
	public static TipoUsuario valueOf(Integer value) {
		if (value == null)
			return null;
		
		for (TipoUsuario tipoUsuario : TipoUsuario.values()) {
			if (tipoUsuario.getValue() == value) {
				return tipoUsuario;
			}
		}
		return null;
	}
}
