
-- LISTA 3

-- zad 2
DECLARE @pseudo VARCHAR(15) = 'TYGRYS'; -- zmienna wejsciowa

IF NOT EXISTS (SELECT 1 FROM Kocury WHERE pseudo = @pseudo)
BEGIN
    PRINT 'Nie znaleziono kota o pseudo: ' + @pseudo;
    RETURN;
END

DECLARE @imie VARCHAR(15);
DECLARE @nazwa_bandy VARCHAR(20);
DECLARE @czy_wrogowie VARCHAR(3);
DECLARE @czy_powyzej_sredniej VARCHAR(3);
DECLARE @data_wst DATE;

SELECT 
    @imie = K.imie,
    @nazwa_bandy = B.nazwa,
    @data_wst = K.w_stadku_od,
    -- podzapytanie sprawdzajace istnienie wrogow
    @czy_wrogowie = CASE 
        WHEN (SELECT COUNT(*) FROM Wrogowie_kocurow WHERE pseudo = K.pseudo) > 0 THEN 'TAK' 
        ELSE 'NIE' 
    END,
    -- porownanie rocznego przydzialu kota ze sredni¹ w jego bandzie
    @czy_powyzej_sredniej = CASE 
        WHEN (K.przydzial_myszy + ISNULL(K.myszy_extra, 0)) * 12 > 
             (SELECT AVG(CAST((K2.przydzial_myszy + ISNULL(K2.myszy_extra, 0)) * 12 AS DECIMAL(10,2)))
              FROM Kocury K2 
              WHERE K2.nr_bandy = K.nr_bandy) 
        THEN 'TAK' 
        ELSE 'NIE' 
    END
FROM Kocury K
JOIN Bandy B ON K.nr_bandy = B.nr_bandy
WHERE K.pseudo = @pseudo;

PRINT 'Pseudo: ' + @pseudo;
PRINT 'Imie: ' + @imie;
PRINT 'Banda: ' + @nazwa_bandy;
PRINT 'Wrogowie: ' + @czy_wrogowie;
PRINT 'Powyzej sredniej: ' + @czy_powyzej_sredniej;
PRINT 'Data wstapienia: ' + CAST(@data_wst AS VARCHAR);


-- zad 7
-- A)
DECLARE @liczba_szefow INT = 5; -- wartosc wejsciowa
DECLARE @rzeczywista_glebokosc INT;
DECLARE @limit_kolumn INT;
DECLARE @sql NVARCHAR(MAX);
DECLARE @header NVARCHAR(MAX);
DECLARE @separator NVARCHAR(MAX);
DECLARE @col_list NVARCHAR(MAX);
DECLARE @joins NVARCHAR(MAX);
DECLARE @i INT = 1;

BEGIN TRY
    -- 1. obliczenie maksymalnej glebokosci hierarchii
    WITH HierarchiaCTE AS (
        SELECT szef, 1 AS poziom
        FROM Kocury
        WHERE funkcja IN ('KOT', 'MILUSIA') AND szef IS NOT NULL
        UNION ALL
        SELECT k.szef, h.poziom + 1
        FROM Kocury k
        INNER JOIN HierarchiaCTE h ON k.pseudo = h.szef
        WHERE k.szef IS NOT NULL
    )
    SELECT @rzeczywista_glebokosc = ISNULL(MAX(poziom), 0) FROM HierarchiaCTE;

    SET @limit_kolumn = IIF(@liczba_szefow < @rzeczywista_glebokosc, @liczba_szefow, @rzeczywista_glebokosc);

    -- 2. budowa dynamicznego SQL (naglowek i separator)
    SET @header = 'CAST(''Imie'' AS CHAR(15)) + ''| ''';
    SET @separator = 'CAST(''---------------'' AS CHAR(15)) + ''-----''';

    WHILE @i <= @limit_kolumn
    BEGIN
        SET @header = @header + '+ CAST(''Szef ' + CAST(@i AS VARCHAR) + ''' AS CHAR(15)) + ''| ''';
        SET @separator = @separator + '+ CAST(''---------------'' AS CHAR(15)) + ''-----''';
        SET @i = @i + 1;
    END

    -- 3. budowa listy kolumn danych i zlaczen
    SET @col_list = 'CAST(k.imie AS CHAR(15)) + ''| ''';
    SET @joins = '';
    SET @i = 1;

    WHILE @i <= @limit_kolumn
    BEGIN
        SET @col_list = @col_list + '+ CAST(ISNULL(s' + CAST(@i AS VARCHAR) + '.imie, '''') AS CHAR(15)) + ''| ''';
        
        IF @i = 1
            SET @joins = @joins + ' LEFT JOIN Kocury s1 ON k.szef = s1.pseudo ';
        ELSE
            SET @joins = @joins + ' LEFT JOIN Kocury s' + CAST(@i AS VARCHAR) + 
                       ' ON s' + CAST(@i-1 AS VARCHAR) + '.szef = s' + CAST(@i AS VARCHAR) + '.pseudo ';
        SET @i = @i + 1;
    END

    -- 4. wykonanie zapytania
    SET @sql = 'SELECT Line FROM (
                    SELECT 1 AS Sort_Grp, ' + @header + ' AS Line
                    UNION ALL
                    SELECT 2 AS Sort_Grp, ' + @separator + '
                    UNION ALL
                    SELECT 3 AS Sort_Grp, ' + @col_list + '
                    FROM Kocury k ' + @joins + '
                    WHERE k.funkcja IN (''KOT'', ''MILUSIA'')
                ) x
                ORDER BY Sort_Grp, Line';

    EXEC sp_executesql @sql;
END TRY
BEGIN CATCH
    PRINT 'Wyst¹pi³ b³¹d: ' + ERROR_MESSAGE();
END CATCH;
