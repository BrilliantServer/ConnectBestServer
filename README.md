# Connect-Best-Server.

---
[Made by Brilliant Team](https://discord.gg/5MHGpAFGEN "The Copyright of the entire source codes is owned by SiongSng according to Article 10 the Copyright Law of the Republic of China.")

Automatically connect to the best server in bungeecord.

## Usage

### Config

```yaml
# Which sort type to use to connect to the best server
# LeastPlayers or MostPlayers
sort: LeastPlayers
# These servers are skipped when finding for the best servers
blacklist-servers:
- test1
- test2
```

## 📃 License

This project is licensed under the GNU GPL V3.0  

## 🔴 Development

### Build

```shell
./gradle shadowJar
```

File location: `build/libs/...`

### Launch Server

```shell
./gradle buildAndLaunchServer
```
### Launch BungeeCord Server

```shell
./gradle buildAndLaunchBungeecordServer
```

## 🟠 Note

The plugin requires BungeeCord
