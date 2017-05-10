package com.mfratane.checkersinterface.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

/**
 * Agrupa funcionalidades de todos os fragments presentes na aplicação.
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Mostra um Toast com duração curta.
     * @param text Texto a ser exibido.
     */
    protected void toastShort(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Mostra um Toast com duração longa.
     * @param text Texto a ser exibido.
     */
    protected void toastLong(String text){
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    protected void warnLog(String text){
        Log.i("Checkers", text);
    }
}
