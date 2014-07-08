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
import java.util.concurrent.ConcurrentLinkedQueue;

import be.artoria.belfortapp.mixare.MixContext;
import be.artoria.belfortapp.mixare.data.DataSource;
import be.artoria.belfortapp.mixare.data.DataSourceStorage;
import be.artoria.belfortapp.mixare.mgr.downloader.DownloadRequest;

class DataSourceMgrImpl implements DataSourceManager {

	private DataSource dataSource;

	private final MixContext ctx;

	public DataSourceMgrImpl(MixContext ctx) {
		this.ctx = ctx;
	}


	public void refreshDataSources() {

		DataSourceStorage.getInstance(ctx).fillData();

			String fields[] = DataSourceStorage.getInstance().getFields();
			dataSource = new DataSource(fields[0], fields[1],
					fields[2], fields[3], fields[4]);
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
		DownloadRequest request = new DownloadRequest(datasource,
				datasource.createRequestParams(lat, lon, alt, radius, locale));
		ctx.getDownloadManager().submitJob(request);

	}

}
