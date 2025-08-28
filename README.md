## Запуск проекта


- `Запуск через docker-compose`
  - выполняем clean package
  - переходим в директорию приложения
  - выполняем docker-compose up -d       
  - если не все контейнеры запустились, выполняем рестарт каждого начиная с configServer, eurekaServer, gateway
  - переходим по http://localhost:1010/