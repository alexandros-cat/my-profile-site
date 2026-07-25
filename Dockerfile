# 1. Gradle イメージを使ってビルド
FROM gradle:8-jdk17 AS build
WORKDIR /app

# プロジェクトファイルをコピー
COPY . .

# テストをスキップして JAR をビルド
RUN gradle bootJar -x test

# 2. 実行環境
FROM eclipse-temurin:17-jre
WORKDIR /app

# ★修正：build/libs/ 内にあるJARファイルを、確実にこのフォルダの app.jar としてコピー
COPY --from=build /app/build/libs/*.jar ./app.jar

EXPOSE 8080
# ★実行パスを ./app.jar に明示
CMD ["java", "-jar", "./app.jar"]
