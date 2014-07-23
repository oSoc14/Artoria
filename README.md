Artoria
=======

Augmented reality app for the Ghent belfry for #oSoc14.


Mixare
=======

The augmented reality engine used is mixare, which can be found at http://www.mixare.org/download/
The part used is the MixView, all other functionality was stripped.


Open Street Map
===============
This app uses Open Street Map. We used OSMDroid ( https://github.com/osmdroid/osmdroid ) and the OSM bonus pack ( https://code.google.com/p/osmbonuspack/ ) to implement this feature.


How to run
========
Import this project in Android studio, click run and you're good to go.

Don't forget to request an API key from mapquest ( http://developer.mapquest.com/ ) and insert it in res/values/keys.xml

How the application is build
========
Data is gotten from https://github.com/oSoc14/ArtoriaData, more information about the data format, etc can be found there.

The code is pretty standard for an android app and self documenting. We tried to follow best practices whenever we were aware of any.

Team
=======
  Laurens De Graeve <br />
  Dieter Beelaert <br />
  
With supervision by <br />

  Jan Vansteenlandt <br />
  Michael Vanderpoorten <br />
  
  
Shout-outs 
========
It wouldn't have been possible to build this app without these awesome libraries

The best drag-sort-listview
https://github.com/bauerca/drag-sort-listview

Gson; painless json
https://code.google.com/p/google-gson/

Picasso; Awesome image loader
https://github.com/square/picasso

