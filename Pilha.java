// Pilha genérica (LIFO) implementada com vetor.
// Último que entra é o primeiro que sai.
public class Pilha<T> {

    public static class PilhaCheiaException extends Exception {
        public PilhaCheiaException(String mensagem) {
            super(mensagem);
        }
    }

    public static class PilhaVaziaException extends Exception {
        public PilhaVaziaException(String mensagem) {
            super(mensagem);
        }
    }

    // mesmo caso da Fila: Object[] porque new T[capacidade] não compila
    private Object[] elementos;
    private int topo;       // quantidade de elementos guardados = índice livre no vetor
    private int capacidade; // tamanho máximo do vetor

    public Pilha(int capacidade) {
        this.capacidade = capacidade;
        this.elementos = new Object[capacidade];
        this.topo = 0;
    }

    public void empilhar(T elemento) throws PilhaCheiaException {
        if (estaCheia()) {
            throw new PilhaCheiaException("A pilha atingiu sua capacidade máxima (" + capacidade + " elementos).");
        }
        elementos[topo] = elemento;
        topo++;
    }

    @SuppressWarnings("unchecked")
    public T desempilhar() throws PilhaVaziaException {
        if (estaVazia()) {
            throw new PilhaVaziaException("A pilha está vazia.");
        }
        topo--;
        T elemento = (T) elementos[topo];
        elementos[topo] = null;
        return elemento;
    }

    // Apenas olha o elemento do topo, sem removê-lo da pilha.
    @SuppressWarnings("unchecked")
    public T consultarTopo() throws PilhaVaziaException {
        if (estaVazia()) {
            throw new PilhaVaziaException("A pilha está vazia.");
        }
        return (T) elementos[topo - 1];
    }

    public boolean estaVazia() {
        return topo == 0;
    }

    public boolean estaCheia() {
        return topo == capacidade;
    }

    public int tamanho() {
        return topo;
    }

    // Exibe os elementos do topo para a base (do mais novo para o mais antigo),
    // respeitando a ordem de acesso LIFO.
    public void exibir() {
        if (estaVazia()) {
            System.out.println("Pilha vazia.");
            return;
        }
        for (int i = topo - 1; i >= 0; i--) {
            System.out.println(elementos[i]);
        }
    }
}
