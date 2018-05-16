package tuliocota.com.br.primeiroapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Date;

import tuliocota.com.br.primeiroapp.R;
import tuliocota.com.br.primeiroapp.dao.AlunoDao;
import tuliocota.com.br.primeiroapp.entidade.Aluno;
import tuliocota.com.br.primeiroapp.entidade.MensagemSms;

/**
 * Created by tulio on 27/04/2018.
 */

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];

        String formato = (String) intent.getSerializableExtra("format");
        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);
        String telefone = sms.getDisplayOriginatingAddress();

        AlunoDao dao = new AlunoDao(context);
        Aluno aluno = dao.buscarPorTelefone(telefone);
        if (aluno != null){

            MensagemSms msg = new MensagemSms();
            msg.setAluno(aluno);
            msg.setData(new Date());
            msg.setMensagem(sms.getMessageBody());
            dao.salvarSms(msg);

            Toast.makeText(context,
                    String.format("Chegou um SMS do aluno %s", aluno.getNome()),
                    Toast.LENGTH_LONG).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.message);
            mp.start();
        }

        dao.close();
    }
}
