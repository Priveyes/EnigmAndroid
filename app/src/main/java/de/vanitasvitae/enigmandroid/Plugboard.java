package de.vanitasvitae.enigmandroid;

import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vanitas on 12.08.15.
 */
public class Plugboard
{
    Integer[] plugs;

    public Plugboard()
    {
        plugs = new Integer[26];
    }

    public Plugboard(int[][] configuration)
    {
        setConfiguration(configuration);
    }

    public Plugboard(String configuration)
    {
        setConfiguration(parseConfigurationString(configuration));
    }

    /**
     * Configure the plugboard according to the given array.
     *
     * @param configuration
     */
    public void setConfiguration(int[][] configuration)
    {
        if(configuration != null) {
            boolean validConfiguration = true;
            plugs = new Integer[26];
            for (int[] p : configuration) {
                if (!setPlugs(p[0], p[1])) {
                    validConfiguration = false;
                    break;
                }
            }
            if (!validConfiguration) plugs = new Integer[26];
        }
        else plugs = new Integer[26];
    }

    /**
     * Parse configuration from input string
     * input must have the following form: "" or "XY" or "XY,VW" or "XY,...,AB"
     * A character must not be inside the input multiple times. Exception is ','
     * This is not catched here!
     * @param input String that codes the configuration
     * @return two dimensional array of plugged symbols
     */
    public static int[][] parseConfigurationString(String input)
    {
        Activity activity = MainActivity.ActivitySingleton.getInstance().getActivity();
        //If length != 0,2,5,8... ( ,XY, XY-VW, ...)
        if(((input.length()+1)%3)!=0&&input.length()!=0)
        {
            Toast.makeText(activity.getApplicationContext(), R.string.error_parsing_plugs,
                    Toast.LENGTH_LONG).show();
            return null;
        }
        else {
            input = input.toUpperCase();
            ArrayList<int[]> plugList = new ArrayList<int[]>();
            int[] plug = new int[2];
            for (int i = 0; i < input.length(); i++) {
                int c = input.charAt(i) - 65;
                if (c < 0 || c > 25) {
                    if (i % 3 != 2) {
                        Toast.makeText(activity.getApplicationContext(), R.string.error_parsing_plugs,
                                Toast.LENGTH_LONG).show();
                        return null;
                    }
                } else {
                    if (i % 3 == 0) {
                        plug = new int[2];
                        plug[0] = c;
                    }
                    if (i % 3 == 1) {
                        plug[1] = c;
                        plugList.add(plug);
                    }

                }
            }
            int[][] parsedConfiguration = new int[plugList.size()][2];
            for(int i=0; i<plugList.size(); i++)
            {
                parsedConfiguration[i] = plugList.get(i);
            }
            return parsedConfiguration;
        }

    }

    /**
     * Set the given plugs (connect a and b)
     * Return false, if something goes wrong (plugs already used somehow)
     * @param a first plug
     * @param b second plug
     * @return success
     */
    public boolean setPlugs(int a, int b)
    {
        Activity activity = MainActivity.ActivitySingleton.getInstance().getActivity();
        if(a==b)
        {
            Toast.makeText(activity.getApplication().getApplicationContext(),
                    activity.getResources().getText(R.string.error_unable_to_plug_a_b).toString()
                            +" "+(char)(a+65)+","+(char) (b+65),Toast.LENGTH_LONG).show();
            return false;
        }
        if(plugs[a] != null || plugs[b] != null)
        {
            Toast.makeText(activity.getApplication().getApplicationContext(),
                    activity.getResources().getText(R.string.error_plug_already_in_use).toString()
                            +" "+(char) (a+65)+","+(char)(b +65), Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            plugs[a] = b;
            plugs[b] = a;
            return true;
        }
    }

    /**
     * Encrypt input via plugboard connections
     * @param input input symbol to encrypt
     * @return encrypted symbol
     */
    public int encrypt(int input)
    {
        if(plugs[input]==null) return input;
        else return plugs[input];
    }
}
