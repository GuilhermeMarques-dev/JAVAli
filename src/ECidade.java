

public enum ECidade {
    BH("Belo Horizonte", 31),
    FORTAL("Fortaleza", 85),
    RECIFE("Recife", 81),
    JP("João Pessoa", 83),
    SL("São Luiz", 98),
    NATAL("Natal", 84);

    private final String nomeCidade;
    private final int ddd;

    private ECidade(String nome, int ddd){
        this.nomeCidade = nome;
        this.ddd = ddd;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public int getDDD() {
        return ddd;
    }
}