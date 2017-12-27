## Description

This sample plugin  show how to implement a custom view with Che. It also shows how to configure the workbench and open/hides certain views.

Read the tutorial at: https://www.eclipse.org/che/docs/plugins/parts/index.html


## How to test sample-plugin-parts plugin

### 1- Link to IDE Assembly

The plugin-parts extension is only a client-side (IDE) extension. You have to introduce the extension as a dependency in `/che/assembly/assembly-ide-war/pom.xml`.

Add:
```XML
...
<dependency>
  <groupId>org.eclipse.che.sample</groupId>
  <artifactId>che-sample-plugin-parts-ide</artifactId>
</dependency>
...
```
You can insert the dependency anywhere in the list. After you have inserted it, run `mvn sortpom:sort` and maven will order the `pom.xml`  for you.


### 2- Rebuild Eclipse Che


```Shell
# Build a new IDE.war
# This IDE web app will be bundled into the assembly
cd che/assembly/assembly-ide-war
mvn clean install

# Create a new Che assembly that includes all new server- and client-side extensions
cd assembly/assembly-main
mvn clean install
```

### 3- Run Eclipse Che

```Shell
# Start Che using the CLI with your new assembly
# Replace <local-repo> with the path to your Che repository, to use local binaries in your local image
# Replace <version> with the actual version you are working on
docker run -it --rm -v /var/run/docker.sock:/var/run/docker.sock \
                    -v <local-path>:/data \
                    -v <local-repo>:/repo \
                       eclipse/che:<version> start --debug

```


### Documentation resources

- IDE Setup: https://www.eclipse.org/che/docs/plugins/setup-che-workspace/index.html
- Building Extensions: https://www.eclipse.org/che/docs/plugins/create-and-build-extensions/index.html
- Run local Eclipse Che binaries: https://www.eclipse.org/che/docs/setup/configuration/index.html#development-mode
