# Connect-Best-Server

---
[Made for Brilliantw Server](https://discord.gg/5MHGpAFGEN "The Copyright of the entire source codes is owned by SiongSng according to Article 10 the Copyright Law of the Republic of China.")

輝煌伺服器中用來自動連線最少人的分流 (Bungeecord 插件)

## Usage | 使用

### Config

```yaml
# Which sort type to use to connect to the best server
# LeastPlayers or MostPlayers
sort: LeastPlayers
# These servers are skipped when finding for the best servers
blacklist-servers: [ "waitroom" ]
```

## 📃 License | 開源授權

This project is licensed under the GNU GPL V3.0  
此專案在 GNU GPL V3.0 下授權

## 🔴 Development | 開發

### Build

```shell
./gradle shadowJar
```

File location: `build/libs/...`

### Launch Server

```shell
./gradle buildAndLaunchServer
```

## 🟠 Precautions | 注意事項

The plugin requires BungeeCord

本插件需要 BungeeCord 才能運作
