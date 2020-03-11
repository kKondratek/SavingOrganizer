package com.kkondratek.savingapp.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.kkondratek.savingapp.R;

public class CurrencySettingDialog extends AppCompatDialogFragment {

    private Spinner currenceSpinner;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_currency_set, null);

        builder.setView(view)
                .setTitle(R.string.currency_setting_name)
        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String currency = (String) currenceSpinner.getSelectedItem();
                        MainActivity.putStringPref("currency", currency, getActivity());
                        System.out.println(MainActivity.getStringPref("currency", getActivity()));
                    }
                });

        currenceSpinner = view.findViewById(R.id.currency_settings);

        return builder.create();
    }
}
