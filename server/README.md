# Timeline Server
## 介绍

本项目是项目时间线的服务器端。

## 主要依赖
- `spring-boot`
- `mysql-connector-java`

测试依赖

- `JUnit 4`
- `rest-assured`
- `mockito`

## 快速启动

```bash
./gradlew build
# 启动 Spring
java -jar build/libs/timeline-0.0.1-SNAPSHOT.jar
```

## FAQ

项目是否进行静态代码检测？

是的，本项目使用 `p3d-pmd` idea plugin 工具进行检测。

项目是否进行代码覆盖度测试？

是的，本项目使用`Jacoco`进行覆盖度检测，你可以运行以下命令生成报告。

```bash
./gradlew jacocoTestCoverageVerification
./gradlew jacocoTestReport
# 报告位于 build/reports/jacocoHtml
```

