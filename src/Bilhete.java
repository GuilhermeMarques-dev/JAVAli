import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bilhete {
    private static int proximoNumero = 1;
    private int numero;
    private Cliente cliente;
    private List<Voo> voos = new ArrayList<>();

    public Bilhete(Cliente cliente){
        if(cliente == null){
            throw new IllegalArgumentException("Erro, cliente inexistente");
        }
        this.cliente = cliente;
        this.numero = proximoNumero++;
    }

    public boolean adicionarVoo(Voo v){
        if(v == null){
            throw new IllegalArgumentException("Voo inexistente");
        }
        if(voos.isEmpty()) {
            voos.add(v);
            return true;
        }

        Voo ultimo = voos.get(voos.size() - 1);

        boolean conecta = v.getOrigem().equals(ultimo.getDestino());

        LocalDate dataUltimo = ultimo.getData();
        LocalDate dataNovo = v.getData();
        boolean mesmoDiaOuSeguinte = dataNovo.equals(dataUltimo)
                || dataNovo.equals(dataUltimo.plusDays(1));

        if (conecta && mesmoDiaOuSeguinte) {
            voos.add(v);
            return true;
        }
        return false;
    }

    public double calcularValor() {
        if (voos.isEmpty()) {
            return 0.0;
        }
        if (voos.size() == 1) {
            return voos.get(0).getPreco();
        }

        double maior = 0;
        double segundo = 0;
        double soma = 0;

        for (Voo v : voos) {
            double preco = v.getPreco();
            soma += preco;

            if (preco > maior) {
                segundo = maior;
                maior = preco;
            } else if (preco > segundo) {
                segundo = preco;
            }
        }

        double restantes = soma - maior - segundo;
        return maior * 0.60 + segundo * 0.40 + restantes * 0.10;
    }

    public Cidade getOrigem(){
        if (voos.isEmpty()) {
            return null;
        }
        return voos.get(0).getOrigem();
    }

    public Cidade getDestino(){
        if (voos.isEmpty()) {
            return null;
        }
        Voo ultimo = voos.get(voos.size() - 1);
        return ultimo.getDestino();
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

}
