# Einführung


## Gliederung

Diese Arbeit lässt sich in drei große Abschnitte aufteilen: Kapitel 2 behandelt 
die Anförderungen eines sicheren Dokumentaustausch sowie der Authententifizierungmechanismen
die für das Verständnis der weiteren Kapitel wichtig sind.
Im folgenden Kapitel 3 werden beispielhaft die aktuelle Stand der Technick vorgestellt und 
ihre technische Umsetzung aufgeführt.
In Kapitel 4 wird anhand der Problemstellung ein
Konzept für die Dokumentaustauschplattform erstellt,
welches in den Kapiteln 5 und 6 konkretisiert
und implementiert wird. Die letzten beiden Kapitel 7 und 8 fassen die Ergebnisse dieser
Arbeit zusammen und machen Vorschläge für eine Verbesserung des Systems.


\newpage

## Motivationen

Der Austausch von vertraulichen Informationen im Netz ist grundsätzlich 
problematisch. Wie können Informationen zwischen Parteien augetauscht
werden, ohne dass Unberechtigte diese mitlessen können.

Der Austausch von vertraulichen Informationen mittels schriftlicher Aufzeichnungen 
ist grundsätzlich problematisch. Wie sollten Informationen zwischen Parteien
ausgetauscht werden, ohne dass Unberechtigte diese mitlesen können. Die Lösung
des Problems besteht darin, die Nachricht verschlüsselt zu übertragen. D. h. die ur-
sprüngliche Nachricht wird so verändert, dass es Unberechtigten deutlich erschwert
wird, den Inhalt einer abgefangenen Nachricht zu erfassen. Bereits in der Antike
wurden vertrauliche Informationen verschlüsselt übermittelt. Schon damals bestan-
den schon folgende Probleme, die noch heute - trotz aufwendigerer Verschlüsselung
– relevant sind.
• (1) Wer kann Nachrichten ver- bzw. entschlüsseln und wie?
• (2) Wie werden die Schlüssel zwischen Sender und Empfänger ausgetauscht?
Die Notwendigkeit eines sicheren Dokumentenaustauschs hat sich in den letzten
Jahren als immer drängender erwiesen, da zum einen ein Austausch via Internet sehr
schnell und einfach möglich ist. Zum anderen ist spätestens durch die NSA-Affäre
deutlich geworden, dass eine Datenübertragung via Internet nicht für vertrauliche
Informationen ohne weitere Maßnahmen geeignet ist.

\newpage



## Stand der Technick

### Email ( SMTS )

### FTP Server


### Web EDI 

#### AS2

#### Web-Upload

Das Speichern von Dokumenten auf einem Internet-Server ist weit verbreitet und
weltweit von jedem Browser aus möglich. Eine Installation zusätzlicher Software,
oder gar die Öffnung zusätzlicher Ports der Unternehmens-Firewall ist nicht 
notwendig. Die Benutzer-Authentifizierung erfolgt i.d.R. per Login/Password.
<br/>
Daten können im Internet mittels des https-Protokolls verschlüsselt übertragen wer-
den. Fälschlicherweise wird angenommen, dass die übertragenen Dokumente dann
auch beim Empfänger „sicher“ gespeichert sind. Jedoch werden lediglich die Dokumente
auf dem Weg zum Server mit SSL verschlüsselt. Danach liegen sie zunächst
unverschlüsselt vor. So werden von einem Server verschlüsselt übertragene Doku-
mente vom Browser entschlüsselt und im Klartext auf dem lokalen PC gespeichert.
Ebenso werden Dokumente, die vom Browser für die Übertragung verschlüsselt
werden vom Server entschlüsselt und liegen am Server unverschlüsselt vor.
Somit besteht dieselbe Problematik und auch derselbe Lösungsansatz wie bei Datei-
Servern. In Folge dessen sollten Dokumente, die per Browser auf einen Datei-Server
geladen werden, vom Client-PC verschlüsselt werden. Die Dokumente müssen also
vor dem Upload verschlüsselt worden sein, oder aber der Browser führt die Ver-
schlüsselung durch. Eine Vorab-Verschlüsselung der Dateien hat den Nachteil, dass
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

