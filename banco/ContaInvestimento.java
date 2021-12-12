package banco;

import java.util.ArrayList;

class Ativo {
    private String nome;
    private double rendimento;

    Ativo(String _nome, double _rendimento) {
        this.nome = _nome;
        this.rendimento = _rendimento;
    }

    public String getNome() {
        return nome;
    }

    public double getRendimento() {
        return rendimento;
    }
}

public class ContaInvestimento extends Conta {
    private ArrayList<Ativo> investimentos;

    public ContaInvestimento(String _nome, String _cpf, double _rendaMensal, int _numeroConta, String _agencia, double _saldo) {
        super(_nome, _cpf, _rendaMensal, _numeroConta, _agencia, _saldo);
        this.investimentos = new ArrayList<Ativo>();
        investimentos.add(new Ativo("soja",1));
        investimentos.add(new Ativo("milho",1.75));
        investimentos.add(new Ativo("oibr3",-2));
    }

    public double simularRendInvest(double quantia, String nomeAtivo) {
        for (Ativo i : investimentos) {
            if (nomeAtivo.equals(i.getNome())) {
                quantia += quantia * i.getRendimento()/100;
            }
        }
        return quantia;
    }

}
