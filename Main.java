import java.util.Scanner;

// Ponto de entrada do programa. Só cuida da interação com o usuário (menu,
// leitura de dados, mensagens); a regra de negócio fica na CentralAtendimento.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();
        CentralAtendimento central = new CentralAtendimento();

        System.out.println(menu.getMenuEntrada());

        int opcao = -1;
        while (opcao != 0) { // repete até o usuário escolher a opção "0 - Encerrar"
            System.out.println(menu.getMenuPrincipal());
            System.out.print("Escolha uma opção: ");
            opcao = lerInteiro(scanner);

            switch (opcao) {
                case 1 -> cadastrarSolicitacao(scanner, central);
                case 2 -> consultarProximaSolicitacao(central);
                case 3 -> atenderProximaSolicitacao(scanner, central);
                case 4 -> exibirFila(central);
                case 5 -> exibirQuantidade(central);
                case 6 -> consultarUltimaOperacao(central);
                case 7 -> exibirHistorico(central);
                case 8 -> desfazerUltimaOperacao(central);
                case 9 -> gerarSolicitacoesTeste(central);
                case 0 -> System.out.println("Encerrando o sistema. Até logo!");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println();
        }

        scanner.close();
    }

    // Garante que o usuário digitou um número antes de continuar,
    private static int lerInteiro(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. Digite um número: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // consome o "Enter" que ficou no buffer após o nextInt()
        return valor;
    }

    // Fica pedindo até o usuário digitar algo que não seja vazio/só espaço.
    private static String lerTextoObrigatorio(Scanner scanner, String mensagem) {
        String texto;
        do {
            System.out.print(mensagem);
            texto = scanner.nextLine();
            if (texto.isBlank()) {
                System.out.println("Esse campo não pode ficar em branco.");
            }
        } while (texto.isBlank());
        return texto;
    }

    // Prioridade tem que ser entre 1 e 5, senão não faz sentido pro sistema.
    private static int lerPrioridade(Scanner scanner) {
        int prioridade;
        do {
            System.out.print("Prioridade (1-5): ");
            prioridade = lerInteiro(scanner);
            if (prioridade < 1 || prioridade > 5) {
                System.out.println("Prioridade precisa ser um número de 1 a 5.");
            }
        } while (prioridade < 1 || prioridade > 5);
        return prioridade;
    }

    // Opção 1: pede os dados da nova solicitação e manda a Central cadastrar.
    private static void cadastrarSolicitacao(Scanner scanner, CentralAtendimento central) {
        String nome = lerTextoObrigatorio(scanner, "Nome do solicitante: ");
        String descricao = lerTextoObrigatorio(scanner, "Descrição do problema: ");
        String categoria = lerTextoObrigatorio(scanner, "Categoria: ");
        int prioridade = lerPrioridade(scanner);
        System.out.print("Responsável (opcional): ");
        String responsavel = scanner.nextLine();

        try {
            Solicitacao solicitacao = central.cadastrarSolicitacao(nome, descricao, categoria, prioridade,
                    responsavel);
            System.out.println("Solicitação cadastrada com sucesso! Código: " + solicitacao.getCodigo());
        } catch (Fila.FilaCheiaException | Pilha.PilhaCheiaException e) {
            // a fila ou a pilha bateram no limite de capacidade (vetor cheio)
            System.out.println("Não foi possível cadastrar: " + e.getMessage());
        }
    }

    // Opção 2: mostra quem é o próximo da fila, sem remover ninguém.
    private static void consultarProximaSolicitacao(CentralAtendimento central) {
        try {
            System.out.println("Próxima solicitação: " + central.consultarProxima());
        } catch (Fila.FilaVaziaException e) {
            System.out.println(e.getMessage());
        }
    }

    // Opção 3: remove o primeiro da fila e marca como atendido (CONCLUIDA).
    private static void atenderProximaSolicitacao(Scanner scanner, CentralAtendimento central) {
        if (central.filaVazia()) {
            System.out.println("Não há solicitações aguardando atendimento.");
            return;
        }
        System.out.print("Responsável pelo atendimento: ");
        String responsavel = scanner.nextLine();
        try {
            Solicitacao solicitacao = central.atenderProxima(responsavel);
            System.out.println("Solicitação atendida: " + solicitacao);
        } catch (Fila.FilaVaziaException | Pilha.PilhaCheiaException e) {
            System.out.println(e.getMessage());
        }
    }

    // Opção 4: lista todas as solicitações que ainda estão esperando atendimento.
    private static void exibirFila(CentralAtendimento central) {
        System.out.println("=== Fila de solicitações ===");
        central.exibirFila();
    }

    // Opção 5: mostra quantas solicitações estão na fila de espera.
    private static void exibirQuantidade(CentralAtendimento central) {
        System.out.println("Quantidade de solicitações aguardando: " + central.quantidadeAguardando());
    }

    // Opção 6: mostra a última operação registrada (topo da pilha), sem removê-la.
    private static void consultarUltimaOperacao(CentralAtendimento central) {
        try {
            System.out.println("Última operação: " + central.consultarUltimaOperacao());
        } catch (Pilha.PilhaVaziaException e) {
            System.out.println(e.getMessage());
        }
    }

    // Opção 7: mostra todo o histórico de operações, da mais recente para a mais antiga.
    private static void exibirHistorico(CentralAtendimento central) {
        System.out.println("=== Histórico de operações (mais recente primeiro) ===");
        central.exibirHistorico();
    }

    // Opção 8: remove (desempilha) a última operação do histórico.
    private static void desfazerUltimaOperacao(CentralAtendimento central) {
        try {
            Operacao operacao = central.desfazerUltimaOperacao();
            System.out.println("Operação desfeita: " + operacao);
        } catch (Pilha.PilhaVaziaException e) {
            System.out.println(e.getMessage());
        }
    }

    // Opção 9: cadastra várias solicitações com dados fixos, só para testar o
    // sistema rapidamente sem precisar digitar tudo na mão toda vez.
    private static void gerarSolicitacoesTeste(CentralAtendimento central) {
        String[] nomes = { "João Silva", "Maria Souza", "Pedro Lima", "Ana Costa", "Carlos Rocha","Maria Souza", "Pedro Lima", "Ana Costa", "Carlos Rocha", "Carlos Rocha" };
        String[] descricoes = { "Internet lenta", "Sem sinal de TV", "Cobrança indevida", "Erro no aplicativo",
                "Troca de equipamento","Internet lenta", "Sem sinal de TV", "Cobrança indevida", "Erro no aplicativo","Internet lenta"};
        String[] categorias = { "Suporte Técnico", "Financeiro", "Suporte Técnico", "Suporte Técnico", "Financeiro","Suporte Técnico", "Financeiro", "Suporte Técnico", "Suporte Técnico", "Financeiro" };
        int[] prioridades = { 3, 1, 5, 2, 4, 5, 1, 2, 3, 4 };

        int cadastradas = 0;
        for (int i = 0; i < nomes.length; i++) {
            try {
                central.cadastrarSolicitacao(nomes[i], descricoes[i], categorias[i], prioridades[i], "");
                cadastradas++;
            } catch (Fila.FilaCheiaException | Pilha.PilhaCheiaException e) {
                System.out.println("Não foi possível cadastrar mais solicitações: " + e.getMessage());
                break;
            }
        }
        System.out.println(cadastradas + " solicitação(ões) de teste cadastrada(s) com sucesso!");
    }
}
