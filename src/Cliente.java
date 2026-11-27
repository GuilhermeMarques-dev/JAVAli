import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private static int proximoIdRegistro = 1;

    private String nome;
    private String sobrenome;
    private int id_registro;
    private List<Bilhete> bilhetes;

    public Cliente() {
        this.bilhetes = new ArrayList<>();
    }

    public Cliente(String nome, String sobrenome) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.id_registro = proximoIdRegistro++;
        this.bilhetes = new ArrayList<>();
    }

    public Cliente(String nome, String sobrenome, int id_registro) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.id_registro = id_registro;
        this.bilhetes = new ArrayList<>();
        if (id_registro >= proximoIdRegistro) {
            proximoIdRegistro = id_registro + 1;
        }
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
            total += b.calcularValor();
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