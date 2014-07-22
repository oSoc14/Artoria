Artoria
=======

Augmented reality app for the Ghent belfry for #oSoc14.


Mixare
=======

The augmented reality engine used is mixare, which can be found at http://www.mixare.org/download/

Open Street Map
===============
This app uses Open Street Map. We used OSMDroid ( https://github.com/osmdroid/osmdroid ) and the OSM bonus pack ( https://code.google.com/p/osmbonuspack/ ) to implement this feature.


How to run
========
Import this project in Android studio, click run and you're good to go.

Don't forget to request an API key from mapquest ( http://developer.mapquest.com/ ) and insert it in a keys.xml file in your res/values folder.

This file should look like this:

&lt;?xml version="1.0" encoding="utf-8"?&gt;
&lt;resources&gt;
    &lt;string name="map_quest_api_key"&gt; your_api_key&lt;/string&gt;
&lt;/resources&gt;


Team
=======
  Laurens De Graeve <br />
  Dieter Beelaert <br />
  
With supervision by <br />

  Jan Vansteenlandt <br />
  Michael Vanderpoorten <br />

