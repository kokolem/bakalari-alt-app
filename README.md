# Alternativní android apikace pro Bakaláře

## O aplikaci
Aplikace bude alternativou pro zastaralou a často nefunkční oficiální aplikaci.

### Upozornění
Aplikace je stále ve vývoji a zatím nebyla vydaná žádná stabilní verze. Měla by být hotová do konce školního roku 2018/19.

### Co bude nabízet
Veškeré funkce oficiální aplikace a něco navíc:
- Zobrazení průměru na dvě desetinná místa.
- Graf ze známek.
- Možnost odškrtnout domácí úkoly jako hotové.
- Design podle [Google Material](https://material.io).
- Funkčnost na androidu 8.1 a 9 (Což o sobě oficiální aplikace tvrdí taky, ale opak je pravdou.)

### Jak bude vypadat
Takhle bude vypadat seznam zadaných úkolů:

![Takhle budou vypadat zadané úkoly](https://i.imgur.com/EvhmcfY.png)

A takhle hotových:

![A takhle hotové](https://i.imgur.com/w6XgnTW.png)

A takhle archiv (snad :-) odevzdaných:

![A takhle archiv (snad :-) odevzdaných](https://i.imgur.com/gT35bMG.png)


Takhle se bude přihlašovat:

![Takhle se bude přihlašovat](https://i.imgur.com/5wpzxQw.png)

## Changelog

### Commit 20190106:

- Přidána třída na výpočet tokenu
- Přidáno parsování XML pro salt, ikod a typ
- Přidáno parsování XML pro jméno uživatele
- Opravena chyba, při které se šlo bez přidání účtu vrátit na demo
- Při prvním spuštění se rovnou zobrazí obrazkovka s přidáním účtu
- Při nedokončené změně účtu se při dalším spuštění rovnou ukáže obrazkovka s výběrem účtu
