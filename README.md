[![kotlin_version](https://img.shields.io/badge/kotlin-1.8.10-blueviolet?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)
[![kotlin_version](https://img.shields.io/badge/java-19-blueviolet?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)
[![minecraft_version](https://img.shields.io/badge/minecraft-1.19.4-green?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)
[![platforms](https://img.shields.io/badge/platform-spigot%7Cfabric%7Cforge-blue?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)

# SynK [WIP]

Syncrhonization plugin for [EmpireProjekt.ru](https://empireprojekt.ru)

<h4 align="center">☄️ Check out my mobile app - it will help you learn foreign words!☄️ </h4>
<p align="center">
    <a href="https://play.google.com/store/apps/details?id=com.makeevrserg.astralearner">
        <img alt="spigot" src="https://img.shields.io/badge/GooglePlay-AstraLearner-1B76CA"/>
    </a>
</p>

## Commands-Core

| Command                  | Description                          | Permission           |
|:-------------------------|:-------------------------------------|:---------------------|
| `/synk server <server>`  | Safely move to server named <server> | synk.server.<server> |
| `/synk history <player>` | See player inventory history         | synk.history         |
| `/synk reload`           | Reload plugin                        | synk.reload          |

## Directory structure

    ├── build-logic         # Build components
    ├── modules             
    │   ├── local-api       # Local api to store player data
    │   ├── models          # Shared models between fabric/forge/spigot
    │   └── remote-api      # Remote SQL api to store player data
    └── plugin              # Spigot template mod

## Build jar executables

    $ ./gradlew :plugin:shadowJar          # assemble the plugin .jar

Also, checkout [AstraLearner](https://play.google.com/store/apps/details?id=com.makeevrserg.astralearner) - it will help
you to learn foreign words easily!