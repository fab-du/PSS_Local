
## Frontend 

In diesem Kapitel handelt es sich um der Clientoberfläche 
in Form eine Webapplication, die der Endbenutzer auf irgendeinem Rechner
der über eine Webbrowser fervügt aufrufen kann. 
Hier ist noch mal zu erinnern, dass eine der wichtige Anforderung
von dieser Arbeit war das Software so konzipiert, dass es kein
zusätzliche Softwareinstallation benötigt wird.


> Das Webapplication wurde in Form eine sog. `Single Page Application`, wie
bereits erwärnt, handelt es sich um eine Technik Webseite zu entwerfen, so
dass die Bedinung von Webapplication ähnlich ist wie von Benutzer schon
bekannt Computersprogramm.

> Neben der Usability-und Portierungsargumente kommt auch die strenge 
Haltung von wichtigen Softwarearchiktekture und Regeln die mit Einsatz
von ___AngularJS___ verbunden sind, nämmlich ___Separation of Concern___ und
___MVVM___.   

### AngularJS und Security 

An der Webbrowser wird vorwiegend Angularjs eingesetzt.
Was Sicherheit angeht, werden nämmlich den Angular 
Speicher strategie, __cookies__ , die über den angular-service
[^$cookies] einsetzbar ist.

1. Cookie speicher Configuration Object 

	* path
	* domain
	* expires
	* secure

Was <i>path</i> und <i>domain</i> angeht wurden die Default
Werte gelassen, und zwar Cookie steht zur Verfügung für
aktuelle Pfad und alle untergeordnete Pfäder bzw. Cookie 
steht  zur Verfügung nur für die Application domain.

War hier konfiguriert wurde war den Parameter <i> __secure__ </i> mit 
den wert <i> __true__ </i> und <i> __expires__ </i> 
mit eine <i> Date </i> Instance in Form eine Zeichenkette,
der konfigurierte die Lebensdauer der Cookies.  

1.a. Unterschied zwischen mit <i> truthy secure</i> und
<i> falsy secure</i> 


Wie es zu sehen ist, kann man sehr leicht durch den Browser
die in Cookie gespeicherte Daten sehen wenn secure nicht gesetzt
ist. was im gegenteil nicht möglich ist wenn secure gesetzt ist.

### [^cookie-configuration] Cookie configuration object 

```JavaScript
  var d = new Date( new Date().getTime() + 600000);
  var n = d.toUTCString().toString();
  
  var cookie_config ={
    secure : true,
    expires : n 
  };
```


[^$cookies]: https://docs.angularjs.org/api/ngCookies/service/$cookies

[^cookie-configuration]: https://docs.angularjs.org/api/ngCookies/provider/$cookiesProvider#defaults

[^cookie-configurations]: Local/src/resources/static/app/scripts/service/sessionStore.js



### Ausstatung von Aktionen

Die unterstehende Grafik repräsentiert die mögliche Aktionen, die den Benutzer 
mithilfe der Webbrowser auslösen kann. Es lass sich dadurch nochmal eine
graphische abstrahierende Darstellung von funktionale Anforderungen darstellen.

![Aktionen](figures/dotty/routes.png)

* G bzw. P : GET bzw. POST HTTP-methode
* C : Request mit kryptographische Aktion
* R(Registered) : Benutzer muss registriert sein.
* O(Open) : gegenteil zu R(Registered) 


\newpage

