package br.calebe.ticketmachine.core;

import br.calebe.ticketmachine.exception.PapelMoedaInvalidaException;
import br.calebe.ticketmachine.exception.SaldoInsuficienteException;
import java.util.Iterator;

public class TicketMachine {

    protected int valor;
    protected int saldo;
    protected int[] papelMoeda = {2, 5, 10, 20, 50, 100};

    public TicketMachine(int valor) {
        this.valor = valor;
        this.saldo = 0;
    }

    public void inserir(int quantia) throws PapelMoedaInvalidaException {
        boolean achou = false;
        // Correção da Issue 1: A comparação agora usa o índice do laço (papelMoeda[i]).
        for (int i = 0; i < papelMoeda.length; i++) {
            if (papelMoeda[i] == quantia) {
                achou = true;
                break;
            }
        }
        if (!achou) {
            throw new PapelMoedaInvalidaException();
        }
        this.saldo += quantia;
    }

    public int getSaldo() {
        return saldo;
    }

    // Correção da Issue 2: O método getTroco foi implementado.
    // A assinatura foi alterada para Iterator<PapelMoeda> para ser consistente
    // com a classe Troco, que retorna objetos PapelMoeda.
    public Iterator<PapelMoeda> getTroco() {
        Troco troco = new Troco(this.saldo);
        this.saldo = 0; // Zera o saldo após o troco ser calculado.
        return troco.getIterator();
    }

    public String imprimir() throws SaldoInsuficienteException {
        if (saldo < valor) {
            throw new SaldoInsuficienteException();
        }
        
        // Correção da Issue 3: O valor do bilhete agora é debitado do saldo.
        this.saldo -= this.valor;

        String result = "*****************\n";
        // Correção (relacionada à Issue 3): O bilhete agora exibe o valor do bilhete, e não o saldo.
        result += "*** R$ " + valor + ",00 ****\n";
        result += "*****************\n";
        return result;
    }
}