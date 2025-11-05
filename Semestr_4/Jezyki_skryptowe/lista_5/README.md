```bash
   python z5.py --data-dir measurements --stations stacje.csv --variable CO --freq 1g --start-date 2023-01-01 --end-date 2023-02-01 random
```
```bash
   python z5.py --data-dir measurements --stations stacje.csv --variable CO --freq 1g --start-date 2023-01-01 --end-date 2023-06-01 stats --station DsWrocWybCon
```
```bash
   python z5.py --data-dir measurements --stations stacje.csv --variable CO --freq 1g --start-date 2023-01-01 --end-date 2023-01-30 anomalies
```

```bash
python zr1.py random-station measurements stacje.csv CO 1g 2023-01-01 2023-02-01
```
```bash
python zr1.py stats measurements stacje.csv CO 1g DsWrocWybCon 2023-01-01 2023-06-01
```
```bash
python zr1.py anomalies measurements CO 1g 2023-01-01 2023-01-31
```
