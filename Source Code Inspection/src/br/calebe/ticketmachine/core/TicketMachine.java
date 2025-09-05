package br.calebe.ticketmachine.core;

import br.calebe.ticketmachine.exception.PapelMoedaInvalidaException;
import br.calebe.ticketmachine.exception.SaldoInsuficienteException;
import java.util.Iterator;

/**
 *
 * @author Calebe de Paula Bianchini
 */
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
        for (int i = 0; i < papelMoeda.length && !achou; i++) {
            // DEFEITO: Comissão [Severidade: Alta]
            // A comparação está sendo feita sempre com o índice 1 (papelMoeda[1]), que corresponde à nota de 5.
            // O correto seria comparar com o índice do laço (papelMoeda[i]).
            // Isso impede a inserção de qualquer nota que não seja a de 5.
            if (papelMoeda[1] == quantia) {
                achou = true;
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
        // DEFEITO: Omissão [Severidade: Alta]
        // O método getTroco, que implementa o caso de uso (Solicitar troco), não foi implementado.
        // Conforme a documentação, ele deveria verificar o saldo e devolvê-lo em notas.
    public Iterator<Integer> getTroco() {
        return null;
    }

    public String imprimir() throws SaldoInsuficienteException {
        if (saldo < valor) {
            throw new SaldoInsuficienteException();
        }
        // DEFEITO: Comissão [Severidade: Alta]
        // A documentação especifica que o valor do bilhete deve ser debitado do saldo.
        // O código não realiza essa operação (saldo = saldo - valor).
        String result = "*****************\n";
        // DEFEITO: Comissão [Severidade: Média]
        // O bilhete impresso mostra o saldo total disponível, e não o valor do bilhete que foi pago.
        // O correto seria exibir o 'valor' do bilhete.
        result += "*** R$ " + saldo + ",00 ****\n";
        result += "*****************\n";
        return result;
    }
}
