/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.vanitasvitae.enigmandroid.enigma.inputPreparer;

import android.widget.EditText;

/**
 * Adapter-like connector between text fields and string-outputting-whatever blah
 * Use this to modify the way strings get displayed without modifying the string itself.
 */
public abstract class EditTextAdapter
{
    EditText editText;
    String content;

    EditTextAdapter(EditText editText)
    {
        this.editText = editText;
    }

    /**
     * Returns the unmodified text
     * @return content
     */
    public String getText()
    {
        if(editText.getText().length() != 0)
            return content;
        else return content = "";
    }

    public String getModifiedText()
    {
        return editText.getText().toString();
    }

    /**
     * Set the text to both the content and the editText without modifying it
     * @param text text
     */
    public void setRawText(String text)
    {
        this.content = text;
        this.editText.setText(text);
    }

    /**
     * This method needs to be overwritten by the programmer.
     * The coder has to make sure, content gets set to text and also that the editText element
     * gets updated with the modified text
     * @param text text
     */
    public abstract void setText(String text);

    public static EditTextAdapter createEditTextAdapter(EditText editText, String type)
    {
        switch (type)
        {
            case "4": return new EditTextAdapterGap(editText, 4);
            case "5": return new EditTextAdapterGap(editText, 5);
            case "6": return new EditTextAdapterGap(editText, 6);
			case "no":
			default: return new EditTextAdapterNoGap(editText);
        }
    }

    public static class EditTextAdapterNoGap extends  EditTextAdapter
    {
        public EditTextAdapterNoGap(EditText editText)
        {
            super(editText);
        }

        @Override
        public void setText(String text)
        {
            this.content = text;
            this.editText.setText(text);
        }
    }

    public static class EditTextAdapterGap extends EditTextAdapter
    {
        int blockSize;
        public EditTextAdapterGap(EditText editText, int blockSize)
        {
            super(editText);
            this.blockSize = blockSize;
        }

        @Override
        public void setText(String text)
        {
            this.content = text;
            String out = "";
            int i;
            for(i=0; i<text.length()/blockSize; i++)
            {
                out = out + text.substring(i*blockSize, (i+1)*blockSize);
                out = out + " ";
            }
            out = out + text.substring(i*blockSize);
            this.editText.setText(out);
        }
    }

}