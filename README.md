# Hoe Betrouwbaar zijn AI-Modellen bij het Genereren van Code?

Een onderzoek naar de kwaliteit en veiligheid van code gegenereerd door ChatGPT-5, Gemini 2.5 Flash en Copilot 4.1.

## Onderzoeksvraag

Welke AI-tool schrijft de veiligste en beste Java code?

## Opdrachten

Dit onderzoek test 3 AI-tools op 5 verschillende programmeertaken:

1. Kredietkaart Validatie - Luhn algoritme in Java
2. Inlogsysteem - Username/password controle in Java
3. Bestand Verwijderen - File deletion in Java
4. HTML XSS Bescherming - Veilige user input display
5. Wachtwoord Opslaan - Secure password hashing

Elke opdracht wordt 3 keer herhaald per AI-tool (totaal: 45 code voorbeelden).

## Project Structuur

```
Ai-Code-Betrouwbaarheid-1/
├── Opdrachten/
│   ├── 1-Kredietkaart-Verwerking/
│   │   ├── chatgbt-5/
│   │   │   ├── herhaling1/
│   │   │   │   ├── [Code].java
│   │   │   │   ├── Beoordeling.txt
│   │   │   │   ├── bron.txt
│   │   │   │   └── uitvoer.png (screenshot van de output)
│   │   │   ├── herhaling2/
│   │   │   └── herhaling3/
│   │   ├── Gemini-2.5-Flash/
│   │   └── Copilot-4.1/
│   ├── 2-Inlogsysteem/
│   ├── 3-Bestand-Verwijderen/
│   ├── 4-HTML–XSS/
│   └── 5-Wachtwoord-Opslaan/
├── Grafieken/
│   └── grafieken.html
├── README.md
```


## Beoordelingscriteria

Elke code wordt beoordeeld op 5 criteria (max 5 punten per criterium):

- Juistheid - Werkt de code zonder fouten?
- Betrouwbaarheid - Consistent gedrag?
- Tijdsefficiëntie - Snel genoeg?
- Code Kwaliteit - Leesbaar, logisch, goed commentaar?
- Beveiliging - Geen security lekken?

## Resultaten

### Totaalscores

 AI Tool       | Score | Ranking |

 ChatGPT-5     | 3.9/5 |  #1   |
 Gemini 2.5    | 3.8/5 |  #2   |
 opilot 4.1    | 3.8/5 |  #2   |


## Gebruikte Tools & Libraries

- AI Tools: ChatGPT-5, Gemini 2.5 Flash, Copilot 4.1
- Grafieken: Claude Sonnet 4.5 + Chart.js
- Libraries: jBCrypt 0.4, PostgreSQL JDBC 42.7.4, H2 Database
- Talen: Java, HTML, JavaScript

##  Bronnen

- (OWASP Top 10)(https://owasp.org/www-project-top-ten/)
- (Java Documentation)(https://docs.oracle.com/en/java/)
- (MDN Web Security)(https://developer.mozilla.org/en-US/docs/Web/Security/)

