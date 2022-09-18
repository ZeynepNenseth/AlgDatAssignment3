package no.oslomet.cs.algdat.Oblig3;


import java.util.*;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    // metoden er tatt fra Kompendiet 5.2.3a) og modifisert som gitt i kommentarene
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Nullverdier er ikke tillatt!");

        Node<T> p = rot;   // refernase til roten
        Node<T> q = null;  // referanse til noden som følger p
        int cmp = 0;       // komparator-verdi er initialisert

        while (p != null) {   // så lenge p er i treet
            q = p;            // q blir forelder til p
            cmp = comp.compare(verdi, p.verdi);  // vi bruker komparatoren og finner komparator verdien

            // vi flytter p til venstre eller høyre etter verdien fra komparatoren
            if (cmp < 0) {
                p = p.venstre;
            } else if (cmp >= 0) { // her ble lagt inn "=" fordi metoden ellers ikke funker om det finnes flere av samme verdien
                p = p.høyre;
            }
        }
        // nå er p ute av treet og blir til en ny node med gitt verdi og q som forelder
        p = new Node<>(verdi, q); //Node q ble lagt inn siden vi har en konstruktør som tar inn foreldre-node
        if (q == null) {         // hvis q er null, så blir p rotnoden
            rot = p;
        } else if (cmp < 0) {    // p blir til venstre barn til q
            q.venstre = p;
        } else {                 // p blir til høyre barn til q etter verdien fra komparatoren
            q.høyre = p;
        }
        antall++;               // antall (noder) økes med en
        endringer++;            // antall endringer økes med en
        return true;
    }

    public boolean fjern(T verdi) { // Kode fra Kompendiet programkode 5.2.8d) som er modifisert ift oppgave 6
        /* Sammendrag fra Kompendiet for å orientere seg om de tilfellene.
        1. p har ingen barn (dvs. p er en bladnode). Hvis p er rotnoden, settes rotreferansen til null. Hvis ikke, settes referansen fra forelderen til p lik null.
        2. p har nøyaktig ett barn (et venstre eller et høyre barn). Hvis p er rotnoden, settes rotreferansen og hvis ikke, referansen fra forelderen, til barnet til p.
        3. p har to barn. Da erstattes verdien til p med verdien til etterfølgeren til p i inorden og isteden fjernes etterfølgeren.*/

        if (verdi == null) return false;  // treet har ingen nullverdier

        if (antall == 0) return false;   // treet er ikke tomt

        Node<T> p = rot;           // hjelpenode p satt til som rotnode, p er noden som skal slettes
        Node<T> q = null;          // q skal være forelder til p
        Node<T> r;                 // hjelpenode r som skal settes til p sitt barn i koden under

        // Tilfelle 1-spesialtilfelle: p har ingen barn (dvs p er bladnode) men p er også rotnoden. Dvs treet har bare en node
        if (verdi == p.verdi && antall == 1) {
            rot = null;                  // for å fjerne p, settes rotrefernsen til null
            antall--;                    // antall noder minkes med en
            return true;                 // vellykket fjerning av node
        }

        // Treet har flere noder. Man leter etter noden med gitt verdi i parameteren ved å traversere treet
        while (p != null) {       // så lenge node p er i treet
            int cmp = comp.compare(verdi, p.verdi);      // Man bruker komparatoren og finner komparator verdien

            // Man flytter p til venstre eller høyre etter verdien fra komparatoren
            if (cmp < 0) {             // p flyttes til venstre barn
                q = p;                 // q følger med som forelder
                p = p.venstre;
            } else if (cmp > 0) {      // p flyytes til høyre barn
                q = p;                 // q følger med som forelder
                p = p.høyre;
            } else break;             // den søkte verdien ligger i p
        }

        // Etter traversering i treet, hvis man ikke finner verdien fra parameteren,
        if (p == null) return false;   // returneres false

        // Tilfelle 1: p har ingen barn (bladnode) og Tilfelle 2: p har ett barn
        if (p.venstre == null || p.høyre == null) {
            if (p.venstre != null) {     // hvis p har venstre barn
                r = p.venstre;           // hjelpenode r blir til p sitt venstre barn
            } else {                     // hvis p har høyre barn
                r = p.høyre;             // hjelpenode r blir til p sitt høyre barn
            }

            if (r != null) {             // hvis r da blir barnet til p (ut fra kode oppe),
                r.forelder = q;          // så settes forelder til r lik q. Dvs båndet mellom r og p blir brutt opp
            }

            if (p == rot) {              // Tilfelle 2-spesialtillfelle: Hvis noden som skal slettes, er rotnoden,
                rot = r;                 // så settes rotnoden lik r.
            } else if (p == q.venstre) {  // p er ikke rotnoden og men er venstre barn til q (forelder til p)
                q.venstre = r;           // så settes q sitt venstre barn til r . Dvs båndet mellom r og p blir brutt opp
            } else {                     // p er ikke rotnoden og men er høyre barn til q (forelder til p)
                q.høyre = r;             // så settes q sitt høyre barn til r . Dvs båndet mellom r og p blir brutt opp
            }
        }

        // Tilfelle 3: p har to barn med to undertilfeller
        else {
            // 3a) : hjelpenode s er lik p
            Node<T> s = p;
            Node<T> u = p.høyre;   // hjelpenode u settes lik p sitt høyre barn = neste node i inorden, dermed blir r fjernet
            while (u.venstre != null) {  // hvis det finnes venstre barn til u (barnebarn til p)
                s = u;                   // s blir forelder til u
                u = u.venstre;           // u flyttes til venstre
            }

            p.verdi = u.verdi;   // verdien til p byttes med verdien til etterfølgeren til p i inorden

            // 3b) : hjelpenode 2 er forskjelig fra p
            if (s != p) {
                s.venstre = u.høyre;   // r fjernes ved at s sitt venstre barn settes lik r sitt høyre barn
                if (s.venstre != null) {
                    s.venstre.forelder = s;

                }
            }
            else s.høyre = u.høyre;
        }

        antall--;   // det er nå én node mindre i treet
        endringer++;
        return true;
    }

    // hvis en verdi forekommer flere ganger i treet, fjerner denne metoden alle forekomstene
    // og teller hvor mange noder ble fjernet
    public int fjernAlle(T verdi) {
        if(tom() || verdi == null) return 0;  // returnerer 0 hvis treet er tomt eller verdien er null

        int teller = 0;   // hjelpevariabel som teller antall verdier som blir fjernet
        while (fjern(verdi)) {  // noder med gitt verdi fjernes ved å bruke fjern-metoden
            teller++;          // teller økes med en hver gang en node fjernes
        }

        return teller;
    }

    public int antall(T verdi) {
        // hvis treet er tomt eller verdier vi sjekker ikke finnes i treet, returneres 0 (det kastes ikke unntak)
        if (tom() || !inneholder(verdi)) {
            return 0;
        }

        int antallLike = 0;  // hjelpevariabel for telle antall like verdier
        Node<T> p = rot;     // hjelpenode p settes som roten
        while (p != null) {   // p flyttes så lenge den er i treet
            int cmp = comp.compare(verdi, p.verdi);  // sammenligner verdien gitt i parameteren og p sin verdi
            if (cmp >= 0) {    // når verdien er større enn eller lik til p sin verdi
                if (cmp == 0){
                    antallLike++; // vi øker hjelpevariabelen når verdiene er like
                }
                p = p.høyre;  // p flyttes til høyre
            }
            else {           // eller til venstre
                p = p.venstre;
            }
        }

        return antallLike;  // returnerer antall like verdier
    }

    // velger postorden måte å traversere og slette nodene siden vi har laget noen postorden metoder
    public void nullstill() {
        if (rot != null) {          // Hvis rotnoden ikke er null,
            Node<T> p = førstePostorden(rot);    // settes hjelpenoden p likt til første pon (postorden node)

            while (p != null) {                 // Så lenge p er i treet,
                Node<T> q = nestePostorden(p);  // settes hjelpenoden q likt til neste pon
                p.verdi = null;                 // verdien til p settes lik null
                p.høyre = null;                 // barna til p settes lik null
                p.venstre = null;

                antall--;                       // antall noder minkes med enn
                endringer++;                    // endringer i treet økes med en

                p = q;                          // nå settes p likt q
            }
            rot = null;                         // helt på slutten settes roten likt null
        }
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        if(p== null) return null;
        // så lenge p har venstre barn, blir p flyttet nedover i treet hvor man holder p helt til venstre
        if (p.venstre != null) {
            return førstePostorden(p.venstre); // ved å kalle på sin venstre barn
        }
        // hvis p ikke har venstre barn, så flyttes p nedover i treet gjennom sitt høyre barn så lenge den har høyre barn
        if (p.høyre != null) {
            return førstePostorden(p.høyre); // ved å kalle på sin høyre barn
        }

        return p;     // returnerer p
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        // 1. tilfelle: p er rotnoden, dvs, p har ingen forelder node. Da returneres null
        if (p.forelder == null) {
            return null;
        }
        // 2. tilfelle: p er ikke rotnoden
        else {
            Node<T> q = p.forelder;  // hjelpenode q som er forelder til p
            if (q.høyre == p) {  // 2a) hvis p er høyre barn til sin forelder,
                return q;        // er forelderen nestePostorden-noden.
            }
            if (q.høyre == null) {  // 2b) hvis høyre barn til p sin forelder er null (p er q sitt venstre enebarn),
                return q;           // er forelderen nestePostorden-noden.
            }
            Node<T> r = q.høyre;    // hjelpenode r som er høyre barn til p sin forelder (dvs p og r er søsken-noder)
            while (r.venstre != null || r.høyre != null) {// 2c) så lenge r sitt venstre barn eksisterer, flyttes r til sitt venstre barn
                if (r.venstre != null) {
                    r = r.venstre;
                } else {
                    r = r.høyre;
                }
            }
            return r; // r er da nestePostorden-noden.
        }
    }

    public void postorden(Oppgave<? super T> oppgave) {
            if (rot == null) return;     // hvis treet er tomt, stopper metoden der

            Node<T> p = førstePostorden(rot);  // første postorden noden i treet blir til hjelpenoden p
            while (p != null) {                // så lenge p er i treet
                oppgave.utførOppgave(p.verdi); // vi bruker metoden som er definert i grensesnittet Oppgave (f. eks å skrive ut til konsollet
                p = nestePostorden(p);         // og finner neste postorden noden vha nestePostorden-metoden
            }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        // Denne metoden følger lignende kode som førstePostorden metoden og utfører en oppgave samtidig
        // Så lenge noden i gitt som parameter har venstre barn, blir noden flyttet nedover i treet hvor noden holder seg helt til venstre
        if (p.venstre != null) {
            // ved å kalle på seg selv med sitt venstre barn i parameter og oppgaven som ønkes utføres.
            postordenRecursive(p.venstre, oppgave);
        }

        // hvis parameter-noden ikke har venstre barn, så flyttes p nedover i treet gjennom sitt høyre barn så lenge den har høyre barn
        if (p.høyre != null) {
            postordenRecursive(p.høyre, oppgave);  // ved å kalle på seg selv med sitt høyre barn i parameter og oppgaven som ønkes utføres.
        }
        oppgave.utførOppgave(p.verdi);             // utfører oppgaven
    }

    public ArrayList<T> serialize() {               // metoden lager en ArrayList fra et tre
        ArrayList<T> minListe = new ArrayList<>();  // oppretter en tom ArrayList
        Node<T> p = rot;                            // hjelpenode p settes som rot-node

        // I oppgaven står at vi må bruke kø for å traversere treet. Vi har ikke kodet noen hjelpeklasser eller hjelpemetoder ang kø.
        // Derfor bruker jeg ArrayDeque (double ended queue) som er implementert i Java for å bruke kø.
        ArrayDeque<Node<T>> slange = new ArrayDeque<>(); // oppretter en tom kø
        slange.add(p);                                   // legger til roten som første element i køen

        while (!slange.isEmpty()) {         // så lenge køen ikke er tom
            Node<T> q = slange.removeFirst();   // fjern det første elementet i køen

            // sjekk om det elementet (node) har barn
            if (q.venstre != null) {       // hvis noden har venstre barn, legges det i køen
                slange.addLast(q.venstre);
            }

            if (q.høyre != null) {         // hvis noden har høyre barn, legges det i køen
                slange.addLast(q.høyre);
            }

            minListe.add(q.verdi);        // ArrayListen oppdateres med verdien til det elementet som ble fjernet fra køen
        }
        return minListe;                 // returnerer ArrayListen
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {  // metoden lager et tre fra en ArrayList
        SBinTre<K> myTree = new SBinTre<K>(c);   // oppretter et tom tre


        for (K verdi : data) {                  // går gjennom Arraylisten via en for-each løkke
            myTree.leggInn(verdi);             // legger noden sin verdi i treet via leggInn-metoden
        }
        return myTree;                        // returnerer treet
    }

} // ObligSBinTre
