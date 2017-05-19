package project.major.itemsniper.Reusables;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Kurt on 4/21/2017.
 */
public class PopUp extends AppCompatDialogFragment{

    private Context context;
    private View v;
    private int layout_to_inflate;
    private Manipulator manipulator;
    private boolean cancelable = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(layout_to_inflate,null);
        builder.setView(v);
        if(!cancelable){
            builder.setCancelable(false);
        }

        manipulateInflatedUI();
        return builder.create();
    }

    public static PopUp createInstance(int layout_to_inflate,Manipulator manipulator,boolean cancelable){

        PopUp p = new PopUp();
        p.layout_to_inflate = layout_to_inflate;
        p.manipulator = manipulator;
        p.cancelable = cancelable;
        if(!cancelable){
            p.setCancelable(false);
        }
        return p;
    }

    private class ManipulatorNotSetException extends Exception{
        ManipulatorNotSetException(String messae){
            super(messae);
        }
    }

    private void manipulateInflatedUI(){
        this.manipulator.manipulate(this.v);
    }

    public static interface Manipulator{
        void manipulate(View v);
    }
}
