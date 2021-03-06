/*
 * Copyleft 2012 - Peer internet solutions 
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
package be.artoria.belfortapp.mixare.mgr.datasource;

import java.util.Locale;

import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.mixare.MixContext;
import be.artoria.belfortapp.mixare.data.DataSource;
import be.artoria.belfortapp.mixare.mgr.downloader.DownloadRequest;



class DataSourceMgrImpl implements DataSourceManager {

	private DataSource dataSource = new DataSource();

	private final MixContext ctx;

	public DataSourceMgrImpl(MixContext ctx) {
		this.ctx = ctx;
	}


	public void refreshDataSources() {
        DataManager.refresh();
	}

    @Override
    public void setAllDataSourcesforLauncher(DataSource source) {
        dataSource = source;

    }

    public void requestDataFromAllActiveDataSource(double lat, double lon,
			double alt, float radius) {
			requestData(dataSource, lat, lon, alt, radius, Locale.getDefault()
					.getLanguage());

	}

	private void requestData(DataSource datasource, double lat, double lon,
			double alt, float radius, String locale) {
		DownloadRequest request = new DownloadRequest(datasource);
		ctx.getDownloadManager().submitJob(request);
	}

}
