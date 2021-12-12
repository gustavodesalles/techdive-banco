package banco;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Conta> contas = new ArrayList<>();
    static ArrayList<String> historico = new ArrayList<>();
    static int idContas = 0;

    public static void criarConta() {
        scanner.nextLine(); // para evitar erros
        System.out.print("Digite o seu nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o seu CPF: ");
        String CPF = scanner.nextLine();

        while (Conta.verificarCPF(CPF) == false) {
            System.out.print("CPF inválido. Tente novamente: ");
            CPF = scanner.nextLine();
        }

        System.out.print("Digite a sua renda mensal: ");
        double rendaMensal = scanner.nextDouble();
        while (rendaMensal < 0) {
            System.out.print("Valor inválido. Tente novamente: ");
            rendaMensal = scanner.nextDouble();
        }

        idContas++;
        int numeroConta = idContas;

        scanner.nextLine(); // para evitar erros
        System.out.print("Digite a sua agência (001/002): ");
        String agencia = scanner.nextLine();
        while (agencia.equals("001") == false && agencia.equals("002") == false) {
            System.out.print("Agência inválida. Tente novamente: ");
            agencia = scanner.nextLine();
        }

        System.out.print("Digite o saldo: ");
        double saldo = scanner.nextDouble();
        if (saldo < 0) {
            saldo = 0;
        }

        System.out.println("Digite o tipo de conta que você deseja criar: \n1 - Corrente\n2 - Poupança\n3 - Investimento");
        int escolha = scanner.nextInt();
        while (escolha != 1 && escolha != 2 && escolha != 3) {
            System.out.print("Número inválido. Tente novamente: ");
            escolha = scanner.nextInt();
        }

        switch (escolha) {
            case 1:
                ContaCorrente contaCorrente = new ContaCorrente(nome, CPF, rendaMensal, numeroConta, agencia, saldo);
                contas.add(contaCorrente);
                break;

            case 2:
                ContaPoupanca contaPoupanca = new ContaPoupanca(nome, CPF, rendaMensal, numeroConta, agencia, saldo);
                contas.add(contaPoupanca);
                break;

            case 3:
                ContaInvestimento contaInvestimento = new ContaInvestimento(nome, CPF, rendaMensal, numeroConta, agencia, saldo);
                contas.add(contaInvestimento);
                break;
        }
        System.out.println("Conta número "+numeroConta+" criada.");
    }

    public static Conta obterConta(int numero) {
        if (!contas.isEmpty()) {
            for (Conta i : contas) {
                if (numero == i.getNumeroConta()) return i;
            }
        }
        return null;
    }

    public static void obterNegativos() {
        for (Conta i : contas) {
            if (i.getSaldo() < 0) System.out.println(i);
        }
    }

    public static void listar(String nomeClasse) {
        for (Conta i : contas) {
            if (String.valueOf(i.getClass()).equals(nomeClasse)) {
                System.out.println(i);
            }
        }
    }

    public static double valorTotal() {
        double res = 0;
        for (Conta i : contas) {
            if (i instanceof ContaInvestimento) {
                res += i.getSaldo();
            }
        }
        return res;
    }

    public static void obterHistorico() {
        for (String s : historico) {
            System.out.println(s);
        }
    }

    public static void menuRelatorios() {
        System.out.println("Selecione uma opção:");
        System.out.println("1 - Listar todas as contas correntes");
        System.out.println("2 - Listar todas as contas-poupança");
        System.out.println("3 - Listar todas as contas-investimento");
        System.out.println("4 - Contas com saldo negativo");
        System.out.println("5 - Total do valor investido");
        System.out.println("6 - Histórico de transações");
        System.out.println("Digite qualquer outra tecla para sair.");

        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                listar("class banco.ContaCorrente");
                main(null);
                break;

            case 2:
                listar("class banco.ContaPoupanca");
                main(null);
                break;

            case 3:
                listar("class banco.ContaInvestimento");
                main(null);
                break;

            case 4:
                obterNegativos();
                main(null);
                break;

            case 5:
                System.out.println("Valor total investido: R$ "+valorTotal());
                main(null);
                break;

            case 6:
                obterHistorico();
                main(null);
                break;

            default:
                main(null);
                break;
        }
    }

    public static void submenu(Conta conta) {

        String res;
        double valor;
        System.out.println("\nSelecione uma opção:");
        System.out.println("1 - Saque");
        System.out.println("2 - Depósito");
        System.out.println("3 - Transferência");
        System.out.println("4 - Checar saldo");
        System.out.println("5 - Extrato");
        System.out.println("6 - Alterar dados");
        System.out.println("7 - Simular rendimento da poupança");
        System.out.println("8 - Simular rendimento do investimento");
        System.out.println("Digite qualquer outra tecla para sair.");

        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                System.out.print("Digite o valor que deseja sacar: ");
                valor = scanner.nextDouble();

                res = conta.saque(valor,false);

                if (res != null) {
                    historico.add("Conta " + conta.getNumeroConta() + " " + res);
                }

                main(null);
                break;

            case 2:
                System.out.print("Digite o valor que deseja depositar: ");
                valor = scanner.nextDouble();

                res = conta.deposito(valor,false);

                historico.add("Conta "+conta.getNumeroConta()+" "+res);

                main(null);
                break;

            case 3:
                if (contas.size() == 1) {
                    System.out.println("Há somente uma conta no banco.");

                    main(null);
                    break;
                } else {
                    System.out.print("Digite o valor que deseja transferir: ");
                    valor = scanner.nextDouble();

                    System.out.print("Digite o número da conta destino: ");
                    int destino = scanner.nextInt();

                    res = conta.transferencia(valor, Main.obterConta(destino));
                    if (res != null) {
                        historico.add("Conta " + conta.getNumeroConta() + " " + res);
                    } else {
                        System.out.println("Não foi possível efetuar a transferência.");
                    }

                    main(null);
                    break;
                }

            case 4:
                System.out.println(conta.getSaldo());

                main(null);
                break;

            case 5:
                conta.getExtrato();

                main(null);
                break;

            case 6:
                scanner.nextLine(); // para evitar erros
                System.out.print("Digite o novo nome: ");
                String novoNome = scanner.nextLine();
                if (!novoNome.isEmpty()) conta.setNome(novoNome);

                System.out.print("Digite a nova renda mensal: ");
                double novaRenda = scanner.nextDouble();
                if (novaRenda >= 0) {
                    conta.setRendaMensal(novaRenda);
                    if (conta instanceof ContaCorrente) conta.setMinimoValor(-0.15 * novaRenda);
                }

                main(null);
                break;

            case 7:
                if (conta instanceof ContaPoupanca) {
                    System.out.print("Digite a quantidade de meses: ");
                    int meses = scanner.nextInt();
                    while (meses <= 0) {
                        System.out.print("Valor inválido. Tente novamente: ");
                        meses = scanner.nextInt();
                    }

                    System.out.print("Digite a rentabilidade da poupança: ");
                    double rent = scanner.nextDouble();
                    System.out.println((((ContaPoupanca) conta).simularRendimento(meses, rent)));

                    main(null);
                    break;
                } else {
                    System.out.println("Esta conta não é poupança.");

                    main(null);
                    break;
                }

            case 8:
                if (conta instanceof ContaInvestimento) {
                    System.out.print("Digite o valor do investimento: ");
                    valor = scanner.nextDouble();

                    scanner.nextLine(); // para evitar erros
                    System.out.print("Digite o nome do ativo (soja/milho/oibr3): ");
                    String nomeAtivo = scanner.nextLine();

                    while (!nomeAtivo.equals("soja") && !nomeAtivo.equals("milho") && !nomeAtivo.equals("oibr3")) {
                        System.out.print("Nome inválido. Digite novamente (soja/milho/oibr3): ");
                        nomeAtivo = scanner.nextLine();
                    }

                    System.out.println((((ContaInvestimento) conta).simularRendInvest(valor, nomeAtivo)));

                    main(null);
                    break;
                } else {
                    System.out.println("Esta conta não é investimento.");

                    main(null);
                    break;
                }

            default:
                main(null);
                break;
        }
    }

    public static void main(String[] args) {
        System.out.println("\nSelecione uma opção:");
        System.out.println("1 - Criar conta");
        System.out.println("2 - Entrar");
        System.out.println("3 - Relatórios");
        System.out.println("Digite qualquer outra tecla para sair.");
        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                Main.criarConta();
                main(null);
                break;

            case 2:
                if (contas.isEmpty()) {
                    System.out.println("Não há contas registradas no banco.");
                    main(null);
                    break;
                }
                System.out.print("Digite o número da conta: ");
                int num = scanner.nextInt();
                Conta conta = Main.obterConta(num);
                if (conta == null) {
                    System.out.println("Conta não encontrada.");
                    main(null);
                    break;
                }
                submenu(conta);
                break;

            case 3:
                menuRelatorios();
                break;

            default:
                System.out.println("Até mais!");
        }
    }
}
