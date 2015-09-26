package de.vanitasvitae.enigmandroid.layout;

import android.widget.EditText;

import de.vanitasvitae.enigmandroid.MainActivity;
import de.vanitasvitae.enigmandroid.R;
import de.vanitasvitae.enigmandroid.enigma.Enigma;
import de.vanitasvitae.enigmandroid.enigma.EnigmaStateBundle;
import de.vanitasvitae.enigmandroid.enigma.inputPreparer.EditTextAdapter;
import de.vanitasvitae.enigmandroid.enigma.inputPreparer.InputPreparer;

/**
 * Abstract LayoutContainer for Enigma machines
 * This class contains the layout and controls the layout elements such as spinners and stuff
 * Copyright (C) 2015  Paul Schaub

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along
 with this program; if not, write to the Free Software Foundation, Inc.,
 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * @author vanitasvitae
 */
public abstract class LayoutContainer
{
    protected EditText inputView;
    protected EditText outputView;

    protected EditTextAdapter input;
    protected EditTextAdapter output;

    protected InputPreparer inputPreparer;
    protected MainActivity main;

    protected EnigmaStateBundle state;

    public abstract Enigma getEnigma();
    protected abstract void initializeLayout();
    public abstract void resetLayout();
    public abstract void setLayoutState(EnigmaStateBundle state);
    protected abstract void refreshState();
    public abstract void showRingSettingsDialog();

    public LayoutContainer(EnigmaStateBundle state)
    {
        main = (MainActivity) MainActivity.ActivitySingleton.getInstance().getActivity();
        this.inputView = (EditText) main.findViewById(R.id.input);
        this.outputView = (EditText) main.findViewById(R.id.output);
        input = EditTextAdapter.createEditTextAdapter(inputView, main.getPrefMessageFormatting());
        output = EditTextAdapter.createEditTextAdapter(outputView, main.getPrefMessageFormatting());
        initializeLayout();
        this.state = state;
    }
    public LayoutContainer()
    {
        main = (MainActivity) MainActivity.ActivitySingleton.getInstance().getActivity();
        this.inputView = (EditText) main.findViewById(R.id.input);
        this.outputView = (EditText) main.findViewById(R.id.output);
        input = EditTextAdapter.createEditTextAdapter(inputView, main.getPrefMessageFormatting());
        output = EditTextAdapter.createEditTextAdapter(outputView, main.getPrefMessageFormatting());
        initializeLayout();
    }

    public void doCrypto()
    {
        if(inputView.getText().length()!=0)
        {
            getEnigma().setState(getState());
            String message = inputView.getText().toString();
            message = inputPreparer.prepareString(message);
            input.setText(message);
            output.setText(getEnigma().encryptString(message));
            setLayoutState(getEnigma().getState());
        }
    }

    public EnigmaStateBundle getState()
    {
        refreshState();
        return state;
    }

    public void setState(EnigmaStateBundle state)
    {
        this.state = state;
    }

    public EditTextAdapter getInput()
    {
        return this.input;
    }

    public EditTextAdapter getOutput()
    {
        return this.output;
    }

    public static LayoutContainer createLayoutContainer(String enigmaType)
    {
            switch (enigmaType) {
                case "I":
                    return new LayoutContainer_I();
                case "M3":
                    return new LayoutContainer_M3();
                case "M4":
                    return new LayoutContainer_M4();
                case "D":
                    return new LayoutContainer_D();
                case "K":
                    return new LayoutContainer_K();
                case "KS":
                    return new LayoutContainer_K_Swiss();
                case "KSA":
                    return new LayoutContainer_K_Swiss_Airforce();
                case "T":
                    return new LayoutContainer_T();
                case "R":
                    return new LayoutContainer_R();
                case "G31":
                    return new LayoutContainer_G31();
                case "G312":
                    return new LayoutContainer_G312();
                case "G260":
                    return new LayoutContainer_G260();
                default:
                    return new LayoutContainer_I();
            }
    }

    public void setInputPreparer(InputPreparer inputPreparer)
    {
        this.inputPreparer = inputPreparer;
    }

    public void setEditTextAdapter(String type)
    {
        String in = input.getText();
        String out = output.getText();
        input = EditTextAdapter.createEditTextAdapter(inputView, type);
        input.setText(in);
        output = EditTextAdapter.createEditTextAdapter(outputView, type);
        output.setText(out);
    }
}

