
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







-- LISTA 3

SET SERVEROUTPUT ON;

-- zad 1
DECLARE
    v_funkcja Kocury.funkcja%TYPE := UPPER('&podaj_funkcje');
    v_znaleziona Kocury.funkcja%TYPE;
BEGIN
    SELECT funkcja 
    INTO v_znaleziona
    FROM Kocury
    WHERE funkcja = v_funkcja
      AND ROWNUM = 1;

    DBMS_OUTPUT.PUT_LINE('Znaleziono kota pelniacego funkcje: ' || v_znaleziona);

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Nie znaleziono kota pelniacego funkcje: ' || v_funkcja);
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Blad: ' || SQLERRM);
END;


-- zad 3
DECLARE
    v_pseudo Kocury.pseudo%TYPE := UPPER('&podaj_pseudo');
    v_imie Kocury.imie%TYPE;
    v_roczny_przydzial NUMBER;
    v_miesiac_wstapienia NUMBER;
BEGIN
    SELECT imie, 
           (NVL(przydzial_myszy, 0) + NVL(myszy_extra, 0)) * 12, 
           EXTRACT(MONTH FROM w_stadku_od)
    INTO v_imie, v_roczny_przydzial, v_miesiac_wstapienia
    FROM Kocury
    WHERE pseudo = v_pseudo;

    -- hierarchia sprawdzania warunkow (IF-ELSIF gwarantuje wyjscie po pierwszym sukcesie)
    IF v_roczny_przydzial > 700 THEN
        DBMS_OUTPUT.PUT_LINE('calkowity roczny przydzial myszy >700');
    ELSIF v_imie LIKE '%A%' THEN
        DBMS_OUTPUT.PUT_LINE('imię zawiera litere A');
    ELSIF v_miesiac_wstapienia = 5 THEN
        DBMS_OUTPUT.PUT_LINE('maj jest miesiacem przystapienia do stada');
    ELSE
        DBMS_OUTPUT.PUT_LINE('nie odpowiada kryteriom');
    END IF;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Nie znaleziono kota o pseudo: ' || v_pseudo);
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Blad: ' || SQLERRM);
END;


-- zad 4
DECLARE
    TYPE t_rekord_kota IS RECORD (
        imie         Kocury.imie%TYPE,
        nr_bandy     Kocury.nr_bandy%TYPE,
        data_wstap   Kocury.w_stadku_od%TYPE
    );
    TYPE t_tablica_kotow IS TABLE OF t_rekord_kota INDEX BY BINARY_INTEGER;
    
    v_mlode_koty t_tablica_kotow;
    v_indeks     BINARY_INTEGER := 1;
BEGIN
    FOR r_kot IN (
        SELECT imie, nr_bandy, w_stadku_od
        FROM Kocury k1
        WHERE w_stadku_od = (
            -- wybór najnizszego stazu dla danej bandy
            SELECT MAX(w_stadku_od)
            FROM Kocury k2
            WHERE k2.nr_bandy = k1.nr_bandy
        )
        ORDER BY nr_bandy ASC
    ) LOOP
        v_mlode_koty(v_indeks).imie := r_kot.imie;
        v_mlode_koty(v_indeks).nr_bandy := r_kot.nr_bandy;
        v_mlode_koty(v_indeks).data_wstap := r_kot.w_stadku_od;
        v_indeks := v_indeks + 1;
    END LOOP;

    DBMS_OUTPUT.PUT_LINE('--- KOTY O NAJNIŻSZYM STAŻU W BANDACH ---');
    DBMS_OUTPUT.PUT_LINE(RPAD('IMIE', 15) || RPAD('NR BANDY', 10) || 'DATA WSTĄPIENIA');
    DBMS_OUTPUT.PUT_LINE('-----------------------------------------');

    IF v_mlode_koty.COUNT > 0 THEN
        FOR i IN v_mlode_koty.FIRST .. v_mlode_koty.LAST LOOP
            DBMS_OUTPUT.PUT_LINE(
                RPAD(v_mlode_koty(i).imie, 15) || 
                RPAD(nvl(to_char(v_mlode_koty(i).nr_bandy), 'BRAK'), 10) || 
                v_mlode_koty(i).data_wstap
            );
        END LOOP;
    ELSE
        DBMS_OUTPUT.PUT_LINE('Brak danych do wyświetlenia.');
    END IF;
END;


-- zad 5
DECLARE
    CURSOR c_koty IS
        SELECT k.imie, k.przydzial_myszy, f.max_myszy, k.rowid
        FROM Kocury k
        JOIN Funkcje f ON k.funkcja = f.funkcja
        ORDER BY k.przydzial_myszy ASC;

    v_suma_myszy     NUMBER;
    v_liczba_zmian   NUMBER := 0;
    v_nowy_przydzial NUMBER;
BEGIN
    SELECT SUM(przydzial_myszy) INTO v_suma_myszy FROM Kocury;

    -- petla zewnetrzna kontroluje globalny limit sumy (obsluga wielu obiegow)
    WHILE v_suma_myszy <= 1050 LOOP
        
        FOR r_kot IN c_koty LOOP
            -- warunek stopu sprawdzany po kazdej pojedynczej zmianie
            EXIT WHEN v_suma_myszy > 1050;

            v_nowy_przydzial := ROUND(r_kot.przydzial_myszy * 1.1);

            -- walidacja gornego limitu dla funkcji
            IF v_nowy_przydzial > r_kot.max_myszy THEN
                v_nowy_przydzial := r_kot.max_myszy;
            END IF;

            -- wykonanie UPDATE tylko gdy wartosc faktycznie ulega zmianie
            IF v_nowy_przydzial <> r_kot.przydzial_myszy THEN
                UPDATE Kocury
                SET przydzial_myszy = v_nowy_przydzial
                WHERE rowid = r_kot.rowid;

                v_liczba_zmian := v_liczba_zmian + 1;
                v_suma_myszy := v_suma_myszy + (v_nowy_przydzial - r_kot.przydzial_myszy);
            END IF;
        END LOOP;
        
    END LOOP;

    DBMS_OUTPUT.PUT_LINE('Calk. przydzial w stadku ' || v_suma_myszy || ' Zmian - ' || v_liczba_zmian);
    DBMS_OUTPUT.PUT_LINE(RPAD('IMIE', 16) || 'Myszki po podwyzce');
    DBMS_OUTPUT.PUT_LINE('--------------- ------------------');

    FOR r_wynik IN (SELECT imie, przydzial_myszy FROM Kocury ORDER BY przydzial_myszy DESC) LOOP
        DBMS_OUTPUT.PUT_LINE(RPAD(r_wynik.imie, 16) || LPAD(r_wynik.przydzial_myszy, 18));
    END LOOP;

    ROLLBACK;
END;


-- zad 6
DECLARE
    v_nr NUMBER := 1;
BEGIN
    DBMS_OUTPUT.PUT_LINE(RPAD('Nr', 4) || RPAD('Pseudonim', 16) || 'Zjada');
    DBMS_OUTPUT.PUT_LINE('-------------------------');

    FOR r_kot IN (
        SELECT pseudo, zjada
        FROM (
            SELECT pseudo, przydzial_myszy + NVL(myszy_extra, 0) AS zjada
            FROM Kocury
            ORDER BY zjada DESC
        )
        WHERE ROWNUM <= 5
    ) LOOP
        DBMS_OUTPUT.PUT_LINE(
            RPAD(v_nr, 4) || 
            RPAD(r_kot.pseudo, 16) || 
            r_kot.zjada
        );
        v_nr := v_nr + 1;
    END LOOP;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Wystąpił błąd: ' || SQLERRM);
END;


-- zad 7
-- B)
DECLARE
    v_liczba_zadana INTEGER := &liczba_szefow;
    v_max_glebokosc INTEGER;
    v_limit_kolumn  INTEGER;
    
    v_sql           VARCHAR2(4000);
    v_pivot_in      VARCHAR2(2000);
    v_cursor        INTEGER;
    v_col_val       VARCHAR2(100);
    v_status        INTEGER;
    v_header        VARCHAR2(4000);
    v_dash          VARCHAR2(4000);
BEGIN
    -- 1. sprawdzenie rzeczywistej glebokosci drzewa
    SELECT MAX(LEVEL) - 1 INTO v_max_glebokosc
    FROM Kocury
    CONNECT BY PRIOR szef = pseudo
    START WITH funkcja IN ('KOT', 'MILUSIA');

    v_limit_kolumn := LEAST(v_liczba_zadana, v_max_glebokosc);

    -- 2. budowa klauzuli IN dla PIVOT
    FOR i IN 1..v_limit_kolumn LOOP
        IF i > 1 THEN v_pivot_in := v_pivot_in || ', '; END IF;
        v_pivot_in := v_pivot_in || (i + 1) || ' AS "SZEF_' || i || '"';
    END LOOP;

    -- 3. konstrukcja dynamicznego zapytania SQL
    v_sql := 'SELECT RPAD(imie, 15)';
    FOR i IN 1..v_limit_kolumn LOOP
        v_sql := v_sql || ', RPAD(NVL("SZEF_' || i || '", '' ''), 15)';
    END LOOP;

    v_sql := v_sql || ' FROM (
        SELECT CONNECT_BY_ROOT imie AS imie, imie AS szef_imie, LEVEL AS lvl
        FROM Kocury
        CONNECT BY PRIOR szef = pseudo
        START WITH funkcja IN (''KOT'', ''MILUSIA'')
    )
    PIVOT (
        MAX(szef_imie) FOR lvl IN (' || v_pivot_in || ')
    ) ORDER BY imie';

    -- 4. wykonanie przy uzyciu DBMS_SQL
    v_cursor := DBMS_SQL.OPEN_CURSOR;
    DBMS_SQL.PARSE(v_cursor, v_sql, DBMS_SQL.NATIVE);

    FOR i IN 1..(v_limit_kolumn + 1) LOOP
        DBMS_SQL.DEFINE_COLUMN(v_cursor, i, v_col_val, 100);
    END LOOP;

    v_status := DBMS_SQL.EXECUTE(v_cursor);

    -- 5. wyswietlenie naglowka
    v_header := RPAD('Imie', 15) || '| ';
    v_dash   := RPAD('-', 16, '-'); 
    
    FOR i IN 1..v_limit_kolumn LOOP
        v_header := v_header || RPAD('Szef ' || i, 15) || '| ';
        v_dash   := v_dash   || RPAD('-', 16, '-') || '-';
    END LOOP;
    
    DBMS_OUTPUT.PUT_LINE(v_header);
    DBMS_OUTPUT.PUT_LINE(v_dash);

    -- 6. wyswietlenie danych
    LOOP
        IF DBMS_SQL.FETCH_ROWS(v_cursor) = 0 THEN EXIT; END IF;
        
        FOR i IN 1..(v_limit_kolumn + 1) LOOP
            DBMS_SQL.COLUMN_VALUE(v_cursor, i, v_col_val);
            DBMS_OUTPUT.PUT(v_col_val || '| ');
        END LOOP;
        DBMS_OUTPUT.NEW_LINE;
    END LOOP;

    DBMS_SQL.CLOSE_CURSOR(v_cursor);

EXCEPTION
    WHEN OTHERS THEN
        IF DBMS_SQL.IS_OPEN(v_cursor) THEN DBMS_SQL.CLOSE_CURSOR(v_cursor); END IF;
        DBMS_OUTPUT.PUT_LINE('Wystąpił błąd: ' || SQLERRM);
END;


-- zad 8
DECLARE
    v_nr     Bandy.nr_bandy%TYPE := &nr_bandy;
    v_nazwa  Bandy.nazwa%TYPE    := '&nazwa';
    v_teren  Bandy.teren%TYPE    := '&teren';
    
    v_count  INTEGER;
    v_msg    VARCHAR2(400);
    
    e_zly_numer  EXCEPTION;
    e_duplikat   EXCEPTION;
BEGIN
    -- 1. walidacja poprawnosci danych
    IF v_nr <= 0 THEN
        RAISE e_zly_numer;
    END IF;

    -- 2. sprawdzanie duplikatow w bazie
    SELECT COUNT(*) INTO v_count FROM Bandy WHERE nr_bandy = v_nr;
    IF v_count > 0 THEN 
        v_msg := v_msg || v_nr || ', '; 
    END IF;

    SELECT COUNT(*) INTO v_count FROM Bandy WHERE nazwa = v_nazwa;
    IF v_count > 0 THEN 
        v_msg := v_msg || v_nazwa || ', '; 
    END IF;

    SELECT COUNT(*) INTO v_count FROM Bandy WHERE teren = v_teren;
    IF v_count > 0 THEN 
        v_msg := v_msg || v_teren || ', '; 
    END IF;

    -- zglaszanie wyjatku jesli zbudowano komunikat o bledach
    IF v_msg IS NOT NULL THEN
        v_msg := RTRIM(v_msg, ', ');
        RAISE e_duplikat;
    END IF;

    -- 3. operacje DML
    INSERT INTO Bandy (nr_bandy, nazwa, teren) 
    VALUES (v_nr, v_nazwa, v_teren);
    
    DBMS_OUTPUT.PUT_LINE('Pomyślnie dodano nową bandę.');

    ROLLBACK;

EXCEPTION
    WHEN e_zly_numer THEN
        DBMS_OUTPUT.PUT_LINE('Numer bandy musi być > 0!');
    WHEN e_duplikat THEN
        DBMS_OUTPUT.PUT_LINE(v_msg || ': juz istnieje');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Błąd: ' || SQLERRM);
END;


-- zad 9
-- skrypt tworzacy procedure
CREATE OR REPLACE PROCEDURE Zmien_Przydzial (
    p_funkcja   IN Kocury.funkcja%TYPE,
    p_przydzial IN Kocury.przydzial_myszy%TYPE
) IS
BEGIN
    -- aktualizacja przydzialu dla podanej funkcji
    UPDATE Kocury
    SET przydzial_myszy = p_przydzial
    WHERE funkcja = UPPER(p_funkcja);

    -- sprawdzenie czy zaktualizowano jakiekolwiek wiersze
    IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Nie znaleziono kotów o funkcji: ' || NVL(UPPER(p_funkcja), 'NULL'));
    ELSE
        DBMS_OUTPUT.PUT_LINE('Zaktualizowano ' || SQL%ROWCOUNT || ' kotów.');
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Wystąpił błąd: ' || SQLERRM);
END;
/
-- blok wywolujacy
DECLARE
    v_funkcja   Kocury.funkcja%TYPE := '&nazwa_funkcji';
    v_przydzial Kocury.przydzial_myszy%TYPE := &nowy_przydzial;
BEGIN
    Zmien_Przydzial(v_funkcja, v_przydzial);
    
    DBMS_OUTPUT.PUT_LINE('--- Weryfikacja danych przed wycofaniem ---');
    
    FOR r_kot IN (
        SELECT imie, przydzial_myszy 
        FROM Kocury 
        WHERE funkcja = UPPER(v_funkcja)
    ) LOOP
        DBMS_OUTPUT.PUT_LINE(RPAD(r_kot.imie, 15) || ' Nowy przydział: ' || r_kot.przydzial_myszy);
    END LOOP;

    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('--- Zmiany zostały wycofane ---');
END;


-- zad 10
CREATE OR REPLACE FUNCTION Oblicz_Podatek(
    p_pseudo IN Kocury.pseudo%TYPE
) RETURN NUMBER IS
    v_przydzial Kocury.przydzial_myszy%TYPE;
    v_extra     Kocury.myszy_extra%TYPE;
    v_nr_bandy  Kocury.nr_bandy%TYPE;
    
    v_podatek   NUMBER := 0;
    v_count     INTEGER;
    v_srednia   NUMBER;
BEGIN
    SELECT przydzial_myszy, myszy_extra, nr_bandy
    INTO v_przydzial, v_extra, v_nr_bandy
    FROM Kocury
    WHERE pseudo = UPPER(p_pseudo);

    -- 1. podatek podstawowy: 5% calkowitych przychodow
    v_podatek := CEIL((NVL(v_przydzial, 0) + NVL(v_extra, 0)) * 0.05);

    -- 2. brak podwladnych (+2 myszy)
    SELECT COUNT(*) INTO v_count FROM Kocury WHERE szef = UPPER(p_pseudo);
    IF v_count = 0 THEN
        v_podatek := v_podatek + 2;
    END IF;

    -- 3. brak wrogow (+1 mysz)
    SELECT COUNT(*) INTO v_count FROM Wrogowie_kocurow WHERE pseudo = UPPER(p_pseudo);
    IF v_count = 0 THEN
        v_podatek := v_podatek + 1;
    END IF;

    -- 4. podatek wlasny
    SELECT AVG(NVL(przydzial_myszy, 0)) INTO v_srednia
    FROM Kocury
    WHERE nr_bandy = v_nr_bandy;
    
    -- jesli zarabia powyzej sredniej w swojej bandzie to oddaje 10% nadwyzki
    IF NVL(v_przydzial, 0) > v_srednia THEN
        v_podatek := v_podatek + FLOOR((v_przydzial - v_srednia) * 0.1);
    END IF;

    RETURN v_podatek;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Nie znaleziono kota: ' || p_pseudo);
        RETURN 0;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Błąd: ' || SQLERRM);
        RETURN 0;
END;
/

-- testowanie funkcji
DECLARE
    v_pseudo Kocury.pseudo%TYPE := '&pseudonim';
    v_wynik  NUMBER;
BEGIN
    v_wynik := Oblicz_Podatek(v_pseudo);
    
    IF v_wynik > 0 THEN
        DBMS_OUTPUT.PUT_LINE('Całkowity podatek dla kota ' || UPPER(v_pseudo) || 
                             ' wynosi: ' || v_wynik);
    END IF;
END;


-- zad 11
-- a)
DECLARE
    v_sql           VARCHAR2(4000);
    v_cols          VARCHAR2(4000); -- kolumny dla glownego SELECT
    v_sum_cols      VARCHAR2(4000); -- kolumny dla podsumowania
    v_separator     VARCHAR2(4000); -- separator
    v_col_header    VARCHAR2(4000); -- naglowek do wyswietlenia
    v_cursor        INTEGER;
    v_status        INTEGER;
    v_col_val       VARCHAR2(100);
    v_liczba_kolumn INTEGER := 3;    -- startuje od 3: Nazwa, Plec, Ile
BEGIN
    -- 1. budowanie dynamicznych kolumn na podstawie tabeli Funkcje
    FOR r IN (SELECT funkcja FROM Funkcje ORDER BY funkcja) LOOP
        v_cols := v_cols || ', TO_CHAR(SUM(DECODE(funkcja, ''' || r.funkcja || ''', przydzial_myszy + NVL(myszy_extra, 0), 0)))';
        v_sum_cols := v_sum_cols || ', TO_CHAR(SUM(DECODE(funkcja, ''' || r.funkcja || ''', przydzial_myszy + NVL(myszy_extra, 0), 0)))';
        v_separator := v_separator || ',''---------''';
        v_col_header := v_col_header || LPAD(r.funkcja, 10);
        
        v_liczba_kolumn := v_liczba_kolumn + 1;
    END LOOP;
    
    v_liczba_kolumn := v_liczba_kolumn + 1; -- dodanie kolumny z suma do licznika

    -- 2. budowa zapytania SQL
    v_sql := '
    SELECT * FROM (
        -- czesc 1: dane szczegolowe
        SELECT B.nazwa,
               DECODE(K.plec, ''D'', ''Kotka'', ''Kocor'') AS plec,
               TO_CHAR(COUNT(K.pseudo)) AS ile ' 
               || v_cols || ', 
               TO_CHAR(SUM(przydzial_myszy + NVL(myszy_extra, 0))) AS suma
        FROM Kocury K
        JOIN Bandy B ON K.nr_bandy = B.nr_bandy
        GROUP BY B.nazwa, K.plec

        UNION ALL

        -- czesc 2: separator (musi mieć tyle samo kolumn: 3 + N + 1)
        SELECT ''Z----------------'', ''------'', ''----'' ' || v_separator || ', ''-------'' FROM DUAL

        UNION ALL

        -- czesc 3: podsumowanie (musi miec tyle samo kolumn: 3 + N + 1)
        SELECT ''ZJADA RAZEM'', '' '', '' '' ' || v_sum_cols || ', TO_CHAR(SUM(przydzial_myszy + NVL(myszy_extra, 0))) 
        FROM Kocury
    ) ORDER BY 1, 2 DESC';

    -- 3. wykonanie
    v_cursor := DBMS_SQL.OPEN_CURSOR;
    DBMS_SQL.PARSE(v_cursor, v_sql, DBMS_SQL.NATIVE);
    
    FOR i IN 1..v_liczba_kolumn LOOP
        DBMS_SQL.DEFINE_COLUMN(v_cursor, i, v_col_val, 100);
    END LOOP;
    
    v_status := DBMS_SQL.EXECUTE(v_cursor);
    
    -- wyswietlanie naglowka
    DBMS_OUTPUT.PUT_LINE(RPAD('NAZWA BANDY', 18) || RPAD('PLEC', 7) || RPAD('ILE', 4) || v_col_header || LPAD('SUMA', 8));
    DBMS_OUTPUT.PUT_LINE(RPAD('-', 17, '-') || ' ' || RPAD('-', 6, '-') || ' ' || RPAD('-', 4, '-') || REPLACE(REPLACE(v_separator,',',' '),'''','') || ' ' || RPAD('-', 7, '-'));
    
    -- petla po wierszach
    LOOP
        IF DBMS_SQL.FETCH_ROWS(v_cursor) = 0 THEN EXIT; END IF;
        
        DBMS_SQL.COLUMN_VALUE(v_cursor, 1, v_col_val);
        DBMS_OUTPUT.PUT(RPAD(v_col_val, 18)); -- Nazwa
        
        DBMS_SQL.COLUMN_VALUE(v_cursor, 2, v_col_val);
        DBMS_OUTPUT.PUT(RPAD(v_col_val, 7));  -- Plec
        
        DBMS_SQL.COLUMN_VALUE(v_cursor, 3, v_col_val);
        DBMS_OUTPUT.PUT(LPAD(v_col_val, 4) || ' '); -- Ile
        
        FOR i IN 4..(v_liczba_kolumn-1) LOOP
            DBMS_SQL.COLUMN_VALUE(v_cursor, i, v_col_val);
            DBMS_OUTPUT.PUT(LPAD(v_col_val, 9) || ' '); -- Funkcje
        END LOOP;
        
        DBMS_SQL.COLUMN_VALUE(v_cursor, v_liczba_kolumn, v_col_val);
        DBMS_OUTPUT.PUT_LINE(LPAD(v_col_val, 7)); -- Suma
    END LOOP;
    
    DBMS_SQL.CLOSE_CURSOR(v_cursor);
EXCEPTION
    WHEN OTHERS THEN
        IF DBMS_SQL.IS_OPEN(v_cursor) THEN DBMS_SQL.CLOSE_CURSOR(v_cursor); END IF;
        DBMS_OUTPUT.PUT_LINE('Błąd: ' || SQLERRM);
END;
/

-- b)
DECLARE
    v_sql           VARCHAR2(4000);
    v_pivot_in      VARCHAR2(4000); 
    v_select_cols   VARCHAR2(4000); 
    v_sum_cols      VARCHAR2(4000); 
    v_separator     VARCHAR2(4000); 
    v_col_header    VARCHAR2(4000);
    v_cursor        INTEGER;
    v_status        INTEGER;
    v_col_val       VARCHAR2(100);
    v_liczba_kolumn INTEGER := 3;
    
    CURSOR c_funkcje IS SELECT funkcja FROM Funkcje ORDER BY funkcja;
BEGIN
    -- 1. budowanie dynamicznych list kolumn
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

    -- 2. budowa zapytania SQL
    v_sql := '
    SELECT * FROM (
        -- czesc 1: dane z PIVOT
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

        -- czesc 2: separator
        SELECT ''Z----------------'', ''------'', ''----'' ' || v_separator || ', ''-------'' FROM DUAL

        UNION ALL

        -- czesc 3: podsumowanie
        SELECT ''ZJADA RAZEM'', '' '', '' '' ' || v_sum_cols || ', TO_CHAR(SUM(przydzial_myszy + NVL(myszy_extra, 0)))
        FROM Kocury
    ) ORDER BY 1, 2 DESC';

    -- 3. wykonanie
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
    WHEN OTHERS THEN
        IF DBMS_SQL.IS_OPEN(v_cursor) THEN DBMS_SQL.CLOSE_CURSOR(v_cursor); END IF;
        DBMS_OUTPUT.PUT_LINE('Błąd: ' || SQLERRM);
END;








