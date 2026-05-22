public class SistemaBancario{
    Arquivo: SaldoInsuficienteException.java
package ao.universidade.poo.banco;

    public class SaldoInsuficienteException extends Exception {

        public SaldoInsuficienteException(String mensagem) {
            super(mensagem);
        }
    }
    Arquivo: Conta.java
package ao.universidade.poo.banco;

    public class Conta {

        protected String numero;
        protected double saldo;

        public Conta(String numero, double saldoInicial) {

            if (numero == null || numero.isBlank()) {
                throw new IllegalArgumentException("Número inválido");
            }

            this.numero = numero;
            this.saldo = saldoInicial;
        }

        public String getNumero() {
            return numero;
        }

        public double getSaldo() {
            return saldo;
        }

        public void depositar(double valor) {

            if (valor <= 0) {
                throw new IllegalArgumentException(
                        "Valor de depósito deve ser positivo"
                );
            }

            saldo += valor;
        }

        public void sacar(double valor)
                throws SaldoInsuficienteException {

            if (valor <= 0) {
                throw new IllegalArgumentException(
                        "Valor de saque deve ser positivo"
                );
            }

            if (saldo < valor) {
                throw new SaldoInsuficienteException(
                        "Saldo insuficiente: saldo="
                                + saldo
                                + ", valor="
                                + valor
                );
            }

            saldo -= valor;
        }

        public void transferir(Conta destino, double valor)
                throws SaldoInsuficienteException {

            if (destino == null) {
                throw new IllegalArgumentException(
                        "Conta destino nula"
                );
            }

            sacar(valor);
            destino.depositar(valor);
        }

        @Override
        public String toString() {
            return String.format(
                    "Conta %s - Saldo: %.2f",
                    numero,
                    saldo
            );
        }
    }
//Arquivo: ContaCorrente.java
package ao.universidade.poo.banco;

    public class ContaCorrente extends Conta {

        private double limite;

        public ContaCorrente(
                String numero,
                double saldoInicial,
                double limite
        ) {

            super(numero, saldoInicial);

            if (limite < 0) {
                throw new IllegalArgumentException("Limite negativo");
            }

            this.limite = limite;
        }

        public double getLimite() {
            return limite;
        }

        public void setLimite(double limite) {

            if (limite < 0) {
                throw new IllegalArgumentException("Limite negativo");
            }

            this.limite = limite;
        }

        @Override
        public void sacar(double valor)
                throws SaldoInsuficienteException {

            if (valor <= 0) {
                throw new IllegalArgumentException(
                        "Valor de saque deve ser positivo"
                );
            }

            if (saldo + limite < valor) {
                throw new SaldoInsuficienteException(
                        "Saldo + limite insuficiente: saldo="
                                + saldo
                                + ", limite="
                                + limite
                                + ", valor="
                                + valor
                );
            }

            saldo -= valor;
        }

        @Override
        public String toString() {
            return String.format(
                    "ContaCorrente %s - Saldo: %.2f - Limite: %.2f",
                    numero,
                    saldo,
                    limite
            );
        }
    }
    Arquivo: ContaPoupanca.java
package ao.universidade.poo.banco;

    public class ContaPoupanca extends Conta {

        private double taxaRendimento;

        public ContaPoupanca(
                String numero,
                double saldoInicial,
                double taxaRendimento
        ) {

            super(numero, saldoInicial);

            if (taxaRendimento < 0) {
                throw new IllegalArgumentException("Taxa negativa");
            }

            this.taxaRendimento = taxaRendimento;
        }

        public double getTaxaRendimento() {
            return taxaRendimento;
        }

        public void setTaxaRendimento(double taxaRendimento) {

            if (taxaRendimento < 0) {
                throw new IllegalArgumentException("Taxa negativa");
            }

            this.taxaRendimento = taxaRendimento;
        }

        public void aplicarRendimento() {
            saldo += saldo * taxaRendimento;
        }

        @Override
        public String toString() {
            return String.format(
                    "ContaPoupanca %s - Saldo: %.2f - Taxa: %.4f",
                    numero,
                    saldo,
                    taxaRendimento
            );
        }
    }
    Arquivo: BancoApp.java
package ao.universidade.poo.banco;

import java.util.ArrayList;
import java.util.List;

    public class BancoApp {

        public static void main(String[] args) {

            List<Conta> contas = new ArrayList<>();

            ContaCorrente cc1 = new ContaCorrente(
                    "001",
                    500.0,
                    300.0
            );

            ContaPoupanca cp1 = new ContaPoupanca(
                    "002",
                    1000.0,
                    0.01
            );

            contas.add(cc1);
            contas.add(cp1);

            System.out.println("Estado inicial das contas:");
            imprimirContas(contas);
            System.out.println();

            System.out.println("Depositando 200 em todas as contas:");

            for (Conta conta : contas) {
                conta.depositar(200);
            }

            imprimirContas(contas);
            System.out.println();

            try {
                System.out.println(
                        "Tentando sacar 1000 da conta corrente:"
                );

                cc1.sacar(1000);

                System.out.println("Saque efetuado.");

            } catch (SaldoInsuficienteException e) {
                System.out.println("Erro: " + e.getMessage());
            }

            imprimirContas(contas);
            System.out.println();

            try {
                System.out.println(
                        "Tentando sacar 5000 da poupança:"
                );

                cp1.sacar(5000);

            } catch (SaldoInsuficienteException e) {
                System.out.println("Erro: " + e.getMessage());
            }

            imprimirContas(contas);
            System.out.println();

            try {
                System.out.println(
                        "Transferindo 300 da poupança para a conta corrente:"
                );

                cp1.transferir(cc1, 300);

                System.out.println("Transferência efetuada.");

            } catch (SaldoInsuficienteException e) {
                System.out.println("Erro: " + e.getMessage());
            }

            imprimirContas(contas);
            System.out.println();

            for (Conta conta : contas) {

                if (conta instanceof ContaPoupanca) {

                    ContaPoupanca cp = (ContaPoupanca) conta;

                    cp.aplicarRendimento();

                    System.out.println(
                            "Rendimento aplicado em "
                                    + cp.getNumero()
                    );
                }
            }

            imprimirContas(contas);
        }

        private static void imprimirContas(List<Conta> contas) {

            for (Conta conta : contas) {
                System.out.println(conta);
            }
        }
    }

}
