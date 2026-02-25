
-- USUWANIE TABEL

IF EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'wr_ko_ps_fk' AND parent_object_id = OBJECT_ID('Wrogowie_kocurow'))
    ALTER TABLE Wrogowie_kocurow DROP CONSTRAINT wr_ko_ps_fk;

IF EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'wr_ko_im_fk' AND parent_object_id = OBJECT_ID('Wrogowie_kocurow'))
    ALTER TABLE Wrogowie_kocurow DROP CONSTRAINT wr_ko_im_fk;

IF EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'ba_sz_fk' AND parent_object_id = OBJECT_ID('Bandy'))
    ALTER TABLE Bandy DROP CONSTRAINT ba_sz_fk;

IF EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'ko_nr_ba_fk' AND parent_object_id = OBJECT_ID('Kocury'))
    ALTER TABLE Kocury DROP CONSTRAINT ko_nr_ba_fk;

IF EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'ko_fu_fk' AND parent_object_id = OBJECT_ID('Kocury'))
    ALTER TABLE Kocury DROP CONSTRAINT ko_fu_fk;

IF EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'ko_sz_fk' AND parent_object_id = OBJECT_ID('Kocury'))
    ALTER TABLE Kocury DROP CONSTRAINT ko_sz_fk;

IF OBJECT_ID('Wrogowie_kocurow') IS NOT NULL DROP TABLE Wrogowie_kocurow;
IF OBJECT_ID('Kocury') IS NOT NULL DROP TABLE Kocury;
IF OBJECT_ID('Bandy') IS NOT NULL DROP TABLE Bandy;
IF OBJECT_ID('Wrogowie') IS NOT NULL DROP TABLE Wrogowie;
IF OBJECT_ID('Funkcje') IS NOT NULL DROP TABLE Funkcje;


-- TWORZENIE TABEL

CREATE TABLE Funkcje(
    funkcja VARCHAR(10) CONSTRAINT funkcje_pk PRIMARY KEY,
    min_myszy NUMERIC(3,0) CONSTRAINT fu_min_values CHECK(min_myszy > 5),
    max_myszy NUMERIC(3,0) CONSTRAINT fu_max_values CHECK(max_myszy < 200),
    CONSTRAINT fu_min_lq_max_values CHECK(min_myszy <= max_myszy)
);

CREATE TABLE Wrogowie(
    imie_wroga VARCHAR(15) CONSTRAINT wrogowie_pk PRIMARY KEY,
    stopien_wrogosci NUMERIC(2,0) CONSTRAINT wr_st_ch CHECK(stopien_wrogosci BETWEEN 1 AND 10),
    gatunek VARCHAR(15),
    lapowka VARCHAR(15)
);

CREATE TABLE Bandy(
    nr_bandy NUMERIC(2,0) CONSTRAINT bandy_pk PRIMARY KEY,
    nazwa VARCHAR(20) CONSTRAINT ba_na_nn NOT NULL,
    teren VARCHAR(15) CONSTRAINT ba_te_un UNIQUE,
    szef_bandy VARCHAR(15) CONSTRAINT ba_sz_un UNIQUE
);

CREATE TABLE Kocury(
    imie VARCHAR(15) CONSTRAINT ko_im_nn NOT NULL,
    plec CHAR(1) CONSTRAINT ko_pl_ch CHECK(plec IN ('M','D')),
    pseudo VARCHAR(15) CONSTRAINT kocury_pk PRIMARY KEY,
    funkcja VARCHAR(10) CONSTRAINT ko_fu_fk FOREIGN KEY REFERENCES Funkcje(funkcja),
    szef VARCHAR(15) CONSTRAINT ko_sz_fk FOREIGN KEY REFERENCES Kocury(pseudo),
    w_stadku_od DATE DEFAULT GETDATE(),
    przydzial_myszy NUMERIC(3,0),
    myszy_extra NUMERIC(3,0),
    nr_bandy NUMERIC(2,0) CONSTRAINT ko_nr_ba_fk FOREIGN KEY REFERENCES Bandy(nr_bandy)
);

ALTER TABLE Bandy ADD CONSTRAINT ba_sz_fk FOREIGN KEY (szef_bandy) REFERENCES Kocury(pseudo);

CREATE TABLE Wrogowie_kocurow(
    pseudo VARCHAR(15) CONSTRAINT wr_ko_ps_fk FOREIGN KEY REFERENCES Kocury(pseudo),
    imie_wroga VARCHAR(15) CONSTRAINT wr_ko_im_fk FOREIGN KEY REFERENCES Wrogowie(imie_wroga),
    data_incydentu DATE CONSTRAINT wr_ko_da_nn NOT NULL,
    opis_incydentu VARCHAR(50),
    CONSTRAINT wr_ko_pk PRIMARY KEY(pseudo, imie_wroga)
);


-- WYPE£NIANIE DANYCH

SET DATEFORMAT ymd;

ALTER TABLE Bandy NOCHECK CONSTRAINT ba_sz_fk;
ALTER TABLE Kocury NOCHECK CONSTRAINT ko_sz_fk;
ALTER TABLE Kocury NOCHECK CONSTRAINT ko_nr_ba_fk;

INSERT INTO Funkcje (funkcja, min_myszy, max_myszy) VALUES 
('SZEFUNIO',90,110),
('BANDZIOR',70,90),
('LOWCZY',60,70),
('LAPACZ',50,60),
('KOT',40,50),
('MILUSIA',20,30),
('DZIELCZY',45,55),
('HONOROWA',6,25);

INSERT INTO Kocury VALUES
('JACEK','M','PLACEK','LOWCZY','LYSY','2008-12-01',67,NULL,2),
('BARI','M','RURA','LAPACZ','LYSY','2009-09-01',56,NULL,2),
('MICKA','D','LOLA','MILUSIA','TYGRYS','2009-10-14',25,47,1),
('LUCEK','M','ZERO','KOT','KURKA','2010-03-01',43,NULL,3),
('SONIA','D','PUSZYSTA','MILUSIA','ZOMBI','2010-11-18',20,35,3),
('LATKA','D','UCHO','KOT','RAFA','2011-01-01',40,NULL,4),
('DUDEK','M','MALY','KOT','RAFA','2011-05-15',40,NULL,4),
('MRUCZEK','M','TYGRYS','SZEFUNIO',NULL,'2002-01-01',103,33,1),
('CHYTRY','M','BOLEK','DZIELCZY','TYGRYS','2002-05-05',50,NULL,1),
('KOREK','M','ZOMBI','BANDZIOR','TYGRYS','2004-03-16',75,13,3),
('BOLEK','M','LYSY','BANDZIOR','TYGRYS','2006-08-15',72,21,2),
('ZUZIA','D','SZYBKA','LOWCZY','LYSY','2006-07-21',65,NULL,2),
('RUDA','D','MALA','MILUSIA','TYGRYS','2006-09-17',22,42,1),
('PUCEK','M','RAFA','LOWCZY','TYGRYS','2006-10-15',65,NULL,4),
('PUNIA','D','KURKA','LOWCZY','ZOMBI','2008-01-01',61,NULL,3),
('BELA','D','LASKA','MILUSIA','LYSY','2008-02-01',24,28,2),
('KSAWERY','M','MAN','LAPACZ','RAFA','2008-07-12',51,NULL,4),
('MELA','D','DAMA','LAPACZ','RAFA','2008-11-01',51,NULL,4);

INSERT INTO Bandy VALUES
(1,'SZEFOSTWO','CALOSC','TYGRYS'),
(2,'CZARNI RYCERZE','POLE','LYSY'),
(3,'BIALI LOWCY','SAD','ZOMBI'),
(4,'LACIACI MYSLIWI','GORKA','RAFA'),
(5,'ROCKERSI','ZAGRODA',NULL);

INSERT INTO Wrogowie VALUES
('KAZIO',10,'CZLOWIEK','FLASZKA'),
('GLUPIA ZOSKA',1,'CZLOWIEK','KORALIK'),
('SWAWOLNY DYZIO',7,'CZLOWIEK','GUMA DO ZUCIA'),
('BUREK',4,'PIES','KOSC'),
('DZIKI BILL',10,'PIES',NULL),
('REKSIO',2,'PIES','KOSC'),
('BETHOVEN',1,'PIES','PEDIGRIPALL'),
('CHYTRUSEK',5,'LIS','KURCZAK'),
('SMUKLA',1,'SOSNA',NULL),
('BAZYLI',3,'KOGUT','KURA DO STADA');

INSERT INTO Wrogowie_kocurow VALUES
('TYGRYS','KAZIO','2004-10-13','USILOWAL NABIC NA WIDLY'),
('ZOMBI','SWAWOLNY DYZIO','2005-03-07','WYBIL OKO Z PROCY'),
('BOLEK','KAZIO','2005-03-29','POSZCZUL BURKIEM'),
('SZYBKA','GLUPIA ZOSKA','2006-09-12','UZYLA KOTA JAKO SCIERKI'),
('MALA','CHYTRUSEK','2007-03-07','ZALECAL SIE'),
('TYGRYS','DZIKI BILL','2007-06-12','USILOWAL POZBAWIC ZYCIA'),
('BOLEK','DZIKI BILL','2007-11-10','ODGRYZL UCHO'),
('LASKA','DZIKI BILL','2008-12-12','POGRYZL ZE LEDWO SIE WYLIZALA'),
('LASKA','KAZIO','2009-01-07','ZLAPAL ZA OGON I ZROBIL WIATRAK'),
('DAMA','KAZIO','2009-02-07','CHCIAL OBEDRZEC ZE SKORY'),
('MAN','REKSIO','2009-04-14','WYJATKOWO NIEGRZECZNIE OBSZCZEKAL'),
('LYSY','BETHOVEN','2009-05-11','NIE PODZIELIL SIE SWOJA KASZA'),
('RURA','DZIKI BILL','2009-09-03','ODGRYZL OGON'),
('PLACEK','BAZYLI','2010-07-12','DZIOBIAC UNIEMOZLIWIL PODEBRANIE KURCZAKA'),
('PUSZYSTA','SMUKLA','2010-11-19','OBRZUCILA SZYSZKAMI'),
('KURKA','BUREK','2010-12-14','POGONIL'),
('MALY','CHYTRUSEK','2011-07-13','PODEBRAL PODEBRANE JAJKA'),
('UCHO','SWAWOLNY DYZIO','2011-07-14','OBRZUCIL KAMIENIAMI');

ALTER TABLE Bandy CHECK CONSTRAINT ba_sz_fk;
ALTER TABLE Kocury CHECK CONSTRAINT ko_sz_fk;
ALTER TABLE Kocury CHECK CONSTRAINT ko_nr_ba_fk;


SELECT * FROM Bandy;
SELECT * FROM Wrogowie;
SELECT * FROM Kocury;
SELECT * FROM Funkcje;
SELECT * FROM Wrogowie_kocurow;



-- Zad 1
SELECT DISTINCT k.imie AS [W stadzie przed szefem lub bez incydentu]
FROM Kocury k
LEFT JOIN Kocury s ON k.szef = s.pseudo
LEFT JOIN Wrogowie_kocurow wk ON k.pseudo = wk.pseudo
WHERE (s.w_stadku_od IS NOT NULL AND k.w_stadku_od < s.w_stadku_od)
   OR wk.pseudo IS NULL;

-- Zad 2
SELECT k.pseudo AS [Kotka],
       w.imie_wroga AS [jej wrog],
       wk.opis_incydentu AS [Przewina wroga]
FROM Kocury k
JOIN Wrogowie_kocurow wk ON k.pseudo = wk.pseudo
JOIN Wrogowie w ON wk.imie_wroga = w.imie_wroga
WHERE k.plec = 'D';

-- Zad 3
SELECT k.pseudo AS [Szpieg],
       k.nr_bandy AS [Banda]
FROM Kocury k
JOIN Kocury t ON k.szef = t.pseudo
WHERE t.pseudo = 'TYGRYS' AND k.nr_bandy <> t.nr_bandy;

-- Zad 4
SELECT ISNULL(s.pseudo, 'Brak przelozonego') AS [Przelozony],
       ISNULL(pod.pseudo, 'Brak podwladnego')  AS [Podwladny]
FROM Kocury pod
FULL OUTER JOIN Kocury s ON pod.szef = s.pseudo
WHERE (pod.plec = 'M' OR pod.pseudo IS NULL) 
  AND (s.plec = 'M' OR s.pseudo IS NULL);

-- Zad 5
SELECT k.pseudo,
       k.przydzial_myszy,
       SUM(DISTINCT kb.przydzial_myszy) AS sum_w_bandzie,
       CAST(ROUND(k.przydzial_myszy * 100.0 / NULLIF( SUM(DISTINCT kb.przydzial_myszy), 0 ), 0) AS INT) AS proc_w_bandzie 
FROM Kocury k
JOIN Kocury kb ON k.nr_bandy = kb.nr_bandy
JOIN Bandy b ON k.nr_bandy = b.nr_bandy
JOIN Wrogowie_kocurow wk ON k.pseudo = wk.pseudo
JOIN Wrogowie w ON wk.imie_wroga = w.imie_wroga
       AND w.stopien_wrogosci > 5
WHERE b.teren IN ('POLE', 'CALOSC')
GROUP BY k.pseudo, k.przydzial_myszy;



-- Zad 6
SELECT k.pseudo AS [Pseudonim],
       k.przydzial_myszy AS [Zjada],
       k.nr_bandy AS [Banda],
       CASE 
          WHEN k.przydzial_myszy > (SELECT AVG(przydzial_myszy*1.0) FROM Kocury) THEN 'Prominent'
          ELSE 'Szaraki'
       END AS [Typ]
FROM Kocury k
WHERE k.przydzial_myszy > (SELECT AVG(przydzial_myszy*1.0) FROM Kocury)
   OR k.przydzial_myszy IN (
        SELECT MIN(k2.przydzial_myszy)
        FROM Kocury k2
        WHERE k2.nr_bandy = k.nr_bandy
        GROUP BY k2.nr_bandy
   );

-- Zad 7
SELECT k.pseudo AS [Kot],
    ROUND(AVG(CAST(k2.przydzial_myszy AS FLOAT)), 2) AS [Srednio w bandzie]
FROM Kocury k
JOIN Kocury k2 ON k.nr_bandy = k2.nr_bandy
WHERE k.plec = 'M'
GROUP BY k.pseudo, k.nr_bandy;

-- Zad 8 
-- wersja z wartoœci¹ globaln¹
SELECT 
    nr_bandy AS [Lepsze bandy],
    ROUND(AVG(CAST(przydzial_myszy AS FLOAT)), 2) AS [Sredni przydzial w bandzie],
    (SELECT ROUND(AVG(CAST(przydzial_myszy AS FLOAT)), 2) FROM Kocury) AS [Sredni przydzial]
FROM Kocury
GROUP BY nr_bandy
HAVING AVG(CAST(przydzial_myszy AS FLOAT)) > (SELECT AVG(CAST(przydzial_myszy AS FLOAT)) FROM Kocury);

-- wersja bez globalnej wartoœci
SELECT 
    nr_bandy AS [Lepsze bandy],
    ROUND(AVG(CAST(przydzial_myszy AS FLOAT)), 2) AS [Sredni przydzial w bandzie]
FROM Kocury
GROUP BY nr_bandy
HAVING AVG(CAST(przydzial_myszy AS FLOAT)) > (SELECT AVG(CAST(przydzial_myszy AS FLOAT)) FROM Kocury);

-- Zad 9
SELECT UPPER(FORMAT(w_stadku_od,'MMMM','en-US')) AS [Miesiac],
       COUNT(*) AS [Liczba rekrutów]
FROM Kocury
GROUP BY MONTH(w_stadku_od),
         FORMAT(w_stadku_od,'MMMM','en-US')
ORDER BY MONTH(w_stadku_od);


-- Zad 10
WITH funkcje_list AS (
  SELECT DISTINCT funkcja
  FROM Kocury
  WHERE funkcja IS NOT NULL
    AND funkcja <> 'SZEFUNIO'
)
SELECT f.funkcja AS funkcja,
       ISNULL(SUM(CASE WHEN b.nazwa = 'CZARNI RYCERZE'
                THEN k.przydzial_myszy + ISNULL(k.myszy_extra,0) END), 0) AS [Banda CZARNI RYCERZE],
       ISNULL(SUM(CASE WHEN b.nazwa = 'BIALI LOWCY'
                THEN k.przydzial_myszy + ISNULL(k.myszy_extra,0) END), 0) AS [Banda BIALI LOWCY]
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
       ISNULL(SUM(CASE WHEN b.nazwa = 'CZARNI RYCERZE' THEN k.przydzial_myszy + ISNULL(k.myszy_extra,0) END), 0) AS [Banda CZARNI RYCERZE],
       ISNULL(SUM(CASE WHEN b.nazwa = 'BIALI LOWCY' THEN k.przydzial_myszy + ISNULL(k.myszy_extra,0) END), 0) AS [Banda BIALI LOWCY]
FROM funkcje_plec f
LEFT JOIN Kocury k
    ON k.funkcja = f.funkcja AND k.plec = f.plec
LEFT JOIN Bandy b
    ON k.nr_bandy = b.nr_bandy
GROUP BY f.funkcja, f.plec;



-- Zad 26
WITH Kotki AS (
    SELECT pseudo, 
           imie
    FROM Kocury
    WHERE plec = 'D'
),
Kotki_z_incydentami AS (
    SELECT DISTINCT k.pseudo
    FROM Kotki k
    JOIN Wrogowie_kocurow wk ON wk.pseudo = k.pseudo
    JOIN Wrogowie w ON wk.imie_wroga = w.imie_wroga
    WHERE w.stopien_wrogosci > 5
)
SELECT k.pseudo AS [Zadziorne kotki]
FROM Kotki_z_incydentami k;

-- Zad 27
WITH Hierarchy AS (
    -- korzeñ: mêscy BANDZIORZY (poziom 1)
    SELECT k.pseudo,
           k.funkcja,
           k.nr_bandy,
           k.plec,
           k.szef,
           k.pseudo AS root_boss,
           1 AS lvl
    FROM Kocury k
    WHERE k.funkcja = 'BANDZIOR' AND k.plec = 'M'

    UNION ALL
    -- rekurencja: potomkowie
    SELECT c.pseudo,
           c.funkcja,
           c.nr_bandy,
           c.plec,
           c.szef,
           h.root_boss,
           h.lvl + 1
    FROM Kocury c
    JOIN Hierarchy h ON c.szef = h.pseudo
)
SELECT h.lvl AS [Poziom],
       h.pseudo AS [Pseudonim],
       h.funkcja AS [Funkcja],
       h.nr_bandy AS [Nr bandy]
FROM Hierarchy h
WHERE h.plec = 'M'
ORDER BY h.root_boss, h.lvl, h.pseudo;

-- Zad 28
WITH Tree AS (
    -- korzeñ: szefowie najwy¿szego poziomu (szef IS NULL)
    SELECT k.pseudo,
           k.imie,
           k.funkcja,
           k.szef,
           k.myszy_extra,
           0 AS lvl,
           CAST(k.imie AS NVARCHAR(MAX)) AS path_order
    FROM Kocury k
    WHERE k.szef IS NULL

    UNION ALL
    -- rekurencja: potomkowie
    SELECT c.pseudo,
           c.imie,
           c.funkcja,
           c.szef,
           c.myszy_extra,
           t.lvl + 1,
           CAST(t.path_order + '|' + c.imie AS NVARCHAR(MAX))
    FROM Kocury c
    JOIN Tree t ON c.szef = t.pseudo
)
SELECT REPLICATE('===>', CASE WHEN lvl = 0 THEN 0 ELSE lvl END) 
         + CAST(lvl AS VARCHAR(10)) + '       '
         + imie 
         AS [Hierarchia],
       ISNULL(szef, 'Sam sobie panem') AS [Pseudo szefa],
       funkcja AS [Funkcja]
FROM Tree
WHERE myszy_extra IS NOT NULL;

-- Zad 29
SELECT k.pseudo AS [Do przeczolagania],
       b.nazwa AS [Nazwa bandy]
FROM Kocury k
JOIN Bandy b ON k.nr_bandy = b.nr_bandy
JOIN Funkcje f ON k.funkcja = f.funkcja
WHERE NOT EXISTS (
    SELECT 1 
    FROM Kocury c 
    WHERE c.szef = k.pseudo    -- brak podw³adnych
)
AND EXISTS (
    SELECT 1 
    FROM Wrogowie_kocurow wk 
    WHERE wk.pseudo = k.pseudo  -- ma wrogów
)
AND ISNULL(k.przydzial_myszy,0) >=
    ( f.min_myszy + ( (f.max_myszy - f.min_myszy) / 3.0 ) );



-- Zad 30
IF OBJECT_ID('bandy_statystyki', 'V') IS NOT NULL
    DROP VIEW bandy_statystyki;
GO

CREATE VIEW bandy_statystyki AS
SELECT 
    b.nazwa AS nazwa_bandy,
    ROUND(AVG(k.przydzial_myszy), 2) AS sre_spoz,
    MAX(k.przydzial_myszy) AS max_spoz,
    MIN(k.przydzial_myszy) AS min_spoz,
    COUNT(*) AS koty,
    SUM(CASE WHEN ISNULL(k.myszy_extra, 0) > 0 THEN 1 ELSE 0 END) AS koty_z_dod
FROM bandy b
JOIN kocury k ON k.nr_bandy = b.nr_bandy
GROUP BY b.nazwa;
GO

SELECT * FROM dbo.bandy_statystyki;

DECLARE @pseudonim VARCHAR(15) = 'PLACEK';  -- nale¿y podaæ wartoœæ przed uruchomieniem

SELECT 
    k.pseudo AS pseudonim,
    k.imie AS imie,
    k.funkcja AS funkcja,
    k.przydzial_myszy AS zjada,
    'OD ' + CAST(bs.min_spoz AS varchar(10)) + ' DO ' + CAST(bs.max_spoz AS varchar(10)) AS granice_spozycia,
    FORMAT(k.w_stadku_od, 'yyyy-MM-dd') AS lowi_od
FROM kocury k
JOIN bandy b ON k.nr_bandy = b.nr_bandy
JOIN bandy_statystyki bs ON bs.nazwa_bandy = b.nazwa
WHERE k.pseudo = @pseudonim;  

-- Zad 31
-- Stan przed
SELECT 
    pseudo AS Pseudonim, 
    plec AS Plec, 
    przydzial_myszy AS [Myszy przed podw.], 
    ISNULL(myszy_extra, 0) AS [Extra przed podw.]
FROM dbo.Kocury
WHERE pseudo IN (
    SELECT k.pseudo
    FROM dbo.Kocury k
    WHERE k.nr_bandy IN (SELECT nr_bandy FROM dbo.Bandy WHERE nazwa IN ('CZARNI RYCERZE', 'LACIACI MYSLIWI'))
      AND (
        SELECT COUNT(DISTINCT k2.w_stadku_od)
        FROM dbo.Kocury k2
        WHERE k2.nr_bandy = k.nr_bandy 
          AND k2.w_stadku_od <= k.w_stadku_od
      ) <= 3
)
ORDER BY nr_bandy, w_stadku_od;

-- Aktualizacja z u¿yciem savepointu 
BEGIN TRANSACTION;

SAVE TRANSACTION before_raise;

UPDATE dbo.Kocury
SET 
    przydzial_myszy = CASE 
        WHEN plec = 'D' THEN przydzial_myszy + 0.1 * (SELECT MIN(przydzial_myszy) FROM dbo.Kocury)
        ELSE przydzial_myszy + 10
    END,
    myszy_extra = ROUND(
        ISNULL(myszy_extra, 0) 
        + 0.15 * ISNULL(
            (SELECT AVG(CAST(ISNULL(k2.myszy_extra,0) AS FLOAT)) 
             FROM dbo.Kocury k2 
             WHERE k2.nr_bandy = dbo.Kocury.nr_bandy),
            0.0
          )
    , 0) 
WHERE pseudo IN (
    SELECT k.pseudo
    FROM dbo.Kocury k
    WHERE k.nr_bandy IN (SELECT nr_bandy FROM dbo.Bandy WHERE nazwa IN ('CZARNI RYCERZE', 'LACIACI MYSLIWI'))
      AND (
        SELECT COUNT(DISTINCT k2.w_stadku_od)
        FROM dbo.Kocury k2
        WHERE k2.nr_bandy = k.nr_bandy 
          AND k2.w_stadku_od <= k.w_stadku_od
      ) <= 3
);

-- Stan po
SELECT 
    pseudo AS Pseudonim, 
    plec AS Plec, 
    przydzial_myszy AS [Myszy po podw.], 
    ISNULL(myszy_extra, 0) AS [Extra po podw.]
FROM dbo.Kocury
WHERE pseudo IN (
    SELECT k.pseudo
    FROM dbo.Kocury k
    WHERE k.nr_bandy IN (SELECT nr_bandy FROM dbo.Bandy WHERE nazwa IN ('CZARNI RYCERZE', 'LACIACI MYSLIWI'))
      AND (
        SELECT COUNT(DISTINCT k2.w_stadku_od)
        FROM dbo.Kocury k2
        WHERE k2.nr_bandy = k.nr_bandy 
          AND k2.w_stadku_od <= k.w_stadku_od
      ) <= 3
)
ORDER BY nr_bandy, w_stadku_od;

-- Cofniêcie zmian
ROLLBACK TRANSACTION before_raise;


-- Zad 32
-- a)
WITH Agg AS (
    SELECT 
        b.nazwa,
        CASE WHEN k.plec = 'D' THEN 'Kotka' ELSE 'Kocor' END AS plec,
        COUNT(k.pseudo) AS ile,
        SUM(CASE WHEN k.funkcja = 'SZEFUNIO' THEN (k.przydzial_myszy + ISNULL(k.myszy_extra,0)) ELSE 0 END) AS szefunio,
        SUM(CASE WHEN k.funkcja = 'BANDZIOR' THEN (k.przydzial_myszy + ISNULL(k.myszy_extra,0)) ELSE 0 END) AS bandzior,
        SUM(CASE WHEN k.funkcja = 'LOWCZY'   THEN (k.przydzial_myszy + ISNULL(k.myszy_extra,0)) ELSE 0 END) AS lowczy,
        SUM(CASE WHEN k.funkcja = 'LAPACZ'   THEN (k.przydzial_myszy + ISNULL(k.myszy_extra,0)) ELSE 0 END) AS lapacz,
        SUM(CASE WHEN k.funkcja = 'KOT'      THEN (k.przydzial_myszy + ISNULL(k.myszy_extra,0)) ELSE 0 END) AS kot,
        SUM(CASE WHEN k.funkcja = 'MILUSIA'  THEN (k.przydzial_myszy + ISNULL(k.myszy_extra,0)) ELSE 0 END) AS milusia,
        SUM(CASE WHEN k.funkcja = 'DZIELCZY' THEN (k.przydzial_myszy + ISNULL(k.myszy_extra,0)) ELSE 0 END) AS dzielczy,
        SUM(k.przydzial_myszy + ISNULL(k.myszy_extra,0)) AS suma
    FROM dbo.Kocury k
    JOIN dbo.Bandy b ON k.nr_bandy = b.nr_bandy
    GROUP BY b.nazwa, CASE WHEN k.plec = 'D' THEN 'Kotka' ELSE 'Kocor' END
)
, MainRows AS (
    SELECT 
        nazwa AS NAZWA_BANDY,
        plec AS PLEC,
        CONVERT(varchar(10), ile) AS ILE,
        CONVERT(varchar(20), ISNULL(szefunio,0)) AS SZEFUNIO,
        CONVERT(varchar(20), ISNULL(bandzior,0)) AS BANDZIOR,
        CONVERT(varchar(20), ISNULL(lowczy,0)) AS LOWCZY,
        CONVERT(varchar(20), ISNULL(lapacz,0)) AS LAPACZ,
        CONVERT(varchar(20), ISNULL(kot,0)) AS KOT,
        CONVERT(varchar(20), ISNULL(milusia,0)) AS MILUSIA,
        CONVERT(varchar(20), ISNULL(dzielczy,0)) AS DZIELCZY,
        CONVERT(varchar(20), ISNULL(suma,0)) AS SUMA
    FROM Agg
)
SELECT NAZWA_BANDY, PLEC, ILE, SZEFUNIO, BANDZIOR, LOWCZY, LAPACZ, KOT, MILUSIA, DZIELCZY, SUMA
FROM MainRows
UNION ALL
SELECT 
    'Z----------------' AS NAZWA_BANDY,
    '------'            AS PLEC,
    '-----'             AS ILE,
    '---------'         AS SZEFUNIO,
    '---------'         AS BANDZIOR,
    '--------'          AS LOWCZY,
    '--------'          AS LAPACZ,
    '--------'          AS KOT,
    '--------'          AS MILUSIA,
    '--------'          AS DZIELCZY,
    '------'            AS SUMA
UNION ALL
SELECT
    'ZJADA RAZEM' AS NAZWA_BANDY,
    ''            AS PLEC,
    ''            AS ILE,
    CONVERT(varchar(20), SUM(ISNULL(szefunio,0)))   AS SZEFUNIO,
    CONVERT(varchar(20), SUM(ISNULL(bandzior,0)))   AS BANDZIOR,
    CONVERT(varchar(20), SUM(ISNULL(lowczy,0)))     AS LOWCZY,
    CONVERT(varchar(20), SUM(ISNULL(lapacz,0)))     AS LAPACZ,
    CONVERT(varchar(20), SUM(ISNULL(kot,0)))        AS KOT,
    CONVERT(varchar(20), SUM(ISNULL(milusia,0)))    AS MILUSIA,
    CONVERT(varchar(20), SUM(ISNULL(dzielczy,0)))   AS DZIELCZY,
    CONVERT(varchar(20), SUM(ISNULL(suma,0)))       AS SUMA
FROM Agg
ORDER BY NAZWA_BANDY, PLEC DESC;


-- b) PIVOT
WITH DANE AS (
    SELECT 
        b.nazwa,
        CASE WHEN k.plec = 'D' THEN 'Kotka' ELSE 'Kocor' END AS plec,
        k.funkcja,
        k.przydzial_myszy + ISNULL(k.myszy_extra, 0) AS liczba
    FROM dbo.Kocury k
    JOIN dbo.Bandy b ON k.nr_bandy = b.nr_bandy
),
SUMMED AS (
    SELECT nazwa, plec, funkcja, SUM(liczba) AS func_sum
    FROM DANE
    GROUP BY nazwa, plec, funkcja
),
PIVOTED_NUM AS (
    SELECT 
        nazwa,
        plec,
        ISNULL([SZEFUNIO], 0)   AS SZEFUNIO,
        ISNULL([BANDZIOR], 0)   AS BANDZIOR,
        ISNULL([LOWCZY], 0)     AS LOWCZY,
        ISNULL([LAPACZ], 0)     AS LAPACZ,
        ISNULL([KOT], 0)        AS KOT,
        ISNULL([MILUSIA], 0)    AS MILUSIA,
        ISNULL([DZIELCZY], 0)   AS DZIELCZY
    FROM SUMMED
    PIVOT (
        SUM(func_sum) FOR funkcja IN (
            [SZEFUNIO], [BANDZIOR], [LOWCZY], [LAPACZ], [KOT], [MILUSIA], [DZIELCZY]
        )
    ) AS p
),
TOTALS_NUM AS (
    SELECT 
        nazwa,
        plec,
        COUNT(*) AS ILE,
        SUM(liczba) AS SUMA
    FROM DANE
    GROUP BY nazwa, plec
),
RESULT_NUM AS (
    SELECT p.nazwa, p.plec, p.SZEFUNIO, p.BANDZIOR, p.LOWCZY, p.LAPACZ, p.KOT, p.MILUSIA, p.DZIELCZY,
           t.ILE, t.SUMA
    FROM PIVOTED_NUM p
    JOIN TOTALS_NUM t
      ON p.nazwa = t.nazwa AND p.plec = t.plec
)
SELECT 
    nazwa AS [NAZWA BANDY],
    plec AS [PLEC],
    CONVERT(varchar(10), ILE) AS [ILE],
    CONVERT(varchar(20), SZEFUNIO)   AS [SZEFUNIO],
    CONVERT(varchar(20), BANDZIOR)   AS [BANDZIOR],
    CONVERT(varchar(20), LOWCZY)     AS [LOWCZY],
    CONVERT(varchar(20), LAPACZ)     AS [LAPACZ],
    CONVERT(varchar(20), KOT)        AS [KOT],
    CONVERT(varchar(20), MILUSIA)    AS [MILUSIA],
    CONVERT(varchar(20), DZIELCZY)   AS [DZIELCZY],
    CONVERT(varchar(20), SUMA)       AS [SUMA]
FROM RESULT_NUM
UNION ALL
SELECT 'Z----------------' AS [NAZWA BANDY], '------' AS [PLEC], '-----' AS [ILE],
       '---------' AS [SZEFUNIO], '---------' AS [BANDZIOR], '--------' AS [LOWCZY],
       '--------' AS [LAPACZ], '--------' AS [KOT], '--------' AS [MILUSIA],
       '--------' AS [DZIELCZY], '------' AS [SUMA]
UNION ALL
SELECT 
    'ZJADA RAZEM' AS [NAZWA BANDY],
    '' AS [PLEC],
    '' AS [ILE],
    CONVERT(varchar(20), SUM(SZEFUNIO))   AS [SZEFUNIO],
    CONVERT(varchar(20), SUM(BANDZIOR))   AS [BANDZIOR],
    CONVERT(varchar(20), SUM(LOWCZY))     AS [LOWCZY],
    CONVERT(varchar(20), SUM(LAPACZ))     AS [LAPACZ],
    CONVERT(varchar(20), SUM(KOT))        AS [KOT],
    CONVERT(varchar(20), SUM(MILUSIA))    AS [MILUSIA],
    CONVERT(varchar(20), SUM(DZIELCZY))   AS [DZIELCZY],
    CONVERT(varchar(20), SUM(SUMA))       AS [SUMA]
FROM RESULT_NUM
ORDER BY [NAZWA BANDY], [PLEC] DESC; 
