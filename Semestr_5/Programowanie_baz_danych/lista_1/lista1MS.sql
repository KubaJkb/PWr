
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

--zad1
SELECT imie_wroga AS WROG, opis_incydentu AS PRZEWINA
FROM Wrogowie_kocurow
WHERE YEAR(data_incydentu) = 2009;


--zad2
SELECT imie, funkcja, CONVERT(varchar(10), w_stadku_od, 23) AS [Z NAMI OD]
FROM Kocury
WHERE plec = 'D' 
  AND w_stadku_od BETWEEN '2005-09-01' AND '2007-07-31';


--zad3
SELECT imie_wroga AS WROG, gatunek, stopien_wrogosci
FROM Wrogowie
WHERE lapowka IS NULL
ORDER BY stopien_wrogosci;


--zad4
SELECT 
  imie + ' zwany ' + pseudo + ' (fun. ' + funkcja + ') lowi myszki w bandzie ' 
      + CAST(nr_bandy AS varchar(10)) + ' od ' + CONVERT(varchar(10), w_stadku_od, 23)
  AS [WSZYSTKO O KOCURACH]
FROM Kocury
WHERE plec = 'M'
ORDER BY w_stadku_od DESC, pseudo;


--zad5
SELECT 
  pseudo,
  STUFF(
    STUFF(pseudo, CHARINDEX('A', pseudo), 1, '#'),
    CHARINDEX('L', pseudo), 1, '%'
  ) AS [Po wymianie A na # oraz L na %]
FROM Kocury
WHERE pseudo LIKE '%A%' AND pseudo LIKE '%L%';


--zad6
SELECT 
  imie,
  CONVERT(varchar(10), w_stadku_od, 23) AS [W stadku],
  ROUND(przydzial_myszy / 1.1, 0) AS Zjadal,
  CONVERT(varchar(10), DATEADD(month, 6, w_stadku_od), 23) AS Podwyzka,
  przydzial_myszy AS Zjada
FROM Kocury
WHERE DATEDIFF(month, w_stadku_od, '2024-07-17') >= 12 * 15
  AND MONTH(w_stadku_od) BETWEEN 3 AND 9;


--zad7
SELECT 
  imie,
  ISNULL(przydzial_myszy * 3, 0)    AS [MYSZY KWARTALNIE],
  ISNULL(myszy_extra * 3, 0)       AS [KWARTALNE DODATKI]
FROM Kocury
WHERE przydzial_myszy > (2 * ISNULL(myszy_extra, 0))
   AND przydzial_myszy >= 55;


--zad8
SELECT 
  imie,
  CASE SIGN((12 * (ISNULL(przydzial_myszy,0) + ISNULL(myszy_extra,0))) - 660)
    WHEN -1 THEN 'Ponizej 660'
    WHEN  0 THEN 'Limit'
    ELSE CONVERT(varchar(10), 12 * (ISNULL(przydzial_myszy,0) + ISNULL(myszy_extra,0)))
  END AS [Zjada rocznie]
FROM Kocury;


--zad9
SELECT pseudo + ' - ' + CASE 
    WHEN COUNT(pseudo) = 1 
    THEN 'Unikalny' 
    ELSE 'nieunikalny' 
    END AS [Unikalnosc atr. PSEUDO]
FROM Kocury
GROUP BY pseudo;

SELECT szef + ' - ' + CASE 
    WHEN COUNT(pseudo) = 1 
    THEN 'Unikalny' 
    ELSE 'nieunikalny' 
    END AS [Unikalnosc atr. SZEF]
FROM Kocury
WHERE szef IS NOT NULL
GROUP BY szef;


--zad10
SELECT pseudo AS Pseudonim, COUNT(imie_wroga) AS [Liczba wrogow]
FROM Wrogowie_kocurow
GROUP BY pseudo
HAVING COUNT(imie_wroga) >= 2;
