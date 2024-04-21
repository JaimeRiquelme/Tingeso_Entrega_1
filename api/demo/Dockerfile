# Usar una imagen base que coincida con la versión de Java utilizada en el proyecto
FROM openjdk:17

# Argumento para especificar la ubicación del archivo jar
ARG JAR_FILE=build/libs/*.jar

# Copiar el archivo jar desde el build de Gradle a la imagen Docker
COPY ${JAR_FILE} gestion-estudiantes-backend.jar

# Exponer el puerto que utiliza tu aplicación
EXPOSE 8090

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "gestion-estudiantes-backend.jar"]
