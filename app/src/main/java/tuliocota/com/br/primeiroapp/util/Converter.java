package tuliocota.com.br.primeiroapp.util;

import android.databinding.InverseMethod;

/**
 * Created by tulio on 09/03/2018.
 */

public class Converter {
    @InverseMethod("toDouble")
    public static String toString(Double valor){
        return valor == null ? "" : String.valueOf(valor);
    }
    @InverseMethod("toInt")
    public static String toString(Integer valor){
        return valor == null ? "" : String.valueOf(valor);
    }
    public static Double toDouble(String valor){
        return valor == null? 0 : Double.parseDouble(valor);
    }
    public static Integer toInt(String valor){
        return valor == null? 0 : Integer.parseInt(valor);
    }
}
