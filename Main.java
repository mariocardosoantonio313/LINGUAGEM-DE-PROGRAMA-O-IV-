import Livro.Livro;

public class Main {
    public static void main(String[] args) {
        //Problema 1 — Sistema de Biblioteca
Arquivo: Livro.java
package ao.universidade.poo.biblioteca;

        public class Livro {
            private String titulo;
            private String autor;
            private String isbn;
            private boolean disponivel = true;

            public Livro(String titulo, String autor, String isbn) {
                setTitulo(titulo);
                setAutor(autor);
                setIsbn(isbn);
            }

            public String getTitulo() {
                return titulo;
            }

            public void setTitulo(String titulo) {
                if (titulo == null || titulo.isBlank()) {
                    throw new IllegalArgumentException("Título inválido");
                }
                this.titulo = titulo;
            }

            public String getAutor() {
                return autor;
            }

            public void setAutor(String autor) {
                if (autor == null || autor.isBlank()) {
                    throw new IllegalArgumentException("Autor inválido");
                }
                this.autor = autor;
            }

            public String getIsbn() {
                return isbn;
            }

            public void setIsbn(String isbn) {
                if (isbn == null || isbn.length() != 13) {
                    throw new IllegalArgumentException("ISBN deve ter 13 caracteres");
                }
                this.isbn = isbn;
            }

            public boolean isDisponivel() {
                return disponivel;
            }

            public boolean emprestar() {
                if (!disponivel) {
                    return false;
                }

                disponivel = false;
                return true;
            }

            public void devolver() {
                disponivel = true;
            }

            public String info() {
                return String.format(
                        "%s - %s (ISBN: %s) Disponível: %s",
                        titulo,
                        autor,
                        isbn,
                        disponivel ? "Sim" : "Não"
                );
            }

            @Override
            public String toString() {
                return info();
            }
        }
Arquivo: Aluno.java
package ao.universidade.poo.biblioteca;

import java.util.ArrayList;
import java.util.List;

        public class Aluno {
            private String nome;
            private String numeroMatricula;
            private String curso;
            private List<Livro> emprestimos = new ArrayList<>();

            public Aluno(String nome, String numeroMatricula, String curso) {
                setNome(nome);
                setNumeroMatricula(numeroMatricula);
                setCurso(curso);
            }

            public String getNome() {
                return nome;
            }

            public void setNome(String nome) {
                if (nome == null || nome.isBlank()) {
                    throw new IllegalArgumentException("Nome inválido");
                }
                this.nome = nome;
            }

            public String getNumeroMatricula() {
                return numeroMatricula;
            }

            public void setNumeroMatricula(String numeroMatricula) {
                if (numeroMatricula == null || numeroMatricula.isBlank()) {
                    throw new IllegalArgumentException("Matrícula inválida");
                }
                this.numeroMatricula = numeroMatricula;
            }

            public String getCurso() {
                return curso;
            }

            public void setCurso(String curso) {
                if (curso == null) {
                    curso = "";
                }

                this.curso = curso;
            }

            public List<Livro> getEmprestimos() {
                return new ArrayList<>(emprestimos);
            }

            public boolean matricularLivro(Livro livro) {
                if (livro == null) {
                    throw new IllegalArgumentException("Livro nulo");
                }

                if (!livro.emprestar()) {
                    return false;
                }

                emprestimos.add(livro);
                return true;
            }

            public boolean devolverLivro(Livro livro) {
                if (livro == null) {
                    throw new IllegalArgumentException("Livro nulo");
                }

                boolean removido = emprestimos.remove(livro);

                if (removido) {
                    livro.devolver();
                }

                return removido;
            }

            public String mostrarEmprestimos() {
                if (emprestimos.isEmpty()) {
                    return nome + " não tem empréstimos.";
                }

                StringBuilder sb = new StringBuilder(
                        nome + " tem os seguintes livros:\n"
                );

                for (Livro livro : emprestimos) {
                    sb.append(" - ")
                            .append(livro.info())
                            .append("\n");
                }

                return sb.toString();
            }

            @Override
            public String toString() {
                return nome + " (" + numeroMatricula + ") - " + curso;
            }
        }
//Arquivo: BibliotecaApp.java
package ao.universidade.poo.biblioteca;

        public class BibliotecaApp {

            public static void main(String[] args) {

                Livro l1 = new Livro(
                        "Introdução a Java",
                        "Ana Silva",
                        "9781234567890"
                );

                Livro l2 = new Livro(
                        "Estruturas de Dados",
                        "Carlos Souza",
                        "9780987654321"
                );

                Aluno a1 = new Aluno(
                        "João Pereira",
                        "2023001",
                        "Engenharia Informática"
                );

                Aluno a2 = new Aluno(
                        "Maria Costa",
                        "2023002",
                        "Sistemas de Informação"
                );

                System.out.println("Estado inicial dos livros:");
                System.out.println(l1.info());
                System.out.println(l2.info());
                System.out.println();

                System.out.println("João tenta emprestar o livro 1:");

                boolean sucesso = a1.matricularLivro(l1);

                System.out.println("Empréstimo bem sucedido? " + sucesso);
                System.out.println(a1.mostrarEmprestimos());
                System.out.println(l1.info());
                System.out.println();

                System.out.println("Maria tenta emprestar o mesmo livro 1:");

                boolean sucesso2 = a2.matricularLivro(l1);

                System.out.println("Empréstimo bem sucedido? " + sucesso2);
                System.out.println(a2.mostrarEmprestimos());
                System.out.println(l1.info());
                System.out.println();

                System.out.println("João devolve o livro 1:");

                boolean devolvido = a1.devolverLivro(l1);

                System.out.println("Devolução efetuada? " + devolvido);
                System.out.println(a1.mostrarEmprestimos());
                System.out.println(l1.info());
                System.out.println();

                System.out.println("Agora Maria tenta novamente:");

                boolean sucesso3 = a2.matricularLivro(l1);

                System.out.println("Empréstimo bem sucedido? " + sucesso3);
                System.out.println(a2.mostrarEmprestimos());
                System.out.println(l1.info());
            }
        }




    }
}