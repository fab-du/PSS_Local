
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
  neben der Kryptographie know-how, den passenden Software, den passenden 
  kryptographischen Algorithmus und der Verschlüsselungschlüssel.
* Schlüsselaustauschsproblematik.
* Infrakstrukturproblemetik.  

### Web-upload

Das Speichern von Dokumenten auf einem Internet-Server ist weit verbreitet und
weltweit von jedem Browser aus möglich. Eine Installation zusätzlicher Software,
oder gar die Öffnung zusätzlicher Ports der Unternehmens-Firewall ist nicht not-
wendig. Die Benutzer-Authentifizierung erfolgt i.d.R. per Login/Password.
<br/>
Daten können im Internet mittels des https-Protokolls verschlüsselt übertragen wer-
den. Fälschlicherweise wird angenommen, dass die übertragenen Dokumente dann
auch beim Empfänger „sicher“ gespeichert sind. Jedoch werden lediglich die Doku-
mente auf dem Weg zum Server mit SSL verschlüsselt. Danach liegen sie zunächst
unverschlüsselt vor. So werden von einem Server verschlüsselt übertragene Doku-
mente vom Browser entschlüsselt und im Klartext auf dem lokalen PC gespeichert.
Ebenso werden Dokumente, die vom Browser für die Übertragung verschlüsselt
werden vom Server entschlüsselt und liegen am Server unverschlüsselt vor.
<br/>
Somit besteht dieselbe Problematik und auch derselbe Lösungsansatz wie bei Datei-
Servern. In Folge dessen sollten Dokumente, die per Browser auf einen Datei-Server
geladen werden, vom Client-PC verschlüsselt werden. Die Dokumente müssen also
vor dem Upload verschlüsselt worden sein, oder aber der Browser führt 
die Verschlüsselung durch. 
Eine Vorab-Verschlüsselung der Dateien hat den Nachteil, dass
das Dokumenten- und Schlüssel-Management vom Anwender eigenverantwortlich
durchgeführt werden muss. Dies ist i.a. den Anwendern zu aufwendig.
Folglich sollte die Verschlüsselung durch den Browser quasi automatisch erfolgen.
Dies wird aktuell nur sehr selten durchgeführt, da die Verschlüsselungs-Software
auch vom Web-Server geladen werden müssen. Und es kann nicht garantiert wer-
den, dass die geladene Software nicht Eindringlingen unbeabsichtigten Zugriff er-
möglicht. In Folge dessen werden Dokumente SSL-verschlüsselt zum Server gesen-
det. Die dort empfangenen, unverschlüsselten Dokumente werden sofort verschlüs-
selt und als Datei abgelegt. Hier bestehen jedoch folgende Probleme: (1) Wie kom-
men die notwendigen Schlüssel zum Server? (2) Ein Eindringling auf dem Server
kann die Klartext-Datei und/oder die Schlüssel mitlesen. Zusammenfassung
Ein Ansatz für ein sicheres Web-Upload ist bisher nicht bekannt.

 

[FTPServer]: "FTP-Server"

### FTP-Server 

Das Problematik bei FTP Server ist dass eine Dritte von aussen aus auf den
auf der FTP-Server gespeicherte Datein nicht zugreifen kann.
Zusätzlich muss der Benutzer der Verantwortung tragen die Datein zu verschlüsseln, 
und selber die Schlüsseln verwalten.

### Cloud-Service 

Cloud-Service hat sich in der letzen 5 Jahre wesentlich verbreitet. Und war 
auf einer guten Weg bis zum NSA-Affäre sich als defakto Standard einzusetzen.
Heute auch trotz die Spionnageskandale, wird Cloud-Service bei viele Endbenutzer
sehr beliebt. Das Risiko ihre geheime Dokumente gestohlen zu haben, was Endbenutzer
eingehen, können sie sich Unternehmen nicht leisten. 
<br/> 
der Einsatz von Cloud-Service bei Unternehmen ist ein absolut No-go. 

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
 



## Zusammenfassung

Schlüsselmanagementssystem und PBK-Infrakstruktur tragen exclusiv die 
Verantwortung über :

* Schlüsselerzeugung
* Schlüsselmanagement
* Schlüsseldeployment
* usw. 

An sich ist dies nicht problematisch, da die Software machen genau das, 
wofür sie konzipiert wurden, wobei wie schon besprochen einige Einschrängkungen
bestehen was Portabilität und Flexibilität angehen. 
<br/> 
Dateiablegerung und Dateiaustausch System erfüllen auch genau die 
Aufgabe wofür sie konzipiert wurden, dabei bestehen jedoch gravierende 
Sicherheitsproblematik.

* Vorabverschlüsselung von Datei
    * Vorabinstallation von Schlüssel
    * Übertragung von Schlüssel
* Offnung weitere Port wie bei FTP-Server
* eingeschränkte Einsatzt von HTTPS bzw. [Endknote-problematik]
* Redeployment 


Diese Arbeit setzt sich als Ziel, eine System zu konzipiert, der die oben
genannte Lücke erfüllen, und von die beide Technologie eins macht, sowie
eine starke Authentication und zuverlässige Vertrauenmechanismus.  


[Endknote-problematik]: Diese Problematik besteht wenn die Übertragungskanal
sicher ist ( zB: HTTPS ) aber nich den Empfang bei Kommunikationspartner.

\newpage 












