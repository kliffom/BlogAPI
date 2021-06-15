# BlogAPI
Progetto base per l'esercitazione final del corso Java. Il progetto è sviluppato in Spring boot
e mette a disposizione delle API rest per registrare un utente ed autenticarlo.
L'autenticazione restituisce un token JWT che dovrà essere utilizzato per accedere alle
API RESTfull richieste dall'esercitazione.

### Requirements
Il progetto prevede la presenza di una server MySQL attivo e raggiungibile che al suo interno
abbia la tabella users creata con la seguente istruzione DDL:

```
CREATE TABLE users (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  password varchar(100) NOT NULL,
  PRIMARY KEY (id)
);
```

### API esposte
Il progetto base mette a disposizione le seguenti API:
* http://{hostname}:{port}/auth
* http://{hostname}:{port}/register
* http://{hostname}:{port}/api/categoria
* http://{hostname}:{port}/api/tag
* http://{hostname}:{port}/api/articolo
* http://{hostname}:{port}/api/articolo/id
* 

###### [POST] http://{hostname}:{port}/auth
Questa API accetta in input un oggetto JSON nel seguente formato:

```
{
	"username": "utente01",
	"password": "password01"
}
```
e, se l'utente è registrato alla piattaforma, viene restituito il seguente oggetto in output:

```
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZGludXp6byIsImV4cCI6MTYyMzI4OTI ..."
}
```

contenente il token JWT da utilizzare per poter accedere alle risorse protette.


###### [POST] http://{hostname}:{port}/register
Questa API accetta in input un oggetto JSON nel seguente formato:

```
{
	"username": "utente01",
	"password": "password01"
}
```

e resituisce il nome dell'utente se la registrazione è andata a buon fine

```
{
    "username": "utente01"
}
```

##### [GET] http://{hostname}:{port}/api/categoria
Questa API restituisce una lista delle categorie presenti nel DB nel seguente oggetto JSON:

```
[
	{
	    "descrizione": "Descrizione categoria 1"
	},
	{
	    "descrizione": "Descrizione categoria 2"
	},
	...
]
```
##### RESPONSE STATUS CODE: 
* 200: categorie restituite correttamente;
* 404: nessuna categoria presente all'interno del DB.

##### [GET] http://{hostname}:{port}/api/tag
Questa API restituisce una lista dei tag presenti nel DB nel seguente oggetto JSON:

```
[
	{
	    "nome": "Nome Tag 1"
	},
	{
	    "nome": "Nome Tag 2"
	},
	...
]
```

##### RESPONSE STATUS CODE: 
* 200: tag restituiti correttamente;
* 404: nessun tag presente all'interno del DB.

Tutte le successive chiamate richiedono la presenza del token JWT ottenuto dalla chiamata [POST] http://{hostname}:{port}/auth all'interno dell'header 'Authorization' della richiesta contenente il valore 'Bearer [valore token ricevuto]'. La mancanza di questo header sarà considerato dal sistema come l'accesso da parte di un utente anonimo.

##### [GET] http://{hostname}:{port}/api/articolo/id
Questa API accetta come id il codice dell'articolo e ne restituisce un oggetto JSON contenente i suoi attributi:

```
{
    "id": id_articolo,
    "titolo": "Titolo articolo",
    "sottotitolo": "Sottotitolo articolo",
    "testo": "Testo corpo articolo",
    "user": {
        "username": "Username autore articolo"
    },
    "categoria": {
        "descrizione": "Categoria articolo"
    },
    "tags": [
        {
            "nome": "Tag 1 articolo"
        },
        {
            "nome": "Tag 2 articolo"
        },
        ...
    ],
    "bozza": false/true,
    "data_creazione": "Data creazione articolo in formato yyyy-mm-ggThh:mm:ss",
    "data_pubblicazione": "yyyy-mm-ggThh:mm:ss",
    "data_modifica": "yyyy-mm-ggThh:mm:ss"
}

```
Se l'oggetto presenta valori null, questi non verranno mostrati.
##### RESPONSE STATUS CODE: 
* 200: Articolo restituito correttamente;
* 404: L'ID passato non corrisponde a nessun articolo oppure l'articolo è in stato di bozza e l'utente loggato non è l'autore

##### [GET] http://{hostname}:{port}/api/articolo
Questa API restituisce una lista degli articoli presenti nel DB nel seguente oggetto JSON:

```
[
	{
	    "id": id_articolo,
	    "titolo": "Titolo articolo",
	    "sottotitolo": "Sottotitolo articolo",
	    "testo": "Testo corpo articolo",
	    "user": {
		"username": "Username autore articolo"
	    },
	    "categoria": {
		"descrizione": "Categoria articolo"
	    },
	    "tags": [
		{
		    "nome": "Tag 1 articolo"
		},
		{
		    "nome": "Tag 2 articolo"
		},
		...
	    ],
	    "bozza": false/true,
	    "data_creazione": "Data creazione articolo in formato yyyy-mm-ggThh:mm:ss",
	    "data_pubblicazione": "yyyy-mm-ggThh:mm:ss",
	    "data_modifica": "yyyy-mm-ggThh:mm:ss"
	},
	...
]
```
Un utente anonimo riceverà come risultato solo gli articoli pubblicati. Un utente registrato e loggato riceverà tutti gli articoli pubblicati insieme ai propri articoli in stato di bozza.
Il servizio consente di poter filtrare i risultati in base a del testo contenuto all'interno di titolo, sottotitolo o corpo, oppure per parole chiave su ID, categoria, autore, tag e stato. Una ricerca per testo è consentita solo se non si esegue una ricerca per parole chiave e viceversa.
Per poter eseguire la ricerca inserire nella query string i parametri:
* search=valore: per eseguire una ricerca per testo all'interno di titolo, sottotitolo o corpo;
* id=valore: per eseguire una ricerca per ID dell'articolo;
* cat=valore: per eseguire una ricerca per categoria;
* tag=valore: per eseguire una ricerca per tag;
* aut=valore: per eseguire una ricerca per autore;
* stato=BOZZA: per eseguire la ricerca dei soli articoli in stato di bozza. Questo parametro è utilizzabile solo da un utente loggato, e il valore dovrà essere BOZZA.
È possibile combinare i filtri di ricerca, ad esclusione di 'search'.

##### RESPONSE STATUS CODE: 
* 200: La ricerca ha prodotto risultati;
* 400: Uno dei parametri inseriti non è corretto;
* 404: La ricerca non ha prodotto alcun risultato.

##### [POST] http://{hostname}:{port}/api/articolo
Questa API accetta in input un oggetto JSON dell'articolo da inserire nel DB nel seguente formato:

```
{
    "titolo": "Titolo articolo",
    "sottotitolo": "Sottotitolo articolo",
    "testo": "Testo articolo", 
    "categoria": {
        "descrizione": "Nome Categoria"
    },
    "tags": [
        {
            "nome": "Tag 1"
        },
	{
	    "nome": "Tag 2"
	},
	...
    ]
}
```
È possibile omettere l'inserimento del sottotitolo. L'articolo rappresentato dall'oggetto JSON sarà inserito nel DB in stato di bozza, con riferimento all'utente loggato e data di creazione pari al momento della richiesta. Per poter pubblicare/modificare l'articolo si utilizza la chiamata [PUT] http://{hostname}:{port}/api/articolo/id .

##### RESPONSE STATUS CODE: 
* 204: L'inserimento è andato a buon fine;
* 400: Uno dei parametri passati nel JDON in input non è valorizzato o corretto;
* 401: Un utente non loggato sta provando ad aggiungere un articolo.

##### [PUT] http://{hostname}:{port}/api/articolo/id
Questa API accetta in input un oggetto JSON dell'articolo con id ricevuto da modificare nel seguente formato:
```
{
    "titolo": "Nuovo titolo articolo",
    "sottotitolo": "Nuovo sottotitolo articolo",
    "testo": "Nuovo testo articolo", 
    "bozza": false,
    "categoria": {
        "descrizione": "Nome nuova Categoria"
    },
    "tags": [
        {
            "nome": "Nuovo Tag 1"
        },
	{
	    "nome": "Nuovo Tag 2"
	},
	...
    ]
}
```
Tutti i campi sono opzionali, inserire solo quelli che si vuole modificare. L'inserimento di "bozza": false inserirà l'articolo nel sistema come pubblicato, andando a inserire una data di pubblicazione e di modifica. Una volta pubblicato non sarà più possibile modificare l'articolo. L'aggiornamento dell'articolo è consentito solo da parte del suo autore.

##### RESPONSE STATUS CODE: 
* 204: L'aggiornamento è andato a buon fine;
* 400: Uno dei parametri passati nel JDON in input non è corretto;
* 401: Un utente non loggato sta provando a modificare un articolo;
* 403: Un utente loggato sta provando a modificare un articolo di cui non è autore;
* 404: L'ID passato non corrisponde a nessun articolo;
* 418: L'utente sta cercando di modificare un articolo già pubblicato.

##### [DELETE] http://{hostname}:{port}/api/articolo/id
Questa API elimina dal DB l'articolo con identificativo id. L'eliminazione è consentita solo da parte dell'autore dell'articolo.

##### RESPONSE STATUS CODE: 
* 204: L'eliminazione è andata a buon fine;
* 401: Un utente non loggato sta provando ad eliminare un articolo;
* 403: Un utente loggato sta provando ad eliminare un articolo di cui non è autore;
* 404: L'ID passato non corrisponde a nessun articolo;
