# sakila
solucion Jerry Calero B.

El  proyecto  contiene  la  carpeta wrapper, donde se encuentra la clase  Wrapper.java  del ejercicio número 1.  Para la ejecucion  del  proteyecto modificar los parametros server.port y server.address  en  application.properties, el servicio  tiene seguridad de spring, usuario y contraseña: sakila ,
pueden probar de  la siguiente  forma:
1. para el punto NO. 1 recibe un parámetro byCity,  cuyo valor es Y  para mostrar la ciudad y cualquier otra valor para mostrar el pais. 
http://172.16.175.77:8189/api/sakila/catalog/getQuantityCustomerLocation?byCity=N

2. recibe dos parametros actor y category
http://172.16.175.77:8189/api/sakila/catalog/getActorInformation?actor=Ada&category=FOREIGN 

3. No recibe  parametros
http://172.16.175.77:8189/api/sakila/operation/getOverdueRental
