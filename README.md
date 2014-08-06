map-package-feature-editor-java
===============================

A sample Java application that uses ArcGIS Runtime to edit feature layers in a map package (MPK).

I originally wrote this sample to see if a non-geodatabase database table with a spatial column could be edited using ArcGIS Runtime's toolkit. I created a map package containing a query layer pointing to such a table. It worked. I suspect that database client software must be installed in order for this to work with the RDBMS of your choice (SQL Server in this case).

## System requirements

- [ArcGIS Runtime SDK for Java](https://developers.arcgis.com/java/) 10.2.3 or higher
- Java Development Kit (JDK) 6 or higher

## License

Copyright 2014 Esri. Licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE) for a complete copy of this license.
