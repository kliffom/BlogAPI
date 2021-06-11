-- Creazione DB
CREATE DATABASE IF NOT EXISTS Blog;

-- Creazione tabella users
CREATE TABLE users (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  password varchar(100) NOT NULL,
  PRIMARY KEY (id)
);

-- Creazione tabella articoli
-- Le FK id_categoria e id_users sono NULL in modo da poter eseguire 'ON DELETE SET NULL' 
-- e non perdere l'articolo alla cancellazione di autore o categoria
CREATE TABLE articoli (
	id BIGINT UNSIGNED auto_increment NOT NULL,
	titolo varchar(150) NOT NULL,
	sottotitolo varchar(300) NULL,
	testo TEXT NOT NULL,
	desc_categoria varchar(50) UNSIGNED NULL,
	id_users BIGINT UNSIGNED NULL,
	bozza BOOL DEFAULT true NOT NULL,
	data_creazione DATETIME NOT NULL,
	data_pubblicazione DATETIME NULL,
	data_modifica DATETIME NULL,
	CONSTRAINT NewTable_PK PRIMARY KEY (id)
);

-- Creazione tabella categoria
CREATE TABLE categoria (
	descrizione varchar(50) NOT NULL,
	CONSTRAINT categoria_PK PRIMARY KEY (descrizione)
);

-- Aggiunta FK per tabella articoli
ALTER TABLE articoli ADD CONSTRAINT articoli__users_FK FOREIGN KEY (id_users) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE;
ALTER TABLE articoli ADD CONSTRAINT articoli_categoria_FK FOREIGN KEY (desc_categoria) REFERENCES categoria(descrizione) ON DELETE SET NULL ON UPDATE CASCADE;

-- Creazione tabella tag
CREATE TABLE tag (
	nome varchar(50) NOT NULL,
	CONSTRAINT tag_PK PRIMARY KEY (nome)
);

-- Creazione join table articoli_tag
CREATE TABLE articoli_tag (
	nome_tag varchar(50) NOT NULL,
	id_articolo BIGINT UNSIGNED NOT NULL,
	CONSTRAINT articoli_tag_PK PRIMARY KEY (nome_tag,id_articolo),
	CONSTRAINT articoli_tag_FK FOREIGN KEY (id_articolo) REFERENCES Blog.articoli(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT articoli_tag_FK_1 FOREIGN KEY (nome_tag) REFERENCES Blog.tag(nome) ON DELETE CASCADE ON UPDATE CASCADE
)