package tuliocota.com.br.primeiroapp.service.dto;

import java.util.List;

import tuliocota.com.br.primeiroapp.entidade.Aluno;

/**
 * Created by tulio on 18/05/18.
 */

public class AlunosResponse {
    private List<Aluno> alunos;

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }
}

