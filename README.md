# Spring Native



## Building a Native Image using Native Build Tools

```shell
sdk install java 22.3.r17-nik
sdk use java 22.3.r17-nik

```

You need to make sure that you’re using `spring-boot-starter-parent` in order to inherit the native profile and that the `org.graalvm.buildtools:native-maven-plugin` plugin is used.

With the native profile active, you can invoke the native:compile goal to trigger native-image compilation.
```shell
mvn -Pnative native:compile

```
The native image executable can be found in the target directory.

You can now start the application by running it directly:
```shell
target/spring-native

```
