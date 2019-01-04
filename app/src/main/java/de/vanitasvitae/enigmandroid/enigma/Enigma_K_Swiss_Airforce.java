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
package de.vanitasvitae.enigmandroid.enigma;

import android.util.Log;

import java.math.BigInteger;

import de.vanitasvitae.enigmandroid.MainActivity;
import de.vanitasvitae.enigmandroid.enigma.parts.EntryWheel;
import de.vanitasvitae.enigmandroid.enigma.parts.Reflector;
import de.vanitasvitae.enigmandroid.enigma.parts.Rotor;

/**
 * Implementation of the Enigma machine of name K (Switzerland, Airforce)
 * Copyright (C) 2015  Paul Schaub
 */
public class Enigma_K_Swiss_Airforce extends Enigma_K
{
	public Enigma_K_Swiss_Airforce()
	{
		super();
		machineType = "KSA";
		Log.d(MainActivity.APP_ID, "Created Enigma KSA");
	}

	@Override
	protected void establishAvailableParts()
	{
		addAvailableEntryWheel(new EntryWheel.EntryWheel_QWERTZ());
		addAvailableRotor(new Rotor.Rotor_K_Swiss_Airforce_I(0,0));
		addAvailableRotor(new Rotor.Rotor_K_Swiss_Airforce_II(0,0));
		addAvailableRotor(new Rotor.Rotor_K_Swiss_Airforce_III(0,0));
		addAvailableReflector(new Reflector.Reflector_K_G260());
	}

	@Override
	public BigInteger getEncodedState(int protocol_version)
	{
		BigInteger s = BigInteger.valueOf(reflector.getRingSetting());
		s = addDigit(s, reflector.getRotation(), 26);
		s = addDigit(s, rotor3.getRingSetting(),26);
		s = addDigit(s, rotor3.getRotation(), 26);
		s = addDigit(s, rotor2.getRingSetting(),26);
		s = addDigit(s, rotor2.getRotation(), 26);
		s = addDigit(s, rotor1.getRingSetting(), 26);
		s = addDigit(s, rotor1.getRotation(), 26);

		s = addDigit(s, rotor3.getIndex(), availableRotors.size());
		s = addDigit(s, rotor2.getIndex(), availableRotors.size());
		s = addDigit(s, rotor1.getIndex(), availableRotors.size());

		s = addDigit(s, 9, 20); //Machine #9
		s = addDigit(s, protocol_version, MainActivity.max_protocol_version);

		return s;
	}
}
