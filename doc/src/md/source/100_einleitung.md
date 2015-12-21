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

\begin{figure}[ht]
 \centering % centering figure
 \scalebox{0.5} % rescale the figure by a factor of 0.8
 {\includegraphics{graph.eps}} % importing figure
 \label{fig:exm} % labeling to refer it inside the text
\end{figure}
--------------------- e


## Stand der Technick

### Email ( SMTS )

### FTP Server

### Web EDI 

#### AS2
