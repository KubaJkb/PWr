
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


-- Zad 1 
SELECT DISTINCT k.imie AS "W stadzie przed szefem lub bez incydentu"
FROM Kocury k
LEFT JOIN Kocury s ON k.szef = s.pseudo
LEFT JOIN Wrogowie_kocurow wk ON k.pseudo = wk.pseudo
WHERE (s.w_stadku_od IS NOT NULL AND k.w_stadku_od < s.w_stadku_od)
   OR wk.pseudo IS NULL;

-- Zad 2 
SELECT k.pseudo AS "Kotka",
       w.imie_wroga AS "jej wrog",
       wk.opis_incydentu AS "Przewina wroga"
FROM Kocury k
JOIN Wrogowie_kocurow wk ON k.pseudo = wk.pseudo
JOIN Wrogowie w ON wk.imie_wroga = w.imie_wroga
WHERE k.plec = 'D';

-- Zad 3 
SELECT k.pseudo AS "Szpieg",
       k.nr_bandy AS "Banda"
FROM Kocury k
JOIN Kocury t ON k.szef = t.pseudo
WHERE t.pseudo = 'TYGRYS' AND k.nr_bandy != t.nr_bandy;

-- Zad 4 
SELECT 
    NVL(s.pseudo, 'Brak przelozonego') AS "Przelozony",
    NVL(pod.pseudo, 'Brak podwladnego')  AS "Podwladny"
FROM Kocury pod
FULL OUTER JOIN Kocury s ON pod.szef = s.pseudo
WHERE 
      (pod.plec = 'M' OR pod.pseudo IS NULL)   -- podwładny jest mężczyzną albo brak podwładnego
  AND (s.plec = 'M' OR s.pseudo IS NULL);   -- przełożony jest mężczyzną albo brak przełożonego

-- Zad 5 
SELECT
  k.pseudo,
  k.przydzial_myszy,
  SUM(DISTINCT kb.przydzial_myszy) AS sum_w_bandzie,
  ROUND(
    k.przydzial_myszy * 100
    / NULLIF( SUM(DISTINCT kb.przydzial_myszy), 0 )
  ) AS proc_w_bandzie
FROM Kocury k
  -- wszyscy członkowie tej samej bandy, aby zbierać przydziały
  JOIN Kocury kb
    ON k.nr_bandy = kb.nr_bandy
  JOIN Bandy b
    ON k.nr_bandy = b.nr_bandy
  -- łączenie z incydentami — wymusza, że kot ma wroga o stopniu > 5
  JOIN Wrogowie_kocurow wk ON k.pseudo = wk.pseudo
  JOIN Wrogowie w ON wk.imie_wroga = w.imie_wroga
       AND w.stopien_wrogosci > 5
WHERE b.teren IN ('POLE', 'CALOSC')
GROUP BY k.pseudo, k.przydzial_myszy;


-- Zad 6 
SELECT 
    pseudo AS "Pseudonim",
    przydzial_myszy AS "Zjada",
    nr_bandy AS "Banda",
    CASE 
        WHEN przydzial_myszy > (SELECT AVG(przydzial_myszy) FROM Kocury) THEN 'Prominent'
        ELSE 'Szaraki'
    END AS "Typ"
FROM Kocury
WHERE przydzial_myszy > (SELECT AVG(przydzial_myszy) FROM Kocury)
   OR przydzial_myszy IN (
        SELECT MIN(przydzial_myszy)
        FROM Kocury k2
        WHERE k2.nr_bandy = Kocury.nr_bandy
        GROUP BY nr_bandy
   );
    
         
-- Zad 7 
SELECT k.pseudo AS "Kot",
    ROUND(AVG(k2.przydzial_myszy), 2) AS "Srednio w bandzie"
FROM Kocury k
JOIN Kocury k2 ON k.nr_bandy = k2.nr_bandy
WHERE k.plec = 'M'
GROUP BY k.pseudo, k.nr_bandy;

-- Zad 8 
-- wersja z wartością globalną
SELECT 
    nr_bandy AS "Lepsze bandy",
    ROUND(AVG(przydzial_myszy), 2) AS "Sredni przydzial w bandzie",
    (SELECT ROUND(AVG(przydzial_myszy), 2) FROM Kocury) AS "Sredni przydzial"
FROM Kocury
GROUP BY nr_bandy
HAVING AVG(przydzial_myszy) > (SELECT AVG(przydzial_myszy) FROM Kocury);
-- wersja bez globalnej wartości
SELECT 
    nr_bandy AS "Lepsze bandy",
    ROUND(AVG(przydzial_myszy), 2) AS "Sredni przydzial w bandzie"
FROM Kocury
GROUP BY nr_bandy
HAVING AVG(przydzial_myszy) > (SELECT AVG(przydzial_myszy) FROM Kocury);

-- Zad 9 
SELECT UPPER(TO_CHAR(w_stadku_od,'FMMonth','NLS_DATE_LANGUAGE=ENGLISH')) AS "Miesiac",
       COUNT(*) AS "Liczba rekrutów"
FROM Kocury
GROUP BY EXTRACT(MONTH FROM w_stadku_od),
         TO_CHAR(w_stadku_od,'FMMonth','NLS_DATE_LANGUAGE=ENGLISH')
ORDER BY EXTRACT(MONTH FROM w_stadku_od);

-- Zad 10 
WITH funkcje_list AS (
  SELECT DISTINCT funkcja
  FROM Kocury
  WHERE funkcja IS NOT NULL
    AND funkcja <> 'SZEFUNIO'
)
SELECT f.funkcja AS funkcja,
       NVL(SUM(CASE WHEN b.nazwa = 'CZARNI RYCERZE'
                THEN k.przydzial_myszy + NVL(k.myszy_extra,0) END), 0) AS "Banda CZARNI RYCERZE",
       NVL(SUM(CASE WHEN b.nazwa = 'BIALI LOWCY'
                THEN k.przydzial_myszy + NVL(k.myszy_extra,0) END), 0) AS "Banda BIALI LOWCY"
FROM funkcje_list f
LEFT JOIN Kocury k ON k.funkcja = f.funkcja
LEFT JOIN Bandy b ON k.nr_bandy = b.nr_bandy
GROUP BY f.funkcja;

-- Zad 11 
WITH funkcje_plec AS (
    SELECT DISTINCT funkcja, plec
    FROM Kocury
    WHERE funkcja IS NOT NULL
      AND funkcja <> 'SZEFUNIO'
)
SELECT f.funkcja,
       f.plec AS P,
       NVL(SUM(CASE WHEN b.nazwa = 'CZARNI RYCERZE' THEN k.przydzial_myszy + NVL(k.myszy_extra,0) END), 0) AS "Banda CZARNI RYCERZE",
       NVL(SUM(CASE WHEN b.nazwa = 'BIALI LOWCY' THEN k.przydzial_myszy + NVL(k.myszy_extra,0) END), 0) AS "Banda BIALI LOWCY"
FROM funkcje_plec f
LEFT JOIN Kocury k
    ON k.funkcja = f.funkcja AND k.plec = f.plec
LEFT JOIN Bandy b
    ON k.nr_bandy = b.nr_bandy
GROUP BY f.funkcja, f.plec;


-- Zad 12 
SELECT k.pseudo AS "POLUJE W POLU",
       k.przydzial_myszy AS "PRZYDZIAL MYSZY",
       b.nazwa AS "BANDA"
FROM Kocury k
JOIN Bandy  b ON k.nr_bandy = b.nr_bandy
WHERE k.przydzial_myszy > 50 AND b.teren IN ('POLE','CALOSC');

-- Zad 13 
SELECT k.imie AS "IMIE",
       TO_CHAR(k.w_stadku_od,'YYYY-MM-DD') AS "POLUJE OD"
FROM Kocury k
JOIN Kocury j ON j.imie = 'JACEK'
WHERE k.w_stadku_od < j.w_stadku_od
ORDER BY k.w_stadku_od DESC;

-- Zad 14 
-- a)
SELECT k.imie AS Imie,
       k.funkcja AS Funkcja,
       NVL(s1.imie, ' ') AS "Szef 1",
       NVL(s2.imie, ' ') AS "Szef 2",
       NVL(s3.imie, ' ') AS "Szef 3"
FROM Kocury k
LEFT JOIN Kocury s1 ON k.szef = s1.pseudo
LEFT JOIN Kocury s2 ON s1.szef = s2.pseudo
LEFT JOIN Kocury s3 ON s2.szef = s3.pseudo
WHERE k.funkcja IN ('KOT','MILUSIA');
-- b)
SELECT
    "Imie",
    "Funkcja",
    NVL("Szef 1", ' ') AS "Szef 1",
    NVL("Szef 2", ' ') AS "Szef 2",
    NVL("Szef 3", ' ') AS "Szef 3"
FROM (
    SELECT 
        CONNECT_BY_ROOT imie AS "Imie", 
        CONNECT_BY_ROOT funkcja AS "Funkcja",
        LEVEL AS lvl,
        imie AS szef_imie
    FROM Kocury
    CONNECT BY PRIOR szef = pseudo
    START WITH funkcja IN ('KOT', 'MILUSIA')
)
PIVOT (
    MIN(szef_imie)
    FOR lvl IN (2 AS "Szef 1", 3 AS "Szef 2", 4 AS "Szef 3")
);
-- c)
SELECT CONNECT_BY_ROOT imie "Imie",
       CONNECT_BY_ROOT funkcja "Funkcja",
       REPLACE(SYS_CONNECT_BY_PATH(imie, ' | '), ' | ' || CONNECT_BY_ROOT IMIE || ' ' , '') "Imiona kolejnych szefow"
FROM Kocury
WHERE szef IS NULL
CONNECT BY PRIOR szef = pseudo
START WITH funkcja IN ('KOT','MILUSIA');

-- Zad 15 
SELECT k.imie AS "Imie kotki",
       b.nazwa AS "Nazwa bandy",
       wk.imie_wroga AS "Imie wroga",
       w.stopien_wrogosci AS "Ocena wroga",
       TO_CHAR(wk.data_incydentu,'YYYY-MM-DD') AS "Data inc."
FROM Kocury k
JOIN Bandy b ON k.nr_bandy = b.nr_bandy
JOIN Wrogowie_kocurow wk ON k.pseudo = wk.pseudo
JOIN Wrogowie w ON wk.imie_wroga = w.imie_wroga
WHERE k.plec = 'D' AND wk.data_incydentu > DATE '2007-01-01';

-- Zad 16 
SELECT b.nazwa AS "Nazwa bandy",
       COUNT(DISTINCT k.pseudo) AS "Koty z wrogami"
FROM Bandy b
JOIN Kocury k ON k.nr_bandy = b.nr_bandy
JOIN Wrogowie_kocurow wk ON wk.pseudo = k.pseudo
GROUP BY b.nazwa;

-- Zad 17 
SELECT k.funkcja AS "Funkcja",
       k.pseudo AS "Pseudonim kota",
       COUNT(DISTINCT wk.imie_wroga) AS "Liczba wrogow"
FROM Kocury k
JOIN Wrogowie_kocurow wk ON k.pseudo = wk.pseudo
GROUP BY k.funkcja, k.pseudo
HAVING COUNT(DISTINCT wk.imie_wroga) > 1;

-- Zad 18 
SELECT imie,
      (przydzial_myszy + NVL(myszy_extra,0)) * 12 AS "DAWKA ROCZNA",
      'powyzej 864' AS "DAWKA"
FROM Kocury
WHERE myszy_extra IS NOT NULL
  AND (przydzial_myszy + NVL(myszy_extra,0)) * 12 > 864
UNION
SELECT imie,
      (przydzial_myszy + NVL(myszy_extra,0)) * 12,
      '864'
FROM Kocury  
WHERE myszy_extra IS NOT NULL
  AND (przydzial_myszy + NVL(myszy_extra,0)) * 12 = 864
UNION
SELECT imie,
      (przydzial_myszy + NVL(myszy_extra,0)) * 12,
      'ponizej 864'
FROM Kocury
WHERE myszy_extra IS NOT NULL
  AND (przydzial_myszy + NVL(myszy_extra,0)) * 12 < 864
ORDER BY "DAWKA ROCZNA" DESC;

-- Zad 19 
-- a)
SELECT b.nr_bandy AS "NR BANDY",
       b.nazwa AS "NAZWA",
       b.teren AS "TEREN"
FROM Bandy b
LEFT JOIN Kocury k ON b.nr_bandy = k.nr_bandy
WHERE k.pseudo IS NULL;
-- b)
SELECT nr_bandy AS "NR BANDY",
       nazwa AS "NAZWA",
       teren AS "TEREN" 
FROM Bandy
MINUS
SELECT DISTINCT b.nr_bandy, b.nazwa, b.teren
FROM Bandy b
JOIN Kocury k ON b.nr_bandy = k.nr_bandy;

-- Zad 20 
SELECT imie AS "IMIE", 
       funkcja AS "FUNKCJA", 
       przydzial_myszy AS "PRZYDZIAL MYSZY"
FROM Kocury
WHERE przydzial_myszy >= ALL ( 
    SELECT 3 * przydzial_myszy
    FROM Kocury k
    JOIN Bandy b ON k.nr_bandy = b.nr_bandy
    WHERE funkcja = 'MILUSIA' AND teren IN ('SAD', 'CALOSC')
    );

-- Zad 21 
SELECT 
    funkcja "Funkcja",
    ROUND(AVG(przydzial_myszy + NVL(myszy_extra,0)), 0) "Srednio najw. i najm. myszy"
FROM Kocury
WHERE funkcja != 'SZEFUNIO'
GROUP BY funkcja
HAVING 
    AVG(przydzial_myszy + NVL(myszy_extra,0)) IN (
        (SELECT MAX(avg_total) 
            FROM (
               SELECT AVG(przydzial_myszy + NVL(myszy_extra,0)) avg_total
               FROM Kocury 
               WHERE funkcja != 'SZEFUNIO'
               GROUP BY funkcja)
            ),
        (SELECT MIN(avg_total) 
            FROM (
               SELECT AVG(przydzial_myszy + NVL(myszy_extra,0)) avg_total
               FROM Kocury
               WHERE funkcja != 'SZEFUNIO' 
               GROUP BY funkcja)
            )
    );

-- Zad 22 
-- a)
ACCEPT n PROMPT 'Podaj wartość n: '
SELECT pseudo,
       przydzial_myszy + NVL(myszy_extra, 0) AS zjada
FROM Kocury k
WHERE (
    SELECT COUNT(DISTINCT (k2.przydzial_myszy + NVL(k2.myszy_extra, 0)))
    FROM Kocury k2
    WHERE (k2.przydzial_myszy + NVL(k2.myszy_extra, 0)) > (k.przydzial_myszy + NVL(k.myszy_extra, 0))
) < &n
ORDER BY zjada DESC;
-- b)
ACCEPT n PROMPT 'Podaj wartość n: '
SELECT pseudo, 
       przydzial_myszy + NVL(myszy_extra,0) AS zjada
FROM Kocury
WHERE przydzial_myszy + NVL(myszy_extra,0) IN
    (SELECT * 
    FROM (SELECT DISTINCT przydzial_myszy + NVL(myszy_extra,0) przydzial
        FROM Kocury
        ORDER BY przydzial DESC)
    WHERE ROWNUM <= &n);
-- c)
ACCEPT n PROMPT 'Podaj wartość n: '
SELECT k.pseudo,
       MAX(k.przydzial_myszy + NVL(k.myszy_extra,0)) AS zjada
FROM Kocury k
JOIN Kocury k2 ON k.przydzial_myszy + NVL(k.myszy_extra,0) <= k2.przydzial_myszy + NVL(k2.myszy_extra,0)
GROUP BY k.pseudo
HAVING COUNT (DISTINCT k2.przydzial_myszy + NVL(k2.myszy_extra,0)) <= &n
ORDER BY zjada DESC;
-- d)
ACCEPT n PROMPT 'Podaj wartość n: '
SELECT pseudo, 
       zjada
FROM (
  SELECT k.pseudo,
         (k.przydzial_myszy + NVL(k.myszy_extra,0)) AS zjada,
         DENSE_RANK() OVER (ORDER BY k.przydzial_myszy + NVL(k.myszy_extra,0) DESC) AS pos
  FROM Kocury k)
WHERE pos <= &n;

-- Zad 23 
WITH lata AS (
    SELECT EXTRACT(YEAR FROM w_stadku_od) rok,
           COUNT(*) liczba_wstapien
    FROM Kocury
    GROUP BY EXTRACT(YEAR FROM w_stadku_od)
),
srednia AS (
    SELECT AVG(liczba_wstapien) srd
    FROM lata
)
SELECT TO_CHAR(rok) "ROK",
       liczba_wstapien "LICZBA WSTAPIEN"
FROM lata
WHERE liczba_wstapien = (
    SELECT MIN(liczba_wstapien) 
    FROM lata 
    WHERE liczba_wstapien >= (SELECT srd FROM srednia)
)
OR liczba_wstapien = (
    SELECT MAX(liczba_wstapien) 
    FROM lata 
    WHERE liczba_wstapien <= (SELECT srd FROM srednia)
)
UNION ALL
SELECT 'Srednia',
       ROUND((SELECT srd FROM srednia), 7)
ORDER BY rok ASC;

-- Zad 24 
-- a)
SELECT k.imie "IMIE",
       k.przydzial_myszy + NVL(k.myszy_extra,0) "ZJADA",
       k.nr_bandy "NR BANDY",
       TO_CHAR(AVG(k2.przydzial_myszy + NVL(k2.myszy_extra,0)), '99.99') "SREDNIA BANDY"
FROM Kocury k
JOIN Kocury k2 ON k.nr_bandy = k2.nr_bandy
WHERE k.plec = 'M'
GROUP BY k.imie, k.przydzial_myszy + NVL(k.myszy_extra,0), k.nr_bandy
HAVING k.przydzial_myszy + NVL(k.myszy_extra,0) <= AVG(k2.przydzial_myszy + NVL(k2.myszy_extra,0));
-- b)
SELECT k.imie "IMIE",
       k.przydzial_myszy + NVL(k.myszy_extra,0) "ZJADA",
       k.nr_bandy "NR BANDY",
       TO_CHAR(b.srednia_bandy, '99.99') "SREDNIA BANDY"
FROM Kocury k
JOIN (
    SELECT nr_bandy,
           AVG(przydzial_myszy + NVL(myszy_extra,0)) srednia_bandy
    FROM Kocury
    GROUP BY nr_bandy
) b ON k.nr_bandy = b.nr_bandy
WHERE k.plec = 'M' AND k.przydzial_myszy + NVL(k.myszy_extra,0) <= b.srednia_bandy;
-- c)
SELECT k.imie "IMIE",
       k.przydzial_myszy + NVL(k.myszy_extra,0) "ZJADA",
       k.nr_bandy "NR BANDY",
       (SELECT TO_CHAR(AVG(przydzial_myszy + NVL(myszy_extra,0)), '99.99')
         FROM Kocury k2
         WHERE k2.nr_bandy = k.nr_bandy) "SREDNIA BANDY"
FROM Kocury k
WHERE k.plec = 'M' AND k.przydzial_myszy + NVL(k.myszy_extra,0) <= (
      SELECT AVG(przydzial_myszy + NVL(myszy_extra,0))
      FROM Kocury k3
      WHERE k3.nr_bandy = k.nr_bandy
);

-- Zad 25
SELECT k.imie,
       TO_CHAR(k.w_stadku_od,'YYYY-MM-DD') AS "WSTAPIL DO STADKA",
       ' ' AS " "
FROM Kocury k
WHERE k.w_stadku_od NOT IN (
    SELECT MAX(k2.w_stadku_od) 
    FROM Kocury k2 
    GROUP BY k2.nr_bandy
    UNION
    SELECT MIN(k2.w_stadku_od) 
    FROM Kocury k2 
    GROUP BY k2.nr_bandy
)
UNION ALL
SELECT k.imie,
       TO_CHAR(k.w_stadku_od,'YYYY-MM-DD'),
       '<--- NAJMLODSZY STAZEM W BANDZIE ' || b.nazwa
FROM Kocury k
JOIN Bandy b ON k.nr_bandy = b.nr_bandy
WHERE k.w_stadku_od = (
    SELECT MAX(w_stadku_od) 
    FROM Kocury k2 
    WHERE k2.nr_bandy = k.nr_bandy
)
UNION ALL
SELECT k.imie,
       TO_CHAR(k.w_stadku_od,'YYYY-MM-DD'),
       '<--- NAJSTARSZY STAZEM W BANDZIE ' || b.nazwa
FROM Kocury k
JOIN Bandy b ON k.nr_bandy = b.nr_bandy
WHERE k.w_stadku_od = (
    SELECT MIN(w_stadku_od) 
    FROM Kocury k2 
    WHERE k2.nr_bandy = k.nr_bandy
)
ORDER BY imie;



-- Zad 30
-- 1) Widok: agregaty na poziomie bandy
CREATE OR REPLACE VIEW bandy_statystyki AS
SELECT b.nazwa AS nazwa_bandy,
       ROUND(AVG(k.przydzial_myszy),2) AS sre_spoz,
       MAX(k.przydzial_myszy) AS max_spoz,
       MIN(k.przydzial_myszy) AS min_spoz,
       COUNT(*) AS koty,
       SUM(CASE WHEN NVL(k.myszy_extra,0) > 0 THEN 1 ELSE 0 END) AS koty_z_dod
FROM bandy b
JOIN kocury k ON k.nr_bandy = b.nr_bandy
GROUP BY b.nazwa;

SELECT * FROM bandy_statystyki;

-- 2) Wybór informacji o kocie, pseudonim podawany jako bind variable :p_pseudo
SELECT k.pseudo AS pseudonim,
       k.imie AS imie,
       k.funkcja AS funkcja,
       k.przydzial_myszy AS zjada,
       'OD ' || bs.min_spoz || ' DO ' || bs.max_spoz AS granice_spozycia,
       TO_CHAR(k.w_stadku_od, 'YYYY-MM-DD') AS lowi_od
FROM kocury k
JOIN bandy b ON k.nr_bandy = b.nr_bandy
JOIN bandy_statystyki bs ON bs.nazwa_bandy = b.nazwa
WHERE k.pseudo = '&pseudonim';


-- Zad 31
-- Stan przed
SELECT pseudo AS "Pseudonim", 
       plec AS "Plec", 
       przydzial_myszy AS "Myszy przed podw.", 
       NVL(myszy_extra, 0) AS "Extra przed podw."
FROM Kocury
WHERE pseudo IN (
    SELECT k.pseudo
    FROM Kocury k
    WHERE k.nr_bandy IN (SELECT nr_bandy FROM Bandy WHERE nazwa IN ('CZARNI RYCERZE', 'LACIACI MYSLIWI'))
    AND (
        SELECT COUNT(DISTINCT k2.w_stadku_od)
        FROM Kocury k2
        WHERE k2.nr_bandy = k.nr_bandy AND k2.w_stadku_od <= k.w_stadku_od
    ) <= 3
)
ORDER BY nr_bandy, w_stadku_od;

-- Aktualizacja
SAVEPOINT before_raise;

UPDATE Kocury
SET przydzial_myszy = CASE 
        WHEN plec = 'D' THEN przydzial_myszy + 0.1 * (SELECT MIN(przydzial_myszy) FROM Kocury)
        ELSE przydzial_myszy + 10
    END,
    myszy_extra = ROUND(NVL(myszy_extra, 0) + 0.15 * (SELECT AVG(NVL(myszy_extra, 0)) FROM Kocury k2 WHERE k2.nr_bandy = Kocury.nr_bandy))
WHERE pseudo IN (
    SELECT k.pseudo
    FROM Kocury k
    WHERE k.nr_bandy IN (SELECT nr_bandy FROM Bandy WHERE nazwa IN ('CZARNI RYCERZE', 'LACIACI MYSLIWI'))
    AND (
        SELECT COUNT(DISTINCT k2.w_stadku_od)
        FROM Kocury k2
        WHERE k2.nr_bandy = k.nr_bandy AND k2.w_stadku_od <= k.w_stadku_od
    ) <= 3
);

-- Stan po
SELECT pseudo AS "Pseudonim", 
       plec AS "Plec", 
       przydzial_myszy AS "Myszy po podw.", 
       NVL(myszy_extra, 0) AS "Extra po podw."
FROM Kocury
WHERE pseudo IN (
    SELECT k.pseudo
    FROM Kocury k
    WHERE k.nr_bandy IN (SELECT nr_bandy FROM Bandy WHERE nazwa IN ('CZARNI RYCERZE', 'LACIACI MYSLIWI'))
    AND (
        SELECT COUNT(DISTINCT k2.w_stadku_od)
        FROM Kocury k2
        WHERE k2.nr_bandy = k.nr_bandy AND k2.w_stadku_od <= k.w_stadku_od
    ) <= 3
)
ORDER BY nr_bandy, w_stadku_od;

-- Cofnięcie zmian
ROLLBACK TO SAVEPOINT before_raise;


-- Zad 32
-- a) DECODE + SUM
SELECT nazwa AS "NAZWA BANDY",
       plec AS "PLEC",
       ile AS "ILE",
       szefunio AS "SZEFUNIO",
       bandzior AS "BANDZIOR",
       lowczy AS "LOWCZY", 
       lapacz AS "LAPACZ",
       kot AS "KOT",
       milusia AS "MILUSIA",
       dzielczy AS "DZIELCZY",
       suma AS "SUMA"
FROM (
    SELECT nazwa,
           DECODE(plec, 'D', 'Kotka', 'Kocor') AS plec,
           TO_CHAR(COUNT(pseudo)) AS ile,
           TO_CHAR(SUM(DECODE(funkcja, 'SZEFUNIO', przydzial_myszy + NVL(myszy_extra, 0), 0))) AS szefunio,
           TO_CHAR(SUM(DECODE(funkcja, 'BANDZIOR', przydzial_myszy + NVL(myszy_extra, 0), 0))) AS bandzior,
           TO_CHAR(SUM(DECODE(funkcja, 'LOWCZY', przydzial_myszy + NVL(myszy_extra, 0), 0))) AS lowczy,
           TO_CHAR(SUM(DECODE(funkcja, 'LAPACZ', przydzial_myszy + NVL(myszy_extra, 0), 0))) AS lapacz,
           TO_CHAR(SUM(DECODE(funkcja, 'KOT', przydzial_myszy + NVL(myszy_extra, 0), 0))) AS kot,
           TO_CHAR(SUM(DECODE(funkcja, 'MILUSIA', przydzial_myszy + NVL(myszy_extra, 0), 0))) AS milusia,
           TO_CHAR(SUM(DECODE(funkcja, 'DZIELCZY', przydzial_myszy + NVL(myszy_extra, 0), 0))) AS dzielczy,
           TO_CHAR(SUM(przydzial_myszy + NVL(myszy_extra, 0))) AS suma
    FROM Kocury
    JOIN Bandy B ON Kocury.nr_bandy = B.nr_bandy
    GROUP BY nazwa, plec
    ORDER BY nazwa, plec DESC
)
UNION ALL
SELECT 'Z----------------', '------', '-----', '---------', '---------', '--------', '--------', '--------', '--------', '--------', '------'
FROM DUAL
UNION ALL
SELECT 'ZJADA RAZEM', ' ', ' ',
       TO_CHAR(SUM(DECODE(funkcja, 'SZEFUNIO', przydzial_myszy + NVL(myszy_extra, 0), 0))),
       TO_CHAR(SUM(DECODE(funkcja, 'BANDZIOR', przydzial_myszy + NVL(myszy_extra, 0), 0))),
       TO_CHAR(SUM(DECODE(funkcja, 'LOWCZY', przydzial_myszy + NVL(myszy_extra, 0), 0))),
       TO_CHAR(SUM(DECODE(funkcja, 'LAPACZ', przydzial_myszy + NVL(myszy_extra, 0), 0))),
       TO_CHAR(SUM(DECODE(funkcja, 'KOT', przydzial_myszy + NVL(myszy_extra, 0), 0))),
       TO_CHAR(SUM(DECODE(funkcja, 'MILUSIA', przydzial_myszy + NVL(myszy_extra, 0), 0))),
       TO_CHAR(SUM(DECODE(funkcja, 'DZIELCZY',przydzial_myszy + NVL(myszy_extra, 0), 0))),
       TO_CHAR(SUM(przydzial_myszy + NVL(myszy_extra, 0)))
FROM Kocury;

-- b) PIVOT
WITH DANE AS (
    SELECT 
        b.nazwa,
        DECODE(k.plec, 'D', 'Kotka', 'Kocor') AS plec,
        k.funkcja,
        k.przydzial_myszy + NVL(k.myszy_extra, 0) AS liczba
    FROM Kocury k
    JOIN Bandy b ON k.nr_bandy = b.nr_bandy
),
PIV AS (
  SELECT nazwa, plec,
         NVL(szefunio,0)  AS szefunio,
         NVL(bandzior,0)  AS bandzior,
         NVL(lowczy,0)    AS lowczy,
         NVL(lapacz,0)    AS lapacz,
         NVL(kot,0)       AS kot,
         NVL(milusia,0)   AS milusia,
         NVL(dzielczy,0)  AS dzielczy
  FROM DANE
  PIVOT (
    SUM(liczba)
    FOR funkcja IN (
      'SZEFUNIO'  AS szefunio,
      'BANDZIOR'  AS bandzior,
      'LOWCZY'    AS lowczy,
      'LAPACZ'    AS lapacz,
      'KOT'       AS kot,
      'MILUSIA'   AS milusia,
      'DZIELCZY'  AS dzielczy
    )
  )
),
AGG AS (
  SELECT nazwa, plec,
         COUNT(*) AS ile,
         SUM(liczba) AS suma
  FROM DANE
  GROUP BY nazwa, plec
)
SELECT "NAZWA BANDY","PLEC","ILE","SZEFUNIO","BANDZIOR","LOWCZY","LAPACZ","KOT","MILUSIA","DZIELCZY","SUMA"
FROM (
  SELECT
    p.nazwa AS "NAZWA BANDY",
    p.plec  AS "PLEC",
    TO_CHAR(a.ile)   AS "ILE",
    TO_CHAR(p.szefunio)  AS "SZEFUNIO",
    TO_CHAR(p.bandzior)  AS "BANDZIOR",
    TO_CHAR(p.lowczy)    AS "LOWCZY",
    TO_CHAR(p.lapacz)    AS "LAPACZ",
    TO_CHAR(p.kot)       AS "KOT",
    TO_CHAR(p.milusia)   AS "MILUSIA",
    TO_CHAR(p.dzielczy)  AS "DZIELCZY",
    TO_CHAR(a.suma)      AS "SUMA"
  FROM PIV p
  JOIN AGG a ON p.nazwa = a.nazwa AND p.plec = a.plec
  UNION ALL
  SELECT 'Z----------------','------','-----','---------','---------','--------','--------','--------','--------','--------','------'
  FROM dual
  UNION ALL
  SELECT 
    'ZJADA RAZEM',' ', ' ',
    TO_CHAR(SUM(szefunio)),
    TO_CHAR(SUM(bandzior)),
    TO_CHAR(SUM(lowczy)),
    TO_CHAR(SUM(lapacz)),
    TO_CHAR(SUM(kot)),
    TO_CHAR(SUM(milusia)),
    TO_CHAR(SUM(dzielczy)),
    TO_CHAR(SUM(suma))
  FROM (
    SELECT p.szefunio, p.bandzior, p.lowczy, p.lapacz, p.kot, p.milusia, p.dzielczy, a.suma
    FROM PIV p
    JOIN AGG a ON p.nazwa = a.nazwa AND p.plec = a.plec
  )
)
ORDER BY "NAZWA BANDY", "PLEC" DESC;


