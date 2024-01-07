# SkyDefenderReboot

A very basic skydefender plugin still in development.
this is a beta version, so it contain a lots of bugs.
Developed for 1.20.4 minecraft version (maybe work on other version idk)

## Commands
```
/skydefender team
                  list
                  join <team>
                  leave
/skydefender settpin <name>
/skydefender settpout <name>
/skydefender resettp
/skydefender setspawn
/skydefender setbanner
/skydefender game start
```

> Other commands will be added soon

## Permissions

No prems for the moment every one can use the commands

## Config

```yaml
# define the distance between the spawn and the max distance of the attacker player can be teleported
spread_distance_from_spawn: 1000
```

## TODO

- [ ] Add a timer system
- [ ] Rework commands and add auto-completion
- [ ] Setup permissions
- [ ] Add timer for nether portal
- [ ] Add a team system for multiple attacker team and one defender team
- [ ] Add world border 

## build

```bash
mvn clean package
```

> The jar will be in the target folder
