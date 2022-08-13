package br.com.dio.desafio.dominio;

import java.util.*;

public class Dev {
    private String nome;
    private final Set<Conteudo> conteudosInscritos = new LinkedHashSet<>();
    private final Set<Conteudo> conteudosConcluidos = new LinkedHashSet<>();

    private static final String DEV_NAO_ESTA_MATRICULADO = "não está matriculado em nenhum conteúdo.";
    private static final String DEV_NAO_CONCLUIU_CONTEUDO = "não concluiu nenhum conteúdo ainda.";

    public void inscreverBootcamp(Bootcamp bootcamp){
        this.conteudosInscritos.addAll(bootcamp.getConteudos());
        bootcamp.addDevInscrito(this);
    }

    public void progredir() {
        Optional<Conteudo> conteudo = this.conteudosInscritos.stream().findFirst();

        if (conteudo.isPresent()) {
            this.conteudosConcluidos.add(conteudo.get());
            this.conteudosInscritos.remove(conteudo.get());
        } else {
            System.err.println(this.getNome() + " " + DEV_NAO_ESTA_MATRICULADO);
        }
    }

    public double calcularTotalXp() {
        return this.conteudosConcluidos
                .stream()
                .mapToDouble(Conteudo::calcularXp)
                .sum();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Conteudo> getConteudosInscritos() {
        return Collections.unmodifiableSet(conteudosInscritos);
    }

    public String getConteudosInscritosFormatado() {
        String s = null;
        if (getConteudosInscritos().isEmpty()) {
            s = this.getNome() + " " + DEV_NAO_ESTA_MATRICULADO;
        } else {
            for (Conteudo c:getConteudosInscritos()) {
                s = "\n" + c.toString();
            }
        }
        return s;
    }

    public Set<Conteudo> getConteudosConcluidos() {
        return Collections.unmodifiableSet(conteudosConcluidos);
    }

    public String getConteudosConcluidosFormatado() {
        String s = null;
        if (getConteudosConcluidos().isEmpty()) {
            s = this.getNome() + " " + DEV_NAO_CONCLUIU_CONTEUDO;
        } else {
            for (Conteudo c:getConteudosConcluidos()) {
                s = "\n" + c.toString();
            }
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dev dev = (Dev) o;
        return Objects.equals(nome, dev.nome) && Objects.equals(conteudosInscritos, dev.conteudosInscritos) && Objects.equals(conteudosConcluidos, dev.conteudosConcluidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, conteudosInscritos, conteudosConcluidos);
    }
}
