::版本号：默认1.0.1
::一个版本号，只能发布一次，需谨慎发布。
mvn clean deploy -P release -Dmaven.test.skip=true -Dfast-mock-version=1.0.1