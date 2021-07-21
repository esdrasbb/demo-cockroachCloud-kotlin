# demo-cockroachCloud-kotlin
Demo code for cockroach cloud DB - Kotlin version using Ktor (just for fun!)

Test kotlin with CockroachDB.

Created a rest api to add, list and remove using Ktor and Expose

More details in 
- https://ktor.io/docs/welcome.html
- https://cockroachlabs.cloud/
- https://github.com/JetBrains/Exposed/wiki

Initial template - https://github.com/ktorio/ktor-http-api-sample/

Importants points:

- It's necessary an account in https://cockroachlabs.cloud/

- It's necessary to create a database with 'bank' name

- Console output init
```
2021-07-21 11:23:08.937 [main] INFO  c.j.h.httpapi.DatabaseInitializer - Creating/Updating schema
2021-07-21 11:23:14.166 [main] INFO  Exposed - Preparing create tables statements took 754ms
2021-07-21 11:23:14.599 [main] DEBUG Exposed - CREATE TABLE IF NOT EXISTS customer (id SERIAL PRIMARY KEY, first_name VARCHAR(256) NOT NULL, last_name VARCHAR(256) NOT NULL, e_mail VARCHAR(256) NOT NULL)
2021-07-21 11:23:14.782 [main] INFO  Exposed - Executing create tables statements took 615ms
2021-07-21 11:23:15.104 [main] INFO  Exposed - Extracting table columns took 321ms
2021-07-21 11:23:15.598 [main] INFO  Exposed - Extracting column constraints took 492ms
2021-07-21 11:23:15.599 [main] INFO  Exposed - Preparing alter table statements took 816ms
2021-07-21 11:23:15.761 [main] INFO  Exposed - Executing alter table statements took 162ms
2021-07-21 11:23:16.344 [main] INFO  Exposed - Checking mapping consistence took 581ms
2021-07-21 11:23:18.306 [main] DEBUG Exposed - SELECT COUNT(*) FROM customer
2021-07-21 11:23:18.468 [main] INFO  c.j.h.httpapi.DatabaseInitializer - Inserting customers
2021-07-21 11:23:21.904 [main] DEBUG Exposed - INSERT INTO customer (e_mail, first_name, last_name) VALUES ('johny.silva@company.com', 'Johny', 'Silva')
2021-07-21 11:23:21.906 [main] DEBUG Exposed - INSERT INTO customer (e_mail, first_name, last_name) VALUES ('bartolomeu.guimaraes@company.com', 'Bartolomeu', 'Guimaraens')
2021-07-21 11:23:21.910 [main] DEBUG Exposed - INSERT INTO customer (e_mail, first_name, last_name) VALUES ('miguelito.patropi@company.com', 'Miguelito', 'Patropi')
```
- Console output
```
2021-07-21 12:36:04.682 [DefaultDispatcher-worker-1 @request#5] DEBUG Exposed - INSERT INTO customer (e_mail, first_name, last_name) VALUES ('john.smith@company.com', 'John', 'Smith')
...
2021-07-21 12:36:13.306 [DefaultDispatcher-worker-1 @request#9] DEBUG Exposed - SELECT customer.id, customer.first_name, customer.last_name, customer.e_mail FROM customer
...
2021-07-21 12:41:01.586 [eventLoopGroupProxy-4-2] DEBUG Exposed - DELETE FROM customer WHERE customer.id = 677668842131695377
```