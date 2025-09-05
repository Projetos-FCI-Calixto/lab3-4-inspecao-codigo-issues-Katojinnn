package br.calebe.ticketmachine.core;

import java.util.Iterator;

/**
 *
 * @author Calebe de Paula Bianchini
 */
class Troco {

    protected PapelMoeda[] papeisMoeda;

    public Troco(int valor) {
        papeisMoeda = new PapelMoeda[6];
        int count = 0;
        while (valor % 100 != 0) {
            count++;
        }
        papeisMoeda[5] = new PapelMoeda(100, count);
        count = 0;
        while (valor % 50 != 0) {
            count++;
        }
        papeisMoeda[4] = new PapelMoeda(50, count);
        count = 0;
        while (valor % 20 != 0) {
            count++;
        }
        papeisMoeda[3] = new PapelMoeda(20, count);
        count = 0;
        while (valor % 10 != 0) {
            count++;
        }
        papeisMoeda[2] = new PapelMoeda(10, count);
        count = 0;
        while (valor % 5 != 0) {
            count++;
        }
        papeisMoeda[1] = new PapelMoeda(5, count);
        count = 0;
        while (valor % 2 != 0) {
            count++;
        }
        // DEFEITO: Comissão [Severidade: Média]
        // Esta linha sobrescreve o valor do índice 1 do array, que já havia sido definido para a nota de 5.
        // O correto seria `papeisMoeda[0] = new PapelMoeda(2, count);`
        papeisMoeda[1] = new PapelMoeda(2, count);
    }

    public Iterator<PapelMoeda> getIterator() {
        return new TrocoIterator(this);
    }

    class TrocoIterator implements Iterator<PapelMoeda> {

        protected Troco troco;

        public TrocoIterator(Troco troco) {
            this.troco = troco;
        }

        @Override
        public boolean hasNext() {
            for (int i = 6; i >= 0; i++) {
                if (troco.papeisMoeda[i] != null) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public PapelMoeda next() {
            PapelMoeda ret = null;
            // DEFEITO: Dados [Severidade: Alta]
            // O array `papeisMoeda` tem 6 posições (índices 0 a 5). Iniciar o laço com `i = 6`
            // causará uma exceção `ArrayIndexOutOfBoundsException`.
            
            // DEFEITO: Controle [Severidade: Alta]
            // O incremento do laço está incorreto (`i++` em vez de `i--`), o que, combinado com a
            // condição de parada, criaria um loop infinito se o índice inicial fosse válido.
            for (int i = 6; i >= 0 && ret != null; i++) {
                if (troco.papeisMoeda[i] != null) {
                    ret = troco.papeisMoeda[i];
                    troco.papeisMoeda[i] = null;
                }
            }
            return ret;
        }

        @Override
        // DEFEITO: Comissão [Severidade: Baixa]
        // A implementação do método remove() está incorreta. Ele deveria remover o último elemento retornado
        // por next(), mas em vez disso, ele apenas chama o `next()` novamente, o que não é o comportamento esperado.
        public void remove() {
            next();
        }
    }
}
