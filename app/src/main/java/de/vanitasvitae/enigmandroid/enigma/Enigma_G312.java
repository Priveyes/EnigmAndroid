package de.vanitasvitae.enigmandroid.enigma;

import java.sql.Ref;

import de.vanitasvitae.enigmandroid.enigma.rotors.Reflector;
import de.vanitasvitae.enigmandroid.enigma.rotors.Rotor;

/**
 * Implementation of the Enigma machine of type G31 (Abwehr)
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
public class Enigma_G312 extends Enigma_G31
{
    public Enigma_G312()
    {
        super();
        machineType = "G312";
    }
    @Override
    public void initialize()
    {
        this.entryWheel = Rotor.createRotor(1, 0, 0);
        this.rotor1 = Rotor.createRotor(50, 0, 0);
        this.rotor2 = Rotor.createRotor(51, 0, 0);
        this.rotor3 = Rotor.createRotor(52, 0, 0);
        this.reflector = Reflector.createReflector(50);
    }
}
