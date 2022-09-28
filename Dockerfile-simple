# FROM openjdk:18-jdk-alpine

# WORKDIR /app

# COPY ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar .

# EXPOSE 8001

# CMD ["java", "-jar", "msvc-usuarios-0.0.1-SNAPSHOT.jar"]


#IMAGEN DE LA QUE SE BASARA NUESTRA PROPIA IMAGEN (EN ESTE CASO CONTIENE EL RUNTIME DE JDK)
FROM openjdk:18-jdk-alpine

#CARPETA DONDE SE TRABAJARA EN EL CONTENEDOR
WORKDIR /app/msvc-usuarios

#SE COPIA TODO EL CODIGO FUENTE DE LA MAQUINA LOCAL AL CONTENEDOR
COPY ./.mvn  ./.mvn
COPY ./mvnw .
COPY ./pom.xml .

#Descargar solo las dependencias
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./src ./src

#RUN SE EJECUTA CUANDO SE CONSTRUYE LA IMAGEN
RUN ./mvnw clean package -DskipTests

EXPOSE 8001

#ENTRYPOINT O CMD FORMA PARTE DE UNA CAPA ADICIONAL QUE NO PERTENECE A LA IMAGEN, PERTENECE AL CONTENEDOR QUE SE EJECUTA EN BASE A LA IMAGEN
CMD ["java", "-jar", "/app/msvc-usuarios/target/msvc-usuarios-0.0.1-SNAPSHOT.jar"]