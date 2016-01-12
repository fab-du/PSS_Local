## Friends 

> Zwei Benutzer werden als **Friends** bezeichnet wenn sie sich gegenseitig
  bestätigen haben. 

Diese Konzept wurde eingefügt um Schlüsselaustausch zu handhabbar zu machen. 

Wenn ein Benutzer sich registrieren, bekommt der ein generierte Schlüsselpaare.
Als weitere Sicherheitsmaßnahmen wird der private Schlüssel mit einem 
zuverlässige unvorhersehbare generierte Zeichenfolge gesalt, und letzlich
mit einem Passphrase verschlüssel. das Resultat diese operation wird 
signert und auf [RemoteServer] geschickt. 

<br/> 
Dies oben beschriebene Vorrang passiert beim alle Benutzer. 
Nun um Benutzer A und B in einer Friend Beziehung zu ziehen, wird 
öffentliche Schlüssel von A durch B signiert und öffentliche
Schlüssel B durch A signiert.  

![add-friends](figures/plantuml/trust_a_new_user.png)





\newpage
