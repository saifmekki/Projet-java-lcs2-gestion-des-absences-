-- Creating database
create database projetjava;

use projetjava;

CREATE TABLE Responsable(id_responsable int primary key, login varchar(25), nom varchar(25),prenom varchar(25), password varchar(25), prio int);

CREATE TABLE Class(id_classe int primary key, libelle varchar(25),niveau int,filière varchar(25));

CREATE TABLE Etudiant(id_etudiant int primary key,login varchar(25), nom varchar(25),prenom varchar(25),password varchar(25), class varchar(10));

CREATE TABLE Enseignant(id_enseignant int primary key, nom varchar(25) , prenom varchar(25), login varchar(25), password varchar(25));
CREATE TABLE Absence(id_etudiant int,id_enseignant int ,dt date, status varchar(15), class varchar(15));
Create Table Matière(id_matiere int,libelle varchar(25));
Create table Correspondance(id_enseignant int,id_matiere int,id_classe int);
INSERT INTO Responsable VALUES(1, 'admin', 'Admin', 'admin','admin', 1);
