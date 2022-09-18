# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Hatice Zeynep Nenseth, S348818, s348818@oslomet.no


# Oppgavebeskrivelse

I oppgave 1 definerte jeg to hjelpenoder p og q og en komparator. Jeg flyttet p og q gjennom treet hvor jeg brukte 
komparatoren for å sammenligne verdien til p (starter i rotnoden) og verdien som er gitt i parameteren av metoden. Hvis 
verdien er mindre enn p sin verdi, flyttes p til sitt venstre ellers til høyre barn. Node q følger p. Når p er ute av treet, 
legges den som en ny node med q som forelder. Ettersom hva verdien til komparatoren ble da vi gikk gjennom treet, blir p 
lagt til som q sitt venstre eller høyre barn.

I oppgave 2 brukte jeg en hjelpenode p og en hjelpevariabel som teller antall like verdier. p settes som rotnoden og 
flyttes gjennom treet. Som i oppg1 hvis parameteren (verdi) er større enn eller lik p sin verdi, blir p til høyre. Da 
gjør man en indre sjekk for om verdiene er like. Hvis ja, så økes hjelpevariabelen. Ellers flyttes p til sitt venstre barn.
Metoden returnerer hjelpevariabelen til slutt.

I oppgave 3 førstePostorden-metoden flyttes noden som jeg får inn som parameter nedover i treet via sitt venstre barn så 
lenge venstre barnet eksisterer. Metoden er rekursivt for å lytte noden nedover. Hvis noden ikke har venstre barn men 
høyre barn, flyttes noden nedover rekursivt gjennom sitt høyre barn. Når noden blir altså til en bladnode som returneres
i metoden. 
I nestePostorden-metoden går jeg gjennom tilfellene og hva disse returnerer som neste postorden-node (pon). 
Hvis noden som vi gir i parameteren er rotnoden, så returneres null. Hvis parameternoden ikke er rotnoden, så er det tre 
tilfeller: Enten er parameternoden høyre barn til sin forelder, da blir foreldernoden til parameteren til neste pon. Eller 
hvis foreldernoden til parameteren ikke har høyre barn (dvs parameteren er venstre enebarn), da blir igjen foreldernoden 
til parameteren til neste pon. Tredje tilfelle er når høyre-søsken til parameteren har selv barn. Så lenge høyre-søsken til 
parameteren har venstre barn, flyttes den til venstre barn og blir til neste pon.

I oppgave 4 postorden(oppgave)-metoden gjør jeg som det står i oppgaveteksten. Vi finner første pon vha førstePostorden 
metoden som tar inn rotnoden som parameter. Deretter traverserer vi treet og utfører oppgaven (f. eks. skriver ut pon
til konsollet) og finner neste pon vha nestePostorden-metoden. Dermed blir hele treet skrevet ut i postorden rekkefølge.

I oppgave 5 serialize-metoden lager jeg en ArrayList fra et tre ved å bruke kø. Vi lager en kø vha ArrayDeque som kan ta inn 
noder som elementer. Rotnoden blir første elementet i køen. Deretter fjerner vi den fra køen og sjekker om denne noden har 
venstre/høyre barn. Hvis ja, legges disse i køen og verdien til det fjernede elementet legges i ArrayListen og så forsetter
vi å fjerne første node/legge inn barn så lenge køen har elementer, dvs vi har ikke ferdig kjørt gjennom treet. 
I deserialize-metoden lager jeg et tre fra en ArrayList. Jeg går gjennom ArrayListen via en for-each løkke og legger inn 
verdiene som nodens verdier. For det bruker jeg leggInn-metoden som har verdi i parameteren.

I oppgave 6 fjern-metoden skal jeg fjerne en node med gitt verdi. Her trenger jeg noen hjelpenoder. Det er hovedsaklig tre 
tilfeller hvordan å gå frem ift hvor noden som skal slettes (slett-node) finnes i treet. I første tilfelle har slett-noden 
ingen barn (dvs. p er en bladnode). Spesialtilfelle er at noden er rotnoden, dvs treet har bare en node. Da settes 
rotreferansen til null for å slette noden. I neste tilfeller har treet flere enn en node. Da starter man på rotnoden og 
traverserer i treet for å finne noden med gitt verdi i parameteren. Hvis man ikke finner verdien, returneres null eller 
returneres slett-noden. Her dekker man to tilfeller at enten slett-noden har ingen barn eller ett barn. Hvis 
slett-noden har et barn, slettes den ved at dens foreldernode blir forelder til dens barnenode. Spesialtillfelle her igjen 
er at slett-noden er rotnoden. Da settes rotreferansen til barnet til slett-noden. Tredje tilfelle er at slett-noden har 
to barn. Istedet for å "slette" slettnoden byttes dens verdi med verdien til dens barn i inorden måte og så slettes barneoden.
I fjernAlle-metoden fjernes alle noder som har verdien gitt i parameteren og antall slettede noder returneres. Her bruker 
jeg fjern-metoden for å fjerne noden. Samtidig har jeg en hjelpevariabel som økes med en hver gang en node blir slettet. 
Denne returneres til slutt.
I nullstill-metoden brukte jeg postorden måte å traversere i treet (starter ved rotnoden) for å slette pon. Her blir både 
verdien og barna til slett-noden settes lik null for å fjerne den.

Warnings-kommentarer:
- Linje 10: Non-ASCII karakterer i koden ("høyre"): Variabelnavnet var allerede gitt i oppgaven.
- Linje 35: Private field "endringer" is assigned but never used: I ingen av de oppgavene måtte vi gjøre noe med variabelen 
  "endringer".
- Linje 84: return value of leggInn() never used.
- Linje 98: Condition "cmp >= 0" is always true: Her trenger vi egentlig ikke å skrive "cmp >= 0" i "else if" siden "if" 
  setningen som sier "cmp < 0" er nok å indikere at "else" betyr "cmp >= 0". Jeg velger å beholde det slik som jeg har 
  skrevet fordi det er lettere å forstå betydningen av de to forskjellige kondisjonene.
- Linje 260: Non-ASCII karakterer i koden ("førstePostorden"): Metodenavnet var allerede gitt i oppgaven.
- Linje 356: Explicit type argument K can be replaced with <>: Dette var allerede gitt i oppgaven.

