# UI Automation Project


### Запуск тестов локально

mvn clean test \
  -Dgroups=test \
  -Dbrowser=firefox \
  -Dmode=headless      

### Запуск тестов GitHub Actions

1. Тесты запускаются автоматически при push-событиях 
2. Вручную через кнопку Run workflow в интерфейсе GitHub Actions.
