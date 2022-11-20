# Opis aplikacji
- API REST pozwalające na przesłanie url strony, której content zostaje zapisany w bazie danych (opisane w pliku `api.yaml`)
- Przyjęcie requestu powoduje zapis rekordu w bazie danych (tabela `resource_download_request`) oraz dodanie do kolejki zlecenia pobrania contentu.
- Konsumer kolejki pobiera zlecenia oraz zapisuje content w tabeli `resource`
- W przypadku błędu dodania do kolejki request otrzymuje status `ERROR` (tabela `resource_download_request`). Zaimplementowano cron joba pozwalającego na ponowienie pobrania.

# Przyjęte założenia
- API przyjmuje tylko url rozpoczynające się od `https://`
- Częstotliwość uruchamiania crona zdefiniowana w `settings.download-interval-cron`
- Maksymalna liczba oczekujących na przetworzenie requestów została zdefiniowana w `settings.queue-max-capacity`

# Przed uruchomieniem aplikacji - środowisko lokalne
- `docker-compose up` w katalogu /docker
- `mvn install` w głównym katalogu

# Przykładowy request
```
POST http://localhost:8080//resource/download
Content-Type: application/json

{ "url" : "https://www.onet.pl" }
```