# Chord Demo Project for CSCI 6421 GWU

## How To Run

```
gradle buildApp
java -jar chord-node-1.0.0.RELEASE.jar --spring.profiles.active=default --chord.node-name="john" --chord.node-port="18001" --chord.finger-ring-size-bits="7" --chord.bootstrapping-node="true"
```