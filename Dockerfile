FROM bellsoft/liberica-openjdk-alpine-musl
COPY ./target/UserService-0.0.1-SNAPSHOT.jar .
CMD ["java","-jar","UserService-0.0.1-SNAPSHOT.jar"]