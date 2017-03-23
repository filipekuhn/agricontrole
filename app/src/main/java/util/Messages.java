package util;

import android.content.DialogInterface;
import android.app.Activity;
import android.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by filipe on 23/03/17.
 */

public class Messages {

    public static void Message(Activity activity, String message){
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public static void addOkMessage(Activity activity, String title, String message, int icon){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setNeutralButton("OK", null);
        alert.setIcon(icon);
        alert.show();
    }

    public static void MessageConfirm(Activity activity, String title, String message, int icon, DialogInterface.OnClickListener listener){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("Sim", listener);
        alert.setNegativeButton("Não", null);
        alert.setIcon(icon);
        alert.show();
    }

    public static AlertDialog createAlertDialog(Activity activity){
        final CharSequence[] items = {
                "Editar",
                "Excluir"
        };

        AlertDialog.Builder alert= new AlertDialog.Builder(activity);
        alert.setTitle("Opções");
        alert.setItems(items, (DialogInterface.OnClickListener) activity);
        return alert.create();
    }

    public static AlertDialog createDialogConfirm(Activity activity){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setMessage("Deseja realmente excluir?");
        alert.setPositiveButton("Sim", (DialogInterface.OnClickListener) activity);
        alert.setNegativeButton("Não", (DialogInterface.OnClickListener) activity);

        return alert.create();
    }
}
