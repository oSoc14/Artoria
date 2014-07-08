package be.artoria.belfortapp.mixare.lib.service;

interface IBootStrap {
	
	 int getZIndex();
	 
	 String getPluginName();
	 
	 String getActivityPackage();
	 
	 String getActivityName();
	 
	 int getActivityRequestCode();
}