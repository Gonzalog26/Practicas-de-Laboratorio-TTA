package eus.ehu.tta.appbasica.negocio;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import eus.ehu.tta.appbasica.R;

/**
 * Created by tta on 15/01/18.
 */

public abstract class ProgressTask<T> extends AsyncTask<Void,Void,T> {

    protected final Context context;
    private final ProgressDialog dialog;
    private Exception e;

    public ProgressTask(Context context){
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.conectando));
    }

    @Override
    protected T doInBackground(Void... params) {
        T result = null;
        try{
            result = work();
        } catch (Exception e){
            this.e = e;
        }
        return result;
    }

    @Override
    protected void onPreExecute() {
        dialog.show();
    }

    @Override
    protected void onPostExecute(T result) {
        if( dialog.isShowing()){
            dialog.dismiss();
        }

        if(e != null){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            onFinish(result);
        }
    }

    protected abstract T work() throws Exception;
    protected abstract void onFinish(T result);
}
