
## Allgemein Designentscheidungen 

### JSON-Format

Systemweit wird JSON-Format bevorzugt um die Daten zwischen die verschiedene
Softwarekomponente zu transpotieren. Explizit ausgedruckt, heisst es dass
alle <i>High-end</i> Funktionen bzw. die Funktion die durch eine eine Softwarekomponent
zur aussenwelt verfügbar gemacht wurden exportieren Daten in JSON-Format.
<br/> 

Diese Entscheidung lasst sich bei der Interoperabilität gründen, sowie 
auch Kriterien wie Einheitlichkeit von Softwareschnittstellen, was bei
der Weiterentwicklung von grossen Bedeutung ist. 
Durch den Einsatz von JSON als Export-Format wird beispielerweise das 
Ersetzen von Softwarekomponent einfach.
<br/> 

Bei Einsatz von JSON wurde die von Google entwickelte [@GSON]("Gson") Bibliothek
benutzt. 

### UTF-8 und Base64 Encoding

Base64 ist mitte

### Http Headers 

Headers sind mächtige Standard wenn es zum Internet kommt, und wichtiger
noch im Bereich Security von Webbasierte Anwendungen.
Bei der Entwurf von dieser Arbeit, wurde die Entscheidung getroffen soviel
wie möglich auf die Standard zu halten, insbesondere bei sicherheitrelevante
Bereiche dieser Arbeit.
<br/> 
__Die richtige Einstellung/Konfiguration von manche Headers tragen  wesentlich
bei, um der Sicherheitgrad eine Webanwendung zu erhöhen.__ 
<br/> 

Es wird noch mal über Headers die Rede sein, bei alle Softwareteil wo sie
gesetzt werden ( LocalServer, RemoteServer, Frontend ), aber hier ist schon mal
wichtig darüber zu erwähnen und eine gesamte Überblick über die Headers 
die Systemweit eingesetzt werden.

![Headers](figures/dotty/headers.png)

* (1) `Content-Security-Policy` spiel eine bedeutende Rolle um XSS-Attack 
  zu vermeiden. mit dem Wert `script-src 'self'` weist die Header hin, dass 
  alle JavaScript source Datei nur von Server geladen werden dürfen. In unsere 
  Fall von LocalServer.

* (2) (3) (4) und (5) Informieren den Webbrowser über dem Authentication Algorithmus 
  bzw. dem Hash-Algorithmus, der eingesetzt wird. 




\pagebreak

