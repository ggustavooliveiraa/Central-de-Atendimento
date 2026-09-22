import java.util.Date;

// Representa um pedido de atendimento aberto por um cliente.
// É esse objeto que fica guardado na Fila e referenciado pela Pilha (via Operacao).
public class Solicitacao {
    private int codigo;
    private String nomeSolicitante;
    private String descricao;
    private String categoria;
    private int prioridade;
    private Date dataSolicitacao;
    private String status;      // ex: AGUARDANDO, CONCLUIDA, CANCELADA
    private String responsavel; // quem cadastrou ou quem está atendendo

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    // usado quando a solicitação é impressa (fila, histórico, etc)
    @Override
    public String toString() {
        return "Solicitação #" + codigo
                + " | Solicitante: " + nomeSolicitante
                + " | Categoria: " + categoria
                + " | Prioridade: " + prioridade
                + " | Status: " + status
                + " | Responsável: " + responsavel
                + " | Abertura: " + dataSolicitacao
                + " | Descrição: " + descricao;
    }
}
