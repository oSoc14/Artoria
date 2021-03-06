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
package be.artoria.belfortapp.mixare.mgr.downloader;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import be.artoria.belfortapp.app.DataManager;
import be.artoria.belfortapp.mixare.MixContext;
import be.artoria.belfortapp.mixare.MixView;
import be.artoria.belfortapp.mixare.data.convert.DataConvertor;
import be.artoria.belfortapp.mixare.lib.marker.Marker;


import android.util.Log;

class DownloadMgrImpl implements Runnable, DownloadManager {

	private DownloadManagerState state = DownloadManagerState.Confused;
	private DownloadResult result;
	private Executor executor = Executors.newSingleThreadExecutor();
	

	public DownloadMgrImpl() {
		state=DownloadManagerState.OffLine;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see DownloadManager#run()
	 */
	public void run() {
        state=DownloadManagerState.Downloading;
        result = processRequest();
        state=DownloadManagerState.OffLine;
	}


	private DownloadResult processRequest() {
		final DownloadResult result = new DownloadResult();

        // load Marker data
        List<Marker> markers = DataConvertor.getInstance().load(DataManager.getAll());
        result.setAccomplish(markers);

		return result;
	}

    @Override
    public void resetActivity() {

    }
    private static final String jobId = "ArtoriaJob";
    /*
         * (non-Javadoc)
         *
         * @see
         * DownloadManager#submitJob(org.be.artoria.belfortapp.mixare.mgr.downloader
         * .DownloadRequest)
         */
	public String submitJob(DownloadRequest job) {
        // jobID
		return jobId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * DownloadManager#getReqResult(java.lang.String)
	 */
	public DownloadResult getReqResult() {
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see DownloadManager#getNextResult()
	 */
	public synchronized DownloadResult getNextResult() {
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see DownloadManager#getResultSize()
	 */
	public int getResultSize(){
		return result == null? 0 :1 ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see DownloadManager#isDone()
	 */
	public Boolean isDone() {
		return result != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see DownloadManager#goOnline()
	 */
	public void switchOn() {
		if (DownloadManagerState.OffLine.equals(getState())){
		    executor.execute(this);
		}else{
			Log.i(MixView.TAG, "DownloadManager already started");
		}
	}

    @Override
    public void switchOff() {
        // Nothing to be done
    }

    @Override
	public DownloadManagerState getState() {
		return state;
	}

}
