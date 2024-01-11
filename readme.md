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
spread_distance_from_spawn:
  min: 1000 # min distance from spawn
  max: 5000 # max distance from spawn

allow_nether_at_day: 2

worldborder:
  start_reduce_at_day: 5 # when to start reducing the worldborder
  finish_reduce_at_day: 10 # when to stop reducing the worldborder and set it to min_size
  start_radius: 1000 # from spawn this value need to be min 2x bigger than max spread_distance_from_spawn
  finish_radius: 250 # from spawn
  movement_time: 1 # how many seconds to reduce the worldborder to the next size (speed)

```

## TODO

- [ ] Add a more commands and admin commands
- [ ] Add pause system
- [ ] Add a reload command
- [ ] Setup better permissions
- [ ] Add a team system for multiple attacker team and one defender team
- [ ] Add a sky protection and remove after x days
- [ ] Add a friendly fire system
- [ ] when game is waitting if player go to far from spawn they will be teleported back to spawn
- [ ] show team on tab
- [ ] add command to load game data on fly
- [ ] compare world border with player rtp max
- [ ] fix defender respawn location

## build

```bash
mvn clean package
```

> The jar will be in the target folder
