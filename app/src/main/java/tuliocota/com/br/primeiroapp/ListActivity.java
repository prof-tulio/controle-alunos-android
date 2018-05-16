package tuliocota.com.br.primeiroapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.List;

import tuliocota.com.br.primeiroapp.adapter.AlunosAdapter;
import tuliocota.com.br.primeiroapp.dao.AlunoDao;
import tuliocota.com.br.primeiroapp.entidade.Aluno;

public class ListActivity extends AppCompatActivity {

    private List<Aluno> alunos;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = findViewById(R.id.listAlunos);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = alunos.get(position);

                Intent intentAbrirCadastro = new Intent(ListActivity.this, CadastroActivity.class);
                intentAbrirCadastro.putExtra("aluno", aluno);
                startActivity(intentAbrirCadastro);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listagem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sair:
                SharedPreferences preferences =
                        PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                finish();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_sms:
                Intent intentListarSms = new Intent(this, ListSmsActivity.class);
                startActivity(intentListarSms);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    private void carregarLista() {
        AlunoDao dao = new AlunoDao(this);
        alunos = dao.listar();
        dao.close();

//        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(
//                this, android.R.layout.simple_list_item_1, alunos);
        AlunosAdapter adapter = new AlunosAdapter(this, alunos);
        listView.setAdapter(adapter);
    }

    private Aluno aluno;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        if (v.equals(listView)) {
            AdapterView.AdapterContextMenuInfo menuInfoListView =
                    (AdapterView.AdapterContextMenuInfo) menuInfo;
            int posicao = menuInfoListView.position;
            aluno = alunos.get(posicao);

            MenuItem menuExcluir = menu.add("Excluir");
            menuExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    AlunoDao dao = new AlunoDao(ListActivity.this);
                    dao.excluir(aluno);
                    dao.close();
                    carregarLista();
                    return false;
                }
            });

            MenuItem menuVisitarSite = menu.add("Visitar site");
            Intent intentSite = new Intent(Intent.ACTION_VIEW);
            intentSite.setData(Uri.parse(aluno.getSite()));
            menuVisitarSite.setIntent(intentSite);

            MenuItem menuEnviarSms = menu.add("Enviar SMS");
            Intent intentSms = new Intent(Intent.ACTION_VIEW);
            intentSms.setData(Uri.parse("sms:" + aluno.getTelefone()));
            menuEnviarSms.setIntent(intentSms);

            MenuItem menuMapa = menu.add("Visualizar endereço");
            Intent intentMapa = new Intent(Intent.ACTION_VIEW);
            intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
            menuMapa.setIntent(intentMapa);

            MenuItem menuLigar = menu.add("Ligar");
            menuLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (ActivityCompat.checkSelfPermission(
                            ListActivity.this, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                ListActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                123
                        );
                    } else {
                        fazerLigacao();
                    }
                    return false;
                }
            });

            MenuItem menuTirarFoto = menu.add("Tirar foto");
            menuTirarFoto.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File file = getArquivoImagem();
                    Uri fotoUri = FileProvider.getUriForFile(ListActivity.this,
                            "tuliocota.com.br.fileprovider", file);
                    intentFoto.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);

                    startActivity(intentFoto);

                    return false;
                }
            });
        }
    }

    private File getArquivoImagem() {
        File diretorioStorage = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(diretorioStorage, "foto_" + aluno.getId() + ".jpg");
        return imageFile;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 123:
                if (permissions[0].equals(Manifest.permission.CALL_PHONE) &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fazerLigacao();
                }
                break;
        }
    }

    private void fazerLigacao() {
        Intent intentLigar = new Intent(Intent.ACTION_CALL);
        intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
        startActivity(intentLigar);
    }

    public void abrirCadastro(View v) {
        Intent intentAbrirTelaCadastro =
                new Intent(this,
                        CadastroActivity.class);
        startActivity(intentAbrirTelaCadastro);
    }
}
