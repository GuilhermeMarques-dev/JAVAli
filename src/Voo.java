import java.time.LocalDate;

public class Voo {
    private static final int CAPACIDADE_MAXIMA = 150;

    private String codigo;
    private LocalDate data;
    private double precoBase;
    private int assentosOcupados;
    private Rota rota;

    public Voo(Rota rota, LocalDate data, double precoBase) {
        this.rota = rota;
        this.data = data;
        this.precoBase = precoBase;
        this.codigo = rota.getCodigo();
        this.assentosOcupados = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDate getData() {
        return data;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public Rota getRota() {
        return rota;
    }

    public int getVagasDisponiveis() {
        return CAPACIDADE_MAXIMA - assentosOcupados;
    }

    public boolean reservarAssento() {
        if (!temVaga()) {
            return false;
        }
        assentosOcupados++;
        return true;
    }

    public boolean cancelarReserva() {
        if (assentosOcupados <= 0) {
            return false;
        }
        assentosOcupados--;
        return true;
    }

    public boolean temVaga() {
        return getVagasDisponiveis() > 0;
    }
}
