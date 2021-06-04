package br.unitins.unicursos.model;

public enum Perfil {
	ADMINISTRADOR(1, "Administrador"),
	PROFESSOR(2, "Professor"),
	ALUNO(3, "Aluno");
	
	private int value;
	private String label;
	
	Perfil (int value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getLabel() {
		return label;
	}
	
	
	public static Perfil valueOf(Integer value) {
		if (value == null)
			return null;
		
		for (Perfil perfil : values()) {
			if (perfil.getValue() == value) {
				return perfil;
			}
		}
		return null;
	}
}
