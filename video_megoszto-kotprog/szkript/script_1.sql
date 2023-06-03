
ALTER SESSION SET NLS_DATE_LANGUAGE = 'ENGLISH';
ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YY';

CREATE TABLE "FELHASZNALO"(
	"FELHASZNALONEV" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"JELSZO" VARCHAR2(8 BYTE) NOT NULL ENABLE, 
	"EMAIL" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	"SZULETESI_DATUM" DATE NOT NULL ENABLE, 
	"TILTOTT_FELHASZNALO" NUMBER(1,0) DEFAULT 0 NOT NULL ENABLE, 
	CONSTRAINT "FELHASZNALO_PK" PRIMARY KEY ("FELHASZNALONEV") ENABLE
);


CREATE TABLE "ADMIN"(
	"ADMIN_ID" NUMBER NOT NULL ENABLE, 
	"FELHASZNALONEV" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	CONSTRAINT "ADMIN_UK1" UNIQUE ("ADMIN_ID") ENABLE, 
	CONSTRAINT "ADMIN_FK1" FOREIGN KEY ("FELHASZNALONEV") REFERENCES "FELHASZNALO" ("FELHASZNALONEV") ENABLE
);

CREATE TABLE "KOMMENT"(
	"FELHASZNALONEV" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"KOMMENT_ID" NUMBER NOT NULL ENABLE, 
	"KOMMENT_TARTALOM" VARCHAR2(100 BYTE), 
	CONSTRAINT "KOMMENT_PK" PRIMARY KEY ("KOMMENT_ID") ENABLE, 
	CONSTRAINT "KOMMENT_FK2" FOREIGN KEY ("FELHASZNALONEV") REFERENCES "FELHASZNALO" ("FELHASZNALONEV") ENABLE
);

CREATE TABLE "VIDEO"(
	"VIDEO_ID" NUMBER NOT NULL ENABLE, 
	"CIM" VARCHAR2(50 BYTE) NOT NULL ENABLE, 
	"MEGTEKINTESEK_SZAMA" NUMBER DEFAULT 0 NOT NULL ENABLE, 
	"KATEGORIA" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"LEIRAS" VARCHAR2(50 BYTE), 
	"KEDVELESEK_SZAMA" NUMBER DEFAULT 0 NOT NULL ENABLE, 
	"TILTOTT_VIDEO" NUMBER(1,0) DEFAULT 0 NOT NULL ENABLE,
	"FAJL_NEVE" VARCHAR2(300) NOT NULL ENABLE,
	CONSTRAINT "VIDEO_PK" PRIMARY KEY ("VIDEO_ID") ENABLE
);

CREATE TABLE "KERESES"(
	"TAG" VARCHAR2(30 BYTE), 
	"FELHASZNALONEV" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"VIDEO_ID" NUMBER NOT NULL ENABLE, 
	CONSTRAINT "KERESES_FK1" FOREIGN KEY ("FELHASZNALONEV") REFERENCES "FELHASZNALO" ("FELHASZNALONEV") ENABLE, 
	CONSTRAINT "KERESES_FK2" FOREIGN KEY ("VIDEO_ID") REFERENCES "VIDEO" ("VIDEO_ID") ENABLE
);

CREATE TABLE "MEGTEKINTES"(
	"FELHASZNALONEV" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"VIDEO_ID" NUMBER NOT NULL ENABLE, 

	CONSTRAINT "MEGTEKINTES_FK1" FOREIGN KEY ("FELHASZNALONEV") REFERENCES "FELHASZNALO" ("FELHASZNALONEV") ENABLE, 
	CONSTRAINT "MEGTEKINTES_FK2" FOREIGN KEY ("VIDEO_ID") REFERENCES "VIDEO" ("VIDEO_ID") ENABLE
);

CREATE TABLE "LEJATSZASI_LISTA"(
	"VIDEOK_SZAMA" NUMBER DEFAULT 0, 
	"LISTA_CIME" VARCHAR2(50 BYTE) NOT NULL ENABLE, 
	"FELHASZNALONEV" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"VIDEO_ID" NUMBER NOT NULL ENABLE, 
	CONSTRAINT "LEJATSZASI_LISTA_PK" PRIMARY KEY ("LISTA_CIME") ENABLE, 
	CONSTRAINT "LEJATSZASI_LISTA_FK1" FOREIGN KEY ("FELHASZNALONEV") REFERENCES "FELHASZNALO" ("FELHASZNALONEV") ENABLE, 
	CONSTRAINT "LEJATSZASI_LISTA_FK2" FOREIGN KEY ("VIDEO_ID") REFERENCES "VIDEO" ("VIDEO_ID") ENABLE
);

CREATE TABLE "TILTAS_V"(
	"VIDEO_ID" NUMBER NOT NULL ENABLE, 
	"TILTOTT_VIDEO" NUMBER(1,0) DEFAULT 1, 
	CONSTRAINT "TILTAS_V_FK1" FOREIGN KEY ("VIDEO_ID") REFERENCES "VIDEO" ("VIDEO_ID") ENABLE
);

CREATE TABLE "TILTAS_F"(
	"FELHASZNALONEV" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"TILTOTT_VIDEO" NUMBER(1,0) DEFAULT 1,
	CONSTRAINT "TILTAS_F_FK1" FOREIGN KEY ("FELHASZNALONEV") REFERENCES "FELHASZNALO" ("FELHASZNALONEV") ENABLE

);

CREATE TABLE "FELTOLTES"(
	"FELHASZNALONEV" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"VIDEO_ID" NUMBER NOT NULL ENABLE, 
	"FELTOLTES_DATUMA" DATE NOT NULL ENABLE, 
	CONSTRAINT "FELTOLTES_FK1" FOREIGN KEY ("FELHASZNALONEV") REFERENCES "FELHASZNALO" ("FELHASZNALONEV") ENABLE, 
	CONSTRAINT "FELTOLTES_FK2" FOREIGN KEY ("VIDEO_ID") REFERENCES "VIDEO" ("VIDEO_ID") ENABLE
);

ALTER TABLE "VIDEO" ADD(
	"KOMMENT_ID" NUMBER NOT NULL ENABLE,
	CONSTRAINT "VIDEO_FK1" FOREIGN KEY ("KOMMENT_ID") REFERENCES "KOMMENT" ("KOMMENT_ID") ENABLE
);

ALTER TABLE "KOMMENT" ADD(
	"VIDEO_ID" NUMBER NOT NULL ENABLE,
	CONSTRAINT "KOMMENT_FK1" FOREIGN KEY ("VIDEO_ID") REFERENCES "VIDEO" ("VIDEO_ID") ENABLE
);

CREATE SEQUENCE KOMMENT_AUTO_INCR 
START WITH 1
INCREMENT BY 1;

create or replace TRIGGER komment_trigger
BEFORE INSERT ON komment
FOR EACH ROW
BEGIN
:new.komment_id := KOMMENT_AUTO_INCR.nextval;
END;

CREATE SEQUENCE VIDEO_AUTO_INCR
START WITH 1
INCREMENT BY 1;

create or replace TRIGGER video_trigger
BEFORE INSERT ON VIDEO
FOR EACH ROW
BEGIN
:new.video_id := VIDEO_AUTO_INCR.nextval;
END;

ALTER SESSION SET NLS_DATE_LANGUAGE = 'HUNGARIAN';
ALTER SESSION SET NLS_DATE_FORMAT = 'YY-MM-DD';