
## Ueberblick

### Email 

eine im ersten Blick triviale Lösung was Austausch von kristischen Dokumenten
angeht besteht darin diese Dokument zu verschlüsseln und die resultierende
verschlüsselte Dokument per Email an der Kommunikationspartner zu senden.
<br/> 
Diese Lösung ist solange ertragbar wenn der Benutzer sich mit Cryptographie
bzw Cryptographiesoftware auskennt.
Begrenzungen an diese Technik sind unerheblich und unheimlich viele : 

* Diese Loesung setzt voraus dass der Kommunikationspartner sich auch mit
  der Kryptographie bzw. Kryptographiesoftware auskennt.
* mehr problemetik, setzt sich auch voraus dass der Kommunikationpartner
  neben der Kryptographie know-how, den passenden Software, den passenden kryptographische
  Algorithmus und der Verschlüsselungschlüssel.
* Schlüsselaustauschsproblematik.
* Infrakstrukturproblemetik.  


[FTPServer]: "FTP-Server"

### FTP-Server 

Das Problematik bei FTP Server ist dass eine Dritte von aussen aus auf den
auf der FTP-Server gespeicherte Datein nicht zugreifen kann.
Zusätzlich muss der Benutzer der Verantwortung tragen die Datein zu verschlüsseln, 
und selber die Schlüsseln verwalten.

### Cloud-Service 

Es besteht hier die gleiche Problematik wie bei [FTP Server][FTP-Server]


## Schlüsselaustausch

der Schlüsselaustausch ist von grossen Bedeutung was Netz- und Informationssicherheit
angeht. Auch bei etablierte Sicherheitsoftware ist Schlüsselaustausch problematik.
<br/>
Aufgrund seines Sensibilität gehört Schlüssel zu __kritische__ Informationen.




## Begriffe

### kritische Informationen

Er handelt sich um Informationen bzw. Daten die auf keinen Fall nirgendwo in
der verschieden Softwarekomponente unverschlüsselt abgespeichert werden dürfen,
oder unverschlüsselt durch der Netz geschickt werden dürfen.

Zu diese Kategorie gehören beispielweise wichtige Benutzersdokumenten, oder
Benutzerscredentials.


### Schlüssel

Hier handelt es sich um kryptographische Schlüssel oder anders ausgedruckt
Chiffrierschlussel. Diese kann verschiedene Formen haben, und jenach
Schlüsselart entweder zur kritischen oder nichtkritischen Informationen
gehören.  

#### Schlüsselpaare

Anhand der RSA Algorithmus werden Sclüsselpaare benötigt. 
Schüsselpaare besteht aus zwei Schlüssel : eine geheime und eine öffentliche
Schlüssel. <br/>
Öffentliche Schlüssel wird eingesetzt um Chiffrierung durchzuführen, 
geheime Schlüssel dagegen führt die Dechiffrierung durch.

__geheime Schlüssel auch bekannt <i>private</i> Schlüssel ist eine kritische
Information__


#### Symmetrische Schlüssel

Es handelt sich um eine geheime Schlüssel, die Anhand der AES Algorithmus
( Symmetrische Verschlüsselungsverfahren ) eingesetzt wird, um Chiffrierung
und Dechiffrierung durchzuführen.
<br/>
__Da die syymetrische Schlüssel sowohl zur Chiffrierung als auch zur
Dechiffrierung eingesetzt wird, ist die Schlüssel eine kritische Information.__

### Passwort und Passphrase

* Unter Passwort versteht man der nur beim Benutzer bekannte Zeichenkette, 
  den ihn ermöglich sich in den System anzumelden.

* Passphrase ist auch nur von der Benutzer bekannt, und darf nicht in irgendeine 
  Form persistent gehalten. Den Passphrase wird benutzt um Benutzer geheimschlüssel
  zu verschlüsseln.



### Symmetrische Verschlüsselung

### Asymmetrische Verschlüsselung

### Publickeys Infracstructur
 
















