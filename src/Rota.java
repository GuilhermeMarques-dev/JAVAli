public class Rota{
    private ECidade origem, destino;
    private String codigo;

    public Rota(ECidade origem, ECidade destino, int codigo){

        if(origem == null){
            throw new IllegalArgumentException("Cidade de origem inexistente");
        } 

        if(destino == null){
            throw new IllegalArgumentException("Cidade de destino inexistente");
        }

        if(origem.equals(destino)){
            throw new IllegalArgumentException("A cidade não pode ser igual ao destino.");
        }

        this.origem = origem;
        this.destino = destino;
    }

    public ECidade getOrigem(){
        return this.origem;
    }

    public ECidade getDestino(){
        return this.destino;
    }

    public String  gerarCodigo(){
        StringBuilder sb = new StringBuilder();

        sb.append(origem.getDDD());
        sb.append(destino.getDDD());

        String cod = sb.toString();
        this.codigo = cod;

        return codigo;
    }
}