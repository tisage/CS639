package com.converter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    EditText dollars;
    EditText francs;
    RadioButton dtof;// dollar to francs option
    Button convert;

    public static final double exchangeRate = 439.36;
    // 1 dollar = 439.36 Comorian francs

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View myView = inflater.inflate(R.layout.fragment_main, container, false);

        dollars = (EditText) myView.findViewById(R.id.dollars);
        francs = (EditText) myView.findViewById(R.id.francs);
        dtof = (RadioButton) myView.findViewById(R.id.ttof);
        dtof.setChecked(true);
        convert = (Button) myView.findViewById(R.id.convert);
        convert.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View v) {

        if (dtof.isChecked()) {
            convertDollarsToEuros();
        }
        // convert back in next version
    }

    protected void convertDollarsToEuros() {
        double val = Double.parseDouble(dollars.getText().toString());
        francs.setText(Double.toString(val * exchangeRate));

    }
}
