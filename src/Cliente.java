import java.util.ArrayList;

public class Cliente {
    private String nome;
    private String sobrenome;
    private int id_registro;
    private List<Bilhete> bilhetes;

    public Cliente() {
        this.bilhetes = new ArrayList<>();
    }

    public Cliente(String nome, String sobrenome, int id_registro) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.id_registro = id_registro;
        this.bilhetes = new ArrayList<>;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public int getId_registro() {
        return id_registro;
    }

    public List<Bilhete> getBilhetes() {
        return bilhetes;
    }

    public void comprarBilhete(Bilhete bilhete) {
        if (bilhete != null) {
            this.bilhetes.add(bilhete);
        }
    }

    public Double totalGasto() {
        double total = 0;

        for (Bilhete b: bilhetes ) {
            total += bilhetes.getValor();
        }

        return total;
    }

    @Override 
    public String toString() {
        return "Cliente [" + 
        "ID: " + id_registro + 
        ", Nome: " + nome + 
        ", Sobrenome: " + sobrenome +
        ", Total Gasto: R$ " + String.format("%.2f", totalGasto()) +
        "]"; 
    }
}