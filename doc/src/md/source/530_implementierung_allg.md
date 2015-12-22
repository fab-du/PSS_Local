
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

\newpage

