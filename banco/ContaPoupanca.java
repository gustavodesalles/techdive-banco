package banco;

public class ContaPoupanca extends Conta {

    public ContaPoupanca(String _nome, String _cpf, double _rendaMensal, int _numeroConta, String _agencia, double _saldo) {
        super(_nome, _cpf, _rendaMensal, _numeroConta, _agencia, _saldo);
    }

    public double simularRendimento(int meses, double rentabilidade) {
        double percentual = rentabilidade / 100;
        double rentabM = Math.pow((1 + percentual), 1.0 / 12.0) - 1; // calcula a rentabilidade mensal a partir da anual
        double capitalFinal = getSaldo();

        for (int i = 0; i < meses; i++) {
            capitalFinal += capitalFinal * rentabM;
        }
        return Math.round(capitalFinal);
    }
}
