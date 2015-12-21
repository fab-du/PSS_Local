
## Nichtfunktionale Anforderungen

### Overall nichtfunktional Anforungen 

* Kein Einsatz von HTTPS 

* RemoteServer darf __keine__ Chiffrierung/Dechiffrierung durchführen

* LocalServer soll von ein USB-Stick getart werden, und soll auch von dort 
  aus im hintergrund laufen.

* Benutzerinteraktion erfolgt durch ein Browser sodass keine zusätzliche
  Software erforderlich ist.



### Wartbarkeit und Änderbarkeit

Die resultierende Software dieser Arbeit, soll in Zukunft gewartet, erweitert
und geändert werden. Neu Features sind schon festgelegt ( sollen aber in der 
jetzige Version nicht implementiert werden )

### Portierbarkeit und Plattformunabhängigkeit

Defakto ist der LocalServer portierbar, __LocalServer läuft auf USB-Stick__ .
Localserver soll auch plattformsunabhängig sein. 
Was RemoteServer angeht soll auch plattformunabhängig sein. Alle Einstellungen
des Remoteserver müssen sich durch externe Konfigurationsdateien durchführen lassen.


### Daten-und Serverintegrität

Der Benutzer soll in der Lage sein die Integrität von RemoteServer zu prüfen und
der auf der letzer abgespeicherte Daten.


