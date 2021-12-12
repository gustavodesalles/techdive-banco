package banco;

public class ContaCorrente extends Conta {

    public ContaCorrente(String _nome, String _cpf, double _rendaMensal, int _numeroConta, String _agencia, double _saldo) {
        super(_nome, _cpf, _rendaMensal, _numeroConta, _agencia, _saldo);
        setMinimoValor(-0.15 * _rendaMensal);
    }

}
