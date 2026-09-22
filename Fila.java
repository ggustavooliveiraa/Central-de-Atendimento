// Fila genérica (FIFO) implementada com vetor.
// Primeiro que entra é o primeiro que sai.
public class Fila<T> {

    public static class FilaCheiaException extends Exception {
        public FilaCheiaException(String mensagem) {
            super(mensagem);
        }
    }

    public static class FilaVaziaException extends Exception {
        public FilaVaziaException(String mensagem) {
            super(mensagem);
        }
    }

    // Object[] e não T[] porque o Java não deixa criar vetor genérico direto
    // (new T[capacidade] nem compila)
    private Object[] elementos;
    private int inicio;     // índice do elemento mais antigo (próximo a sair)
    private int fim;        // índice do último elemento inserido
    private int quantidade; // quantos elementos estão realmente ocupados no vetor
    private int capacidade; // tamanho máximo do vetor

    public Fila(int capacidade) {
        this.capacidade = capacidade;
        this.elementos = new Object[capacidade];
        this.inicio = 0;
        this.fim = -1;
        this.quantidade = 0;
    }

    // Insere no fim da fila. O % faz o índice "fim" voltar pro 0 quando chega
    // no final do vetor, aproveitando os espaços que o desenfileirar() liberou
    // (por isso é uma fila circular)
    public void enfileirar(T elemento) throws FilaCheiaException {
        if (estaCheia()) {
            throw new FilaCheiaException("A fila atingiu sua capacidade máxima (" + capacidade + " elementos).");
        }
        fim = (fim + 1) % capacidade;
        elementos[fim] = elemento;
        quantidade++;
    }

    @SuppressWarnings("unchecked")
    public T desenfileirar() throws FilaVaziaException {
        if (estaVazia()) {
            throw new FilaVaziaException("A fila está vazia.");
        }
        T elemento = (T) elementos[inicio];
        elementos[inicio] = null;
        inicio = (inicio + 1) % capacidade;
        quantidade--;
        return elemento;
    }

    // Apenas olha o próximo elemento a sair, sem removê-lo da fila.
    @SuppressWarnings("unchecked")
    public T consultarProximo() throws FilaVaziaException {
        if (estaVazia()) {
            throw new FilaVaziaException("A fila está vazia.");
        }
        return (T) elementos[inicio];
    }

    public boolean estaVazia() {
        return quantidade == 0;
    }

    public boolean estaCheia() {
        return quantidade == capacidade;
    }

    public int tamanho() {
        return quantidade;
    }

    // Exibe os elementos do início (mais antigo) para o fim (mais novo),
    // respeitando a ordem de atendimento FIFO.
    public void exibir() {
        if (estaVazia()) {
            System.out.println("Fila vazia.");
            return;
        }
        for (int i = 0; i < quantidade; i++) {
            // percorre "quantidade" posições a partir do início, sempre voltando
            // ao começo do vetor quando passa do fim (por isso o %)
            int indice = (inicio + i) % capacidade;
            System.out.println(elementos[indice]);
        }
    }
}
