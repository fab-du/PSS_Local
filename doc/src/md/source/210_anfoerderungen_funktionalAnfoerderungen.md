# Anforderungen

## Funktionale Anforderungen

Die folgenden funktionalen Anforderungen müssen durch das System erfüllt wer-
den.

* Der Administrator muss in der Lage sein, neue Benutzer im System hinzuzu-
fügen und zu entfernen.
* Der Benutzer kann eine Vertrauensbeziehung zu anderen Benutzern erstellen
und sie wieder zerstören.
* Der Benutzer kann eine Gruppe erstellen und entfernen.
* Der Benutzer kann vertraute Benutzer in einer Gruppe hinzufügen und ent-
fernen
* Der Benutzer kann den Zugriff auf seine Dateischlüssel an alle Mitglieder
einer Gruppe freigeben und diese Freigabe auch wieder zurückziehen.
Die funktionalen Anforderungen sind in der Abbildung 2.1 zusammengefasst.



\newpage

## Nicht funktionale Anforderungen

* Das System oder Konzept muss die Integrität eines Benutzers oder seines
öffentlichen Schlüssels gewährleisten und den Mechanismus definieren, mit
dem sie geprüft werden.

* Das System muss die Sicherheit sensible Daten (Benutzerpasswörter) ge-
währleisten. Die Passwörter dürfen nicht in Klartext gespeichert werden.

* Der Dateienschlüsselaustausch zwischen den Mitgliedern einer Gruppe muss
sicher sein, damit keine weiteren Akteure darauf zugreifen können.

* Das System muss über ein robustes Authentifizierungsverfahren verfügen.

* Das Authentifizierungsverfahren muss widerstandsfähig sein gegen gewöhn-
liche Angriffe, wie Replay Attacks, Offline- und Online-Dictionary-Attacks,
etc.

* Das Konzept sollte den Mechanismus so definieren, dass Dateien zurückzu-
gewonnen werden, nachdem das System unsicher geworden ist.

* Das System muss es ermöglichen, dass zwei Benutzer mit unterschiedlichen
IT-Infrastrukturen Dateien untereinander austauschen können.

* Eine SSL-Verbindung mit SSL-Zertifikat ist für die Erstellung dieses Kon-
zepts nicht zugelassen.

* Das System muss portierbar sein. Es darf keine Installation auf dem lokalen
Rechner des Benutzers benötigen. Auf diesem Rechner dürfen auch keine
Daten(Dateien oder Schlüssel) gespeichert werden.




