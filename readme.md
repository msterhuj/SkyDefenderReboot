# SkyDefenderReboot

A very basic skydefender plugin still in development.
This is a beta version, so it contain a lots of bugs.
But the plugin is playable if its commited on master branch.
Developed for 1.20.4 minecraft version (maybe work on other version idk)

## Commands
```
/skydefender team
                  list
                  join <team>
                  leave
/skydefender teleporter 
                        setin <name>
                        setout <name>
                        reset <name>
/skydefender setspawn
/skydefender setbanner
/skydefender game
                  start
                  reset
```

> Other commands will be added soon

## Permissions

All commands can be used by all players except the /skydefender game * commands

## Config

```yaml
# define the distance between the spawn and the max distance of the attacker player can be teleported
spread_distance_from_spawn: 1000
```

## TODO

- [x] Add a timer system
- [x] Rework commands and add auto-completion
- [ ] Add a more commands and admin commands
- [ ] Add add pause system
- [ ] Add a reload command
- [ ] Setup better permissions
- [ ] Add timer for nether portal
- [ ] Add a team system for multiple attacker team and one defender team
- [ ] Add world border 
- [ ] Add a sky protection and remove after x days

## build

```bash
mvn clean package
```

> The jar will be in the target folder
