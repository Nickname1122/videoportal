ALTER SESSION SET NLS_DATE_LANGUAGE = 'ENGLISH';
ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MM-YY';

CREATE TABLE "FELHASZNALO"(
                              "FELHASZNALONEV" VARCHAR2(30 BYTE) NOT NULL ENABLE,
                              "JELSZO" VARCHAR2(8 BYTE) NOT NULL ENABLE,
                              "EMAIL" VARCHAR2(100 BYTE) NOT NULL ENABLE,
                              "SZULETESI_DATUM" DATE NOT NULL ENABLE,
                              "BEJELENTKEZVE" NUMBER(1,0) DEFAULT 0 NOT NULL ENABLE,
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
CREATE SEQUENCE KOMMENT_AUTO_INCR
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE "VIDEO"(
                        "VIDEO_ID" NUMBER NOT NULL ENABLE,
                        "CIM" VARCHAR2(50 BYTE) NOT NULL ENABLE,
                        "MEGTEKINTESEK_SZAMA" NUMBER DEFAULT 0 NOT NULL ENABLE,
                        "KATEGORIA" VARCHAR2(30 BYTE) NOT NULL ENABLE,
                        "LEIRAS" VARCHAR2(50 BYTE),
                        "KEDVELESEK_SZAMA" NUMBER DEFAULT 0 NOT NULL ENABLE,
                        "FAJL_NEVE" VARCHAR2(300 BYTE) NOT NULL ENABLE,
                        "FELTOLTES_DATUMA" DATE NOT NULL ENABLE,
                        "STILL_KEP" VARCHAR2(500 BYTE) NOT NULL ENABLE,
                        "FELTOLTO_NEV" VARCHAR2(30 BYTE) NOT NULL ENABLE,
                        CONSTRAINT "VIDEO_PK" PRIMARY KEY ("VIDEO_ID") ENABLE
);
CREATE SEQUENCE VIDEO_AUTO_INCR
    START WITH 1
    INCREMENT BY 1;

/*CREATE TABLE "KERESES"(
                          "TAG" VARCHAR2(30 BYTE),
                          "FELHASZNALONEV" VARCHAR2(30 BYTE) NOT NULL ENABLE,
                          "VIDEO_ID" NUMBER NOT NULL ENABLE,
                          CONSTRAINT "KERESES_FK1" FOREIGN KEY ("FELHASZNALONEV") REFERENCES "FELHASZNALO" ("FELHASZNALONEV") ENABLE,
                          CONSTRAINT "KERESES_FK2" FOREIGN KEY ("VIDEO_ID") REFERENCES "VIDEO" ("VIDEO_ID") ENABLE
);*/

CREATE TABLE "MEGTEKINTES"(
                              "FELHASZNALONEV" VARCHAR2(30 BYTE),
                              "VIDEO_ID" NUMBER,
                              "VIDEO_KATEGORIA" VARCHAR2(30 BYTE),
                              CONSTRAINT "MEGTEKINTES_FK1" FOREIGN KEY ("FELHASZNALONEV") REFERENCES "FELHASZNALO" ("FELHASZNALONEV") ENABLE,
                              CONSTRAINT "MEGTEKINTES_FK2" FOREIGN KEY ("VIDEO_ID") REFERENCES "VIDEO" ("VIDEO_ID") ENABLE
);

CREATE TABLE "LEJATSZASI_LISTA"(
                                   "LISTA_ID" NUMBER NOT NULL ENABLE,
                                   "LISTA_CIME" VARCHAR2(50 BYTE) NOT NULL ENABLE,
                                   "FELHASZNALONEV" VARCHAR2(30 BYTE) NOT NULL ENABLE,
                                   "VIDEO_ID" NUMBER NOT NULL ENABLE,
                                   CONSTRAINT "LEJATSZASI_LISTA_PK" PRIMARY KEY ("LISTA_ID") ENABLE,
                                   CONSTRAINT "LEJATSZASI_LISTA_FK1" FOREIGN KEY ("FELHASZNALONEV") REFERENCES "FELHASZNALO" ("FELHASZNALONEV") ENABLE,
                                   CONSTRAINT "LEJATSZASI_LISTA_FK2" FOREIGN KEY ("VIDEO_ID") REFERENCES "VIDEO" ("VIDEO_ID") ENABLE
);

CREATE SEQUENCE LISTA_AUTO_INCR
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE "TILTAS_V"(
                           "VIDEO_ID" NUMBER NOT NULL ENABLE,
                           "TILTOTT_VIDEO" NUMBER(1,0) DEFAULT 1,
                           CONSTRAINT "TILTAS_V_FK1" FOREIGN KEY ("VIDEO_ID") REFERENCES "VIDEO" ("VIDEO_ID") ENABLE
);

CREATE TABLE "TILTAS_F"(
                           "FELHASZNALONEV" VARCHAR2(30 BYTE) NOT NULL ENABLE,
                           "TILTOTT_FELHASZNALO" NUMBER(1,0) DEFAULT 1,
                           CONSTRAINT "TILTAS_F_FK1" FOREIGN KEY ("FELHASZNALONEV") REFERENCES "FELHASZNALO" ("FELHASZNALONEV") ENABLE

);

CREATE TABLE "VIDEO_AJANLAS"(
                                "V_ID" NUMBER,
                                "V_CIME" VARCHAR2(30 BYTE)
);

ALTER TABLE "KOMMENT" ADD(
    "VIDEO_ID" NUMBER NOT NULL ENABLE,
    CONSTRAINT "KOMMENT_FK1" FOREIGN KEY ("VIDEO_ID") REFERENCES "VIDEO" ("VIDEO_ID") ENABLE
    );

create or replace TRIGGER T_LISTA_ID
    BEFORE INSERT ON LEJATSZASI_LISTA
    FOR EACH ROW
BEGIN
    IF :new.LISTA_ID IS NULL THEN
        :new.LISTA_ID := LISTA_AUTO_INCR.nextval;
    END IF;
END;
CREATE OR REPLACE TRIGGER T_VIDEO_ID
    BEFORE INSERT ON VIDEO
    FOR EACH ROW
BEGIN
    IF :new.VIDEO_ID IS NULL THEN
        :new.VIDEO_ID := VIDEO_AUTO_INCR.nextval;
    END IF;
END;
/
ALTER TRIGGER "T_VIDEO_ID" ENABLE;


CREATE OR REPLACE TRIGGER T_KOMMENT_ID
    BEFORE INSERT ON KOMMENT
    FOR EACH ROW
BEGIN
    IF :new.KOMMENT_ID IS NULL THEN
        :new.KOMMENT_ID := KOMMENT_AUTO_INCR.nextval;
    END IF;
END;
/
ALTER TRIGGER "T_KOMMENT_ID" ENABLE;


create or replace TRIGGER ajanlastlistaz
    AFTER INSERT
    ON VIDEO
DECLARE
    CURSOR ajanlasKurzor IS
        SELECT video_id, cim FROM (select video_id, cim from video order by feltoltes_datuma DESC)
        Where ROWNUM <=3;
BEGIN
    DELETE FROM VIDEO_AJANLAS;
    FOR i IN ajanlasKurzor
        LOOP
            INSERT INTO VIDEO_AJANLAS(v_id, v_cime) VALUES (i.video_id, i.cim);
            EXIT WHEN SQL%NOTFOUND;
        END LOOP;
END;


create or replace TRIGGER  felhasznaloMegallapit
    AFTER INSERT ON VIDEO
    FOR EACH ROW
    WHEN (NEW.video_id > 0)
BEGIN
    DBMS_OUTPUT.PUT_LINE('Uj feltoltes: ' || :NEW.feltolto_nev);
END;

CREATE OR REPLACE TRIGGER lejatszasilistatorles
    BEFORE DELETE ON FELHASZNALO
    FOR EACH ROW
BEGIN
    DELETE FROM LEJATSZASI_LISTA
    WHERE FELHASZNALONEV = :OLD.FELHASZNALONEV;
END;

create or replace TRIGGER lejatszasilistatorles
    BEFORE DELETE ON FELHASZNALO
    FOR EACH ROW
BEGIN
    DELETE FROM LEJATSZASI_LISTA
    WHERE FELHASZNALONEV = :OLD.FELHASZNALONEV;
END;

create or replace TRIGGER  videoTorol
    BEFORE DELETE ON VIDEO
    FOR EACH ROW
BEGIN
    DELETE FROM KOMMENT WHERE VIDEO_ID = VIDEO_ID;
    DELETE FROM LEJATSZASI_LISTA WHERE VIDEO_ID = VIDEO_ID;
END;


create or replace PROCEDURE tovabbi(videoid IN video.video_id%type)
AS
    v_felhasznalo VARCHAR2(30);
    CURSOR ajanlasKurzor IS
        SELECT video_id, cim, feltolto_nev FROM video;
BEGIN
    SELECT feltolto_nev INTO v_felhasznalo FROM video where video.video_id LIKE videoid;
    FOR k IN ajanlasKurzor LOOP
            IF k.feltolto_nev = v_felhasznalo THEN
                dbms_output.put_line(k.feltolto_nev  || ' további videói: ' ||  k.video_id || ', ' || k.cim);
            END IF;
            EXIT WHEN ajanlasKurzor%NOTFOUND;
        END LOOP;
END;


create or replace PROCEDURE hasonlo(felh_nev IN felhasznalo.felhasznalonev%type)
AS
    max_sz NUMBER;
    CURSOR kategoriaKurzor IS
        SELECT video_id, cim, kategoria FROM VIDEO;
    CURSOR videoKurzor IS
        SELECT  video_kategoria, (COUNT(video_kategoria)) AS szam FROM MEGTEKINTES WHERE felhasznalonev = felh_nev GROUP BY video_kategoria;
BEGIN
    SELECT MAX(COUNT(video_kategoria)) INTO max_sz FROM MEGTEKINTES GROUP BY video_kategoria;

    FOR k IN kategoriaKurzor LOOP
            FOR v IN videoKurzor LOOP
                    IF v.szam = max_sz AND k.kategoria = v.video_kategoria
                    THEN
                        dbms_output.put_line( k.video_id || k.cim);
                    END IF;
                    EXIT WHEN videoKurzor%NOTFOUND;
                    EXIT WHEN kategoriaKurzor%NOTFOUND;
                END LOOP;
        END LOOP;

END;

--pelda adatok
--a tobbi adat a lokalis adatbazisban talalhato, csatolt kep rola a dokumentacioban
INSERT INTO "HOMEUSER"."FELHASZNALO" (FELHASZNALONEV, JELSZO, EMAIL, SZULETESI_DATUM) VALUES ('Zsolt', 'asd', 'zsolt@gmail.com', TO_DATE('1989-04-15 11:23:53', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "HOMEUSER"."VIDEO" (CIM, KATEGORIA, LEIRAS, FAJL_NEVE, FELTOLTES_DATUMA, STILL_KEP, FELTOLTO_NEV) VALUES ('Cica', 'cicas', 'Cuki cica', 'cat', TO_DATE('2020-05-02 11:35:16', 'YYYY-MM-DD HH24:MI:SS'), 'cica', 'Zsolt');
INSERT INTO "HOMEUSER"."KOMMENT"(FELHASZNALONEV, KOMMENT_ID, KOMMENT_TARTALOM, VIDEO_ID) VALUES ('Zsolt', '28', 'Jó videó', '100');
INSERT INTO "HOMEUSER"."ADMIN"(ADMIN_ID, FELHASZNALONEV) VALUES ('11', 'Levente');
INSERT INTO "HOMEUSER"."LEJATSZASI_LISTA"(VIDEOK_SZAMA, LISTA_CIME, FELHASZNALONEV, VIDEO_ID) VALUES ('1', 'Vicces videok', 'Zsolt', '100');


ALTER SESSION SET NLS_DATE_LANGUAGE = 'HUNGARIAN';
ALTER SESSION SET NLS_DATE_FORMAT = 'YY-MM-DD';