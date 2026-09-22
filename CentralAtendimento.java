import java.util.Date;

// Aqui fica a regra de negócio do sistema. O Main só chama os métodos daqui,
// quem mexe na Fila e na Pilha de verdade é essa classe.
public class CentralAtendimento {
    private static final int CAPACIDADE = 50;

    private Fila<Solicitacao> filaEspera;         // solicitações aguardando atendimento (FIFO)
    private Pilha<Operacao> historicoOperacoes;   // últimas operações feitas no sistema (LIFO)
    private int proximoCodigo;                    // gera o código de cada nova solicitação

    public CentralAtendimento() {
        filaEspera = new Fila<>(CAPACIDADE);
        historicoOperacoes = new Pilha<>(CAPACIDADE);
        proximoCodigo = 101;
    }

    // não trato a exceção aqui, só repasso pro Main decidir o que mostrar pro usuário
    public Solicitacao cadastrarSolicitacao(String nomeSolicitante, String descricao, String categoria,
            int prioridade, String responsavel) throws Fila.FilaCheiaException, Pilha.PilhaCheiaException {
        // checa a pilha ANTES de mexer na fila. Se checasse depois, dava pra cair numa
        // situação ruim: solicitação já entrou na fila mas não sobrou espaço pra
        // registrar ela no histórico (pilha e fila não ficam mais "sincronizadas")
        if (historicoOperacoes.estaCheia()) {
            throw new Pilha.PilhaCheiaException("Histórico de operações está cheio, não é possível cadastrar agora.");
        }

        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setCodigo(proximoCodigo++);
        solicitacao.setNomeSolicitante(nomeSolicitante);
        solicitacao.setDescricao(descricao);
        solicitacao.setCategoria(categoria);
        solicitacao.setPrioridade(prioridade);
        solicitacao.setDataSolicitacao(new Date());
        solicitacao.setStatus("AGUARDANDO");
        solicitacao.setResponsavel(responsavel);

        filaEspera.enfileirar(solicitacao);
        historicoOperacoes.empilhar(new Operacao("CADASTRO", solicitacao));

        return solicitacao;
    }

    // Só olha quem é o próximo da fila, sem tirar ninguém dela.
    public Solicitacao consultarProxima() throws Fila.FilaVaziaException {
        return filaEspera.consultarProximo();
    }

    // Remove o primeiro da fila (o que está esperando há mais tempo) e conclui o atendimento.
    public Solicitacao atenderProxima(String responsavel) throws Fila.FilaVaziaException, Pilha.PilhaCheiaException {
        // mesma ideia do cadastro: se a pilha estiver cheia, aborta ANTES de tirar
        // a solicitação da fila, senão ela seria removida da fila e perdida, sem
        // nunca aparecer no histórico
        if (historicoOperacoes.estaCheia()) {
            throw new Pilha.PilhaCheiaException("Histórico de operações está cheio, não é possível atender agora.");
        }

        Solicitacao solicitacao = filaEspera.desenfileirar();
        solicitacao.setStatus("CONCLUIDA");
        if (responsavel != null && !responsavel.isBlank()) {
            solicitacao.setResponsavel(responsavel);
        }
        historicoOperacoes.empilhar(new Operacao("ATENDIMENTO", solicitacao));
        return solicitacao;
    }

    public boolean filaVazia() {
        return filaEspera.estaVazia();
    }

    public int quantidadeAguardando() {
        return filaEspera.tamanho();
    }

    public void exibirFila() {
        filaEspera.exibir();
    }

    public boolean historicoVazio() {
        return historicoOperacoes.estaVazia();
    }

    // Consulta a última operação sem removê-la do histórico (olha o topo da pilha).
    public Operacao consultarUltimaOperacao() throws Pilha.PilhaVaziaException {
        return historicoOperacoes.consultarTopo();
    }

    public void exibirHistorico() {
        historicoOperacoes.exibir();
    }

    // Desempilha a última operação. Obs: só tira o registro do histórico, não
    // desfaz o efeito dela na Solicitacao (o status continua CONCLUIDA, por ex.)
    public Operacao desfazerUltimaOperacao() throws Pilha.PilhaVaziaException {
        return historicoOperacoes.desempilhar();
    }
}
