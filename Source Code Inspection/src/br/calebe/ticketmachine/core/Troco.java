// Arquivo: src/br/calebe/ticketmachine/core/Troco.java

package br.calebe.ticketmachine.core;

import java.util.Iterator;
import java.util.NoSuchElementException;

class Troco {

    protected PapelMoeda[] papeisMoeda;

    // Correção das Issues 4, 5 e 6: O construtor foi reescrito com uma lógica funcional.
    public Troco(int valor) {
        papeisMoeda = new PapelMoeda[6];
        int[] notas = {100, 50, 20, 10, 5, 2};
        
        for (int i = 0; i < notas.length; i++) {
            int quantidade = valor / notas[i];
            if (quantidade > 0) {
                papeisMoeda[i] = new PapelMoeda(notas[i], quantidade);
                valor %= notas[i];
            }
        }
    }

    public Iterator<PapelMoeda> getIterator() {
        return new TrocoIterator(this);
    }

    // Correção da Issue 7: A classe TrocoIterator foi completamente reescrita.
    class TrocoIterator implements Iterator<PapelMoeda> {

        protected Troco troco;
        protected int cursor;

        public TrocoIterator(Troco troco) {
            this.troco = troco;
            this.cursor = 0;
        }

        @Override
        public boolean hasNext() {
            for (int i = cursor; i < troco.papeisMoeda.length; i++) {
                if (troco.papeisMoeda[i] != null) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public PapelMoeda next() {
            for (int i = cursor; i < troco.papeisMoeda.length; i++) {
                if (troco.papeisMoeda[i] != null) {
                    PapelMoeda ret = troco.papeisMoeda[i];
                    cursor = i + 1;
                    return ret;
                }
            }
            throw new NoSuchElementException();
        }
    }
}