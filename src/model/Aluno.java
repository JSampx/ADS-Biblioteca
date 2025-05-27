package model;


import java.time.LocalDate;

public class Aluno {

    
    private int idAluno;
    private String nomeAluno;
    private String matricula;
    private LocalDate dataNascimento;

    
    public Aluno() {
    }

    public Aluno(int idAluno, String nomeAluno, String matricula, LocalDate dataNascimento) {
        this.idAluno = idAluno;
        this.nomeAluno = nomeAluno;
        this.matricula = matricula;
        this.dataNascimento = dataNascimento;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
