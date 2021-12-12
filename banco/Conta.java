package banco;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Conta {
    // Atributos
    private String nome;
    private String cpf;
    private double rendaMensal;
    private int numeroConta;
    private String agencia;
    private double saldo;
    private ArrayList<String> extrato;
    private double minimoValor = 0;

    // Construtor
    public Conta(String _nome, String _cpf, double _rendaMensal, int _numeroConta, String _agencia, double _saldo) {
        this.nome = _nome;
        this.cpf = _cpf;
        this.rendaMensal = _rendaMensal;
        this.numeroConta = _numeroConta;
        this.agencia = _agencia;
        this.saldo = _saldo;
        this.extrato = new ArrayList<String>();
    }

    // Métodos
    public static boolean verificarCPF(String cpf) { // adaptado do código disponível em: https://www.devmedia.com.br/validando-o-cpf-em-uma-aplicacao-java/22097
        if (cpf.length() != 11) return false;
        char dig10 = cpf.charAt(9);
        char dig11 = cpf.charAt(10);
        int num, soma, peso, resto;
        int zeroAscii = 48;
        char digv1, digv2;

        // calcular o primeiro digito verificador
        soma = 0;
        peso = 10;

        for (int i = 0; i < 9; i++) {
            num = (int) (cpf.charAt(i) - zeroAscii); // subtrair por 48 de acordo com a tabela ASCII
            soma += (num * peso);
            peso--;
        }

        resto = 11 - (soma % 11);
        if (resto == 10 || resto == 11) {
            digv1 = '0';
        } else digv1 = (char) (resto + zeroAscii);

        // calcular o segundo digito verificador
        soma = 0;
        peso = 11;
        for (int j = 0; j < 10; j++) {
            num = (int) (cpf.charAt(j) - zeroAscii);
            soma += (num * peso);
            peso--;
        }

        resto = 11 - (soma % 11);
        if (resto == 10 || resto == 11) {
            digv2 = '0';
        } else digv2 = (char) (resto + zeroAscii);

        return (digv1 == dig10 && digv2 == dig11);
    }

    public String saque(double quantia, boolean isTransfer) {
        String res = null;

        if (saldo - quantia >= minimoValor) {
            saldo -= quantia;
            if (!isTransfer) { // caso o saque não seja parte de uma transferência, adiciona-se uma string ao extrato
                res = "Saque R$ "+quantia+" "+ LocalDateTime.now().toString();
                extrato.add(res);
                System.out.println(res);
            }
        } else {
            System.out.println("Saldo insuficiente.");
        }

        return res;
    }

    public String deposito(double quantia, boolean isTransfer) {
        String res = null;

        saldo += quantia;
        if (!isTransfer) { // caso o depósito não seja parte de uma transferência, adiciona-se uma string ao extrato
            res = "Depósito R$ "+quantia+" "+ LocalDateTime.now().toString();
            extrato.add(res);
            System.out.println(res);
        }

        return res;
    }

    public String transferencia(double quantia, Conta destino) {
        String res = null;

        saque(quantia, true);
        if (saldo - quantia >= minimoValor && destino != null) {
            destino.deposito(quantia, true);
            res = "Transferência R$ "+quantia+" Destino "+destino.getNumeroConta()+" "+ LocalDateTime.now().toString();
            extrato.add(res);
            System.out.println(res);
        }

        return res;
    }

    public void getExtrato() {
        for (String i : extrato) {
            System.out.println(i);
        }
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public double getRendaMensal() {
        return rendaMensal;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getMinimoValor() {
        return minimoValor;
    }

    public void setMinimoValor(double minimoValor) {
        this.minimoValor = minimoValor;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRendaMensal(double rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public void setAgencia(String agencia) {
        if (agencia == "001" || agencia == "002") this.agencia = agencia;
    }

    @Override
    public String toString() {
        return "Conta " + numeroConta +
                " Nome: " + nome +
                " CPF: " + cpf +
                " Renda mensal: " + rendaMensal +
                " Agência " + agencia +
                " Saldo " + saldo;
    }
}