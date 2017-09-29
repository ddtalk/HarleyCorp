### Deploy jar to maven local

mvn install:install-file  -Dfile=/d/client_sdk/client-sdk.core-1.0.0.jar    -DgroupId=com.dingtalk.open  -DartifactId=client-sdk.core   -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file  -Dfile=/d/client_sdk/client-sdk.api-1.0.0.jar     -DgroupId=com.dingtalk.open  -DartifactId=client-sdk.api    -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file  -Dfile=/d/client_sdk/client-sdk.common-1.0.0.jar  -DgroupId=com.dingtalk.open  -DartifactId=client-sdk.common -Dversion=1.0.0 -Dpackaging=jar
mvn install:install-file  -Dfile=/d/client_sdk/client-sdk.spring-1.0.0.jar  -DgroupId=com.dingtalk.open  -DartifactId=client-sdk.spring -Dversion=1.0.0 -Dpackaging=jar

mvn deploy:deploy-file -DgroupId=com.dingtalk.open -DartifactId=client-sdk.core       -Dversion=1.0.0 -Dpackaging=jar -Dfile=/d/client_sdk/client-sdk.core-1.0.0.jar   -Durl=http://127.0.0.1:8081/nexus/repository/maven-releases/ -DrepositoryId=maven-releases -DgeneratePom=true
mvn deploy:deploy-file -DgroupId=com.dingtalk.open -DartifactId=client-sdk.api        -Dversion=1.0.0 -Dpackaging=jar -Dfile=/d/client_sdk/client-sdk.api-1.0.0.jar    -Durl=http://127.0.0.1:8081/nexus/repository/maven-releases/ -DrepositoryId=maven-releases -DgeneratePom=true
mvn deploy:deploy-file -DgroupId=com.dingtalk.open -DartifactId=client-sdk.common     -Dversion=1.0.0 -Dpackaging=jar -Dfile=/d/client_sdk/client-sdk.common-1.0.0.jar -Durl=http://127.0.0.1:8081/nexus/repository/maven-releases/ -DrepositoryId=maven-releases -DgeneratePom=true
mvn deploy:deploy-file -DgroupId=com.dingtalk.open -DartifactId=client-sdk.spring     -Dversion=1.0.0 -Dpackaging=jar -Dfile=/d/client_sdk/client-sdk.spring-1.0.0.jar -Durl=http://127.0.0.1:8081/nexus/repository/maven-releases/ -DrepositoryId=maven-releases -DgeneratePom=true
