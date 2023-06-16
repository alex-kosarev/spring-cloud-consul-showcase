# Spring Cloud и Consul

Проект, демонстрирующий использование поиска сервисов и распределённых конфигураций при помощи Spring Cloud и Consul.

## Запуск consul
Для запуска потребуется инстанс consul с ACL, параметры сервера можно описать в файле (пусть будет agent.json):
```json
{
    "acl": {
	"enabled": true,
	"default_policy": "deny",
	"enable_token_persistence": true
    }
}
```

Запустить consul можно следующей командой:

```shell
consul agent -server -bind=127.0.0.1 -ui -data-dir=consul_data -bootstrap -config-file=agent.json
```

После этого надо подключтить ACL:

```shell
consul acl bootstrap
```

Пример вывода:
```
akosarev@akosarev-MS-7C37:~/tmp/consul$ ./consul acl bootstrap
AccessorID:       2b5db047-3ba4-2cb4-3f05-7cf5575bdd2b
SecretID:         50845298-c8dd-b7a5-51f7-34beb046f30b
Description:      Bootstrap Token (Global Management)
Local:            false
Create Time:      2023-06-16 15:12:07.066603506 +0500 +05
Policies:
   00000000-0000-0000-0000-000000000001 - global-management
```

Если хотите использовать утилиту `consul` для администрирования, то `SecretID` нужно экспортировать в переменную окружения ` CONSUL_HTTP_TOKEN`:

```shell
export CONSUL_HTTP_TOKEN=50845298-c8dd-b7a5-51f7-34beb046f30b
```

