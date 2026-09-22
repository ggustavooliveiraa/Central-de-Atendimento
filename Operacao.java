// Representa um registro de histórico (o que aconteceu com uma Solicitacao).
// Cada Operacao é empilhada em CentralAtendimento.historicoOperacoes.
public class Operacao {
    private String tipo; // ex: CADASTRO, ATENDIMENTO, CANCELAMENTO
    private Solicitacao solicitacao;

    public Operacao(String tipo, Solicitacao solicitacao) {
        this.tipo = tipo;
        this.solicitacao = solicitacao;
    }

    public String getTipo() {
        return tipo;
    }

    public Solicitacao getSolicitacao() {
        return solicitacao;
    }

    @Override
    public String toString() {
        return "[" + tipo + "] Solicitação " + solicitacao.getCodigo()
                + " - " + solicitacao.getNomeSolicitante();
    }
}
