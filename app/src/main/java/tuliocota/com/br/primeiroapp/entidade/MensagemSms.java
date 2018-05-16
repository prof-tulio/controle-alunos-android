package tuliocota.com.br.primeiroapp.entidade;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tulio on 11/05/18.
 */

public class MensagemSms {
    private Aluno aluno;
    private Date data;
    private String mensagem;

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(data) + " - " + aluno.getNome() + " - " + mensagem;
    }
}
