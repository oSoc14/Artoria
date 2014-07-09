/*
 * Copyright (C) 2010- Peer internet solutions
 * 
 * This file is part of be.artoria.belfortapp.mixare.
 * 
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. 
 * 
 * You should have received a copy of the GNU General Public License along with 
 * this program. If not, see <http://www.gnu.org/licenses/>
 */

package be.artoria.belfortapp.mixare.data;

import android.graphics.Color;

/**
 * The DataSource class is able to create the URL where the information about a
 * place can be found.
 * 
 * @author hannes
 * @author Laurens De Graeve
 * 
 */
public class DataSource {

	private static final String name = "Artoria";

    public enum TYPE {
        WIKIPEDIA, BUZZ, TWITTER, OSM, MIXARE, ARTORIA
    };

    private static final TYPE type = TYPE.ARTORIA;


	public DataSource() {
	}

	public int getColor() {
		return Color.RED;
	}


	public TYPE getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return "ARTORIA DataSource ";
	}
}
