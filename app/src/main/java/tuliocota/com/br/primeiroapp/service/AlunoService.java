package tuliocota.com.br.primeiroapp.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tuliocota.com.br.primeiroapp.entidade.Aluno;
import tuliocota.com.br.primeiroapp.service.dto.AlunosResponse;

/**
 * Created by tulio on 18/05/18.
 */

public interface AlunoService {
    public static final String URL_BASE = "http://demo5896037.mockable.io/";

    @GET("alunos")
    Call<AlunosResponse> listarAlunos();

    @POST("alunos")
    Call<Void> salvarAluno(@Body Aluno aluno);

    @PATCH("alunos/{id}")
    Call<Void> atualizarAluno(@Path("id") Long id, @Body Aluno aluno);

    @DELETE("alunos/{id}")
    Call<Void> excluirAluno(@Path("id") Long id);
}
