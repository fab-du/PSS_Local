
## CryptUtils ( Cryptographics Utilities )

Es handelt sich um Hilfbibliothek, die von [JCA] zur Verfügung gestellte
kryptographische Methode abtrahieren. 
diese weitere Abstraktion führt zur eine einfache Benutzung von 
kryptographischen funktion wie die unterstehende beispiel zeigt.


### Allgemein design


> Symmetrische und asymmetrische Schlüsselerzeugung geschehen mithilfe
  zusätzliche Parametern um die Sicherheitgrad von Schlüssel zu erhöhen.
  Sicherheit ein Algorithmus, hängt nicht von der Algorithmus selbst, aber
    allein an die Stärke der Schlüssel
  Diese Parametern sind nämmlich : 

* Salt 
* Iteration bzw. Count ( Zähler ) 
* Password ( nur in der Fall von Passwordbasierte Schlüsselerzeugung ) 

> Schlüssellänge sind standardmässige auf die maximale gelegt. Diese ist 
  streng reglementiert. der Schlüssellänge kann aber durch der Client
  freikonfiguriert werden, in Bezug von Inlandregeln in der Bereich.
  Diese Bibliothek hält sich an der standard erlaubte Schlüssellänge. 
  siehe unter stehende Tabelle [-@JAVATM]  zufolge.

 | Algorithmus  | max. Schlüssellänge  |
 | :----------: | :------------------: |
 | DES          | 64                   |
 | DESede       | *                    |
 | RC2          | 128                  |
 | RC4          | 128                  |
 | RSA          | *                    |
 | alle andere  | 128                  |
 |              |                      |


> Dritten Provider können anstatt von [JCE]  benutzt werden. 

> Eintragname von exportierte JSON, verwendete Algorithmus, Schlüssellänge, 
  lassen sich durch eine Konfigurationdatei konfiguriert. 
  siehe beispiel-Konfigurationdatei [Anhang A] . 

> Alle exportierte Daten sind in JSON-Format und mit Base64 codiert.


### Java Cryptography Architecture ( JCA )


### Schlüsselerzeugung 

#### Klassendiagramm

![Schluessel-klasse-diagramm](figures/plantuml/cryptUtils_key.png)



### Chiffrierung bzw. Dechiffrierung

#### Klassendiagramm

![De/Chiffrierung-diagramm](figures/plantuml/cryptUtils_cipher.png)


```java

String password = new String("mypassword");
String keypair = CryptUtils("PBE-RSA").generateKey( password );

```

CryptUtils-module wurde als Fabrik-Method-module und Dekorator entworfen.

Es wird eine Schnittstelle für die Erzeugung von Objekten definiert. Die
Entscheidung, welche konkrete Klasse zu instanziieren, zu konfigurie-
ren und schließlich zurückzugeben ist, wird konkreten (Unter-)Klassen
überlassen, die diese Schnittstelle implementieren. [PatternKompakt]


\newpage

