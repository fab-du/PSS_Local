
## Funktionale Anforderungen

Ziel des System ist die Dokumentaustausch zwischen Partei von unterschiedliche
Unternehmen zu gestalten, und der dabei relevant sicherheitmechanismus anzufertigen.
<br/>
Die folgenden funktionalen Anforderungen sollen dabei erfüllt wer-
den.

### Administratorrecht  __<i>ADMIN_ROLE</i>__  

* Der Administrator muss in der Lage sein, neue Benutzer im System hinzuzu-
  fügen und zu entfernen.

* Der Administrator darf nicht in der Lage sein Benutzer kritische Informationen
  zu modifizieren oder zu

### Benutzerrecht  __<i>USER_ROLE</i>__ 

* Der Benutzer kann eine Vertrauensbeziehung zu anderen Benutzern erstellen und sie wieder zerstören.
* Der Benutzer kann eine Gruppe erstellen und entfernen.
* Der Benutzer kann vertraute Benutzer in einer Gruppe hinzufügen und entfernen
* Der Benutzer kann den Zugriff auf seine Dateischlüssel an alle Mitglieder
  einer Gruppe freigeben und diese Freigabe auch wieder zurückziehen.

### Registrierung und Login

Bei der Registrierung und Login dürfen keine Password oder Passphrase durch
die Netz übertragen werden.  


### Data upload

unchiffrierte Datei dürfen nicht durch der Netz übertragen werden, da 
Datei auch als kritischen Daten gilt, müssen die vorab lokal chiffriert 
werden befor sie dann an RemoteServer geschickt werden.

### graphische Zusammenfassung von funktionale Anforderungen

Dabei ist noch anschaulich das __LocalServer__ und __RemoteServer__ zwei 
unterschiedliche Softwaresystem sind die getrennt voneinander laufen.
Noch bedeuntender ist es dass man der __RemoteServer__ nur duch der
__LocalServer__ ansprechen kann. 


![Funktionale Anforderungen](figures/plantuml/functionaleAnforderungen.png)


\newpage

