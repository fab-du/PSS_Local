## Authentifizierung 

Authenfizierung spielt systemweit eine bedeutende Rolle. Dabei passieren 
alle notwendige Prüfung von [LocalServer], [RemoteServer],  [RemoteServer Integrität]
und natürlich Authenfizierung von Benutzer. 
<br/> 

Es durfte systemweit keine Einsatzt von Zertifikat/SSL-Verbindung oder 
Aufbau eine zustandbehaft Verbindung kommen, spricht die Kommunikationskanal
ist unsicher.

Um Benutzercredentials von [LocalServer] auf [RemoteServer] zu übermitteln
unter Anhaltung von Spezifikation, wurde [@SRP] Algorithmus wie im
RFC2945 spezifiziert benutzt.
<br/> 
Beim Einsatz von [SRP-Secure Remote Password Protocol] lasst sich auch einfach
der gegenseitige Aunthenfizierung von [LokalServer] und [RemoteServer] 
realisieren, diese geschehet auch beim Authentifizierungsphase.


### SRP-Secure Remote Password Protocol

[SRP] ermöglich es die Benutzercredentials zu übertragragen ohne dabei
kritischen Informationen zu verraten. 
Beim Registrierung werden Benutzerspassword und Benutzername in 
eine **nichtzurückkehrbare** Operation zu eine korrespondierte Information
berechnet nämmlich **Verifier**. Auch wenn eine Unbefugte die 
Verifier bekommt, kann der nichts damit anfangen. 
<br/> 

SRP hat viele Vorteile nämmlich : 

* Dicht gegen Man-in-the-middle [^Man-in-the-middle], da keine kritischen Informationen 
  ausgetausch werden.  
* Starke Sicherheitsgrad wird trotz eine schwache Password erreicht.
* Möglichkeit dass Client-Server sich gegenseitig authentifizieren.

: Algorithmus beschreibung

```

The following is a description of SRP-6 and 6a, the latest versions of SRP:

  N    A large safe prime (N = 2q+1, where q is prime)
       All arithmetic is done modulo N.
  g    A generator modulo N
  k    Multiplier parameter (k = H(N, g) in SRP-6a, k = 3 for legacy SRP-6)
  s    User's salt
  I    Username
  p    Cleartext Password
  H()  One-way hash function
  ^    (Modular) Exponentiation
  u    Random scrambling parameter
  a,b  Secret ephemeral values
  A,B  Public ephemeral values
  x    Private key (derived from p and s)
  v    Password verifier
The host stores passwords using the following formula:
  x = H(s, p)               (s is chosen randomly)
  v = g^x                   (computes password verifier)
The host then keeps {I, s, v} in its password database. The authentication protocol itself goes as follows:
User -> Host:  I, A = g^a                  (identifies self, a = random number)
Host -> User:  s, B = kv + g^b             (sends salt, b = random number)

        Both:  u = H(A, B)

        User:  x = H(s, p)                 (user enters password)
        User:  S = (B - kg^x) ^ (a + ux)   (computes session key)
        User:  K = H(S)

        Host:  S = (Av^u) ^ b              (computes session key)
        Host:  K = H(S)
Now the two parties have a shared, strong session key K. To complete authentication, they need to prove to each other that their keys match. One possible way:
User -> Host:  M = H(H(N) xor H(g), H(I), s, A, B, K)
Host -> User:  H(A, M, K)

```



[^Man-in-the-middle]: A und B sei Kommunikationknote, C ein Unbefugte 
greift an der Kommunikationskanal und bearbeitet/sieht/stiehlt die Informationen.


\newpage
