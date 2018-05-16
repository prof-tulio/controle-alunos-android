package tuliocota.com.br.primeiroapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

import tuliocota.com.br.primeiroapp.R;
import tuliocota.com.br.primeiroapp.entidade.Aluno;

/**
 * Created by tulio on 20/04/2018.
 */

public class AlunosAdapter extends BaseAdapter {

    private List<Aluno> alunos;
    private Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Aluno aluno = alunos.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        view = inflater.inflate(R.layout.list_item, null);

        TextView txtNome = view.findViewById(R.id.txtNome);
        TextView txtTelefone = view.findViewById(R.id.txtTelefone);
        ImageView imageView = view.findViewById(R.id.imageView);

        txtNome.setText(aluno.getNome());
        txtTelefone.setText(aluno.getEndereco());

        File imgFile = getArquivoImagem(aluno.getId().toString());
        Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        imageView.setImageBitmap(bitmap);

        return view;
    }

    private File getArquivoImagem(String id){
        File diretorioStorage = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(diretorioStorage, "foto_" + id + ".jpg");
        return imageFile;
    }
}
