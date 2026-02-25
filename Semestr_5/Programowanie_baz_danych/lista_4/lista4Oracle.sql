
-- USUWANIE TABEL 

DROP TABLE Wrogowie_kocurow CASCADE CONSTRAINTS;
DROP TABLE Kocury CASCADE CONSTRAINTS;
DROP TABLE Bandy CASCADE CONSTRAINTS;
DROP TABLE Wrogowie CASCADE CONSTRAINTS;
DROP TABLE Funkcje CASCADE CONSTRAINTS;


-- TWORZENIE TABEL

CREATE TABLE Funkcje (
    funkcja VARCHAR2(10) CONSTRAINT pk_funkcje PRIMARY KEY,
    min_myszy NUMBER(3) CONSTRAINT chk_min_myszy CHECK (min_myszy > 5),
    max_myszy NUMBER(3),
    CONSTRAINT chk_max_myszy CHECK (max_myszy < 200 AND max_myszy >= min_myszy)
);

CREATE TABLE Wrogowie (
    imie_wroga VARCHAR2(15) CONSTRAINT pk_wrogowie PRIMARY KEY,
    stopien_wrogosci NUMBER(2) CONSTRAINT chk_stopien CHECK (stopien_wrogosci BETWEEN 1 AND 10),
    gatunek VARCHAR2(15),  
    lapowka VARCHAR2(20)  
);

CREATE TABLE Bandy (
    nr_bandy NUMBER(2) CONSTRAINT pk_bandy PRIMARY KEY,
    nazwa VARCHAR2(20) CONSTRAINT nn_bandy_nazwa NOT NULL,
    teren VARCHAR2(15) CONSTRAINT un_bandy_teren UNIQUE,
    szef_bandy VARCHAR2(15) --CONSTRAINT fk_bandy_szef REFERENCES Kocury(pseudo)
);

CREATE TABLE Kocury (
    imie VARCHAR2(15) CONSTRAINT nn_kocury_imie NOT NULL, 
    plec VARCHAR2(1) CONSTRAINT chk_kocury_plec CHECK (plec IN ('M', 'D')),
    pseudo VARCHAR2(15) CONSTRAINT pk_kocury PRIMARY KEY,  
    funkcja VARCHAR2(10) CONSTRAINT fk_kocury_funkcje REFERENCES Funkcje(funkcja),
    szef VARCHAR2(15) CONSTRAINT fk_kocury_szef REFERENCES Kocury(pseudo),
    w_stadku_od DATE DEFAULT SYSDATE,
    przydzial_myszy NUMBER(3),  
    myszy_extra NUMBER(3),  
    nr_bandy NUMBER(2) CONSTRAINT fk_kocury_bandy REFERENCES Bandy(nr_bandy)
);

ALTER TABLE Bandy
ADD CONSTRAINT fk_bandy_szef FOREIGN KEY (szef_bandy) REFERENCES Kocury(pseudo);

CREATE TABLE Wrogowie_kocurow (
    pseudo VARCHAR2(15) CONSTRAINT fk_wk_kocury REFERENCES Kocury(pseudo),
    imie_wroga VARCHAR2(15) CONSTRAINT fk_wk_wrogowie REFERENCES Wrogowie(imie_wroga),
    data_incydentu DATE CONSTRAINT nn_wk_data NOT NULL,
    opis_incydentu VARCHAR2(50),
    constraint pk_wrogowie_kocurow PRIMARY KEY (pseudo, imie_wroga)  
);


-- WSTAWIANIE DANYCH

ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD';

--Funkcje(funkcja,min_myszy,max_myszy) 
INSERT INTO Funkcje VALUES ('SZEFUNIO',90,110 );
INSERT INTO Funkcje VALUES ('BANDZIOR',70,90 );
INSERT INTO Funkcje VALUES ('LOWCZY',60,70 );
INSERT INTO Funkcje VALUES ('LAPACZ',50,60 );
INSERT INTO Funkcje VALUES ('KOT',40,50 );
INSERT INTO Funkcje VALUES ('MILUSIA',20,30 );
INSERT INTO Funkcje VALUES ('DZIELCZY',45,55 );
INSERT INTO Funkcje VALUES ('HONOROWA',6,25 );

--Wrogowie(imie_wroga,stopien_wrogosci,gatunek,lapowka) 
INSERT INTO Wrogowie VALUES ('KAZIO',10,'CZLOWIEK','FLASZKA' );
INSERT INTO Wrogowie VALUES ('GLUPIA ZOSKA',1,'CZLOWIEK','KORALIK' );
INSERT INTO Wrogowie VALUES ('SWAWOLNY DYZIO',7,'CZLOWIEK','GUMA DO ZUCIA' );
INSERT INTO Wrogowie VALUES ('BUREK',4,'PIES','KOSC' );
INSERT INTO Wrogowie VALUES ('DZIKI BILL',10,'PIES',NULL );
INSERT INTO Wrogowie VALUES ('REKSIO',2,'PIES','KOSC' );
INSERT INTO Wrogowie VALUES ('BETHOVEN',1,'PIES','PEDIGRIPALL' );
INSERT INTO Wrogowie VALUES ('CHYTRUSEK',5,'LIS','KURCZAK' );
INSERT INTO Wrogowie VALUES ('SMUKLA',1,'SOSNA',NULL );
INSERT INTO Wrogowie VALUES ('BAZYLI',3,'KOGUT','KURA DO STADA' );

ALTER TABLE Bandy DISABLE CONSTRAINT fk_bandy_szef;
--Bandy(nr_bandy,nazwa,teren,szef_bandy) 
INSERT INTO Bandy VALUES (1,'SZEFOSTWO','CALOSC','TYGRYS' );
INSERT INTO Bandy VALUES (2,'CZARNI RYCERZE','POLE','LYSY' );
INSERT INTO Bandy VALUES (3,'BIALI LOWCY','SAD','ZOMBI' );
INSERT INTO Bandy VALUES (4,'LACIACI MYSLIWI','GORKA','RAFA' );
INSERT INTO Bandy VALUES (5,'ROCKERSI','ZAGRODA',NULL );

ALTER TABLE Kocury DISABLE CONSTRAINT fk_kocury_szef;
--Kocury(imie,plec,pseudo,funkcja,szef,w_stadku_od,przydzial_myszy,myszy_extra,nr_bandy) 
INSERT INTO Kocury VALUES ('JACEK','M','PLACEK','LOWCZY','LYSY','2008-12-01',67,NULL,2 );
INSERT INTO Kocury VALUES ('BARI','M','RURA','LAPACZ','LYSY','2009-09-01',56,NULL,2 );
INSERT INTO Kocury VALUES ('MICKA','D','LOLA','MILUSIA','TYGRYS','2009-10-14',25,47,1 );
INSERT INTO Kocury VALUES ('LUCEK','M','ZERO','KOT','KURKA','2010-03-01',43,NULL,3 );
INSERT INTO Kocury VALUES ('SONIA','D','PUSZYSTA','MILUSIA','ZOMBI','2010-11-18',20,35,3 );
INSERT INTO Kocury VALUES ('LATKA','D','UCHO','KOT','RAFA','2011-01-01',40,NULL,4 );
INSERT INTO Kocury VALUES ('DUDEK','M','MALY','KOT','RAFA','2011-05-15',40,NULL,4 );
INSERT INTO Kocury VALUES ('MRUCZEK','M','TYGRYS','SZEFUNIO',NULL,'2002-01-01',103,33,1 );
INSERT INTO Kocury VALUES ('CHYTRY','M','BOLEK','DZIELCZY','TYGRYS','2002-05-05',50,NULL,1 );
INSERT INTO Kocury VALUES ('KOREK','M','ZOMBI','BANDZIOR','TYGRYS','2004-03-16',75,13,3 );
INSERT INTO Kocury VALUES ('BOLEK','M','LYSY','BANDZIOR','TYGRYS','2006-08-15',72,21,2 );
INSERT INTO Kocury VALUES ('ZUZIA','D','SZYBKA','LOWCZY','LYSY','2006-07-21',65,NULL,2 );
INSERT INTO Kocury VALUES ('RUDA','D','MALA','MILUSIA','TYGRYS','2006-09-17',22,42,1 );
INSERT INTO Kocury VALUES ('PUCEK','M','RAFA','LOWCZY','TYGRYS','2006-10-15',65,NULL,4 );
INSERT INTO Kocury VALUES ('PUNIA','D','KURKA','LOWCZY','ZOMBI','2008-01-01',61,NULL,3 );
INSERT INTO Kocury VALUES ('BELA','D','LASKA','MILUSIA','LYSY','2008-02-01',24,28,2 );
INSERT INTO Kocury VALUES ('KSAWERY','M','MAN','LAPACZ','RAFA','2008-07-12',51,NULL,4 );
INSERT INTO Kocury VALUES ('MELA','D','DAMA','LAPACZ','RAFA','2008-11-01',51,NULL,4 );

ALTER TABLE Kocury ENABLE CONSTRAINT fk_kocury_szef;

ALTER TABLE Bandy ENABLE CONSTRAINT fk_bandy_szef;

--Wrogowie_kocurow(pseudo,imie_wroga,data_incydentu,opis_incydentu) 
INSERT INTO Wrogowie_kocurow VALUES ('TYGRYS','KAZIO','2004-10-13','USILOWAL NABIC NA WIDLY' );
INSERT INTO Wrogowie_kocurow VALUES ('ZOMBI','SWAWOLNY DYZIO','2005-03-07','WYBIL OKO Z PROCY' );
INSERT INTO Wrogowie_kocurow VALUES ('BOLEK','KAZIO','2005-03-29','POSZCZUL BURKIEM' );
INSERT INTO Wrogowie_kocurow VALUES ('SZYBKA','GLUPIA ZOSKA','2006-09-12','UZYLA KOTA JAKO SCIERKI' );
INSERT INTO Wrogowie_kocurow VALUES ('MALA','CHYTRUSEK','2007-03-07','ZALECAL SIE' );
INSERT INTO Wrogowie_kocurow VALUES ('TYGRYS','DZIKI BILL','2007-06-12','USILOWAL POZBAWIC ZYCIA' );
INSERT INTO Wrogowie_kocurow VALUES ('BOLEK','DZIKI BILL','2007-11-10','ODGRYZL UCHO' );
INSERT INTO Wrogowie_kocurow VALUES ('LASKA','DZIKI BILL','2008-12-12','POGRYZL ZE LEDWO SIE WYLIZALA' );
INSERT INTO Wrogowie_kocurow VALUES ('LASKA','KAZIO','2009-01-07','ZLAPAL ZA OGON I ZROBIL WIATRAK' );
INSERT INTO Wrogowie_kocurow VALUES ('DAMA','KAZIO','2009-02-07','CHCIAL OBEDRZEC ZE SKORY' );
INSERT INTO Wrogowie_kocurow VALUES ('MAN','REKSIO','2009-04-14','WYJATKOWO NIEGRZECZNIE OBSZCZEKAL' );
INSERT INTO Wrogowie_kocurow VALUES ('LYSY','BETHOVEN','2009-05-11','NIE PODZIELIL SIE SWOJA KASZA' );
INSERT INTO Wrogowie_kocurow VALUES ('RURA','DZIKI BILL','2009-09-03','ODGRYZL OGON' );
INSERT INTO Wrogowie_kocurow VALUES ('PLACEK','BAZYLI','2010-07-12','DZIOBIAC UNIEMOZLIWIL PODEBRANIE KURCZAKA' );
INSERT INTO Wrogowie_kocurow VALUES ('PUSZYSTA','SMUKLA','2010-11-19','OBRZUCILA SZYSZKAMI' );
INSERT INTO Wrogowie_kocurow VALUES ('KURKA','BUREK','2010-12-14','POGONIL' );
INSERT INTO Wrogowie_kocurow VALUES ('MALY','CHYTRUSEK','2011-07-13','PODEBRAL PODEBRANE JAJKA' );
INSERT INTO Wrogowie_kocurow VALUES ('UCHO','SWAWOLNY DYZIO','2011-07-14','OBRZUCIL KAMIENIAMI' );


SELECT * FROM Wrogowie;
SELECT * FROM Funkcje;
SELECT * FROM Bandy;
SELECT * FROM Kocury;
SELECT * FROM Wrogowie_kocurow;







-- LISTA 4

SET SERVEROUTPUT ON;


-- zad 1
-- a)
DECLARE
    v_liczba_szefow NUMBER := &liczba_przelozonych;
    v_max_depth NUMBER;
    v_limit NUMBER;
    v_sql VARCHAR2(32000);
    v_kolumny VARCHAR2(32000);
    v_zlaczenia VARCHAR2(32000);
    v_naglowek VARCHAR2(32000);
    v_wynik VARCHAR2(4000);
    TYPE t_ref_cursor IS REF CURSOR;
    c_kursor t_ref_cursor;
    e_nieprawidlowa_liczba EXCEPTION;
BEGIN
    IF v_liczba_szefow <= 0 THEN
        RAISE e_nieprawidlowa_liczba;
    END IF;

    SELECT MAX(LEVEL) - 1 INTO v_max_depth
    FROM Kocury
    START WITH funkcja IN ('KOT', 'MILUSIA')
    CONNECT BY PRIOR szef = pseudo;

    IF v_max_depth IS NULL THEN
        v_max_depth := 0;
    END IF;

    IF v_liczba_szefow < v_max_depth THEN
        v_limit := v_liczba_szefow;
    ELSE
        v_limit := v_max_depth;
    END IF;

    v_kolumny := 'RPAD(k0.imie, 15) || ''| ''';
    v_naglowek := RPAD('Imie', 15) || '| ';
    v_zlaczenia := '';

    FOR i IN 1 .. v_limit LOOP
        v_naglowek := v_naglowek || RPAD('Szef ' || i, 15) || '| ';
        v_kolumny := v_kolumny || ' || RPAD(NVL(k' || i || '.imie, '' ''), 15) || ''| ''';
        v_zlaczenia := v_zlaczenia || ' LEFT JOIN Kocury k' || i || ' ON k' || (i-1) || '.szef = k' || i || '.pseudo ';
    END LOOP;

    v_sql := 'SELECT ' || v_kolumny || ' FROM Kocury k0 ' || v_zlaczenia || ' WHERE k0.funkcja IN (''KOT'', ''MILUSIA'') ORDER BY k0.imie';

    DBMS_OUTPUT.PUT_LINE(v_naglowek);
    DBMS_OUTPUT.PUT_LINE(RPAD('-', LENGTH(v_naglowek)-1, '-'));

    OPEN c_kursor FOR v_sql;
    LOOP
        FETCH c_kursor INTO v_wynik;
        EXIT WHEN c_kursor%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(v_wynik);
    END LOOP;
    CLOSE c_kursor;

EXCEPTION
    WHEN e_nieprawidlowa_liczba THEN
        DBMS_OUTPUT.PUT_LINE('Blad: Liczba przelozonych musi byc wieksza od 0.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Wystapil blad: ' || SQLERRM);
        IF c_kursor%ISOPEN THEN
            CLOSE c_kursor;
        END IF;
END;
/

-- b)
DECLARE
    v_liczba_szefow NUMBER := &liczba_przelozonych;
    v_max_depth NUMBER;
    v_limit NUMBER;
    v_sql VARCHAR2(32000);
    v_kolumny VARCHAR2(32000);
    v_in_clause VARCHAR2(32000);
    v_naglowek VARCHAR2(32000);
    v_wynik VARCHAR2(4000);
    TYPE t_ref_cursor IS REF CURSOR;
    c_kursor t_ref_cursor;
    e_nieprawidlowa_liczba EXCEPTION;
BEGIN
    IF v_liczba_szefow <= 0 THEN
        RAISE e_nieprawidlowa_liczba;
    END IF;

    SELECT MAX(LEVEL) - 1 INTO v_max_depth
    FROM Kocury
    START WITH funkcja IN ('KOT', 'MILUSIA')
    CONNECT BY PRIOR szef = pseudo;

    IF v_max_depth IS NULL THEN
        v_max_depth := 0;
    END IF;

    IF v_liczba_szefow < v_max_depth THEN
        v_limit := v_liczba_szefow;
    ELSE
        v_limit := v_max_depth;
    END IF;

    v_kolumny := 'RPAD(imie_pracownika, 15) || ''| ''';
    v_naglowek := RPAD('Imie', 15) || '| ';
    v_in_clause := '';

    FOR i IN 1 .. v_limit LOOP
        v_naglowek := v_naglowek || RPAD('Szef ' || i, 15) || '| ';
        IF i > 1 THEN
            v_in_clause := v_in_clause || ', ';
        END IF;
        v_in_clause := v_in_clause || i || ' AS "' || i || '"';
        v_kolumny := v_kolumny || ' || RPAD(NVL("' || i || '", '' ''), 15) || ''| ''';
    END LOOP;

    v_sql := 'SELECT ' || v_kolumny || ' FROM (
                SELECT CONNECT_BY_ROOT imie AS imie_pracownika, imie AS imie_szefa, LEVEL - 1 AS poziom
                FROM Kocury
                START WITH funkcja IN (''KOT'', ''MILUSIA'')
                CONNECT BY PRIOR szef = pseudo
              )
              PIVOT (
                MAX(imie_szefa) FOR poziom IN (' || v_in_clause || ')
              )
              ORDER BY imie_pracownika';

    DBMS_OUTPUT.PUT_LINE(v_naglowek);
    DBMS_OUTPUT.PUT_LINE(RPAD('-', LENGTH(v_naglowek)-1, '-'));

    OPEN c_kursor FOR v_sql;
    LOOP
        FETCH c_kursor INTO v_wynik;
        EXIT WHEN c_kursor%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(v_wynik);
    END LOOP;
    CLOSE c_kursor;

EXCEPTION
    WHEN e_nieprawidlowa_liczba THEN
        DBMS_OUTPUT.PUT_LINE('Blad: Liczba przelozonych musi byc wieksza od 0.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Wystapil blad: ' || SQLERRM);
        IF c_kursor%ISOPEN THEN
            CLOSE c_kursor;
        END IF;
END;
/



-- zad 2
-- a)
DECLARE
    v_sql           VARCHAR2(32000);
    v_cols          VARCHAR2(32000); 
    v_sum_cols      VARCHAR2(32000); 
    v_separator     VARCHAR2(32000); 
    v_col_header    VARCHAR2(32000); 
    v_cursor        INTEGER;
    v_status        INTEGER;
    v_col_val       VARCHAR2(100);
    v_liczba_kolumn INTEGER := 3;    
    v_count         NUMBER;
    e_brak_funkcji  EXCEPTION;
BEGIN
    SELECT COUNT(*) INTO v_count FROM Funkcje;
    IF v_count = 0 THEN
        RAISE e_brak_funkcji;
    END IF;

    FOR r IN (SELECT funkcja FROM Funkcje ORDER BY funkcja) LOOP
        v_cols := v_cols || ', TO_CHAR(SUM(DECODE(funkcja, ''' || r.funkcja || ''', przydzial_myszy + NVL(myszy_extra, 0), 0)))';
        v_sum_cols := v_sum_cols || ', TO_CHAR(SUM(DECODE(funkcja, ''' || r.funkcja || ''', przydzial_myszy + NVL(myszy_extra, 0), 0)))';
        v_separator := v_separator || ',''---------''';
        v_col_header := v_col_header || LPAD(r.funkcja, 10);
        v_liczba_kolumn := v_liczba_kolumn + 1;
    END LOOP;
    
    v_liczba_kolumn := v_liczba_kolumn + 1;

    v_sql := '
    SELECT * FROM (
        SELECT B.nazwa,
               DECODE(K.plec, ''D'', ''Kotka'', ''Kocor'') AS plec,
               TO_CHAR(COUNT(K.pseudo)) AS ile ' 
               || v_cols || ', 
               TO_CHAR(SUM(przydzial_myszy + NVL(myszy_extra, 0))) AS suma
        FROM Kocury K
        JOIN Bandy B ON K.nr_bandy = B.nr_bandy
        GROUP BY B.nazwa, K.plec
        UNION ALL
        SELECT ''Z----------------'', ''------'', ''----'' ' || v_separator || ', ''-------'' FROM DUAL
        UNION ALL
        SELECT ''ZJADA RAZEM'', '' '', '' '' ' || v_sum_cols || ', TO_CHAR(SUM(przydzial_myszy + NVL(myszy_extra, 0))) 
        FROM Kocury
    ) ORDER BY 1, 2 DESC';

    v_cursor := DBMS_SQL.OPEN_CURSOR;
    DBMS_SQL.PARSE(v_cursor, v_sql, DBMS_SQL.NATIVE);
    
    FOR i IN 1..v_liczba_kolumn LOOP
        DBMS_SQL.DEFINE_COLUMN(v_cursor, i, v_col_val, 100);
    END LOOP;
    
    v_status := DBMS_SQL.EXECUTE(v_cursor);
    
    DBMS_OUTPUT.PUT_LINE(RPAD('NAZWA BANDY', 18) || RPAD('PLEC', 7) || RPAD('ILE', 4) || v_col_header || LPAD('SUMA', 8));
    DBMS_OUTPUT.PUT_LINE(RPAD('-', 17, '-') || ' ' || RPAD('-', 6, '-') || ' ' || RPAD('-', 4, '-') || REPLACE(REPLACE(v_separator,',',' '),'''','') || ' ' || RPAD('-', 7, '-'));
    
    LOOP
        IF DBMS_SQL.FETCH_ROWS(v_cursor) = 0 THEN EXIT; END IF;
        
        DBMS_SQL.COLUMN_VALUE(v_cursor, 1, v_col_val);
        DBMS_OUTPUT.PUT(RPAD(v_col_val, 18));
        
        DBMS_SQL.COLUMN_VALUE(v_cursor, 2, v_col_val);
        DBMS_OUTPUT.PUT(RPAD(v_col_val, 7)); 
        
        DBMS_SQL.COLUMN_VALUE(v_cursor, 3, v_col_val);
        DBMS_OUTPUT.PUT(LPAD(v_col_val, 4) || ' ');
        
        FOR i IN 4..(v_liczba_kolumn-1) LOOP
            DBMS_SQL.COLUMN_VALUE(v_cursor, i, v_col_val);
            DBMS_OUTPUT.PUT(LPAD(v_col_val, 9) || ' ');
        END LOOP;
        
        DBMS_SQL.COLUMN_VALUE(v_cursor, v_liczba_kolumn, v_col_val);
        DBMS_OUTPUT.PUT_LINE(LPAD(v_col_val, 7));
    END LOOP;
    
    DBMS_SQL.CLOSE_CURSOR(v_cursor);
EXCEPTION
    WHEN e_brak_funkcji THEN
        DBMS_OUTPUT.PUT_LINE('Blad: Brak zdefiniowanych funkcji w tabeli Funkcje.');
    WHEN OTHERS THEN
        IF DBMS_SQL.IS_OPEN(v_cursor) THEN DBMS_SQL.CLOSE_CURSOR(v_cursor); END IF;
        DBMS_OUTPUT.PUT_LINE('Blad: ' || SQLERRM);
END;
/

-- b)
DECLARE
    v_sql           VARCHAR2(32000);
    v_pivot_in      VARCHAR2(32000); 
    v_select_cols   VARCHAR2(32000); 
    v_sum_cols      VARCHAR2(32000); 
    v_separator     VARCHAR2(32000); 
    v_col_header    VARCHAR2(32000);
    v_cursor        INTEGER;
    v_status        INTEGER;
    v_col_val       VARCHAR2(100);
    v_liczba_kolumn INTEGER := 3;
    v_count         NUMBER;
    e_brak_funkcji  EXCEPTION;
    CURSOR c_funkcje IS SELECT funkcja FROM Funkcje ORDER BY funkcja;
BEGIN
    SELECT COUNT(*) INTO v_count FROM Funkcje;
    IF v_count = 0 THEN
        RAISE e_brak_funkcji;
    END IF;

    FOR r IN c_funkcje LOOP
        IF v_pivot_in IS NOT NULL THEN v_pivot_in := v_pivot_in || ', '; END IF;
        v_pivot_in := v_pivot_in || '''' || r.funkcja || ''' AS "' || r.funkcja || '"';
        v_select_cols := v_select_cols || ', TO_CHAR(NVL("' || r.funkcja || '", 0))';
        v_sum_cols := v_sum_cols || ', TO_CHAR(SUM(DECODE(funkcja, ''' || r.funkcja || ''', przydzial_myszy + NVL(myszy_extra, 0), 0)))';
        v_separator := v_separator || ',''---------''';
        v_col_header := v_col_header || LPAD(r.funkcja, 10);
        v_liczba_kolumn := v_liczba_kolumn + 1;
    END LOOP;
    
    v_liczba_kolumn := v_liczba_kolumn + 1;

    v_sql := '
    SELECT * FROM (
        SELECT nazwa, plec, TO_CHAR(ile) ' || v_select_cols || ', TO_CHAR(suma)
        FROM (
            SELECT B.nazwa,
                   DECODE(K.plec, ''D'', ''Kotka'', ''Kocor'') AS plec,
                   K.funkcja,
                   K.przydzial_myszy + NVL(K.myszy_extra, 0) AS myszy
            FROM Kocury K JOIN Bandy B ON K.nr_bandy = B.nr_bandy
        )
        PIVOT (
            SUM(myszy) FOR funkcja IN (' || v_pivot_in || ')
        )
        JOIN (
            SELECT B.nazwa, DECODE(K.plec, ''D'', ''Kotka'', ''Kocor'') AS plec, COUNT(*) as ile, SUM(przydzial_myszy + NVL(myszy_extra,0)) as suma
            FROM Kocury K JOIN Bandy B ON K.nr_bandy = B.nr_bandy
            GROUP BY B.nazwa, K.plec
        ) USING (nazwa, plec)
        UNION ALL
        SELECT ''Z----------------'', ''------'', ''----'' ' || v_separator || ', ''-------'' FROM DUAL
        UNION ALL
        SELECT ''ZJADA RAZEM'', '' '', '' '' ' || v_sum_cols || ', TO_CHAR(SUM(przydzial_myszy + NVL(myszy_extra, 0)))
        FROM Kocury
    ) ORDER BY 1, 2 DESC';

    v_cursor := DBMS_SQL.OPEN_CURSOR;
    DBMS_SQL.PARSE(v_cursor, v_sql, DBMS_SQL.NATIVE);
    
    FOR i IN 1..v_liczba_kolumn LOOP
        DBMS_SQL.DEFINE_COLUMN(v_cursor, i, v_col_val, 100);
    END LOOP;
    
    v_status := DBMS_SQL.EXECUTE(v_cursor);
    
    DBMS_OUTPUT.PUT_LINE(RPAD('NAZWA BANDY', 18) || RPAD('PLEC', 7) || RPAD('ILE', 4) || v_col_header || LPAD('SUMA', 8));
    DBMS_OUTPUT.PUT_LINE(RPAD('-', 17, '-') || ' ' || RPAD('-', 6, '-') || ' ' || RPAD('-', 4, '-') || REPLACE(REPLACE(v_separator,',',' '),'''','') || ' ' || RPAD('-', 7, '-'));

    LOOP
        IF DBMS_SQL.FETCH_ROWS(v_cursor) = 0 THEN EXIT; END IF;
        
        DBMS_SQL.COLUMN_VALUE(v_cursor, 1, v_col_val);
        DBMS_OUTPUT.PUT(RPAD(v_col_val, 18));
        
        DBMS_SQL.COLUMN_VALUE(v_cursor, 2, v_col_val);
        DBMS_OUTPUT.PUT(RPAD(v_col_val, 7));
        
        DBMS_SQL.COLUMN_VALUE(v_cursor, 3, v_col_val);
        DBMS_OUTPUT.PUT(LPAD(v_col_val, 4) || ' ');
        
        FOR i IN 4..(v_liczba_kolumn-1) LOOP
            DBMS_SQL.COLUMN_VALUE(v_cursor, i, v_col_val);
            DBMS_OUTPUT.PUT(LPAD(v_col_val, 9) || ' ');
        END LOOP;
        
        DBMS_SQL.COLUMN_VALUE(v_cursor, v_liczba_kolumn, v_col_val);
        DBMS_OUTPUT.PUT_LINE(LPAD(v_col_val, 7));
    END LOOP;
    
    DBMS_SQL.CLOSE_CURSOR(v_cursor);

EXCEPTION
    WHEN e_brak_funkcji THEN
        DBMS_OUTPUT.PUT_LINE('Blad: Brak zdefiniowanych funkcji w tabeli Funkcje.');
    WHEN OTHERS THEN
        IF DBMS_SQL.IS_OPEN(v_cursor) THEN DBMS_SQL.CLOSE_CURSOR(v_cursor); END IF;
        DBMS_OUTPUT.PUT_LINE('Blad: ' || SQLERRM);
END;
/



-- zad 3
CREATE OR REPLACE TRIGGER trg_nadaj_nr_bandy
BEFORE INSERT ON Bandy
FOR EACH ROW
BEGIN
  SELECT NVL(MAX(nr_bandy), 0) + 1 
  INTO :NEW.nr_bandy 
  FROM Bandy;
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Wystąpił błąd: ' || SQLERRM);
END;
/

DECLARE
    v_max_przed NUMBER;
    v_max_po NUMBER;
    v_nazwa_testowa VARCHAR2(20) := 'TESTOWA_BANDA';
BEGIN
    SELECT MAX(nr_bandy) INTO v_max_przed FROM Bandy;
    DBMS_OUTPUT.PUT_LINE('Maksymalny numer bandy przed wstawieniem: ' || v_max_przed);

    INSERT INTO Bandy (nazwa, teren, szef_bandy) 
    VALUES (v_nazwa_testowa, 'POLIGON', NULL);

    SELECT MAX(nr_bandy) INTO v_max_po FROM Bandy;
    DBMS_OUTPUT.PUT_LINE('Maksymalny numer bandy po wstawieniu (wyzwalacz): ' || v_max_po);

    IF v_max_po = v_max_przed + 1 THEN
        DBMS_OUTPUT.PUT_LINE('Test OK: Wyzwalacz nadał poprawny numer.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Test BŁĄD: Numer niepoprawny.');
    END IF;
    
    DELETE FROM Bandy WHERE nazwa = v_nazwa_testowa;
    DBMS_OUTPUT.PUT_LINE('Usunięto dane testowe.');
END;
/

DROP TRIGGER trg_nadaj_nr_bandy;



-- zad 4
CREATE TABLE Proby_wykroczenia (
    kto VARCHAR2(30),
    kiedy DATE,
    jakiemu VARCHAR2(15),
    operacja VARCHAR2(20)
);

CREATE OR REPLACE TRIGGER trg_weryfikacja_przydzialu
BEFORE INSERT OR UPDATE OF przydzial_myszy ON Kocury
FOR EACH ROW
DECLARE
    v_min_myszy Funkcje.min_myszy%TYPE;
    v_max_myszy Funkcje.max_myszy%TYPE;
    v_operacja VARCHAR2(20);
    e_poza_zakresem EXCEPTION;
    PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
    SELECT min_myszy, max_myszy 
    INTO v_min_myszy, v_max_myszy 
    FROM Funkcje 
    WHERE funkcja = :NEW.funkcja;

    IF NVL(:NEW.przydzial_myszy, 0) < v_min_myszy OR NVL(:NEW.przydzial_myszy, 0) > v_max_myszy THEN
        RAISE e_poza_zakresem;
    END IF;

EXCEPTION
    WHEN e_poza_zakresem THEN
        IF INSERTING THEN
            v_operacja := 'INSERT';
        ELSE
            v_operacja := 'UPDATE';
        END IF;

        INSERT INTO Proby_wykroczenia (kto, kiedy, jakiemu, operacja)
        VALUES (USER, SYSDATE, :NEW.pseudo, v_operacja);
        
        COMMIT;
        
        RAISE_APPLICATION_ERROR(-20001, 'Przydział myszy ' || :NEW.przydzial_myszy || ' jest poza dozwolonym zakresem <' || v_min_myszy || ', ' || v_max_myszy || '>');
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Nie znaleziono definicji dla funkcji: ' || :NEW.funkcja);
        ROLLBACK;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Wystąpił nieoczekiwany błąd: ' || SQLERRM);
        ROLLBACK;
END;
/

DECLARE
    v_test_pseudo VARCHAR2(15) := 'PLACEK'; 
    v_old_myszy NUMBER;
BEGIN
    SELECT przydzial_myszy INTO v_old_myszy FROM Kocury WHERE pseudo = v_test_pseudo;
    
    BEGIN
        DBMS_OUTPUT.PUT_LINE('Próba 1 (Poprawna): Ustawienie 65 (zakres 60-70 dla LOWCZY)');
        UPDATE Kocury SET przydzial_myszy = 65 WHERE pseudo = v_test_pseudo;
        DBMS_OUTPUT.PUT_LINE('Sukces: Zmiana zatwierdzona.');
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Błąd w próbie poprawnej: ' || SQLERRM);
    END;

    BEGIN
        DBMS_OUTPUT.PUT_LINE('Próba 2 (Błędna): Ustawienie 100 (poza zakresem)');
        UPDATE Kocury SET przydzial_myszy = 100 WHERE pseudo = v_test_pseudo;
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Oczekiwany błąd przechwycony: ' || SQLERRM);
    END;

    FOR r IN (SELECT * FROM Proby_wykroczenia WHERE jakiemu = v_test_pseudo) LOOP
        DBMS_OUTPUT.PUT_LINE('Log wykroczenia: Kto=' || r.kto || ', Operacja=' || r.operacja);
    END LOOP;

    UPDATE Kocury SET przydzial_myszy = v_old_myszy WHERE pseudo = v_test_pseudo;
END;
/

DROP TRIGGER trg_weryfikacja_przydzialu;
DROP TABLE Proby_wykroczenia;



-- zad 5
-- a)
CREATE OR REPLACE PACKAGE wirus_pakiet AS
    przydzial_tygrysa NUMBER;
    do_kary NUMBER := 0;
    do_nagrody NUMBER := 0;
END;
/

CREATE OR REPLACE TRIGGER wirus_bst
BEFORE UPDATE OF przydzial_myszy ON Kocury
BEGIN
    SELECT przydzial_myszy INTO wirus_pakiet.przydzial_tygrysa
    FROM Kocury WHERE pseudo = 'TYGRYS';
    
    wirus_pakiet.do_kary := 0;
    wirus_pakiet.do_nagrody := 0;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Błąd w triggerze BEFORE STATEMENT: ' || SQLERRM);
END;
/

CREATE OR REPLACE TRIGGER wirus_br
BEFORE UPDATE OF przydzial_myszy ON Kocury
FOR EACH ROW
DECLARE
    v_min_zmiana NUMBER;
BEGIN
    IF :NEW.funkcja = 'MILUSIA' THEN
        IF :NEW.przydzial_myszy < :OLD.przydzial_myszy THEN
            :NEW.przydzial_myszy := :OLD.przydzial_myszy;
        ELSE
            v_min_zmiana := 0.1 * wirus_pakiet.przydzial_tygrysa;
            
            IF (:NEW.przydzial_myszy - :OLD.przydzial_myszy) < v_min_zmiana THEN
                :NEW.przydzial_myszy := :NEW.przydzial_myszy + v_min_zmiana;
                :NEW.myszy_extra := NVL(:NEW.myszy_extra, 0) + 5;
                wirus_pakiet.do_kary := wirus_pakiet.do_kary + v_min_zmiana;
            ELSE
                wirus_pakiet.do_nagrody := wirus_pakiet.do_nagrody + 5;
            END IF;
        END IF;
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Błąd w triggerze BEFORE ROW: ' || SQLERRM);
END;
/

CREATE OR REPLACE TRIGGER wirus_ast
AFTER UPDATE OF przydzial_myszy ON Kocury
BEGIN
    IF wirus_pakiet.do_kary > 0 OR wirus_pakiet.do_nagrody > 0 THEN
        UPDATE Kocury
        SET przydzial_myszy = przydzial_myszy - wirus_pakiet.do_kary,
            myszy_extra = NVL(myszy_extra, 0) + wirus_pakiet.do_nagrody
        WHERE pseudo = 'TYGRYS';
        
        wirus_pakiet.do_kary := 0;
        wirus_pakiet.do_nagrody := 0;
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Błąd w triggerze AFTER STATEMENT: ' || SQLERRM);
END;
/

DECLARE
    v_lola_przed NUMBER;
    v_tygrys_przed NUMBER;
    v_lola_po NUMBER;
    v_tygrys_po NUMBER;
BEGIN
    SELECT przydzial_myszy INTO v_lola_przed FROM Kocury WHERE pseudo = 'LOLA';
    SELECT przydzial_myszy INTO v_tygrys_przed FROM Kocury WHERE pseudo = 'TYGRYS';
    
    DBMS_OUTPUT.PUT_LINE('Przed zmianą: LOLA=' || v_lola_przed || ', TYGRYS=' || v_tygrys_przed);
    
    UPDATE Kocury SET przydzial_myszy = przydzial_myszy + 1 WHERE pseudo = 'LOLA';
    
    SELECT przydzial_myszy INTO v_lola_po FROM Kocury WHERE pseudo = 'LOLA';
    SELECT przydzial_myszy INTO v_tygrys_po FROM Kocury WHERE pseudo = 'TYGRYS';
    
    DBMS_OUTPUT.PUT_LINE('Po zmianie (+1): LOLA=' || v_lola_po || ', TYGRYS=' || v_tygrys_po);
    
    UPDATE Kocury SET przydzial_myszy = v_lola_przed WHERE pseudo = 'LOLA';
    UPDATE Kocury SET przydzial_myszy = v_tygrys_przed WHERE pseudo = 'TYGRYS';
    DBMS_OUTPUT.PUT_LINE('Przywrócono dane.');
END;
/

DROP TRIGGER wirus_bst;
DROP TRIGGER wirus_br;
DROP TRIGGER wirus_ast;
DROP PACKAGE wirus_pakiet;

-- b)
CREATE OR REPLACE TRIGGER wirus_compound
FOR UPDATE OF przydzial_myszy ON Kocury
COMPOUND TRIGGER
    v_przydzial_tygrysa NUMBER;
    v_do_kary NUMBER := 0;
    v_do_nagrody NUMBER := 0;
    v_min_zmiana NUMBER;

    BEFORE STATEMENT IS
    BEGIN
        SELECT przydzial_myszy INTO v_przydzial_tygrysa
        FROM Kocury WHERE pseudo = 'TYGRYS';
        v_do_kary := 0;
        v_do_nagrody := 0;
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Błąd w sekcji BEFORE STATEMENT: ' || SQLERRM);
    END BEFORE STATEMENT;

    BEFORE EACH ROW IS
    BEGIN
        IF :NEW.funkcja = 'MILUSIA' THEN
            IF :NEW.przydzial_myszy < :OLD.przydzial_myszy THEN
                :NEW.przydzial_myszy := :OLD.przydzial_myszy;
            ELSE
                v_min_zmiana := 0.1 * v_przydzial_tygrysa;
                
                IF (:NEW.przydzial_myszy - :OLD.przydzial_myszy) < v_min_zmiana THEN
                    :NEW.przydzial_myszy := :NEW.przydzial_myszy + v_min_zmiana;
                    :NEW.myszy_extra := NVL(:NEW.myszy_extra, 0) + 5;
                    v_do_kary := v_do_kary + v_min_zmiana;
                ELSE
                    v_do_nagrody := v_do_nagrody + 5;
                END IF;
            END IF;
        END IF;
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Błąd w sekcji BEFORE EACH ROW: ' || SQLERRM);
    END BEFORE EACH ROW;

    AFTER STATEMENT IS
    BEGIN
        IF v_do_kary > 0 OR v_do_nagrody > 0 THEN
            UPDATE Kocury
            SET przydzial_myszy = przydzial_myszy - v_do_kary,
                myszy_extra = NVL(myszy_extra, 0) + v_do_nagrody
            WHERE pseudo = 'TYGRYS';
        END IF;
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Błąd w sekcji AFTER STATEMENT: ' || SQLERRM);
    END AFTER STATEMENT;

END;
/

DECLARE
    v_lola_przed NUMBER;
    v_tygrys_przed NUMBER;
    v_lola_po NUMBER;
    v_tygrys_po NUMBER;
BEGIN
    SELECT przydzial_myszy INTO v_lola_przed FROM Kocury WHERE pseudo = 'LOLA';
    SELECT przydzial_myszy INTO v_tygrys_przed FROM Kocury WHERE pseudo = 'TYGRYS';
    
    DBMS_OUTPUT.PUT_LINE('Przed zmianą (Compound): LOLA=' || v_lola_przed || ', TYGRYS=' || v_tygrys_przed);
    
    UPDATE Kocury SET przydzial_myszy = przydzial_myszy + 1 WHERE pseudo = 'LOLA';
    
    SELECT przydzial_myszy INTO v_lola_po FROM Kocury WHERE pseudo = 'LOLA';
    SELECT przydzial_myszy INTO v_tygrys_po FROM Kocury WHERE pseudo = 'TYGRYS';
    
    DBMS_OUTPUT.PUT_LINE('Po zmianie (+1): LOLA=' || v_lola_po || ', TYGRYS=' || v_tygrys_po);
    
    UPDATE Kocury SET przydzial_myszy = v_lola_przed WHERE pseudo = 'LOLA';
    UPDATE Kocury SET przydzial_myszy = v_tygrys_przed WHERE pseudo = 'TYGRYS';
    DBMS_OUTPUT.PUT_LINE('Przywrócono dane.');
END;
/

DROP TRIGGER wirus_compound;



-- zad 6
CREATE TABLE Dodatki_extra (
    pseudo VARCHAR2(15),
    dodatek_extra NUMBER(3)
);

CREATE OR REPLACE TRIGGER zemsta_tygrysa
BEFORE UPDATE OF przydzial_myszy ON Kocury
FOR EACH ROW
DECLARE
    PRAGMA AUTONOMOUS_TRANSACTION;
    v_sql VARCHAR2(1000);
BEGIN
    IF LOGIN_USER != 'TYGRYS' 
       AND :NEW.funkcja = 'MILUSIA' 
       AND :NEW.przydzial_myszy > :OLD.przydzial_myszy 
    THEN
        FOR r IN (SELECT pseudo FROM Kocury WHERE funkcja = 'MILUSIA') 
        LOOP
            v_sql := 'MERGE INTO Dodatki_extra D 
                      USING (SELECT :1 AS pseudo FROM DUAL) S 
                      ON (D.pseudo = S.pseudo) 
                      WHEN MATCHED THEN 
                        UPDATE SET D.dodatek_extra = D.dodatek_extra - 10 
                      WHEN NOT MATCHED THEN 
                        INSERT (pseudo, dodatek_extra) VALUES (:2, -10)';
                        
            EXECUTE IMMEDIATE v_sql USING r.pseudo, r.pseudo;
        END LOOP;
        
        COMMIT; 
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Błąd w wyzwalaczu zemsta_tygrysa: ' || SQLERRM);
        ROLLBACK; 
END;
/

DECLARE
    v_lola_myszy NUMBER;
BEGIN
    SELECT przydzial_myszy INTO v_lola_myszy FROM Kocury WHERE pseudo = 'LOLA';
    
    UPDATE Kocury SET przydzial_myszy = przydzial_myszy + 5 WHERE pseudo = 'LOLA';
    
    DBMS_OUTPUT.PUT_LINE('Zawartość tabeli Dodatki_extra po podwyżce dla MILUSIEJ:');
    FOR r IN (SELECT * FROM Dodatki_extra) LOOP
        DBMS_OUTPUT.PUT_LINE('Pseudo: ' || r.pseudo || ', Dodatek: ' || r.dodatek_extra);
    END LOOP;

    UPDATE Kocury SET przydzial_myszy = v_lola_myszy WHERE pseudo = 'LOLA';
END;
/

DROP TRIGGER zemsta_tygrysa;
DROP TABLE Dodatki_extra;



-- zad 7
-- KROK 1: TWORZENIE SEKWENCJI I TABELI (DYNAMICZNY SQL)
CREATE SEQUENCE myszy_seq
 START WITH 1
 INCREMENT BY 1
 NOMAXVALUE;

BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE Myszy (
        nr_myszy NUMBER(10) CONSTRAINT pk_myszy PRIMARY KEY,
        lowca VARCHAR2(15) CONSTRAINT fk_myszy_lowca REFERENCES Kocury(pseudo),
        zjadacz VARCHAR2(15) CONSTRAINT fk_myszy_zjadacz REFERENCES Kocury(pseudo),
        waga_myszy NUMBER(3) CONSTRAINT chk_myszy_waga CHECK (waga_myszy BETWEEN 15 AND 40),
        data_zlowienia DATE CONSTRAINT nn_myszy_data_zl NOT NULL,
        data_wydania DATE,
        CONSTRAINT chk_myszy_daty CHECK (data_zlowienia <= data_wydania)
    )';
END;
/

-- KROK 2: WYPEŁNIANIE DANYMI HISTORYCZNYMI (WIĄZANIE MASOWE)
DECLARE
    v_start_date DATE := TO_DATE('2004-01-01', 'YYYY-MM-DD');
    v_end_date DATE := TRUNC(SYSDATE - 1);
    v_iter_date DATE;
    v_last_wednesday DATE;
    
    TYPE t_pseudo IS TABLE OF Kocury.pseudo%TYPE;
    TYPE t_limit IS TABLE OF NUMBER;
    
    v_kocury t_pseudo;
    v_limity t_limit;
    
    TYPE t_mysz IS RECORD (
        nr_myszy NUMBER(10),
        lowca VARCHAR2(15),
        zjadacz VARCHAR2(15),
        waga_myszy NUMBER(3),
        data_zlowienia DATE,
        data_wydania DATE
    );
    TYPE t_myszy_table IS TABLE OF t_mysz;
    v_myszy_batch t_myszy_table := t_myszy_table();
    
    v_total_myszy_mc NUMBER;
    v_avg_myszy_kot NUMBER;
    v_myszy_idx NUMBER := 0;
    v_hunter_idx NUMBER;
    v_eater_idx NUMBER;
    v_current_eater_limit NUMBER;
    v_temp_limit t_limit;
    
    e_zla_data EXCEPTION;
BEGIN
    IF v_start_date > v_end_date THEN
        RAISE e_zla_data;
    END IF;

    v_iter_date := v_start_date;

    LOOP
        v_last_wednesday := NEXT_DAY(LAST_DAY(v_iter_date) - 7, 'ŚRODA');
        
        IF v_last_wednesday > v_end_date THEN
            v_last_wednesday := NEXT_DAY(LAST_DAY(ADD_MONTHS(v_iter_date, -1)) - 7, 'ŚRODA');
            EXIT WHEN v_iter_date > v_end_date; 
        END IF;

        IF v_iter_date > v_last_wednesday THEN
            v_iter_date := ADD_MONTHS(v_iter_date, 1);
            v_iter_date := TRUNC(v_iter_date, 'MM'); 
            CONTINUE;
        END IF;

        SELECT pseudo, NVL(przydzial_myszy, 0) + NVL(myszy_extra, 0)
        BULK COLLECT INTO v_kocury, v_limity
        FROM Kocury
        WHERE w_stadku_od < v_last_wednesday;

        IF v_kocury.COUNT > 0 THEN
            v_total_myszy_mc := 0;
            v_temp_limit := v_limity;
            
            FOR i IN 1..v_limity.COUNT LOOP
                v_total_myszy_mc := v_total_myszy_mc + v_limity(i);
            END LOOP;

            v_avg_myszy_kot := CEIL(v_total_myszy_mc / v_kocury.COUNT);
            v_eater_idx := 1;

            FOR i IN 1..v_total_myszy_mc LOOP
                v_myszy_batch.EXTEND;
                v_myszy_idx := v_myszy_idx + 1;
                
                v_myszy_batch(v_myszy_idx).nr_myszy := myszy_seq.NEXTVAL;
                v_myszy_batch(v_myszy_idx).waga_myszy := DBMS_RANDOM.VALUE(15, 40);
                v_myszy_batch(v_myszy_idx).data_wydania := v_last_wednesday;
                
                v_hunter_idx := MOD(i, v_kocury.COUNT) + 1; 
                v_myszy_batch(v_myszy_idx).lowca := v_kocury(v_hunter_idx);
                
                v_myszy_batch(v_myszy_idx).data_zlowienia := 
                    TRUNC(v_iter_date, 'MM') + DBMS_RANDOM.VALUE(0, v_last_wednesday - TRUNC(v_iter_date, 'MM'));

                LOOP
                    IF v_eater_idx > v_kocury.COUNT THEN
                        v_eater_idx := 1;
                    END IF;
                    
                    IF v_temp_limit(v_eater_idx) > 0 THEN
                        v_myszy_batch(v_myszy_idx).zjadacz := v_kocury(v_eater_idx);
                        v_temp_limit(v_eater_idx) := v_temp_limit(v_eater_idx) - 1;
                        v_eater_idx := v_eater_idx + 1;
                        EXIT;
                    ELSE
                        v_eater_idx := v_eater_idx + 1;
                    END IF;
                END LOOP;
            END LOOP;
        END IF;

        v_iter_date := ADD_MONTHS(v_iter_date, 1);
        v_iter_date := TRUNC(v_iter_date, 'MM'); 
        EXIT WHEN v_iter_date > v_end_date;
    END LOOP;

    FORALL i IN 1..v_myszy_batch.COUNT
        INSERT INTO Myszy VALUES (
            v_myszy_batch(i).nr_myszy,
            v_myszy_batch(i).lowca,
            v_myszy_batch(i).zjadacz,
            v_myszy_batch(i).waga_myszy,
            v_myszy_batch(i).data_zlowienia,
            v_myszy_batch(i).data_wydania
        );

EXCEPTION
    WHEN e_zla_data THEN
        DBMS_OUTPUT.PUT_LINE('Data startowa nie może być późniejsza niż końcowa!');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Błąd podczas generowania danych: ' || SQLERRM);
END;
/

-- KROK 3: PROCEDURA PRZYJĘCIA MYSZY (DYNAMICZNY SQL + BULK)
CREATE OR REPLACE PROCEDURE przyjmij_na_stan (
    p_pseudo IN VARCHAR2,
    p_data   IN DATE
) IS
    TYPE t_mysz_ext IS RECORD (
        nr_myszy NUMBER(10),
        waga     NUMBER(3)
    );
    TYPE t_mysz_ext_tab IS TABLE OF t_mysz_ext;
    
    v_myszy_ext t_mysz_ext_tab;
    v_table_name VARCHAR2(50);
    v_sql_select VARCHAR2(1000);
    v_sql_delete VARCHAR2(1000);
    v_kot_exists NUMBER;
    
    e_brak_kota EXCEPTION;
    e_brak_tabeli EXCEPTION;
    PRAGMA EXCEPTION_INIT(e_brak_tabeli, -00942); 

BEGIN
    SELECT COUNT(*) INTO v_kot_exists FROM Kocury WHERE pseudo = p_pseudo;
    IF v_kot_exists = 0 THEN
        RAISE e_brak_kota;
    END IF;

    v_table_name := 'MYSZY_KOTA_' || UPPER(p_pseudo);
    v_sql_select := 'SELECT nr_myszy, waga_myszy FROM ' || v_table_name || ' WHERE data_zlowienia = :1';
    v_sql_delete := 'DELETE FROM ' || v_table_name || ' WHERE data_zlowienia = :1';

    EXECUTE IMMEDIATE v_sql_select BULK COLLECT INTO v_myszy_ext USING p_data;

    IF v_myszy_ext.COUNT > 0 THEN
        FORALL i IN 1..v_myszy_ext.COUNT
            INSERT INTO Myszy (nr_myszy, lowca, zjadacz, waga_myszy, data_zlowienia, data_wydania)
            VALUES (
                myszy_seq.NEXTVAL, 
                p_pseudo, 
                NULL, 
                v_myszy_ext(i).waga, 
                p_data, 
                NULL
            );
            
        EXECUTE IMMEDIATE v_sql_delete USING p_data;
    ELSE
        DBMS_OUTPUT.PUT_LINE('Brak myszy złowionych w dniu ' || TO_CHAR(p_data, 'YYYY-MM-DD') || ' przez kota ' || p_pseudo);
    END IF;

EXCEPTION
    WHEN e_brak_kota THEN
        DBMS_OUTPUT.PUT_LINE('Nie znaleziono kota o pseudonimie: ' || p_pseudo);
    WHEN e_brak_tabeli THEN
        DBMS_OUTPUT.PUT_LINE('Błąd: Tabela zewnętrzna ' || v_table_name || ' nie istnieje!');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Wystąpił błąd w procedurze przyjmij_na_stan: ' || SQLERRM);
END;
/

-- KROK 4: PROCEDURA WYPŁATY (WIĄZANIE MASOWE + HIERARCHIA)
CREATE OR REPLACE PROCEDURE wyplata IS
    v_next_wednesday DATE;
    
    TYPE t_kot_rec IS RECORD (
        pseudo VARCHAR2(15),
        zapotrzebowanie NUMBER
    );
    TYPE t_kot_tab IS TABLE OF t_kot_rec;
    v_koty t_kot_tab;
    
    TYPE t_mysz_id IS TABLE OF NUMBER(10);
    v_myszy_ids t_mysz_id;
    
    TYPE t_update_rec IS RECORD (
        nr_myszy NUMBER(10),
        zjadacz VARCHAR2(15)
    );
    TYPE t_update_tab IS TABLE OF t_update_rec;
    v_updates t_update_tab := t_update_tab();
    
    v_total_needed NUMBER := 0;
    v_mysz_idx NUMBER := 1;
    v_kot_idx NUMBER := 1;
    v_assigned_count NUMBER := 0;
    
    e_brak_myszy EXCEPTION;
BEGIN
    v_next_wednesday := NEXT_DAY(LAST_DAY(SYSDATE) - 7, 'ŚRODA');
    
    SELECT pseudo, NVL(przydzial_myszy, 0) + NVL(myszy_extra, 0)
    BULK COLLECT INTO v_koty
    FROM Kocury
    CONNECT BY PRIOR pseudo = szef
    START WITH szef IS NULL
    ORDER BY LEVEL, przydzial_myszy DESC;

    FOR i IN 1..v_koty.COUNT LOOP
        v_total_needed := v_total_needed + v_koty(i).zapotrzebowanie;
    END LOOP;

    SELECT nr_myszy
    BULK COLLECT INTO v_myszy_ids
    FROM Myszy
    WHERE zjadacz IS NULL AND data_wydania IS NULL;

    IF v_myszy_ids.COUNT = 0 THEN
        RAISE e_brak_myszy;
    END IF;

    WHILE v_mysz_idx <= v_myszy_ids.COUNT AND v_total_needed > 0 LOOP
        IF v_koty(v_kot_idx).zapotrzebowanie > 0 THEN
            v_updates.EXTEND;
            v_assigned_count := v_assigned_count + 1;
            
            v_updates(v_assigned_count).nr_myszy := v_myszy_ids(v_mysz_idx);
            v_updates(v_assigned_count).zjadacz := v_koty(v_kot_idx).pseudo;
            
            v_koty(v_kot_idx).zapotrzebowanie := v_koty(v_kot_idx).zapotrzebowanie - 1;
            v_total_needed := v_total_needed - 1;
            v_mysz_idx := v_mysz_idx + 1;
        END IF;
        
        v_kot_idx := v_kot_idx + 1;
        IF v_kot_idx > v_koty.COUNT THEN
            v_kot_idx := 1;
        END IF;
    END LOOP;

    FORALL i IN 1..v_updates.COUNT
        UPDATE Myszy
        SET zjadacz = v_updates(i).zjadacz,
            data_wydania = v_next_wednesday
        WHERE nr_myszy = v_updates(i).nr_myszy;

EXCEPTION
    WHEN e_brak_myszy THEN
        DBMS_OUTPUT.PUT_LINE('Brak myszy w magazynie do wypłaty!');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Błąd podczas wypłaty: ' || SQLERRM);
END;
/

-- KROK 5: INICJALIZACJA TABEL ZEWNĘTRZNYCH (DLA WSZYSTKICH KOTÓW)
BEGIN
    FOR k IN (SELECT pseudo FROM Kocury) LOOP
        BEGIN
            EXECUTE IMMEDIATE 'CREATE TABLE MYSZY_KOTA_' || UPPER(k.pseudo) || ' (
                nr_myszy NUMBER(10),
                waga_myszy NUMBER(3),
                data_zlowienia DATE
            )';
        EXCEPTION
            WHEN OTHERS THEN NULL; 
        END;
    END LOOP;
END;
/

-- KROK 6: TEST WERYFIKUJĄCY DZIAŁANIE
SET SERVEROUTPUT ON;

DECLARE
    v_pseudo_kota   VARCHAR2(15) := 'TYGRYS';
    v_data_lowow    DATE := TO_DATE('2024-05-15', 'YYYY-MM-DD'); -- Przykładowa data
    v_count_before  NUMBER;
    v_count_after   NUMBER;
    v_table_name    VARCHAR2(50);
BEGIN
    DBMS_OUTPUT.PUT_LINE('==================================================');
    DBMS_OUTPUT.PUT_LINE('ROZPOCZĘCIE TESTU PROCEDUR EWIDENCJI MYSZY');
    DBMS_OUTPUT.PUT_LINE('==================================================');

    -- 1. PRZYGOTOWANIE ŚRODOWISKA
    v_table_name := 'MYSZY_KOTA_' || v_pseudo_kota;
    
    BEGIN
        EXECUTE IMMEDIATE 'DROP TABLE ' || v_table_name;
    EXCEPTION
        WHEN OTHERS THEN NULL;
    END;

    EXECUTE IMMEDIATE 'CREATE TABLE ' || v_table_name || ' (nr_myszy NUMBER, waga_myszy NUMBER, data_zlowienia DATE)';
    EXECUTE IMMEDIATE 'INSERT INTO ' || v_table_name || ' VALUES (9001, 25, :1)' USING v_data_lowow;
    EXECUTE IMMEDIATE 'INSERT INTO ' || v_table_name || ' VALUES (9002, 35, :1)' USING v_data_lowow;
    EXECUTE IMMEDIATE 'INSERT INTO ' || v_table_name || ' VALUES (9003, 18, :1)' USING v_data_lowow;
    
    DBMS_OUTPUT.PUT_LINE('[1] Utworzono zewnętrzną tabelę ' || v_table_name || ' i dodano 3 myszy.');

    SELECT COUNT(*) INTO v_count_before FROM Myszy 
    WHERE lowca = v_pseudo_kota AND data_zlowienia = v_data_lowow;
    
    DBMS_OUTPUT.PUT_LINE('    -> Liczba myszy w głownej bazie przed przyjęciem: ' || v_count_before);

    -- 2. TEST PROCEDURY: PRZYJMIJ_NA_STAN
    DBMS_OUTPUT.PUT_LINE('[2] Uruchamianie procedury PRZYJMIJ_NA_STAN...');
    przyjmij_na_stan(v_pseudo_kota, v_data_lowow);

    SELECT COUNT(*) INTO v_count_after FROM Myszy 
    WHERE lowca = v_pseudo_kota AND data_zlowienia = v_data_lowow AND zjadacz IS NULL;
    
    DBMS_OUTPUT.PUT_LINE('    -> Liczba myszy w głównej bazie po przyjęciu (do rozdania): ' || v_count_after);
    
    IF v_count_after = v_count_before + 3 THEN
        DBMS_OUTPUT.PUT_LINE('    -> SUKCES: Myszy zostały poprawnie przeniesione.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('    -> BŁĄD: Liczba myszy się nie zgadza!');
    END IF;

    -- 3. TEST PROCEDURY: WYPLATA
    DBMS_OUTPUT.PUT_LINE('[3] Uruchamianie procedury WYPLATA...');
    wyplata();

    DBMS_OUTPUT.PUT_LINE('[4] Weryfikacja przydziałów dla dodanych myszy:');
    
    FOR r IN (
        SELECT nr_myszy, waga_myszy, zjadacz, data_wydania 
        FROM Myszy 
        WHERE lowca = v_pseudo_kota 
          AND data_zlowienia = v_data_lowow
          AND waga_myszy IN (25, 35, 18)
    ) LOOP
        DBMS_OUTPUT.PUT_LINE('    -> Mysz waga ' || r.waga_myszy || 
                             ' | Zjedzona przez: ' || NVL(r.zjadacz, 'JESZCZE W MAGAZYNIE') || 
                             ' | Data wydania: ' || NVL(TO_CHAR(r.data_wydania, 'YYYY-MM-DD'), '-'));
    END LOOP;

    -- 4. SPRZĄTANIE
    BEGIN
        EXECUTE IMMEDIATE 'DROP TABLE ' || v_table_name;
    EXCEPTION
        WHEN OTHERS THEN NULL;
    END;
    DBMS_OUTPUT.PUT_LINE('==================================================');
    DBMS_OUTPUT.PUT_LINE('KONIEC TESTU');
    DBMS_OUTPUT.PUT_LINE('==================================================');

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('!!! WYSTĄPIŁ BŁĄD KRYTYCZNY W TEŚCIE: ' || SQLERRM);
END;
/
