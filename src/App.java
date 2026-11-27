import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    /*
    Aluno 1)
    - Mostrar menu de Usuario
    - Gerar automaticamente as rotas da cidade
    */

    public static int mostrarMenu(Scanner sc) {
        System.out.println("\n=========== Menu - Agência de Viagens ===========");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Cadastrar voo");
        System.out.println("3 - Procurar voos");
        System.out.println("4 - Comprar bilhete");
        System.out.println("5 - Relatório: dados de um cliente");
        System.out.println("6 - Relatório: bilhetes vendidos por origem");
        System.out.println("7 - Relatório: total arrecadado e comissão");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");

        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static List<Rota> gerarRotas() {
        List<Rota> rotas = new ArrayList<>();

        for (ECidade origem : ECidade.values()) {
            for (ECidade destino : ECidade.values()) {
                if (origem != destino) {
                    rotas.add(new Rota(origem, destino, 0));
                }
            }
        }

        return rotas;
    }

    /*
    Aluno 2)
    - Fazer loop principal no main
    - Procurar voos
    */

    public static void procurarVoo(Scanner sc, List<Rota> rotas, List<Voo> voos) {
        IO.println("\n=== PROCURAR VOOS ===");

        if (voos == null || voos.isEmpty()) {
            IO.println("Nenhum voo cadastrado no sistema.");
            return;
        }
        if (rotas == null || rotas.isEmpty()) {
            IO.println("Nenhuma rota disponível.");
            return;
        }

        IO.println("Rotas:");
        for (int i = 0; i < rotas.size(); i++) {
            Rota r = rotas.get(i);
            IO.println((i + 1) + " - " + r.getOrigem().getNomeCidade()
                    + " -> " + r.getDestino().getNomeCidade()
                    + " (Código: " + r.gerarCodigo() + ")");
        }

        IO.print("Escolha o número da rota: ");
        int indiceRota = Integer.parseInt(sc.nextLine()) - 1;
        if (indiceRota < 0 || indiceRota >= rotas.size()) {
            IO.println("Rota inválida!");
            return;
        }
        Rota rotaFiltro = rotas.get(indiceRota);

        boolean encontrou = false;
        for (Voo v : voos) {
            Rota rotaVoo = v.getRota();
            if (rotaVoo.getOrigem() == rotaFiltro.getOrigem()
                    && rotaVoo.getDestino() == rotaFiltro.getDestino()) {
                encontrou = true;
                IO.println("Código: " + v.getCodigo()
                        + " | Data: " + v.getData()
                        + " | Preço: R$ " + String.format("%.2f", v.getPrecoBase())
                        + " | Vagas: " + v.getVagasDisponiveis());
            }
        }

        if (!encontrou) {
            IO.println("Nenhum voo encontrado para esta rota.");
        }
    }

    public static void main() {
        Scanner sc = new Scanner(System.in);
        List<Rota> rotas = gerarRotas();
        List<Voo> voos = new ArrayList<>();
        List<Cliente> clientes = new ArrayList<>();

        int opcao;
        do {
            opcao = mostrarMenu(sc);
            switch (opcao) {
                case 2 -> cadastrarVoo(sc, rotas, voos);
                case 3 -> procurarVoo(sc, rotas, voos);
                case 4 -> comprarBilhete(sc, clientes, voos);
                case 0 -> IO.println("Encerrando o sistema. Até logo!");
                default -> IO.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        sc.close();
    }

    /*
    Aluno 3)
    - Cadastrar cliente
    - Gerar bilhete
    */
    public static void cadastrarCliente(Scanner sc, List<Cliente> clientes) {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Sobrenome: ");
        String sobrenome = sc.nextLine();
        System.out.print("ID de registro: ");
        int id = Integer.parseInt(sc.nextLine());
        clientes.add(new Cliente(nome, sobrenome, id));
        System.out.println("Cliente cadastrado.");
    }

    public static Bilhete gerarBilhete(Cliente cliente, List<Voo> voos) {

        Bilhete novo = new Bilhete(cliente);

        for(Voo voo : voos){
            novo.adicionarVoo(voo);
        }

        return novo;

    }



    /*
    Aluno 4)
    - Cadastrar voos
    - Comprar bilhete
    */
    public static Voo cadastrarVoo(Scanner sc, List<Rota> rotas, List<Voo> voos) {
        if (rotas == null || rotas.isEmpty()) {
            System.out.println("Nenhuma rota cadastrada para criar um voo.");
            return null;
        }

        System.out.println("\n=== CADASTRAR VOO ===");
        System.out.println("Rotas disponíveis:");
        for (int i = 0; i < rotas.size(); i++) {
            Rota r = rotas.get(i);
            System.out.println((i + 1) + " - " + r.getOrigem().getNomeCidade()
                    + " -> " + r.getDestino().getNomeCidade()
                    + " (Código: " + r.gerarCodigo() + ")");
        }

        System.out.print("Escolha o número da rota: ");
        int indiceRota = Integer.parseInt(sc.nextLine()) - 1;
        if (indiceRota < 0 || indiceRota >= rotas.size()) {
            System.out.println("Rota inválida!");
            return null;
        }
        Rota rotaEscolhida = rotas.get(indiceRota);

        LocalDate dataVoo = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (dataVoo == null) {
            System.out.print("Digite a data do voo (dd/MM/yyyy): ");
            String dataStr = sc.nextLine();
            try {
                dataVoo = LocalDate.parse(dataStr, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido! Use dd/MM/yyyy.");
            }
        }

        System.out.print("Digite o preço base do voo: R$ ");
        double precoBase = Double.parseDouble(sc.nextLine().replace(",", "."));

        Voo novoVoo = new Voo(rotaEscolhida, dataVoo, precoBase);
        voos.add(novoVoo);

        System.out.println("Voo cadastrado com sucesso! Código: " + novoVoo.getCodigo());
        return novoVoo;
    }

    public static void comprarBilhete(Scanner sc, List<Cliente> clientes, List<Voo> voos) {
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        if (voos == null || voos.isEmpty()) {
            System.out.println("Nenhum voo disponível.");
            return;
        }

        System.out.println("\n=== COMPRAR BILHETE ===");
        System.out.print("Informe o ID de registro do cliente: ");
        int idCliente = Integer.parseInt(sc.nextLine());

        Cliente clienteEscolhido = null;
        for (Cliente c : clientes) {
            if (c.getId_registro() == idCliente) {
                clienteEscolhido = c;
                break;
            }
        }

        if (clienteEscolhido == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        Bilhete bilhete = new Bilhete(clienteEscolhido);
        boolean adicionandoVoos = true;

        while (adicionandoVoos) {
            System.out.println("\nVoos disponíveis:");
            for (int i = 0; i < voos.size(); i++) {
                Voo v = voos.get(i);
                System.out.println((i + 1) + " - Código: " + v.getCodigo()
                        + " | " + v.getRota().getOrigem().getNomeCidade()
                        + " -> " + v.getRota().getDestino().getNomeCidade()
                        + " | Data: " + v.getData()
                        + " | Preço: R$ " + String.format("%.2f", v.getPrecoBase())
                        + " | Vagas: " + v.getVagasDisponiveis());
            }

            System.out.print("Escolha o número do voo para adicionar ao bilhete (ou 0 para finalizar): ");
            int opcaoVoo = Integer.parseInt(sc.nextLine());

            if (opcaoVoo == 0) {
                break;
            }

            int indiceVoo = opcaoVoo - 1;
            if (indiceVoo < 0 || indiceVoo >= voos.size()) {
                System.out.println("Opção de voo inválida!");
                continue;
            }

            Voo vooEscolhido = voos.get(indiceVoo);
            if (!vooEscolhido.temVaga()) {
                System.out.println("Este voo não possui vagas disponíveis!");
                continue;
            }

            if (bilhete.adicionarVoo(vooEscolhido)) {
                vooEscolhido.reservarAssento();
                System.out.println("Voo adicionado ao bilhete com sucesso!");
            } else {
                System.out.println("Não foi possível adicionar este voo (verifique conexão de origem/destino e datas).");
            }

            System.out.print("Deseja adicionar outro voo (conexão) a este bilhete? (S/N): ");
            String continuar = sc.nextLine().trim();
            if (!continuar.equalsIgnoreCase("S")) {
                adicionandoVoos = false;
            }
        }

        if (bilhete.getOrigem() != null) {
            comprarBilhete(clienteEscolhido, bilhete);
        } else {
            System.out.println("Nenhum voo foi adicionado. Compra cancelada.");
        }
    }

    public static void comprarBilhete(Cliente cliente, Bilhete bilhete) {
        if (cliente == null || bilhete == null) {
            System.out.println("Cliente ou bilhete inválido.");
            return;
        }
        cliente.comprarBilhete(bilhete);
        System.out.println("\nCompra concluída com sucesso!");
        System.out.println("Bilhete nº: " + bilhete.getNumero());
        System.out.println("Cliente: " + cliente.getNome() + " " + cliente.getSobrenome());
        System.out.println("Valor final do bilhete: R$ " + String.format("%.2f", bilhete.calcularValor()));
    }
}