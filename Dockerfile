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

# ★ここを修正：不要なプレインJARを弾き、app.jar という名前で保存します
COPY --from=build /app/build/libs/*[!plain].jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
