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

All the things I want to add/fix is on the [issues tab](https://github.com/msterhuj/SkyDefenderReboot/issues)

And also on the code there is a lot of TODO comments

If you want other features you can create an issue and
I will see if I can add it, or you can add it yourself im open to pull requests :)

## Build

```bash
mvn clean package
```

> The jar will be in the target folder
