# UI Automation Project


### Запуск тестов локально

mvn clean test \
  -Dgroups=test \
  -Dbrowser=firefox \  # или chrome
  -Dmode=headless      # или fullscreen, kiosk

### Запуск тестов GitHub Actions

Тесты запускаются автоматически при push-событиях или вручную через кнопку Run workflow в интерфейсе GitHub Actions.
