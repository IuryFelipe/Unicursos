package br.unitins.unicursos.model;


public enum CategoriaCurso {
	FOTOGRAFIA(1, "Fotografia"),
	ADMINISTRACAO(2, "Administra��o"),
	ENGENHARIA(3, "Engenharia"),
	ARTES(4, "Artes"),
	MUSICA(5, "M�sica"),
	INFORMATICA(6, "Inform�tica"),
	SAUDE(7, "Sa�de");
	
	private int value;
	private String label;
	
	CategoriaCurso (int value, String label) {
		this.value = value;
		this.label = label;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getLabel() {
		return label;
	}
	
	
	public static CategoriaCurso valueOf(Integer value) {
		if (value == null)
			return null;
		
		for (CategoriaCurso categoriaCurso : values()) {
			if (categoriaCurso.getValue() == value) {
				return categoriaCurso;
			}
		}
		return null;
	}
}
