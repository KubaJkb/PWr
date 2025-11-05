
-- USUWANIE TABEL 

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Wrogowie_kocurow CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE Bandy CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE Kocury CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE Funkcje CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE Wrogowie CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/


-- TWORZENIE TABEL 

CREATE TABLE Funkcje(
    funkcja VARCHAR2(10) CONSTRAINT funkcje_pk PRIMARY KEY,
    min_myszy NUMBER(3) CONSTRAINT fu_min_values CHECK(min_myszy > 5) DEFERRABLE INITIALLY DEFERRED,
    max_myszy NUMBER(3) CONSTRAINT fu_max_values CHECK(200 > max_myszy) DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT fu_min_lq_max_values CHECK(min_myszy <= max_myszy) DEFERRABLE INITIALLY DEFERRED
);

CREATE TABLE Wrogowie(
    imie_wroga VARCHAR2(15) CONSTRAINT wrogowie_pk PRIMARY KEY,
    stopien_wrogosci NUMBER(2) CONSTRAINT wr_st_ch CHECK(stopien_wrogosci BETWEEN 1 AND 10) DEFERRABLE INITIALLY DEFERRED,
    gatunek VARCHAR2(15),
    lapowka VARCHAR2(20)
);

CREATE TABLE Bandy(
    nr_bandy NUMBER(2) CONSTRAINT bandy_pk PRIMARY KEY,
    nazwa VARCHAR2(20) CONSTRAINT ba_na_nn NOT NULL,
    teren VARCHAR2(15) CONSTRAINT ba_te_un UNIQUE,
    szef_bandy VARCHAR2(15) CONSTRAINT ba_sz_un UNIQUE
);

CREATE TABLE Kocury(
    imie VARCHAR2(15) CONSTRAINT ko_im_nn NOT NULL,
    plec VARCHAR2(1) CONSTRAINT ko_pl_ch CHECK(plec IN ('M','D')) DEFERRABLE INITIALLY DEFERRED,
    pseudo VARCHAR2(15) CONSTRAINT kocury_pk PRIMARY KEY,
    funkcja VARCHAR2(10) CONSTRAINT ko_fu_fk REFERENCES Funkcje(funkcja) DEFERRABLE INITIALLY DEFERRED,
    szef VARCHAR2(15) CONSTRAINT ko_sz_fk REFERENCES Kocury(pseudo) DEFERRABLE INITIALLY DEFERRED,
    w_stadku_od DATE DEFAULT SYSDATE,
    przydzial_myszy NUMBER(3),
    myszy_extra NUMBER(3),
    nr_bandy NUMBER(2) CONSTRAINT ko_nr_ba_fk REFERENCES Bandy(nr_bandy) DEFERRABLE INITIALLY DEFERRED
);

ALTER TABLE Bandy ADD CONSTRAINT ba_sz_fk FOREIGN KEY (szef_bandy) REFERENCES Kocury(pseudo) DEFERRABLE INITIALLY DEFERRED;

CREATE TABLE Wrogowie_kocurow(
    pseudo VARCHAR2(15) CONSTRAINT wr_ko_ps_fk REFERENCES Kocury(pseudo) DEFERRABLE INITIALLY DEFERRED,
    imie_wroga VARCHAR2(15) CONSTRAINT wr_ko_im_fk REFERENCES Wrogowie(imie_wroga) DEFERRABLE INITIALLY DEFERRED,
    data_incydentu DATE CONSTRAINT wr_ko_da_nn NOT NULL,
    opis_incydentu VARCHAR2(50),
    CONSTRAINT wr_ko_pk PRIMARY KEY(pseudo, imie_wroga)
);

ALTER SESSION SET CONSTRAINTS = DEFERRED;


-- WYPEŁNIANIE DANYCH 

ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD';

INSERT ALL
INTO Funkcje VALUES('SZEFUNIO',90,110)
INTO Funkcje VALUES('BANDZIOR',70,90)
INTO Funkcje VALUES('LOWCZY',60,70)
INTO Funkcje VALUES('LAPACZ',50,60)
INTO Funkcje VALUES('KOT',40,50)
INTO Funkcje VALUES('MILUSIA',20,30)
INTO Funkcje VALUES('DZIELCZY',45,55)
INTO Funkcje VALUES('HONOROWA',6,25)
SELECT * FROM Dual;

INSERT ALL
INTO Bandy VALUES (1,'SZEFOSTWO','CALOSC','TYGRYS')
INTO Bandy VALUES (2,'CZARNI RYCERZE','POLE','LYSY')
INTO Bandy VALUES (3,'BIALI LOWCY','SAD','ZOMBI')
INTO Bandy VALUES (4,'LACIACI MYSLIWI','GORKA','RAFA')
INTO Bandy VALUES (5,'ROCKERSI','ZAGRODA',NULL)
SELECT * FROM dual;

INSERT ALL
INTO Kocury VALUES ('JACEK','M','PLACEK','LOWCZY','LYSY','2008-12-01',67,NULL,2)
INTO Kocury VALUES ('BARI','M','RURA','LAPACZ','LYSY','2009-09-01',56,NULL,2)
INTO Kocury VALUES ('MICKA','D','LOLA','MILUSIA','TYGRYS','2009-10-14',25,47,1)
INTO Kocury VALUES ('LUCEK','M','ZERO','KOT','KURKA','2010-03-01',43,NULL,3)
INTO Kocury VALUES ('SONIA','D','PUSZYSTA','MILUSIA','ZOMBI','2010-11-18',20,35,3)
INTO Kocury VALUES ('LATKA','D','UCHO','KOT','RAFA','2011-01-01',40,NULL,4)
INTO Kocury VALUES ('DUDEK','M','MALY','KOT','RAFA','2011-05-15',40,NULL,4)
INTO Kocury VALUES ('MRUCZEK','M','TYGRYS','SZEFUNIO',NULL,'2002-01-01',103,33,1)
INTO Kocury VALUES ('CHYTRY','M','BOLEK','DZIELCZY','TYGRYS','2002-05-05',50,NULL,1)
INTO Kocury VALUES ('KOREK','M','ZOMBI','BANDZIOR','TYGRYS','2004-03-16',75,13,3)
INTO Kocury VALUES ('BOLEK','M','LYSY','BANDZIOR','TYGRYS','2006-08-15',72,21,2)
INTO Kocury VALUES ('ZUZIA','D','SZYBKA','LOWCZY','LYSY','2006-07-21',65,NULL,2)
INTO Kocury VALUES ('RUDA','D','MALA','MILUSIA','TYGRYS','2006-09-17',22,42,1)
INTO Kocury VALUES ('PUCEK','M','RAFA','LOWCZY','TYGRYS','2006-10-15',65,NULL,4)
INTO Kocury VALUES ('PUNIA','D','KURKA','LOWCZY','ZOMBI','2008-01-01',61,NULL,3)
INTO Kocury VALUES ('BELA','D','LASKA','MILUSIA','LYSY','2008-02-01',24,28,2)
INTO Kocury VALUES ('KSAWERY','M','MAN','LAPACZ','RAFA','2008-07-12',51,NULL,4)
INTO Kocury VALUES ('MELA','D','DAMA','LAPACZ','RAFA','2008-11-01',51,NULL,4)
SELECT * FROM dual;

INSERT ALL
INTO Wrogowie VALUES('KAZIO',10,'CZLOWIEK','FLASZKA')
INTO Wrogowie VALUES('GLUPIA ZOSKA',1,'CZLOWIEK','KORALIK')
INTO Wrogowie VALUES('SWAWOLNY DYZIO',7,'CZLOWIEK','GUMA DO ZUCIA')
INTO Wrogowie VALUES('BUREK',4,'PIES','KOSC')
INTO Wrogowie VALUES('DZIKI BILL',10,'PIES',NULL)
INTO Wrogowie VALUES('REKSIO',2,'PIES','KOSC')
INTO Wrogowie VALUES('BETHOVEN',1,'PIES','PEDIGRIPALL')
INTO Wrogowie VALUES('CHYTRUSEK',5,'LIS','KURCZAK')
INTO Wrogowie VALUES('SMUKLA',1,'SOSNA',NULL)
INTO Wrogowie VALUES('BAZYLI',3,'KOGUT','KURA DO STADA')
SELECT * FROM dual;

INSERT ALL
INTO Wrogowie_kocurow VALUES('TYGRYS','KAZIO','2004-10-13','USILOWAL NABIC NA WIDLY')
INTO Wrogowie_kocurow VALUES('ZOMBI','SWAWOLNY DYZIO','2005-03-07','WYBIL OKO Z PROCY')
INTO Wrogowie_kocurow VALUES('BOLEK','KAZIO','2005-03-29','POSZCZUL BURKIEM')
INTO Wrogowie_kocurow VALUES('SZYBKA','GLUPIA ZOSKA','2006-09-12','UZYLA KOTA JAKO SCIERKI')
INTO Wrogowie_kocurow VALUES('MALA','CHYTRUSEK','2007-03-07','ZALECAL SIE')
INTO Wrogowie_kocurow VALUES('TYGRYS','DZIKI BILL','2007-06-12','USILOWAL POZBAWIC ZYCIA')
INTO Wrogowie_kocurow VALUES('BOLEK','DZIKI BILL','2007-11-10','ODGRYZL UCHO')
INTO Wrogowie_kocurow VALUES('LASKA','DZIKI BILL','2008-12-12','POGRYZL ZE LEDWO SIE WYLIZALA')
INTO Wrogowie_kocurow VALUES('LASKA','KAZIO','2009-01-07','ZLAPAL ZA OGON I ZROBIL WIATRAK')
INTO Wrogowie_kocurow VALUES('DAMA','KAZIO','2009-02-07','CHCIAL OBEDRZEC ZE SKORY')
INTO Wrogowie_kocurow VALUES('MAN','REKSIO','2009-04-14','WYJATKOWO NIEGRZECZNIE OBSZCZEKAL')
INTO Wrogowie_kocurow VALUES('LYSY','BETHOVEN','2009-05-11','NIE PODZIELIL SIE SWOJA KASZA')
INTO Wrogowie_kocurow VALUES('RURA','DZIKI BILL','2009-09-03','ODGRYZL OGON')
INTO Wrogowie_kocurow VALUES('PLACEK','BAZYLI','2010-07-12','DZIOBIAC UNIEMOZLIWIL PODEBRANIE KURCZAKA')
INTO Wrogowie_kocurow VALUES('PUSZYSTA','SMUKLA','2010-11-19','OBRZUCILA SZYSZKAMI')
INTO Wrogowie_kocurow VALUES('KURKA','BUREK','2010-12-14','POGONIL')
INTO Wrogowie_kocurow VALUES('MALY','CHYTRUSEK','2011-07-13','PODEBRAL PODEBRANE JAJKA')
INTO Wrogowie_kocurow VALUES('UCHO','SWAWOLNY DYZIO','2011-07-14','OBRZUCIL KAMIENIAMI')
SELECT * FROM dual;

COMMIT;


SELECT * FROM Bandy;
SELECT * FROM Wrogowie;
SELECT * FROM Kocury;
SELECT * FROM Funkcje;
SELECT * FROM Wrogowie_kocurow;


--zad1
SELECT imie_wroga "WROG", opis_incydentu "PRZEWINA" 
FROM Wrogowie_kocurow
WHERE EXTRACT(YEAR FROM data_incydentu)=2009; 

--zad2
SELECT imie, funkcja, TO_CHAR(w_stadku_od, 'YYYY-MM-DD') "Z NAMI OD" 
FROM Kocury
WHERE plec='D' AND w_stadku_od BETWEEN '2005-09-01' AND '2007-07-31';

--zad3
SELECT imie_wroga "WROG", gatunek, stopien_wrogosci 
FROM Wrogowie
WHERE lapowka IS NULL
ORDER BY stopien_wrogosci;

--zad4
SELECT imie || ' zwany ' || pseudo || ' (fun. '|| funkcja ||') lowi myszki w bandzie ' ||nr_bandy||' od '|| TO_CHAR(w_stadku_od, 'YYYY-MM-DD') "WSZYSTKO O KOCURACH"
FROM Kocury
WHERE plec='M'
ORDER BY w_stadku_od DESC, pseudo;

--zad5 
SELECT pseudo, REGEXP_REPLACE(REGEXP_REPLACE(pseudo,'A','#',1,1),'L','%',1,1) "Po wymianie A na # oraz L na %" 
FROM KOCURY
WHERE pseudo LIKE '%A%' AND pseudo LIKE '%L%';

--zad6
SELECT 
    imie,
    TO_CHAR(w_stadku_od, 'YYYY-MM-DD') AS "W stadku",
    ROUND(przydzial_myszy / 1.1) AS "Zjadal",
    TO_CHAR(ADD_MONTHS(w_stadku_od, 6), 'YYYY-MM-DD') AS "Podwyzka",
    przydzial_myszy AS "Zjada"
FROM Kocury
WHERE MONTHS_BETWEEN(DATE '2024-07-17', w_stadku_od) >= 12 * 15   
AND TO_NUMBER(TO_CHAR(w_stadku_od, 'MM')) BETWEEN 3 AND 9;  

        
--zad7
SELECT 
    imie, 
    NVL(przydzial_myszy*3,0) "MYSZY KWARTALNIE", 
    NVL(myszy_extra*3,0) "KWARTALNE DODATKI" 
FROM Kocury
WHERE przydzial_myszy > (2 * NVL(myszy_extra, 0)) 
AND przydzial_myszy >= 55;


--zad8
SELECT 
    imie, 
    DECODE(SIGN((12 * (NVL(przydzial_myszy,0) + NVL(myszy_extra, 0))) - 660),
          -1, 'Ponizej 660',
          0, 'Limit',
          12 * (NVL(przydzial_myszy,0) + NVL(myszy_extra, 0))
    ) "Zjada rocznie"
FROM Kocury;


--zad9 
SELECT pseudo || ' - ' || CASE COUNT(pseudo) 
    WHEN 1 
    THEN 'Unikalny'
    ELSE 'nieunikalny' 
    END "Unikalnosc atr. PSEUDO"
FROM Kocury
GROUP BY pseudo;

SELECT szef || ' - ' || CASE COUNT(pseudo)
    WHEN  1
    THEN 'Unikalny'
    ELSE 'nieunikalny' 
    END "Unikalnosc atr. SZEF"
FROM Kocury
WHERE szef IS NOT NULL
GROUP BY szef;


--zad10
SELECT pseudo "Pseudonim", COUNT(imie_wroga) "Liczba wrogow"
FROM wrogowie_kocurow 
GROUP BY pseudo
HAVING COUNT(imie_wroga)>=2;


--zad11 
SELECT pseudo, TO_CHAR(w_stadku_od, 'YYYY-MM-DD') "W STADKU",
    CASE
        WHEN NEXT_DAY(LAST_DAY('2024-10-29')-7,3) >= '2024-10-29' THEN
            CASE
                WHEN (EXTRACT (DAY FROM w_stadku_od) <= 15)
                THEN TO_CHAR(NEXT_DAY(LAST_DAY('2024-10-29')-7, 3), 'YYYY-MM-DD')
                ELSE TO_CHAR(NEXT_DAY(LAST_DAY(ADD_MONTHS('2024-10-29',1))-7, 3), 'YYYY-MM-DD')
                END
        ELSE TO_CHAR(NEXT_DAY(LAST_DAY(ADD_MONTHS('2024-10-29',1))-7,3), 'YYYY-MM-DD')
        END "Wyplata"
FROM Kocury
ORDER BY w_stadku_od;

SELECT pseudo, TO_CHAR(w_stadku_od, 'YYYY-MM-DD') "W STADKU",
    CASE
        WHEN NEXT_DAY(LAST_DAY('2024-10-31')-7,3) >= '2024-10-31' THEN
            CASE
                WHEN (EXTRACT (DAY FROM w_stadku_od) <= 15)
                THEN TO_CHAR(NEXT_DAY(LAST_DAY('2024-10-31')-7, 3), 'YYYY-MM-DD')
                ELSE TO_CHAR(NEXT_DAY(LAST_DAY(ADD_MONTHS('2024-10-31',1))-7, 3), 'YYYY-MM-DD')
                END
        ELSE TO_CHAR(NEXT_DAY(LAST_DAY(ADD_MONTHS('2024-10-31',1))-7,3), 'YYYY-MM-DD')
        END "Wyplata"
FROM Kocury
ORDER BY w_stadku_od;


--zad12
SELECT 'Liczba kotow = ' || COUNT(pseudo) ||
       ' lowi jako ' || funkcja ||
       ' i zjada max. ' || TO_CHAR(MAX(NVL(przydzial_myszy,0) + NVL(myszy_extra,0)),'00.00') ||
       ' myszy miesiecznie' AS info
FROM Kocury
WHERE plec != 'M'
GROUP BY funkcja
HAVING funkcja != 'SZEFUNIO' 
AND AVG(NVL(przydzial_myszy,0) + NVL(myszy_extra,0)) > 50;
       
       
--zad13
SELECT nr_bandy "Nr bandy", 
       plec "Plec", 
       MIN(NVL(przydzial_myszy,0)) "Minimalny przydzial"
FROM Kocury
GROUP BY nr_bandy, plec;


--zad14
SELECT LEVEL "Poziom", pseudo "Pseudonim", funkcja "Funkcja", nr_bandy "Nr bandy"
FROM Kocury
WHERE plec='M'
CONNECT BY PRIOR pseudo = szef
START WITH funkcja='BANDZIOR';


--zad15
SELECT LPAD((LEVEL-1),(LEVEL-1)*4+1,'===>') || '    ' || imie "Hierarchia", 
       NVL(szef, 'Sam sobie panem') "Pseudo szefa", 
       funkcja "Funkcja"
FROM Kocury
WHERE myszy_extra IS NOT NULL
CONNECT BY PRIOR pseudo = szef
START WITH szef IS NULL;


--zad16
SELECT LPAD(' ', (LEVEL-1)*4) || pseudo AS "Droga sluzbowa"
FROM Kocury
CONNECT BY PRIOR szef = pseudo
START WITH 
    plec='M'
    AND EXTRACT(YEAR FROM DATE '2024-07-17') - EXTRACT(YEAR FROM w_stadku_od) > 15
    AND myszy_extra IS NULL;
       
