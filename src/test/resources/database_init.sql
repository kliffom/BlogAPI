

-- Droppo le tabelle per avere gli id che ripartono da 0 per poter eseguire i test

-- DROP TABLE users;
-- DROP TABLE categoria;
-- DROP TABLE articoli;
-- DROP TABLE tag;
-- DROP TABLE articoli_tag;

-- Droppo le tabelle per avere gli id che ripartono da 0 per poter eseguire i test

DROP DATABASE BlogTest;

CREATE DATABASE BlogTest;

USE BlogTest;

CREATE TABLE IF NOT EXISTS users (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  password varchar(100) NOT NULL,
  PRIMARY KEY (id)
);


-- Creazione tabella categoria
CREATE TABLE IF NOT EXISTS categoria (
	descrizione varchar(50) NOT NULL,
	CONSTRAINT categoria_PK PRIMARY KEY (descrizione)
);

-- Creazione tabella articoli

CREATE TABLE IF NOT EXISTS articoli (
	id BIGINT UNSIGNED auto_increment NOT NULL,
	titolo varchar(150) NOT NULL,
	sottotitolo varchar(300) NULL,
	testo TEXT NOT NULL,
	desc_categoria varchar(50) NOT NULL,
	id_users BIGINT UNSIGNED NOT NULL,
	bozza BOOL DEFAULT true NOT NULL,
	data_creazione DATETIME NOT NULL,
	data_pubblicazione DATETIME NULL,
	data_modifica DATETIME NULL,
	CONSTRAINT NewTable_PK PRIMARY KEY (id),
	CONSTRAINT articoli__users_FK FOREIGN KEY (id_users) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT articoli_categoria_FK FOREIGN KEY (desc_categoria) REFERENCES categoria(descrizione) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Aggiunta FK per tabella articoli

-- ALTER TABLE articoli ADD CONSTRAINT articoli__users_FK FOREIGN KEY (id_users) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE;
-- ALTER TABLE articoli ADD CONSTRAINT articoli_categoria_FK FOREIGN KEY (desc_categoria) REFERENCES categoria(descrizione) ON DELETE SET NULL ON UPDATE CASCADE;

-- Creazione tabella tag
CREATE TABLE IF NOT EXISTS tag (
	nome varchar(50) NOT NULL,
	CONSTRAINT tag_PK PRIMARY KEY (nome)
);

-- Creazione join table articoli_tag
CREATE TABLE IF NOT EXISTS articoli_tag (
	nome_tag varchar(50) NOT NULL,
	id_articolo BIGINT UNSIGNED NOT NULL,
	CONSTRAINT articoli_tag_PK PRIMARY KEY (nome_tag,id_articolo),
	CONSTRAINT articoli_tag_FK FOREIGN KEY (id_articolo) REFERENCES articoli(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT articoli_tag_FK_1 FOREIGN KEY (nome_tag) REFERENCES tag(nome) ON DELETE CASCADE ON UPDATE CASCADE
);

-- INSERIMENTI DA UTILIZZARE PER IL TEST 
-- Gli inserimenti sono commentati perché vengono gestiti dalla classe TestDbInit

--
--
-- ddinuzzo/password02
-- INSERT INTO users
-- (username, password)
-- VALUES('ddinuzzo', '$2a$10$vj3PqvSqQSsLhknZpxU2oOIUOdmm6cpPu1shwcyXHVzba.xBWLe4K'),
-- ('pangaro', '$2a$10$OQHlM4KBaY4M4beq/S33JeWpgqj0uLBGn9KAxazHvGcYmZ/BEyjvO');

-- Inserimento categorie
-- INSERT INTO categoria (descrizione)
-- VALUES ('Tecnologia'),
-- ('Musica');

-- Inserimento tags
-- INSERT INTO tag (nome)
-- VALUES ('Programmazione'),
-- ('Concerto'),
-- ('Palco');

-- Inserimento articoli
-- INSERT INTO articoli (titolo, sottotitolo, testo, desc_categoria, id_users, bozza, data_creazione, data_pubblicazione, data_modifica) 
-- VALUES ('Articolo1', 'Sottotitolo1', 'Testo articolo 1', 'Tecnologia', 1, 1, '2021-06-15 16:31:45.0', NULL, NULL),
-- 	 ('Articolo2', 'Sottotitolo2', 'Testo articolo 2', 'Tecnologia', 2, 1, '2021-06-15 16:32:45.0', NULL, NULL),
--	 ('Articolo3', 'Sottotitolo3', 'Testo articolo 3', 'Musica', 2, 1, '2021-06-15 16:33:45.0', NULL, NULL);
	 
-- Inserimento tag su articoli
-- INSERT INTO articoli_tag (nome_tag, id_articolo)
-- VALUES ('Programmazione', 1),
-- ('Concerto', 1),
-- ('Concerto', 2),
-- ('Palco', 2);

