package tuliocota.com.br.primeiroapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        txtNome.setText(aluno.getNome());
        txtTelefone.setText(aluno.getEndereco());

        return view;
    }
}
