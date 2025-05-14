package model;


public class Aluno {

    
    private int idAluno;
    private String nomeAluno;
    private String matricula;
    private String dataNascimento;

    
    public Aluno() {
    }

    public Aluno(int idAluno, String nomeAluno, String matricula, String dataNascimento) {
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

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
