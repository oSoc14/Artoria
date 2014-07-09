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

import be.artoria.belfortapp.R;
import be.artoria.belfortapp.mixare.data.convert.DataConvertor;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * The DataSource class is able to create the URL where the information about a
 * place can be found.
 * 
 * @author hannes
 * @author Laurens De Graeve
 * 
 */
public class DataSource {

	private String name;
	private String url;

    public enum TYPE {
        WIKIPEDIA, BUZZ, TWITTER, OSM, MIXARE, ARTORIA
    };
       	public enum DISPLAY {
		CIRCLE_MARKER, NAVIGATION_MARKER, IMAGE_MARKER
	};

	private boolean enabled;
	private DISPLAY display = DISPLAY.CIRCLE_MARKER;
    private TYPE type = TYPE.ARTORIA;


	public DataSource() {

	}

	public DataSource(String name, String url, TYPE type, DISPLAY display,
			boolean enabled) {
		this.name = name;
		this.url = url;
		this.type = type;
		this.display = display;
		this.enabled = enabled;
		Log.d("org/mixare", "New Datasource!" + name + " " + url + " " + type + " "
				+ display + " " + enabled);
	}

	public DataSource(String name, String url, int typeInt, int displayInt,
			boolean enabled) {
		TYPE typeEnum = TYPE.values()[typeInt];
		DISPLAY displayEnum = DISPLAY.values()[displayInt];
		this.name = name;
		this.url = url;
		this.type = typeEnum;
		this.display = displayEnum;
		this.enabled = enabled;
	}

	public DataSource(String name, String url, String typeString,
			String displayString, String enabledString) {
		TYPE typeEnum = TYPE.values()[Integer.parseInt(typeString)];
		DISPLAY displayEnum = DISPLAY.values()[Integer.parseInt(displayString)];
		Boolean enabledBool = Boolean.parseBoolean(enabledString);
		this.name = name;
		this.url = url;
		this.type = typeEnum;
		this.display = displayEnum;
		this.enabled = enabledBool;
	}

	public String createRequestParams(double lat, double lon, double alt,
			float radius, String locale) {
		String ret = "";
		if (!ret.startsWith("file://")) {
			switch (this.type) {

			case WIKIPEDIA:
				float geoNamesRadius = radius > 20 ? 20 : radius; // Free
																	// service
																	// limited
																	// to 20km
				ret += "?lat=" + lat + "&lng=" + lon + "&radius="
						+ geoNamesRadius + "&maxRows=50" + "&lang=" + locale
						+ "&username=be.artoria.belfortapp.mixare";
				break;

			case BUZZ:
				ret += "&lat=" + lat + "&lon=" + lon + "&radius=" + radius
						* 1000;
				break;

			case TWITTER:
				ret += "?geocode=" + lat + "%2C" + lon + "%2C"
						+ Math.max(radius, 1.0) + "km";
				break;

			case MIXARE:
				ret += "?latitude=" + Double.toString(lat) + "&longitude="
						+ Double.toString(lon) + "&altitude="
						+ Double.toString(alt) + "&radius="
						+ Double.toString(radius);
				break;

			case ARTORIA:
				ret += "&lat=" + Double.toString(lat) + "&lng="
						+ Double.toString(lon);
				break;

			case OSM:
				ret += DataConvertor.getOSMBoundingBox(lat, lon, radius);
				break;
			}

		}

		return ret;
	}

	public int getColor() {
		int ret;
		switch (this.type) {
		case BUZZ:
			ret = Color.rgb(4, 228, 20);
			break;
		case TWITTER:
			ret = Color.rgb(50, 204, 255);
			break;
		case WIKIPEDIA:
			ret = Color.RED;
			break;
		case ARTORIA:
			ret = Color.RED;
			break;
		default:
			ret = Color.WHITE;
			break;
		}
		return ret;
	}

	public int getDataSourceIcon() {
		int ret;
		switch (this.type) {
		case BUZZ:
			ret = R.drawable.buzz;
			break;
		case TWITTER:
			ret = R.drawable.twitter;
			break;
		case OSM:
			ret = R.drawable.osm;
			break;
		case WIKIPEDIA:
			ret = R.drawable.wikipedia;
			break;
		case ARTORIA:
			ret = R.drawable.arena;
			break;
		default:
			ret = R.drawable.ic_launcher;
			break;
		}
		return ret;
	}

	public int getDisplayId() {
		return this.display.ordinal();
	}

	public int getTypeId() {
		return this.type.ordinal();
	}

	public DISPLAY getDisplay() {
		return this.display;
	}

	public TYPE getType() {
		return this.type;
	}

	public boolean getEnabled() {
		return this.enabled;
	}

	public String getName() {
		return this.name;
	}

	public String getUrl() {
		return this.url;
	}

	public String serialize() {
		return this.getName() + "|" + this.getUrl() + "|" + this.getTypeId()
				+ "|" + this.getDisplayId() + "|" + this.getEnabled();
	}

	public void setEnabled(boolean isChecked) {
		this.enabled = isChecked;
	}

	@Override
	public String toString() {
		return "DataSource [name=" + name + ", url=" + url + ", enabled="
				+ enabled + ", type=" + type + ", display=" + display + "]";
	}

	/**
	 * Check the minimum required data
	 * 
	 * @return boolean
	 */
	public boolean isWellFormed() {
		boolean out = false;
		if (isUrlWellFormed() || getName() != null || !getName().isEmpty()) {
			out = true;
		}
		return out;
	}

	public boolean isUrlWellFormed() {
		return getUrl() != null || !getUrl().isEmpty()
				|| "http://".equalsIgnoreCase(getUrl());
	}

}
