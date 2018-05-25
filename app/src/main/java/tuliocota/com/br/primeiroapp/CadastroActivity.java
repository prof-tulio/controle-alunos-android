package tuliocota.com.br.primeiroapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tuliocota.com.br.primeiroapp.dao.AlunoDao;
import tuliocota.com.br.primeiroapp.databinding.ActivityCadastroBinding;
import tuliocota.com.br.primeiroapp.entidade.Aluno;
import tuliocota.com.br.primeiroapp.service.AlunoService;

public class CadastroActivity extends AppCompatActivity {

    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCadastroBinding binding = DataBindingUtil.
                setContentView(this, R.layout.activity_cadastro);

        aluno = (Aluno) getIntent().getSerializableExtra("aluno");

        if (aluno == null) {
            aluno = new Aluno();
        }
        binding.setAluno(aluno);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_salvar:
                salvar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void salvar() {
        AlunoDao dao = new AlunoDao(this);
        dao.salvar(aluno);
        dao.close();

        Toast.makeText(this,
                String.format("Aluno %s cadastrado com sucesso",
                        aluno.getNome()), Toast.LENGTH_LONG)
                .show();
        finish();
    }

    public void salvarRest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AlunoService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AlunoService alunoService = retrofit.create(AlunoService.class);

        Call<Void> request = null;

        if (aluno.getId() == null){
            request = alunoService.salvarAluno(aluno);
        } else {
            request = alunoService.atualizarAluno(aluno.getId(), aluno);
        }
        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CadastroActivity.this);
                    builder.setMessage("Aluno salvo com sucesso")
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    //Alert que deu ruim
                }
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //Alert
                finish();
            }
        });

    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}
