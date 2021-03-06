/*
 * Copyright (C) 2012- Peer internet solutions & Finalist IT Group
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
package be.artoria.belfortapp.mixare.mgr.downloader;

import be.artoria.belfortapp.mixare.data.DataSource;

public class DownloadRequest {
	private DataSource source;

	public DownloadRequest(DataSource source) {
		if (source==null){
			throw new IllegalArgumentException("DataSource is NULL");
		}
		this.source = source;

	}

	public DataSource getSource() {
		return source;
	}

	public String toString(){
		return " Artoria download request";
	}

}
