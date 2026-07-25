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

# ビルドされた JAR ファイルをコピー
COPY --from=build /app/build/libs/*.jar demo.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]