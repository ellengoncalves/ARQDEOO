package poo_semana03_excecoes.entities;

public enum CursoArqEnum {
    TECNICO("Tecnico"), GRADUACAO("Graduação"), 
    POS_GRADUACAO("Pós-Graduação"), NAO_INFORMADO("Inexistente.");

    private String desc;
    CursoArqEnum(String desc) { this.desc = desc; }
    public String getDesc() { return desc; }

    public static CursoArqEnum parse(String texto) {
        for (CursoArqEnum c : values()) {
            if (c.getDesc().equalsIgnoreCase(texto)) return c;
        }
        return NAO_INFORMADO;
    }
}
